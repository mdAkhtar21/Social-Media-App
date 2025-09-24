package com.example.socialmedialapp.android.common.theming

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(SmallSpacing),
    small = RoundedCornerShape(MediumSpacing),
    medium = RoundedCornerShape(LargeSpacing),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp)
)
