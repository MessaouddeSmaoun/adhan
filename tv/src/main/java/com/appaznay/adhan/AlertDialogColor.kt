package com.appaznay.adhan

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.NativeKeyEvent
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


@Composable
    fun SimpleAlertDialog(
        show: Boolean,
        onDismiss: () -> Unit,
        onConfirm: (Color) -> Unit
    ) {

    var backgroundColorFocused1 by remember { mutableStateOf(Color(0xFFBBB9B9)) }
    var backgroundColorFocused2 by remember { mutableStateOf(Color(0xFFBBB9B9)) }
    var backgroundColorFocused3 by remember { mutableStateOf(Color(0xFFBBB9B9)) }
    var backgroundColorFocused4 by remember { mutableStateOf(Color(0xFFBBB9B9)) }
    var backgroundColorFocused5 by remember { mutableStateOf(Color(0xFFBBB9B9)) }
    var backgroundColorFocused6 by remember { mutableStateOf(Color(0xFFBBB9B9)) }
    var backgroundColorFocused7 by remember { mutableStateOf(Color(0xFFBBB9B9)) }
    var backgroundColorFocused8 by remember { mutableStateOf(Color(0xFFBBB9B9)) }
    var backgroundColorFocused9 by remember { mutableStateOf(Color(0xFFBBB9B9)) }
    var backgroundColorFocused10 by remember { mutableStateOf(Color(0xFFBBB9B9)) }
    var backgroundColorFocused11 by remember { mutableStateOf(Color(0xFFBBB9B9)) }
    var backgroundColorFocused12 by remember { mutableStateOf(Color(0xFFBBB9B9)) }
    var backgroundColorFocused13 by remember { mutableStateOf(Color(0xFFBBB9B9)) }
    var backgroundColorFocused14 by remember { mutableStateOf(Color(0xFFBBB9B9)) }
    val backgroundColor by remember { mutableStateOf(Color(0xFFBBB9B9)) }



    var colorSelected  by remember { mutableStateOf(Color(0xCCFF0000))}

        if (show) {
            Dialog(
                onDismissRequest = onDismiss,
            ) {
                Column (verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .height(400.dp)
                        .width(700.dp)
                        .background(Color.Black)
                        .onKeyEvent { event ->
                            if (event.type == KeyEventType.KeyDown && ((event.key == Key.Spacebar) || (event.nativeKeyEvent.action == NativeKeyEvent.KEYCODE_DPAD_CENTER))) {
                                    onConfirm(colorSelected)
                                    true
                                } else   if (event.type == KeyEventType.KeyDown && ((event.nativeKeyEvent.action == NativeKeyEvent.KEYCODE_BACK))) {
                                onDismiss()
                                true
                            } else false
                            }
                        )
                {
                    Row (horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier.fillMaxWidth()
                        ){
                        Box(modifier = Modifier
                            .height(50.dp)
                            .width(80.dp)
                            .onFocusChanged {
                                if (it.isFocused) Log.d("color", "rouge")
                                colorSelected = Color(0xCCFF0000)
                                backgroundColorFocused1 =
                                    if (it.isFocused) backgroundColor else Color.Transparent
                            }
                            .focusable()
                            .border(width = 3.dp, color = backgroundColorFocused1)
                            .background(Color(0xCCFF0000)))
                        Box(modifier = Modifier
                            .height(50.dp)
                            .width(80.dp)
                            .onFocusChanged {
                                if (it.isFocused) Log.d("color", "jaune")
                                colorSelected = Color(0xCCFFB700)
                                backgroundColorFocused2 =
                                    if (it.isFocused) backgroundColor else Color.Transparent
                            }
                            .focusable()
                            .border(width = 3.dp, color = backgroundColorFocused2)
                            .background(Color(0xCCFFB700)))
                        Box(modifier = Modifier
                            .height(50.dp)
                            .width(80.dp)
                            .onFocusChanged {
                                if (it.isFocused) Log.d("color", "vert")
                                colorSelected = Color(0xCC37FF00)
                                backgroundColorFocused3 =
                                    if (it.isFocused) backgroundColor else Color.Transparent
                            }
                            .focusable()
                            .border(width = 3.dp, color = backgroundColorFocused3)
                            .background(Color(0xCC37FF00)))
                        Box(modifier = Modifier
                            .height(50.dp)
                            .width(80.dp)
                            .onFocusChanged {
                                if (it.isFocused) Log.d("color", "bleu vert")
                                colorSelected = Color(0xCC00FFCC)
                                backgroundColorFocused14 =
                                    if (it.isFocused) backgroundColor else Color.Transparent
                            }
                            .focusable()
                            .border(width = 3.dp, color = backgroundColorFocused14)
                            .background(Color(0xCC00FFCC)))
                        Box(modifier = Modifier
                            .height(50.dp)
                            .width(80.dp)
                            .onFocusChanged {
                                if (it.isFocused) Log.d("color", "bleu")
                                colorSelected = Color(0xCC0055FF)
                                backgroundColorFocused4 =
                                    if (it.isFocused) backgroundColor else Color.Transparent
                            }
                            .focusable()
                            .border(width = 3.dp, color = backgroundColorFocused4)
                            .background(Color(0xCC0055FF)))
                    }
                    Row (horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier.fillMaxWidth()){
                        Box(modifier = Modifier
                            .height(50.dp)
                            .width(80.dp)
                            .onFocusChanged {
                                if (it.isFocused) Log.d("color", "rose")
                                colorSelected = Color(0xCCFF00DD)
                                backgroundColorFocused5 =
                                    if (it.isFocused) backgroundColor else Color.Transparent
                            }
                            .focusable()
                            .border(width = 3.dp, color = backgroundColorFocused5)
                            .background(Color(0xCCFF00DD)))
                        Box(modifier = Modifier
                            .height(50.dp)
                            .width(80.dp)
                            .onFocusChanged {
                                if (it.isFocused) Log.d("color", "mauve")
                                colorSelected = Color(0xCCBB00FF)
                                backgroundColorFocused6 =
                                    if (it.isFocused) backgroundColor else Color.Transparent
                            }
                            .focusable()
                            .border(width = 3.dp, color = backgroundColorFocused6)
                            .background(Color(0xCCBB00FF)))
                        Box(modifier = Modifier
                            .height(50.dp)
                            .width(80.dp)
                            .onFocusChanged {
                                if (it.isFocused) Log.d("color", "vert")
                                colorSelected = Color(0xCC00FF7B)
                                backgroundColorFocused7 =
                                    if (it.isFocused) backgroundColor else Color.Transparent
                            }
                            .focusable()
                            .border(width = 3.dp, color = backgroundColorFocused7)
                            .background(Color(0xCC00FF7B)))
                        Box(modifier = Modifier
                            .height(50.dp)
                            .width(80.dp)
                            .onFocusChanged {
                                if (it.isFocused) Log.d("color", "rose bombon")
                                colorSelected = Color(0xCCFF006F)
                                backgroundColorFocused13 =
                                    if (it.isFocused) backgroundColor else Color.Transparent
                            }
                            .focusable()
                            .border(width = 3.dp, color = backgroundColorFocused13)
                            .background(Color(0xCCFF006F)))
                        Box(modifier = Modifier
                            .height(50.dp)
                            .width(80.dp)
                            .onFocusChanged {
                                if (it.isFocused) Log.d("color", "range")
                                colorSelected = Color(0xCCFF6F00)
                                backgroundColorFocused8 =
                                    if (it.isFocused) backgroundColor else Color.Transparent
                            }
                            .focusable()
                            .border(width = 3.dp, color = backgroundColorFocused8)
                            .background(Color(0xCCFF6F00)))
                    }
                    Row (horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier.fillMaxWidth()){
                        Box(modifier = Modifier
                            .height(50.dp)
                            .width(80.dp)
                            .onFocusChanged {
                                if (it.isFocused) Log.d("color", "rose")
                                colorSelected = Color(0xCC000000)
                                backgroundColorFocused9 =
                                    if (it.isFocused) backgroundColor else Color.Transparent
                            }
                            .focusable()
                            .border(width = 3.dp, color = backgroundColorFocused9)
                            .background(Color(0xCC000000)))
                        Box(modifier = Modifier
                            .height(50.dp)
                            .width(80.dp)
                            .onFocusChanged {
                                if (it.isFocused) Log.d("color", "vert")
                                colorSelected = Color(0xCC919191)
                                backgroundColorFocused12 =
                                    if (it.isFocused) backgroundColor else Color.Transparent
                            }
                            .focusable()
                            .border(width = 3.dp, color = backgroundColorFocused12)
                            .background(Color(0xCC919191)))
                        Box(modifier = Modifier
                            .height(50.dp)
                            .width(80.dp)
                            .onFocusChanged {
                                if (it.isFocused) Log.d("color", "mauve")
                                colorSelected = Color(0xCCFFFFFF)
                                backgroundColorFocused10 =
                                    if (it.isFocused) backgroundColor else Color.Transparent
                            }
                            .focusable()
                            .border(width = 3.dp, color = backgroundColorFocused10)
                            .background(Color(0xCCFFFFFF)))
                        Box(modifier = Modifier
                            .height(50.dp)
                            .width(80.dp)
                            .onFocusChanged {
                                if (it.isFocused) Log.d("color", "rose bombon")
                                colorSelected = Color(0x00FF006F)
                                backgroundColorFocused11 =
                                    if (it.isFocused) backgroundColor else Color.Transparent
                            }
                            .focusable()
                            .border(width = 3.dp, color = backgroundColorFocused11)
                            .background(Color(0x00FF006F)))
                    }
                }
            }
        }
    }

