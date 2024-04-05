package com.appaznay.adhan

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.NativeKeyEvent
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Affichage(
    onExitClick: () -> Unit = {},
    timerViewModel: AthanViewModel = viewModel()
) {


    val colorFont by timerViewModel.colorFont.collectAsState()
    val colorBoxNext by timerViewModel.colorBoxNext.collectAsState()
    val colorBoxDefault by timerViewModel.colorBoxDefault.collectAsState()

    var colorBoxDefaultAffichage by remember { mutableStateOf(colorBoxDefault) }
    var colorBoxNextAffichage by remember { mutableStateOf(colorBoxNext) }
    var colorFontAffichage by remember { mutableStateOf(colorFont) }


    val imageIndex by timerViewModel.imageIndex.collectAsState()
    var isEditable by remember { mutableStateOf(false) }


    var isEcranNoir by remember { mutableStateOf(false) }
    var ecranNoir by remember { mutableIntStateOf(15) }


    var backgroundColorFocused1 by remember { mutableStateOf(Color(0xFFBBB9B9)) }
    var backgroundColorFocused2 by remember { mutableStateOf(Color(0xFFBBB9B9)) }
    var backgroundColorFocused3 by remember { mutableStateOf(Color(0xFFBBB9B9)) }
    var backgroundColorFocused4 by remember { mutableStateOf(Color(0xFFBBB9B9)) }
    var backgroundColorFocused5 by remember { mutableStateOf(Color(0xFFBBB9B9)) }
    var backgroundColorFocused6 by remember { mutableStateOf(Color(0xFFBBB9B9)) }
    var backgroundColor by remember { mutableStateOf(Color(0xFFBBB9B9)) }
    backgroundColor = when (isEditable) {
        true -> Color.Red
        false -> Color.Green
    }


    val titreStryle = TextStyle(
        fontSize = 40.sp,
        color = Color.White
    )
    val texteStryle = TextStyle(
        fontSize = 25.sp,
        color = Color.White
    )

    var isShowDefault by remember { mutableStateOf(false) }
    var isShowNext by remember { mutableStateOf(false) }
    var isShowFont by remember { mutableStateOf(false) }

    SimpleAlertDialog(show = isShowDefault,
        onDismiss = { isShowDefault = false },
        onConfirm = {
            isShowDefault = false
            colorBoxDefaultAffichage = it
        })
    SimpleAlertDialog(show = isShowNext,
        onDismiss = { isShowNext = false },
        onConfirm = {
            isShowNext = false
            colorBoxNextAffichage = it
        })
    SimpleAlertDialog(show = isShowFont,
        onDismiss = { isShowFont = false },
        onConfirm = {
            isShowFont = false
            colorFontAffichage = it
        })


    Column(
        modifier = Modifier
            .background(Color(0xCDD3D3D3))
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = stringResource(id = R.string.affichage_page), style = titreStryle)
        Column(verticalArrangement = Arrangement.Center) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(verticalArrangement = Arrangement.SpaceAround) {


                    Text(text = stringResource(id = R.string.color_text), style = texteStryle)
                   Text(
                        text = stringResource(id = R.string.box_color_default),
                        style = texteStryle
                    )
                    Text(text = stringResource(id = R.string.box_color_next), style = texteStryle)
                    Text(text = stringResource(id = R.string.ecran_athan), style = texteStryle)
                }
                Spacer(modifier = Modifier.width(120.dp))
                Column(verticalArrangement = Arrangement.SpaceAround) {
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(60.dp)
                            .background(colorFontAffichage)
                            .onFocusChanged {
                                backgroundColorFocused1 =
                                    if (it.isFocused) backgroundColor else Color.Transparent
                            }
                            .onKeyEvent { event ->
                                if ((event.key == Key.Enter) || (event.nativeKeyEvent.action == NativeKeyEvent.KEYCODE_DPAD_CENTER)) {
                                    isShowFont = true
                                    true
                                } else {
                                    false
                                }
                            }
                            .border(width = 3.dp, color = backgroundColorFocused1)
                            .focusable()
                    )
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(60.dp)
                            .background(colorBoxDefaultAffichage)
                            .onFocusChanged {
                                backgroundColorFocused2 =
                                    if (it.isFocused) backgroundColor else Color.Transparent
                            }
                            .onKeyEvent { event ->
                                if ((event.key == Key.Enter) || (event.nativeKeyEvent.action == NativeKeyEvent.KEYCODE_DPAD_CENTER)) {
                                    isShowDefault = true
                                    true
                                } else {
                                    false
                                }
                            }
                            .border(width = 3.dp, color = backgroundColorFocused2)
                            .focusable()
                    )
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(60.dp)
                            .background(colorBoxNextAffichage)
                            .onFocusChanged {
                                backgroundColorFocused3 =
                                    if (it.isFocused) backgroundColor else Color.Transparent
                            }
                            .onKeyEvent { event ->
                                if ((event.key == Key.Enter) || (event.nativeKeyEvent.action == NativeKeyEvent.KEYCODE_DPAD_CENTER)) {
                                    isShowNext = true
                                    true
                                } else {
                                    false
                                }
                            }
                            .border(width = 3.dp, color = backgroundColorFocused3)
                            .focusable()
                    )
                    Row {
                        Checkbox(checked = isEcranNoir, onCheckedChange = {
                            isEcranNoir = it
                        },
                            modifier = Modifier
                                .onFocusChanged {
                                    backgroundColorFocused5 =
                                        if (it.isFocused) backgroundColor else Color.Transparent
                                }
                                .border(width = 3.dp, color = backgroundColorFocused5)
                                .focusable())
                        Spacer(modifier = Modifier.width(50.dp))
                        Text(
                            text = "$ecranNoir", style = texteStryle,
                            modifier = Modifier
                                .onFocusChanged {
                                    backgroundColorFocused6 =
                                        if (it.isFocused) backgroundColor else Color.Transparent
                                }
                                .onKeyEvent { event ->
                                    if ((event.key == Key.Enter) || (event.nativeKeyEvent.action == NativeKeyEvent.KEYCODE_DPAD_CENTER)) {
                                        isEditable = !isEditable
                                    }
                                    if (isEditable) {
                                        if ((event.key == Key.Enter) || (event.nativeKeyEvent.action == NativeKeyEvent.KEYCODE_DPAD_UP)) {
                                            ecranNoir++
                                            true
                                        } else if ((event.key == Key.Enter) || (event.nativeKeyEvent.action == NativeKeyEvent.KEYCODE_DPAD_DOWN)) {
                                            ecranNoir--
                                            true
                                        } else {
                                            false
                                        }
                                    } else false
                                }
                                .focusable()
                                .background(backgroundColorFocused6),
                        )
                    }
                }
                Spacer(modifier = Modifier.width(120.dp))

                Image(painter = painterResource(id = timerViewModel.listImage[imageIndex]), "",
                    modifier = Modifier
                        .size(200.dp, 150.dp)
                        .onKeyEvent { event ->
                            if ((event.key == Key.Enter) || (event.nativeKeyEvent.action == NativeKeyEvent.KEYCODE_DPAD_CENTER)) {
                                isEditable = !isEditable
                                backgroundColorFocused4 =
                                    if (isEditable) backgroundColor else Color.Transparent
                            }
                            if (isEditable) {
                                if ((event.key == Key.DirectionUp) || (event.nativeKeyEvent.action == NativeKeyEvent.KEYCODE_DPAD_UP)) {
                                    timerViewModel.imageIndexPlus()
                                    true
                                } else if ((event.key == Key.DirectionDown) || (event.nativeKeyEvent.action == NativeKeyEvent.KEYCODE_DPAD_DOWN)) {
                                    timerViewModel.imageIndexMoins()
                                    true
                                } else {
                                    false
                                }
                            } else false
                        }
                        .onFocusChanged {
                            backgroundColorFocused4 =
                                if (it.isFocused) {
                                    backgroundColor
                                } else {
                                    Color.Transparent
                                }
                        }
                        .border(width = 3.dp, color = backgroundColorFocused4)
                        .focusable()
                        .background(Color.White))



            }


        }


        val context = LocalContext.current

        Button(onClick = {

            timerViewModel.setColor(colorBoxDefaultAffichage,colorBoxNextAffichage,colorFontAffichage)
            timerViewModel.SaveAffichage(context)
            onExitClick()
        }) {
            Text(text = stringResource(id = R.string.save))
        }

    }


}
