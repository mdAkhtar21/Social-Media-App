package com.example.socialmedialapp.android.common.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.socialmedialapp.android.R
import com.example.socialmedialapp.android.common.theming.MediumSpacing
import com.example.socialmedialapp.android.common.theming.SocialAppTheme

@Composable
fun FollowsButton(
    modifier: Modifier = Modifier,
    isOutlined: Boolean = false,
    @StringRes text: Int,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = if (isOutlined){
            ButtonDefaults.outlinedButtonColors()
        }else{
            ButtonDefaults.buttonColors()
        },
        border = if (isOutlined){
            ButtonDefaults.outlinedButtonBorder
        }else{
            null
        },
        elevation = ButtonDefaults.elevatedButtonElevation(0.dp)
    ) {
        Text(
            text = stringResource(id = text),
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp)
        )
    }
}

@Preview
@Composable
private fun FollowButtonPreview() {
    SocialAppTheme {
        Surface {
            FollowsButton(
                text = R.string.follow_text_label,
                isOutlined = false,
                modifier = Modifier
                    .widthIn(100.dp)
                    .heightIn(30.dp)
                    .padding(horizontal = MediumSpacing),
                onClick = {}
            )
        }
    }
}