package com.example.wiggle.core.util

import androidx.compose.ui.text.googlefonts.GoogleFont
import com.example.wiggle.R

//also make font_certs.xml
val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)