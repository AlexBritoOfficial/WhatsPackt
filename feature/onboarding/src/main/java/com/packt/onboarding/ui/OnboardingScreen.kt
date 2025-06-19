package com.packt.onboarding.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    onboardingPages: List<OnboardingPage>,
    onGetStarted: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { onboardingPages.size }, initialPage = 0)
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .padding(WindowInsets.systemBars.asPaddingValues()) // ✅ This adds padding for nav bar & status bar

    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { page ->
            OnboardingPageContent(onboardingPages[page])
        }

        Spacer(Modifier.height(16.dp))

        SimplePagerIndicator(
            currentPage = pagerState.currentPage,
            pageCount = onboardingPages.size,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = "Page ${pagerState.currentPage + 1} of ${onboardingPages.size}",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = onGetStarted) {
                Text("Skip", color = MaterialTheme.colorScheme.primary)
            }

            if (pagerState.currentPage == onboardingPages.lastIndex) {
                Button(onClick = onGetStarted) {
                    Text("Get Started", color = MaterialTheme.colorScheme.onPrimary)
                }
            } else {
                Button(onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }) {
                    Text("Next", color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
    }
}

@Composable
fun SimplePagerIndicator(
    currentPage: Int,
    pageCount: Int,
    modifier: Modifier = Modifier,
    activeColor: Color = MaterialTheme.colorScheme.primary,
    inactiveColor: Color = activeColor.copy(alpha = 0.3f)
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.Center) {
        repeat(pageCount) { index ->
            val color = if (index == currentPage) activeColor else inactiveColor
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}
