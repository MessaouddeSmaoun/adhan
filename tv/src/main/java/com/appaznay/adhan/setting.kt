package com.appaznay.adhan

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.NativeKeyEvent
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SettingPage(
    onExitClick: () -> Unit = {},
    timerViewModel: AthanViewModel = viewModel()
) {




    val salateSobh by timerViewModel.salateSobh.collectAsState()
    val salateTohr by timerViewModel.salateTohr.collectAsState()
    val salateAsr by timerViewModel.salateAsr.collectAsState()
    val salateMaghreb by timerViewModel.salateMaghreb.collectAsState()
    val salateIcha by timerViewModel.salateIcha.collectAsState()
    val salateChorok by timerViewModel.salateChorok.collectAsState()
    val salatejomo3a by timerViewModel.salatejomo3a.collectAsState()


    var backgroundColor by remember { mutableStateOf( Color(0xFFBBB9B9)) }


    var isEditable by remember { mutableStateOf(true) }

    val titreStryle = TextStyle(
        fontSize = 40.sp,
        color = Color.Black
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .onKeyEvent { event ->
                if ((event.key == Key.Enter) || (event.nativeKeyEvent.action == NativeKeyEvent.KEYCODE_DPAD_CENTER)) {
                    isEditable = !isEditable
                    backgroundColor = when (isEditable) {
                        true -> Color.Red
                        false -> Color.Green   }

                    true
                } else false
            },
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.setting_page), style = titreStryle)
        Column(verticalArrangement = Arrangement.SpaceAround) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 200.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {

                ItemSalateSetting(item = salateSobh, isEditable = isEditable, backgroundColor = backgroundColor)
                ItemSalateSetting(item = salateTohr, isEditable = isEditable, backgroundColor = backgroundColor)
                ItemSalateSetting(item = salateAsr, isEditable = isEditable, backgroundColor = backgroundColor)
                ItemSalateSetting(item = salateMaghreb, isEditable = isEditable, backgroundColor = backgroundColor)
                ItemSalateSetting(item = salateIcha, isEditable = isEditable, backgroundColor = backgroundColor)

            }
            
            Spacer(modifier = Modifier.height(50.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                ItemSalateSetting(item = salateChorok, isEditable = isEditable, backgroundColor = backgroundColor)
                ItemSalateSetting(item = salatejomo3a, isEditable = isEditable, backgroundColor = backgroundColor)

            }
        }

        val context = LocalContext.current

        Button(onClick = {
            timerViewModel.SaveSetting(context)
            onExitClick()}) {
            Text(text = stringResource(id = R . string . save))
        }
    }
}


@Composable
fun ItemSalateSetting(
    item: SalateTime = SalateTime("elSobh", "19", "10", "10", false),
    isEditable: Boolean = true,
    backgroundColor : Color = Color(0xFFBBB9B9),
    timerViewModel: AthanViewModel = viewModel()
) {
    var backgroundColor1 by remember { mutableStateOf(Color(0xFFBBB9B9)) }
    var backgroundColor2 by remember { mutableStateOf(Color(0xFFBBB9B9)) }
    var backgroundColor3 by remember { mutableStateOf(Color(0xFFBBB9B9)) }
    var backgroundColor4 by remember { mutableStateOf(Color(0xFFBBB9B9)) }


    val colorBoite = Color(0xC439A5CF)



    val titreStryle = TextStyle(
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )
    val txtStryle = TextStyle(
        fontSize = 20.sp,
        color = Color.Black
    )

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .drawBehind {
                drawRoundRect(
                    colorBoite, cornerRadius = CornerRadius(10.dp.toPx())
                )
            }
            .padding(3.dp)
    ) {
        Text(text = item.nameSalate, style = titreStryle)
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            Text(text = item.timeAthanHour,
                style = txtStryle,
                modifier = Modifier
                    .onFocusChanged {
                        backgroundColor1 = if (it.isFocused) backgroundColor else Color.Transparent
                    }
                    .onKeyEvent { event ->
                        if (isEditable) {
                        if ((event.key == Key.DirectionUp) || (event.nativeKeyEvent.action == NativeKeyEvent.KEYCODE_DPAD_UP)) {
                            timerViewModel.incHour(item)
                                true
                            } else if ((event.key == Key.DirectionDown) || (event.nativeKeyEvent.action == NativeKeyEvent.KEYCODE_DPAD_DOWN)) {
                                timerViewModel.decHour(item)
                                true
                            } else false
                        } else false
                    }
                    .background(backgroundColor1)
                    .focusable()

            )
            Text(
                text = ":",
                style = txtStryle,
            )
            Text(text = item.timeAthanMin,
                style = txtStryle,
                modifier = Modifier
                    .onFocusChanged {
                        backgroundColor2 = if (it.isFocused) backgroundColor else Color.Transparent
                    }
                    .onKeyEvent { event ->
                        if (isEditable) {
                            if ((event.key == Key.DirectionUp) || (event.nativeKeyEvent.action == NativeKeyEvent.KEYCODE_DPAD_UP)) {
                                timerViewModel.incMin(item)
                                true
                            } else if ((event.key == Key.DirectionDown) || (event.nativeKeyEvent.action == NativeKeyEvent.KEYCODE_DPAD_DOWN)) {
                                timerViewModel.decMin(item)
                                true
                            } else false
                        } else false
                    }
                    .background(backgroundColor2)
                    .focusable()
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = item.timeIqama,
            style = txtStryle,
            modifier = Modifier
                .onFocusChanged {
                    backgroundColor3 = if (it.isFocused) backgroundColor else Color.Transparent
                }
                .onKeyEvent { event ->
                    if (isEditable) {
                        if  ((event.key == Key.DirectionUp) || (event.nativeKeyEvent.action == NativeKeyEvent.KEYCODE_DPAD_UP)) {
                            timerViewModel.incAthan(item)
                            true
                        } else if ((event.key == Key.DirectionDown) || (event.nativeKeyEvent.action == NativeKeyEvent.KEYCODE_DPAD_DOWN)) {
                            timerViewModel.decAthan(item)
                            true
                        } else false
                    } else false
                }
                .background(backgroundColor3)
                .focusable()

        )
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {

            Text(text = stringResource(id = R.string.athan), style = txtStryle)
            Spacer(modifier = Modifier.width(10.dp))

            Image(painter = if (item.isAthan) painterResource(R.drawable.notifications_on) else painterResource(
                R.drawable.notifications_off
            ), "",
                modifier = Modifier
                    .onFocusChanged {
                        backgroundColor4 = if (it.isFocused) backgroundColor else Color.Transparent
                    }
                    .onKeyEvent { event ->

                        if  ((event.key == Key.Enter) || (event.nativeKeyEvent.action == NativeKeyEvent.KEYCODE_DPAD_CENTER)) {
                            timerViewModel.checkAthan(item)
                            Log.d("teste", "atahan click")
                            true
                        } else false
                    }
                    .background(backgroundColor4)
                    .focusable())


        }
    }


}

