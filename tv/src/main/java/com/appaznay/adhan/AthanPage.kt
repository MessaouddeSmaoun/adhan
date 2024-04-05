package com.appaznay.adhan

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun AthanPage(timerViewModel: AthanViewModel = viewModel()) {


    val reste by timerViewModel.reste.collectAsState()
    val messageAthan by timerViewModel.messageAthan.collectAsState()
    val athanTime by timerViewModel.athanTime.collectAsState()

    val titreStryle = TextStyle(
        fontSize = 90.sp,
        color = Color.Black
    )
    val texteStryle = TextStyle(
        fontSize = 60.sp,
        color = Color.Black
    )
    var isBlack by remember { mutableStateOf(false) }

    if (reste == "00:00") {
        isBlack = true
        timerViewModel.setAlarm()
    }


    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (messageAthan != "ecran_noir") {
            Row (verticalAlignment = Alignment.CenterVertically) {
                if(messageAthan == "الأذان") {
                    Image(painter = painterResource(id = R.drawable.adhan_man), contentDescription = "")
                    Spacer(modifier = Modifier.width(100.dp))
                }
                Text(text = messageAthan, style = titreStryle)

                if(messageAthan == "الأذان") {
                    Spacer(modifier = Modifier.width(100.dp))
                    Image(painter = painterResource(id = R.drawable.adhan_man), contentDescription = "")
                }
            }


            Spacer(modifier = Modifier.height(40.dp))

            if(messageAthan == "الأذان"){
            Text(
                text = " ${stringResource(id = R.string.zaqt_salate)} $athanTime",
                style = texteStryle
            )
                Spacer(modifier = Modifier.height(25.dp))

                Text(
                    text = reste,
                    style = texteStryle
                )
            } else if (messageAthan =="الإقامة بعد"){
                Text(
                    text = reste.split(" ").first(),
                    style = texteStryle
                )
            }

            if(messageAthan == "أقيموا الصلاة يرحمكم الله") {
                Image(painter = painterResource(id = R.drawable.salat_jama3a), contentDescription = "",
                    modifier = Modifier.size(200.dp,200.dp))
            }


        } else {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.Black))
        }

    }


}
