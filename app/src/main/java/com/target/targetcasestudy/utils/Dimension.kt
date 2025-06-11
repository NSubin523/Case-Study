package com.target.targetcasestudy.utils

import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

object Dimension {
    object FontSize {
        val titleSmall  = 12.sp
        val titleMedium = 16.sp
        val titleLarge  = 20.sp
        val display     = 24.sp
    }

    object Weight {
        val regular = FontWeight.Normal
        val medium  = FontWeight.Medium
        val bold    = FontWeight.Bold
    }

    object Padding {
        /**
         * Standard
         */
        val xxs = 4.dp
        val xs = 6.dp
        val s = 8.dp
        val m = 10.dp
        val l = 12.dp
        val xl = 16.dp
        val xxl = 20.dp

        /**
         * For Button
         */
        val buttonMinHeight = 56.dp

        /**
         * Images
         */
        val dealDetailImageMinHeight = 400.dp
        val dealListImageHeight = 160.dp
        val dealListImageWidth = 180.dp

        /**
         * Divider
         */
        val dividerSlim = 1.dp;

        /**
         * Text
         */
        val textPadding = 2.dp
    }
}