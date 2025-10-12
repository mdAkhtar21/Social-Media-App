package com.example.socialmedialapp.android.common.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.socialmedialapp.android.common.theming.LargeSpacing
import com.example.socialmedialapp.android.common.theming.MediumSpacing
import com.example.socialmedialapp.android.common.util.Constants

internal fun LazyListScope.loadingMoreItem() {
    item(key = Constants.LOADING_MORE_ITEM_KEY) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = MediumSpacing,
                    horizontal = LargeSpacing
                ),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}