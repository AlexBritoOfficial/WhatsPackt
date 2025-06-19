package com.packt.onboarding.ui

import com.packt.onboarding.R

data class OnboardingPage(
    val title: String,
    val description: String// You can also use a painter if needed
)

val onboardingPages = listOf(
    OnboardingPage("Welcome to WhatsPackt", "Secure messaging made simple."),
    OnboardingPage("Fast & Private", "Chat confidently with end-to-end encryption."),
    OnboardingPage("Stay Connected", "Group chats, media sharing, and more.")
)
