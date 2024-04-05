package com.appaznay.adhan

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
fun Hadith(
    timerViewModel: AthanViewModel = viewModel()
) {




    val salateSobh by timerViewModel.salateSobh.collectAsState()
    val salateTohr by timerViewModel.salateTohr.collectAsState()
    val salateAsr by timerViewModel.salateAsr.collectAsState()
    val salateMaghreb by timerViewModel.salateMaghreb.collectAsState()
    val salateIcha by timerViewModel.salateIcha.collectAsState()
    val salatejomo3a by timerViewModel.salatejomo3a.collectAsState()

    val timerState by timerViewModel.timer.collectAsState()
    val dateState by timerViewModel.date.collectAsState()
    val hadith by timerViewModel.hadith.collectAsState()
    val nextTime by timerViewModel.nextTime.collectAsState()
    val nameDay by timerViewModel.nameDay.collectAsState()


    val colorFont by timerViewModel.colorFont.collectAsState()
    val colorBoxNext by timerViewModel.colorBoxNext.collectAsState()
    val colorBoxDefault by timerViewModel.colorBoxDefault.collectAsState()


    val hadithStyle = TextStyle(
        fontSize = 45.sp, color = colorFont,
        textAlign = TextAlign.Center)

    val colortexte = colorFont


    Column ( modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally){
        Column  (  modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally){
            Row {
                Image(painter = painterResource(id = R.drawable.mosque), contentDescription = "", colorFilter = ColorFilter.tint(colorFont)
                    , modifier = Modifier.size(40.dp,40.dp))
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                    //.background(Color.LightGray)
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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
            ) {

                Text(
                    text = timerState, style = TextStyle(
                        fontSize = 25.sp, color = colortexte, fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                      //  .background(Color.LightGray)
                )
                Text(
                    text = dateState, style = TextStyle(
                        fontSize = 25.sp, color = colortexte, fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                   //     .background(Color.LightGray)
                )
            }
        }
        Text(
            text = hadith,
            style = hadithStyle,
            modifier = Modifier.background(Color(0xB2B8B8B8)).fillMaxWidth()

        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ItemSalate2(salateIcha, colorFont = colorFont, colorCard =   if (nextTime == "العشاء")colorBoxNext else colorBoxDefault)
            ItemSalate2(salateMaghreb, colorFont = colorFont, colorCard =   if (nextTime == "المغرب")colorBoxNext else colorBoxDefault)
            ItemSalate2(salateAsr, colorFont = colorFont, colorCard = if (nextTime == "العصر")colorBoxNext else colorBoxDefault)
            if (nameDay == 6) {
                ItemSalate2(
                    salatejomo3a,
                    colorFont = colorFont,
                    colorCard = if (nextTime == "الجمعة") colorBoxNext else colorBoxDefault
                )
            } else {
                ItemSalate2(
                    salateTohr,
                    colorFont = colorFont,
                    colorCard = if (nextTime == "الظهر") colorBoxNext else colorBoxDefault
                )
            }

            ItemSalate2(salateSobh, colorFont = colorFont, colorCard =  if (nextTime == "الصبح")colorBoxNext else colorBoxDefault)
        }
    }
}

@Composable
fun ItemSalate2(item: SalateTime, colorFont: Color = Color.White, colorCard: Color = Color.Blue) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .drawBehind {
            drawRoundRect(
                colorCard, cornerRadius = CornerRadius(7.dp.toPx())
            )
        }
        .padding(horizontal = 5.dp, vertical = 3.dp)) {
        Text(
            text = item.nameSalate,
            style = TextStyle(
                fontSize = 30.sp, color = colorFont
            ),
        )
        Text(
            text = "${item.timeAthanHour}:${item.timeAthanMin}",
            style = TextStyle(
                fontSize = 30.sp, fontWeight = FontWeight.Bold, color = colorFont
            ),
        )
    }
}