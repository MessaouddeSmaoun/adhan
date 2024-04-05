package com.appaznay.adhan

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun Clock(
    timerViewModel: AthanViewModel = viewModel()
) {



    val salateSobh by timerViewModel.salateSobh.collectAsState()
    val salateTohr by timerViewModel.salateTohr.collectAsState()
    val salateAsr by timerViewModel.salateAsr.collectAsState()
    val salateMaghreb by timerViewModel.salateMaghreb.collectAsState()
    val salateIcha by timerViewModel.salateIcha.collectAsState()
    val salateChorok by timerViewModel.salateChorok.collectAsState()
    val salatejomo3a by timerViewModel.salatejomo3a.collectAsState()



    val nameDay by timerViewModel.nameDay.collectAsState()
    val nextTime by timerViewModel.nextTime.collectAsState()

    val colorFont by timerViewModel.colorFont.collectAsState()
    val colorBoxNext by timerViewModel.colorBoxNext.collectAsState()
    val colorBoxDefault by timerViewModel.colorBoxDefault.collectAsState()

    Row {
        Image(painter = painterResource(id = R.drawable.mosque), contentDescription = "", colorFilter = ColorFilter.tint(colorFont)
            , modifier = Modifier.size(40.dp,40.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
            ,
            text = stringResource(id = R.string.jama3), style = TextStyle(
                fontSize = 25.sp,
                color = colorFont,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.ExtraBold
            )
        )
        Spacer(modifier = Modifier.width(10.dp))
        Image(painter = painterResource(id = R.drawable.mosque), contentDescription = "", colorFilter = ColorFilter.tint(colorFont)
            , modifier = Modifier.size(40.dp,40.dp))
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {




        ItemSalate(salatejomo3a, colorFont = colorFont, colorCard = colorBoxDefault)
        ItemClock(colorFont = colorFont, colorCard = colorBoxNext)
        ItemSalate(salateChorok, colorFont = colorFont, colorCard = if (nextTime == "الشروق") colorBoxNext else colorBoxDefault)
    }

    Spacer(
        modifier = Modifier
            .height(30.dp)
            .fillMaxWidth()
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ItemSalate(
            salateIcha,
            colorFont = colorFont,
            colorCard = if (nextTime == "العشاء") colorBoxNext else colorBoxDefault
        )
        ItemSalate(
            salateMaghreb,
            colorFont = colorFont,
            colorCard = if (nextTime == "المغرب") colorBoxNext else colorBoxDefault
        )
        ItemSalate(
            salateAsr,
            colorFont = colorFont,
            colorCard = if (nextTime == "العصر") colorBoxNext else colorBoxDefault
        )

        if (nameDay == 6) {
            ItemSalate(
                salatejomo3a,
                colorFont = colorFont,
                colorCard = if (nextTime == "الجمعة") colorBoxNext else colorBoxDefault
            )
        } else {
            ItemSalate(
                salateTohr,
                colorFont = colorFont,
                colorCard = if (nextTime == "الظهر") colorBoxNext else colorBoxDefault
            )
        }

        ItemSalate(
            salateSobh,
            colorFont = colorFont,
            colorCard = if (nextTime == "الصبح") colorBoxNext else colorBoxDefault
        )
    }
    Spacer(
        modifier = Modifier
            .height(70.dp)
            .fillMaxWidth()
            .background(Color(0x16FF0000))
    )
}


@Composable
fun ItemSalate(item: SalateTime, colorFont: Color = Color.White, colorCard: Color = Color.Blue) {


    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .drawBehind {
            drawRoundRect(
                colorCard, cornerRadius = CornerRadius(10.dp.toPx())
            )
        }
        .padding(8.dp)) {
        Row {
            Text(
                text = item.nameSalate,
                style = TextStyle(
                    fontSize = 45.sp, color = colorFont
                ),
            )
        }
        Text(
            text = "${item.timeAthanHour}:${item.timeAthanMin}",
            style = TextStyle(
                fontSize = 40.sp, fontWeight = FontWeight.Bold, color = colorFont
            ),
        )
        Row {

            if (item.timeIqama != "") {
                Text(
                    text = "${item.timeIqama}+",
                    style = TextStyle(
                        fontSize = 40.sp, color = colorFont
                    ),
                )
            }
            /*

            Spacer(modifier = Modifier.width(10.dp))
            Image(
                painter = if (item.isAthan) painterResource(R.drawable.notifications_on) else painterResource(
                    R.drawable.notifications_off
                ), "",
            )

             */

        }

    }
}

@Composable
fun ItemClock(
    colorFont: Color = Color.White,
    colorCard: Color = Color.Blue,
    colorBack: Color = Color.LightGray,
    timerViewModel: AthanViewModel = viewModel()
) {
    val timerState by timerViewModel.timer.collectAsState()
    val dateState by timerViewModel.date.collectAsState()
    val nextTime by timerViewModel.nextTime.collectAsState()

    val reste by timerViewModel.reste.collectAsState()

    val texr = timerState.split(":")

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .drawBehind {
            drawRoundRect(
                colorCard, cornerRadius = CornerRadius(10.dp.toPx())
            )
        }
        .fillMaxWidth(0.55f)
        .padding(8.dp)) {
        Row(verticalAlignment = Alignment.Bottom) {

            Text(
                text = "${texr.first()}:${
                    if (texr.size > 1) {
                        texr[1]
                    } else {
                        texr.first()                    }
                }", style = TextStyle(
                    fontSize = 70.sp,
                    color = colorFont,
                    fontWeight = FontWeight.Bold,
                )
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = texr.last(), style = TextStyle(
                    fontSize = 35.sp,
                    color = colorBack,
                    fontWeight = FontWeight.Bold,
                ), modifier = Modifier.padding(bottom = 10.dp)
            )
        }
        Text(
            text = dateState, style = TextStyle(
                fontSize = 40.sp, color = colorFont
            )
        )
        Row(horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .drawBehind {
                    drawRoundRect(
                        colorBack, cornerRadius = CornerRadius(10.dp.toPx())
                    )
                }

                .padding(horizontal = 8.dp, vertical = 2.dp)) {
            Image(painter = painterResource(id = R.drawable.adhan), contentDescription = "", colorFilter = ColorFilter.tint(colorFont)
                , modifier = Modifier.size(30.dp,30.dp))
            Text(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = if(nextTime !=  "noSalate") reste else "",
                style = TextStyle(
                    fontSize = 22.sp, color = colorFont
                )
            )
            Image(painter = painterResource(id = R.drawable.adhan), contentDescription = "", colorFilter = ColorFilter.tint(colorFont)
            , modifier = Modifier.size(30.dp,30.dp))
        }
    }
}