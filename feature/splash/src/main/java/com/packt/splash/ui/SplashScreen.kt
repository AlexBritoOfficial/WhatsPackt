package com.packt.splash.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.packt.framework.navigation.LastRouteDataStore
import com.packt.framework.navigation.NavRoutes

import com.packt.framework.ui.theme.RedBackground
import com.packt.framework.ui.theme.WhiteText
import com.packt.splash.R
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navigateToNewScreen: () -> Unit) {

    var visible by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(if (visible) 1f else 0f)

    LaunchedEffect(Unit) {
        visible = true
        kotlinx.coroutines.delay(3500)
        navigateToNewScreen()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(RedBackground),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(id = R.drawable.whatspacktsplash),
                contentDescription = "WhatsPackt Logo",
                modifier = Modifier.fillMaxSize(),
                tint = WhiteText
            )
            Spacer(modifier = Modifier.height(SplashScreenConstants.SpacerHeight))
            Text(
                SplashScreenConstants.TitleText,
                fontSize = SplashScreenConstants.TitleFontSize,
                fontWeight = FontWeight.Bold,
                color = WhiteText
            )
            Text(
                SplashScreenConstants.SubtitleText,
                fontSize = SplashScreenConstants.SubtitleFontSize,
                color = WhiteText
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {

}