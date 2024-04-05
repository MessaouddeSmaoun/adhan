package com.appaznay.adhan

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.NativeKeyEvent
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appaznay.adhan.ui.theme.AdhanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContent {


            AdhanTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), shape = RectangleShape
                ) {
                    Greeting()

                }
            }
        }
    }
}


@Composable
fun Greeting(timerViewModel: AthanViewModel = viewModel()) {



    val athanTime by timerViewModel.athanTime.collectAsState()

    val pageChange by timerViewModel.pageChange.collectAsState()


    val imageFond by timerViewModel.imageFond.collectAsState()

    LaunchedEffect(Unit) {
        timerViewModel.startTimer()
        timerViewModel.startDater()
        timerViewModel.startPageChange()
    }



    var screen by remember { mutableStateOf("") }



    var backgroundColor2 by remember { mutableStateOf(Color(0xFFBBB9B9)) }
    var backgroundColor3 by remember { mutableStateOf(Color(0xFFBBB9B9)) }
    var backgroundColor4 by remember { mutableStateOf(Color(0xFFBBB9B9)) }
    val nextFocus = FocusRequester()
    val beforeFocus = FocusRequester()

    var isFocusable by remember { mutableStateOf(true) }
    var widthMEnu by remember { mutableIntStateOf(0) }



    @Composable
    fun FilterChipA() {

        Icon( Icons.Outlined.MoreVert, contentDescription = "",
            modifier = Modifier
                .onFocusChanged {
                    if (it.isFocused) Log.d("teste", "affichase focused")
                }
                .onKeyEvent { event ->
                    if (event.type == KeyEventType.KeyDown && ((event.key == Key.Enter) || (event.nativeKeyEvent.action == NativeKeyEvent.KEYCODE_DPAD_CENTER))) {

                          isFocusable = true
                          widthMEnu = 240
                          beforeFocus.freeFocus()
                          nextFocus.requestFocus()

                        true
                    } else {
                        false
                    }
                }
                .focusRequester(beforeFocus)
                .focusable())


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 10.dp)
        ) {


            if(!timerViewModel.isHadith){
                val context = LocalContext.current
                timerViewModel.accederAuFichierRaw(context)
                timerViewModel.GetDataSetting(context)
            }

            if(athanTime == "notTime") {
                when (screen) {
                    "Setting" -> SettingPage(onExitClick = { screen = "" })
                    "Affichage" -> Affichage(onExitClick = { screen = "" })
                    "About" -> InfoPage(onItemClick = { screen = "" })
                    else -> {
                        if (pageChange) {
                            Clock()
                        } else {
                            Hadith()
                        }
                    }
                }
            } else {
                timerViewModel.setAlarm()
                AthanPage() }
        }

    }



    @Composable
    fun FilterChipC() {
        Column(modifier = Modifier
            .fillMaxHeight()
            .background(Color(0x39FFFFFF))
            .width(widthMEnu.dp),
            verticalArrangement = Arrangement.Center) {
            Row(
                modifier = Modifier
                    .focusRequester(nextFocus)
                    .onFocusChanged {
                        backgroundColor3 = if (it.isFocused) {
                            Log.d("teste", "Setting focused")
                            Color.Red
                        } else Color.Blue
                    }
                    .onKeyEvent { event ->
                        if (event.type == KeyEventType.KeyDown && ((event.key == Key.Enter) || (event.nativeKeyEvent.action == NativeKeyEvent.KEYCODE_DPAD_UP))) {
                            screen = "Setting"
                            isFocusable = false
                            widthMEnu = 0
                            nextFocus.freeFocus()
                            beforeFocus.requestFocus()
                            Log.d("teste", "Setting.Enter $isFocusable")

                            true
                        } else {
                            false
                        }
                    }
                    .focusable(isFocusable)
                    .background(backgroundColor3)
                    .drawBehind {
                        drawRoundRect(
                            backgroundColor3, cornerRadius = CornerRadius(10.dp.toPx())
                        )
                    },
            ) {

                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = stringResource(id = R.string.setting_page)
                )
                Spacer(modifier = Modifier.width(10.dp))


                Text(
                    text = stringResource(id = R.string.setting_page),
                    style = TextStyle(
                        fontSize = 23.sp,
                        color = Color.Black,

                        )
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier
                    .onFocusChanged {
                        backgroundColor2 = if (it.isFocused) {
                            Log.d("teste", "affichage focused")
                            Color.Red
                        } else Color.Blue
                    }
                    .onKeyEvent { event ->
                        if (event.type == KeyEventType.KeyDown && ((event.key == Key.Enter) || (event.nativeKeyEvent.action == NativeKeyEvent.KEYCODE_DPAD_CENTER))) {
                            screen = "Affichage"
                            isFocusable = false
                            widthMEnu = 0
                            nextFocus.freeFocus()
                            beforeFocus.requestFocus()
                            Log.d("teste", "Affichage.Enter $isFocusable")

                            true
                        } else {
                            false
                        }
                    }
                    .focusable(isFocusable)
                    .background(backgroundColor2)
                    .drawBehind {
                        drawRoundRect(
                            backgroundColor2, cornerRadius = CornerRadius(10.dp.toPx())
                        )
                    },
            ) {

                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = stringResource(id = R.string.affichage_page)
                )
                Spacer(modifier = Modifier.width(10.dp))


                Text(
                    text = stringResource(id = R.string.affichage_page),
                    style = TextStyle(
                        fontSize = 23.sp,
                        color = Color.Black,

                        )
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier

                    .onFocusChanged {
                        backgroundColor4 = if (it.isFocused) {
                            Log.d("teste", "About focused")
                            Color.Red
                        } else Color.Blue
                    }
                    .onKeyEvent { event ->
                        if (event.type == KeyEventType.KeyDown && ((event.key == Key.Enter) || (event.nativeKeyEvent.action == NativeKeyEvent.KEYCODE_DPAD_CENTER))) {
                            screen = "About"
                            isFocusable = false
                            widthMEnu = 0
                            nextFocus.freeFocus()
                            beforeFocus.requestFocus()
                            Log.d("teste", "About.Enter $isFocusable")

                            true
                        } else {
                            false
                        }
                    }
                    .focusable(isFocusable)
                    .background(backgroundColor4)
                    .drawBehind {
                        drawRoundRect(
                            backgroundColor4, cornerRadius = CornerRadius(10.dp.toPx())
                        )
                    },
            ) {

                Icon(imageVector = Icons.Outlined.Info, contentDescription = "About")
                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "About",
                    style = TextStyle(
                        fontSize = 23.sp,
                        color = Color.Black,
                    )
                )
            }
        }
    }


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier

            .fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .focusGroup()
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.image_12),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            FilterChipA()
            FilterChipC()
        }


    }


}

