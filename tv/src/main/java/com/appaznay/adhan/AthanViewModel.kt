package com.appaznay.adhan

import android.content.Context
import android.icu.util.IslamicCalendar
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.random.Random


class AthanViewModel : ViewModel() {


    lateinit var hadithArray: Array<String>
    lateinit var baadaSalatArray: Array<String>

    var isHadith = false


    fun accederAuFichierRaw(context: Context) {
        hadithArray =
            context.assets.open("ahadith/hadith.csv").bufferedReader().use { it.readLines() }
                .toTypedArray()
        baadaSalatArray =
            context.assets.open("ahadith/bada_salat.csv").bufferedReader().use { it.readLines() }
                .toTypedArray()
          isHadith = true
    }

    fun GetDataSetting(context: Context) {
        val sharedPref =
            context.getSharedPreferences("AthanAppAznay", Context.MODE_PRIVATE) ?: return
        val sobh = sharedPref.getString(
            "sobh",
            "${Salawate.SALATSOBH.salat.nameSalate}:${Salawate.SALATSOBH.salat.timeAthanHour}:${Salawate.SALATSOBH.salat.timeAthanMin}:${Salawate.SALATSOBH.salat.timeIqama}:${Salawate.SALATSOBH.salat.isAthan}"
        )
        val tohr = sharedPref.getString(
            "tohr",
            "${Salawate.SALATTOHR.salat.nameSalate}:${Salawate.SALATTOHR.salat.timeAthanHour}:${Salawate.SALATTOHR.salat.timeAthanMin}:${Salawate.SALATTOHR.salat.timeIqama}:${Salawate.SALATTOHR.salat.isAthan}"
        )
        val asr = sharedPref.getString(
            "asr",
            "${Salawate.SALATASR.salat.nameSalate}:${Salawate.SALATASR.salat.timeAthanHour}:${Salawate.SALATASR.salat.timeAthanMin}:${Salawate.SALATASR.salat.timeIqama}:${Salawate.SALATASR.salat.isAthan}"
        )
        val maghreb = sharedPref.getString(
            "maghreb",
            "${Salawate.SALATMAGHREB.salat.nameSalate}:${Salawate.SALATMAGHREB.salat.timeAthanHour}:${Salawate.SALATMAGHREB.salat.timeAthanMin}:${Salawate.SALATMAGHREB.salat.timeIqama}:${Salawate.SALATMAGHREB.salat.isAthan}"
        )
        val icha = sharedPref.getString(
            "icha",
            "${Salawate.SALATICHA.salat.nameSalate}:${Salawate.SALATICHA.salat.timeAthanHour}:${Salawate.SALATICHA.salat.timeAthanMin}:${Salawate.SALATICHA.salat.timeIqama}:${Salawate.SALATICHA.salat.isAthan}"
        )
        val chorok = sharedPref.getString(
            "chorok",
            "${Salawate.SALATCHOROK.salat.nameSalate}:${Salawate.SALATCHOROK.salat.timeAthanHour}:${Salawate.SALATCHOROK.salat.timeAthanMin}:${Salawate.SALATCHOROK.salat.timeIqama}:${Salawate.SALATCHOROK.salat.isAthan}"
        )
        val jomo3a = sharedPref.getString(
            "jomo3a",
            "${Salawate.SALATJOMO3A.salat.nameSalate}:${Salawate.SALATJOMO3A.salat.timeAthanHour}:${Salawate.SALATJOMO3A.salat.timeAthanMin}:${Salawate.SALATJOMO3A.salat.timeIqama}:${Salawate.SALATJOMO3A.salat.isAthan}"
        )

        _salateSobh.value = listing(sobh!!)
        _salateChorok.value = listing(chorok!!)
        _salateTohr.value = listing(tohr!!)
        _salateAsr.value = listing(asr!!)
        _salateMaghreb.value = listing(maghreb!!)
        _salateIcha.value = listing(icha!!)
        _salatejomo3a.value = listing(jomo3a!!)

        val ecronNoir = sharedPref.getString(
            "ecronNoir",
            "${Salawate.SALATJOMO3A.salat.nameSalate}:${Salawate.SALATJOMO3A.salat.timeAthanHour}:${Salawate.SALATJOMO3A.salat.timeAthanMin}:${Salawate.SALATJOMO3A.salat.timeIqama}:${Salawate.SALATJOMO3A.salat.isAthan}"
        )
        val imageteste = sharedPref.getString("imageIndex", "12")
        _imageIndex.value = imageteste!!.toInt()
        val couleur = sharedPref.getString(
            "couleur",
            "${0x80356BF7}:${0x809AFF9F}:${0x80FFFFFF}:${0xFFFFFFFF}"
        )

        var teste = ecronNoir!!.split(":")
        isEcranNoir = teste.get(0).toBoolean()
        ecranNoir = teste.get(1).toInt()


/*
        teste = couleur!!.split(":")
          _colorBoxDefault.value = Color(teste.get(0).toInt())
          _colorBoxNext.value = Color(teste.get(1).toInt())
         colorBack= Color(teste.get(2).toInt())
          _colorFont.value = Color(teste.get(3).toInt())

 */

        _imageFond.value = listImage[_imageIndex.value]

        Log.d("assets", "GetDataSetting")
    }

    fun listing(valeur: String): SalateTime {
        val teste = valeur.split(":")

        return SalateTime(
            nameSalate = teste[0],
            timeAthanHour = teste[1],
            timeAthanMin = teste[2],
            timeIqama = teste[3],
            isAthan = teste[4].toBoolean(),
        )
    }

    fun SaveSetting(context: Context) {

        timeSobh =
            convertirStringEnCalendar("${_salateSobh.value.timeAthanHour}:${_salateSobh.value.timeAthanMin}")
        timeTohr =
            convertirStringEnCalendar("${_salateTohr.value.timeAthanHour}:${_salateTohr.value.timeAthanMin}")
        timeAsr =
            convertirStringEnCalendar("${_salateAsr.value.timeAthanHour}:${_salateAsr.value.timeAthanMin}")
        timeMaghreb =
            convertirStringEnCalendar("${_salateMaghreb.value.timeAthanHour}:${_salateMaghreb.value.timeAthanMin}")
        timeIcha =
            convertirStringEnCalendar("${_salateIcha.value.timeAthanHour}:${_salateIcha.value.timeAthanMin}")
        timeChorok =
            convertirStringEnCalendar("${_salateChorok.value.timeAthanHour}:${_salateChorok.value.timeAthanMin}")
        timeJomo3a =
            convertirStringEnCalendar("${_salatejomo3a.value.timeAthanHour}:${_salatejomo3a.value.timeAthanMin}")

        val sharedPref = context.getSharedPreferences("AthanAppAznay", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(
                "sobh",
                "${_salateSobh.value.nameSalate}:${_salateSobh.value.timeAthanHour}:${_salateSobh.value.timeAthanMin}:${_salateSobh.value.timeIqama}:${_salateSobh.value.isAthan}"
            )
            putString(
                "tohr",
                "${_salateTohr.value.nameSalate}:${_salateTohr.value.timeAthanHour}:${_salateTohr.value.timeAthanMin}:${_salateTohr.value.timeIqama}:${_salateTohr.value.isAthan}"
            )
            putString(
                "asr",
                "${_salateAsr.value.nameSalate}:${_salateAsr.value.timeAthanHour}:${_salateAsr.value.timeAthanMin}:${_salateAsr.value.timeIqama}:${_salateAsr.value.isAthan}"
            )
            putString(
                "maghreb",
                "${_salateMaghreb.value.nameSalate}:${_salateMaghreb.value.timeAthanHour}:${_salateMaghreb.value.timeAthanMin}:${_salateMaghreb.value.timeIqama}:${_salateMaghreb.value.isAthan}"
            )
            putString(
                "icha",
                "${_salateIcha.value.nameSalate}:${_salateIcha.value.timeAthanHour}:${_salateIcha.value.timeAthanMin}:${_salateIcha.value.timeIqama}:${_salateIcha.value.isAthan}"
            )
            putString(
                "chorok",
                "${_salateChorok.value.nameSalate}:${_salateChorok.value.timeAthanHour}:${_salateChorok.value.timeAthanMin}:${_salateChorok.value.timeIqama}:${_salateChorok.value.isAthan}"
            )
            putString(
                "jomo3a",
                "${_salatejomo3a.value.nameSalate}:${_salatejomo3a.value.timeAthanHour}:${_salatejomo3a.value.timeAthanMin}:${_salatejomo3a.value.timeIqama}:${_salatejomo3a.value.isAthan}"
            )
            apply()
        }
    }

    fun SaveAffichage(context: Context) {
        _imageFond.value = listImage[_imageIndex.value]
        val sharedPref = context.getSharedPreferences("AthanAppAznay", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("ecronNoir", "${isEcranNoir}:${ecranNoir}")
            putString("imageIndex", "${_imageIndex.value}")
            putString(
                "couleur",
                "${_colorBoxDefault.value}:${_colorBoxNext.value}:${colorBack}:${_colorFont.value}"
            )
            apply()
        }

        Log.d("assets", "SaveAffichage")
    }

    var colorBack = Color.Green
    var isEcranNoir = false
    var ecranNoir = 15

    private val _colorBoxNext = MutableStateFlow(Color(0xCCBA0DE2))
    val colorBoxNext: StateFlow<Color> = _colorBoxNext
    private val _colorBoxDefault = MutableStateFlow(Color(0xCC356BF7))
    val colorBoxDefault: StateFlow<Color> = _colorBoxDefault
    private val _colorFont = MutableStateFlow(Color(0xFFFFFFFF))
    val colorFont: StateFlow<Color> = _colorFont

    private var timerJob: Job? = null
    private val _timer = MutableStateFlow("")
    val timer: StateFlow<String> = _timer

    private var dateJob: Job? = null
    private val _date = MutableStateFlow("")
    val date: StateFlow<String> = _date

    private var colorJob: Job? = null


    var _ischange = false
    var _isBadaSalat = false

    private val _hadith = MutableStateFlow("")
    val hadith: StateFlow<String> = _hadith
    private val _imageIndex = MutableStateFlow(0)
    val imageIndex: StateFlow<Int> = _imageIndex


    private val _nextTime = MutableStateFlow("")
    val nextTime: StateFlow<String> = _nextTime
    private val _athanTime = MutableStateFlow("notTime")
    val athanTime: StateFlow<String> = _athanTime

    private val _imageFond = MutableStateFlow(R.drawable.image_12)
    val imageFond: StateFlow<Int> = _imageFond.asStateFlow()

    private var pageJob: Job? = null
    private val _PageChange = MutableStateFlow(true)
    val pageChange: StateFlow<Boolean> = _PageChange

    private val _salateSobh = MutableStateFlow(Salawate.SALATSOBH.salat)
    val salateSobh: StateFlow<SalateTime> = _salateSobh
    private val _salateTohr = MutableStateFlow(Salawate.SALATTOHR.salat)
    val salateTohr: StateFlow<SalateTime> = _salateTohr
    private val _salateAsr = MutableStateFlow(Salawate.SALATASR.salat)
    val salateAsr: StateFlow<SalateTime> = _salateAsr
    private val _salateMaghreb = MutableStateFlow(Salawate.SALATMAGHREB.salat)
    val salateMaghreb: StateFlow<SalateTime> = _salateMaghreb
    private val _salateIcha = MutableStateFlow(Salawate.SALATICHA.salat)
    val salateIcha: StateFlow<SalateTime> = _salateIcha
    private val _salateChorok = MutableStateFlow(Salawate.SALATCHOROK.salat)
    val salateChorok: StateFlow<SalateTime> = _salateChorok
    private val _salatejomo3a = MutableStateFlow(Salawate.SALATJOMO3A.salat)
    val salatejomo3a: StateFlow<SalateTime> = _salatejomo3a

    private val _messageAthan = MutableStateFlow("")
    val messageAthan: StateFlow<String> = _messageAthan

    private var _alarm = MutableStateFlow(Calendar.getInstance())
    val alarm: StateFlow<Calendar> = _alarm
    private val _reste = MutableStateFlow("")
    val reste: StateFlow<String> = _reste
    var _isSalate = false

    var index = 0

    private val _nameDay = MutableStateFlow(1)
    val nameDay: StateFlow<Int> = _nameDay

    private var delai = mutableIntStateOf(0)


    private var timeSobh =
        convertirStringEnCalendar("${_salateSobh.value.timeAthanHour}:${_salateSobh.value.timeAthanMin}")
    private var timeTohr =
        convertirStringEnCalendar("${_salateTohr.value.timeAthanHour}:${_salateTohr.value.timeAthanMin}")
    private var timeAsr =
        convertirStringEnCalendar("${_salateAsr.value.timeAthanHour}:${_salateAsr.value.timeAthanMin}")
    private var timeMaghreb =
        convertirStringEnCalendar("${_salateMaghreb.value.timeAthanHour}:${_salateMaghreb.value.timeAthanMin}")
    private var timeIcha =
        convertirStringEnCalendar("${_salateIcha.value.timeAthanHour}:${_salateIcha.value.timeAthanMin}")
    private var timeChorok =
        convertirStringEnCalendar("${_salateChorok.value.timeAthanHour}:${_salateChorok.value.timeAthanMin}")
    private var timeJomo3a =
        convertirStringEnCalendar("${_salatejomo3a.value.timeAthanHour}:${_salatejomo3a.value.timeAthanMin}")


    fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                val time = Calendar.getInstance()
                val formatter = SimpleDateFormat("HH:mm:ss", Locale.ROOT)
                _timer.value = formatter.format(time.time)


                val athanTimeSalate = when (_nextTime.value) {
                    "الصبح" -> timeSobh
                    "الشروق" -> timeChorok
                    "الظهر" -> timeTohr
                    "العصر" -> timeAsr
                    "المغرب" -> timeMaghreb
                    "العشاء" -> timeIcha
                    else -> timeJomo3a
                }


                if (!_isSalate) {

                    val teste = Calendar.getInstance()
                    teste.set(Calendar.HOUR_OF_DAY, athanTimeSalate.get(Calendar.HOUR_OF_DAY))
                    teste.set(Calendar.MINUTE, athanTimeSalate.get(Calendar.MINUTE))
                    teste.set(Calendar.SECOND, 0)
                    teste.add(Calendar.HOUR_OF_DAY, -time.get(Calendar.HOUR_OF_DAY))
                    teste.add(Calendar.MINUTE, -time.get(Calendar.MINUTE))
                    teste.add(Calendar.SECOND, -time.get(Calendar.SECOND))



                    _reste.value = "${timeFormat(teste)} ${_nextTime.value}  بعد"

                    nextAthanName()

                } else {

                    val teste = Calendar.getInstance()
                    teste.set(Calendar.HOUR_OF_DAY, _alarm.value.get(Calendar.HOUR_OF_DAY))
                    teste.set(Calendar.MINUTE, _alarm.value.get(Calendar.MINUTE))
                    teste.set(Calendar.SECOND, 0)
                    teste.add(Calendar.HOUR_OF_DAY, -time.get(Calendar.HOUR_OF_DAY))
                    teste.add(Calendar.MINUTE, -time.get(Calendar.MINUTE))
                    teste.add(Calendar.SECOND, -time.get(Calendar.SECOND))

                    val testeminute = if (teste.get(Calendar.MINUTE) > 9) {
                        "${teste.get(Calendar.MINUTE)}"
                    } else {
                        "0${teste.get(Calendar.MINUTE)}"
                    }

                    val testeseconde = if (teste.get(Calendar.SECOND) > 9) {
                        "${teste.get(Calendar.SECOND)}"
                    } else {
                        "0${teste.get(Calendar.SECOND)}"
                    }

                    _reste.value = "$testeminute:$testeseconde الإقامة  بعد"

                    delai.intValue = delai.intValue + 1

                    if (delai.intValue == 30) {
                        _athanTime.value = "notTime"
                    }

                    if ((teste.get(Calendar.MINUTE) == 0) && (teste.get(Calendar.SECOND) == 45)) {
                        _messageAthan.value = "الإقامة بعد"
                        _athanTime.value = "Time"
                    }

                    if ((teste.get(Calendar.MINUTE) == 0) && (teste.get(Calendar.SECOND) == 0)) {
                        _messageAthan.value = "أقيموا الصلاة يرحمكم الله"
                        viewModelScope.launch {
                            delay(15000)
                            _messageAthan.value = "ecran_noir"
                             delay(15 * 60 * 1000)
                            _isSalate = false
                            _athanTime.value = "notTime"
                            _isBadaSalat = true
                        }
                    }
                }
                delay(1000) // Update every second
            }
        }
    }

    private fun timeFormat(time:Calendar): String {
        val testehour = if (time.get(Calendar.HOUR_OF_DAY) > 9) {
            "${time.get(Calendar.HOUR_OF_DAY)}"
        } else {
            "0${time.get(Calendar.HOUR_OF_DAY)}"
        }

        val testeminute = if (time.get(Calendar.MINUTE) > 9) {
            "${time.get(Calendar.MINUTE)}"
        } else {
            "0${time.get(Calendar.MINUTE)}"
        }

        val testeseconde = if (time.get(Calendar.SECOND) > 9) {
            "${time.get(Calendar.SECOND)}"
        } else {
            "0${time.get(Calendar.SECOND)}"
        }
        return  "$testehour:$testeminute:$testeseconde"
    }

    private fun nextAthanName() {
        val time = Calendar.getInstance()
        val nowFormatter = SimpleDateFormat("HH:mm", Locale.ROOT)
        val mmmaintenant = nowFormatter.format(time.time)
        val maintenant = convertirStringEnCalendar(mmmaintenant)


        val teste = timeFormat(time)



        if (time.get(Calendar.DAY_OF_WEEK) == 6) {
        _athanTime.value = when (teste) {
            timeFormat(timeSobh) -> _salateSobh.value.nameSalate
            timeFormat(timeJomo3a) -> _salatejomo3a.value.nameSalate
            timeFormat(timeAsr) -> _salateAsr.value.nameSalate
            timeFormat(timeIcha) -> _salateIcha.value.nameSalate
            timeFormat(timeMaghreb) -> _salateMaghreb.value.nameSalate
            timeFormat(timeChorok) -> _salateChorok.value.nameSalate
            else -> "notTime"
        }} else {
            _athanTime.value = when (teste) {
                timeFormat(timeSobh) -> _salateSobh.value.nameSalate
                timeFormat(timeTohr) -> _salateTohr.value.nameSalate
                timeFormat(timeAsr) -> _salateAsr.value.nameSalate
                timeFormat(timeIcha) -> _salateIcha.value.nameSalate
                timeFormat(timeMaghreb) -> _salateMaghreb.value.nameSalate
                timeFormat(timeChorok) -> _salateChorok.value.nameSalate
                else -> "notTime"
        }}

        delai.intValue = 0

        if (_athanTime.value != "notTime") {
            _messageAthan.value = "الأذان"
        } else {
            if (time.get(Calendar.DAY_OF_WEEK) == 6) {
                if (maintenant.before(timeSobh)) _nextTime.value = "الصبح"
                else if (maintenant.before(timeChorok) && maintenant.after(timeSobh)) _nextTime.value =
                    "الشروق"
                else if (maintenant.before(timeJomo3a) && maintenant.after(timeChorok)) _nextTime.value =
                    "الجمعة"
                else if (maintenant.before(timeAsr) && maintenant.after(timeJomo3a)) _nextTime.value =
                    "العصر"
                else if (maintenant.before(timeMaghreb) && maintenant.after(timeAsr)) _nextTime.value =
                    "المغرب"
                else if (maintenant.before(timeIcha) && maintenant.after(timeMaghreb)) _nextTime.value =
                    "العشاء"
                else if (maintenant.after(timeIcha)) _nextTime.value = "noSalate"
            } else {
                if (maintenant.before(timeSobh)) _nextTime.value = "الصبح"
                else if (maintenant.before(timeChorok) && maintenant.after(timeSobh)) _nextTime.value =
                    "الشروق"
                else if (maintenant.before(timeTohr) && maintenant.after(timeChorok)) _nextTime.value =
                    "الظهر"
                else if (maintenant.before(timeAsr) && maintenant.after(timeTohr)) _nextTime.value =
                    "العصر"
                else if (maintenant.before(timeMaghreb) && maintenant.after(timeAsr)) _nextTime.value =
                    "المغرب"
                else if (maintenant.before(timeIcha) && maintenant.after(timeMaghreb)) _nextTime.value =
                    "العشاء"
                else if (maintenant.after(timeIcha)) _nextTime.value = "noSalate"
            }

        }



    }

    fun startDater() {
        dateJob?.cancel() // Cancel previous job if exists
        dateJob = viewModelScope.launch {
            while (true) {
                val date = Calendar.getInstance()
                val formatter =
                    SimpleDateFormat("yyyy EEEE dd MMMM", Locale.forLanguageTag("ar-DZ"))
                _ischange = !_ischange
                _date.value = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    if (_ischange) {
                        formatter.format(date.time)
                    } else {
                        getCurrentIslamicDate()
                    }
                } else {
                    formatter.format(date.time)
                }
                delay(5000)


                timeSobh =
                    convertirStringEnCalendar("${_salateSobh.value.timeAthanHour}:${_salateSobh.value.timeAthanMin}")
                timeTohr =
                    convertirStringEnCalendar("${_salateTohr.value.timeAthanHour}:${_salateTohr.value.timeAthanMin}")
                timeAsr =
                    convertirStringEnCalendar("${_salateAsr.value.timeAthanHour}:${_salateAsr.value.timeAthanMin}")
                timeMaghreb =
                    convertirStringEnCalendar("${_salateMaghreb.value.timeAthanHour}:${_salateMaghreb.value.timeAthanMin}")
                timeIcha =
                    convertirStringEnCalendar("${_salateIcha.value.timeAthanHour}:${_salateIcha.value.timeAthanMin}")
                timeChorok =
                    convertirStringEnCalendar("${_salateChorok.value.timeAthanHour}:${_salateChorok.value.timeAthanMin}")
                timeJomo3a =
                    convertirStringEnCalendar("${_salatejomo3a.value.timeAthanHour}:${_salatejomo3a.value.timeAthanMin}")
                _nameDay.value = date.get(Calendar.DAY_OF_WEEK)

            }
        }
    }

    fun startPageChange() {
        pageJob?.cancel() // Cancel previous job if exists
        pageJob = viewModelScope.launch {
            while (true) {
                if (_isBadaSalat) {
                    _PageChange.value = false
                    if (isHadith) {
                        _hadith.value = baadaSalatArray[index]
                        index++
                          delay(15 * 1000)

                        if (index > (baadaSalatArray.size - 1)) {
                            index = 0
                            _isBadaSalat = false
                        }
                    }
                } else {
                    index = 0
                    _PageChange.value = !_PageChange.value
                    if (isHadith) _hadith.value = hadithArray[Random.nextInt(0, hadithArray.size - 1)]
                    if (_PageChange.value) delay(60 * 1000) else delay(25 * 1000)
                }
            }
        }
    }

    fun convertirStringEnCalendar(dateStr: String): Calendar {

        val teste = dateStr.split((":"))
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, teste[0].toInt())
        calendar.set(Calendar.MINUTE, teste[1].toInt())
        calendar.set(Calendar.SECOND, 0)

        return calendar
    }


    fun incHour(item: SalateTime) {
        var timeAthanHour = (item.timeAthanHour.toInt() + 1).toString()
        if (timeAthanHour.toInt() >= 24) timeAthanHour = "0"
        if (timeAthanHour.toInt() <= 9) timeAthanHour = "0${timeAthanHour}"
        val salate = whatSalate(item.nameSalate)
        salate.update { currentState ->
            currentState.copy(
                timeAthanHour = timeAthanHour
            )
        }
    }

    fun decHour(item: SalateTime) {
        var timeAthanHour = (item.timeAthanHour.toInt() - 1).toString()
        if (timeAthanHour.toInt() < 0) timeAthanHour = "23"
        if (timeAthanHour.toInt() <= 9) timeAthanHour = "0${timeAthanHour}"
        val salate = whatSalate(item.nameSalate)
        salate.update { currentState ->
            currentState.copy(
                timeAthanHour = timeAthanHour
            )
        }
    }

    fun incMin(item: SalateTime) {
        var timeAthanMin = (item.timeAthanMin.toInt() + 1).toString()
        if (timeAthanMin.toInt() >= 60) timeAthanMin = "0"
        if (timeAthanMin.toInt() <= 9) timeAthanMin = "0${timeAthanMin}"
        val salate = whatSalate(item.nameSalate)
        salate.update { currentState ->
            currentState.copy(
                timeAthanMin = timeAthanMin
            )
        }
    }

    fun decMin(item: SalateTime) {
        var timeAthanMin = (item.timeAthanMin.toInt() - 1).toString()
        if (timeAthanMin.toInt() < 0) timeAthanMin = "59"
        if (timeAthanMin.toInt() <= 9) timeAthanMin = "0${timeAthanMin}"
        val salate = whatSalate(item.nameSalate)
        salate.update { currentState ->
            currentState.copy(
                timeAthanMin = timeAthanMin
            )
        }

    }

    fun incAthan(item: SalateTime) {
        var timeIqama = (item.timeIqama.toInt() + 1).toString()
        if (timeIqama.toInt() >= 60) timeIqama = "0"
        if (timeIqama.toInt() <= 9) timeIqama = "0${timeIqama}"
        val salate = whatSalate(item.nameSalate)
        salate.update { currentState ->
            currentState.copy(
                timeIqama = timeIqama
            )
        }

    }

    fun decAthan(item: SalateTime) {
        var timeIqama = (item.timeIqama.toInt() - 1).toString()
        if (timeIqama.toInt() < 0) timeIqama = "59"
        if (timeIqama.toInt() <= 9) timeIqama = "0${timeIqama}"

        val salate = whatSalate(item.nameSalate)
        salate.update { currentState ->
            currentState.copy(timeIqama = timeIqama)
        }
    }

    fun checkAthan(item: SalateTime) {
        val isAthan = !item.isAthan

    }


    fun whatSalate(name: String): MutableStateFlow<SalateTime> {
        val salate = when (name) {
            "الصبح" -> _salateSobh
            "الظهر" -> _salateTohr
            "العصر" -> _salateAsr
            "المغرب" -> _salateMaghreb
            "العشاء" -> _salateIcha
            "الشروق" -> _salateChorok
            else -> _salatejomo3a
        }
        return salate

    }

    val listImage = intArrayOf(
        R.drawable.image_1,
        R.drawable.image_2,
        R.drawable.image_3,
        R.drawable.image_4,
        R.drawable.image_5,
        R.drawable.image_6,
        R.drawable.image_7,
        R.drawable.image_8,
        R.drawable.image_9,
        R.drawable.image_10,
        R.drawable.image_11,
        R.drawable.image_12,
        R.drawable.image_13
    )


    fun imageIndexPlus() {
        if (_imageIndex.value == 12) _imageIndex.value = 0 else _imageIndex.value++
    }

    fun imageIndexMoins() {
        if (_imageIndex.value == 0) _imageIndex.value = 12 else _imageIndex.value--
    }


    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
        dateJob?.cancel()
        colorJob?.cancel()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getCurrentIslamicDate(): String {
        val islamicCalendar = IslamicCalendar()
        val formatter = SimpleDateFormat("EEEE", Locale.forLanguageTag("ar-DZ"))
        val jour = formatter.format(islamicCalendar.time)

        val islamicDay = islamicCalendar.get(IslamicCalendar.DAY_OF_MONTH)
        val islamicMonth = islamicCalendar.get(IslamicCalendar.MONTH) + 1
        val islamicYear = islamicCalendar.get(IslamicCalendar.YEAR)

        val moisIslamic = when (islamicMonth) {
            1 -> "محرم"
            2 -> "صفر"
            3 -> "ربيع الأول"
            4 -> "ربيع الثَّانِي"
            5 -> "جمادى الأولى"
            6 -> "جمادى الثَّانِي"
            7 -> "رجب"
            8 -> "شعبان"
            9 -> "رمضان"
            10 -> "شوال"
            11 -> "ذو القعدة"
            else -> "ذو الحجة"
        }

        return "$jour $islamicDay $moisIslamic $islamicYear"
    }

    fun setAlarm() {

        if (!_isSalate) {
            val time = Calendar.getInstance()
            val teste = when (_athanTime.value) {
                "الصبح" -> _salateSobh.value.timeIqama
                "الظهر" -> _salateTohr.value.timeIqama
                "العصر" -> _salateAsr.value.timeIqama
                "المغرب" -> _salateMaghreb.value.timeIqama
                "العشاء" -> _salateIcha.value.timeIqama
                "الجمعة" -> _salatejomo3a.value.timeIqama
                else -> _salateChorok.value.timeIqama
            }
            time.add(Calendar.MINUTE, teste.toInt())
            _alarm.value = time
            _isSalate = true
        }
    }

    fun setColor(colorBoxDefault: Color, colorBoxNext: Color, colorFont: Color) {

        _colorBoxDefault.value = colorBoxDefault
        _colorBoxNext.value = colorBoxNext
        _colorFont.value = colorFont


        Log.d("assets", "${_colorFont.value.toArgb()}")

    }

}