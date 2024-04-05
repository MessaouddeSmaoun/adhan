package com.appaznay.adhan

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun InfoPage(onItemClick: () -> Unit = {},
             timerViewModel: AthanViewModel = viewModel()  ) {



    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

    }
}

