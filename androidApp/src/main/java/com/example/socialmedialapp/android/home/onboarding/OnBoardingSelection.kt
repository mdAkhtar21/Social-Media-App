package com.example.socialmedialapp.android.home.onboarding

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.socialmedialapp.android.R
import com.example.socialmedialapp.android.common.dummy_data.sampleUsers
import com.example.socialmedialapp.android.common.theming.LargeSpacing
import com.example.socialmedialapp.android.common.theming.MediumSpacing
import com.example.socialmedialapp.android.common.theming.SocialAppTheme
import com.example.socialmedialapp.common.domain.model.FollowsUser

@Composable
fun OnBoardingSection(
    users: List<FollowsUser>,
    onUserClick: (FollowsUser) -> Unit,
    onFollowButtonClick: (Boolean, FollowsUser) -> Unit,
    onBoardingFinish: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.onboarding_title),
            modifier = modifier
                .fillMaxWidth()
                .padding(top = MediumSpacing),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )

        Text(
            text = stringResource(id = R.string.oboarding_guidance_subtitle),
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = LargeSpacing),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = modifier.height(LargeSpacing))

        UsersRow(
            users = users,
            onUserClick = onUserClick,
            onFollowButtonClick = onFollowButtonClick
        )

        OutlinedButton(
            onClick = onBoardingFinish,
            shape = RoundedCornerShape(percent = 50),
            modifier = modifier
                .fillMaxWidth(fraction = 0.5f)
                .align(Alignment.CenterHorizontally)
                .padding(vertical = LargeSpacing)
        ) {
            Text(text = stringResource(id = R.string.onboarding_button_text))
        }
    }
}

@Composable
private fun UsersRow(
    modifier: Modifier = Modifier,
    users: List<FollowsUser>,
    onUserClick: (FollowsUser) -> Unit,
    onFollowButtonClick: (Boolean, FollowsUser) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(LargeSpacing),
        contentPadding = PaddingValues(horizontal = LargeSpacing),
        modifier = modifier
    ) {
        items(items = users, key = { user -> user.id }) {
            OnBoardingUserItem(
                followsUser = it,
                onUserClick = onUserClick,
                onFollowButtonClick = onFollowButtonClick
            )
        }
    }
}


@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun OnBoardingSectionPreview() {
    SocialAppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            OnBoardingSection(
                users = sampleUsers.map { it.toFollowsUser() },
                onUserClick = {},
                onFollowButtonClick = { _, _ -> },
                onBoardingFinish = {}
            )
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun UsersRowPreview() {
    SocialAppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            UsersRow(
                users = sampleUsers.map { it.toFollowsUser() },
                onUserClick = {},
                onFollowButtonClick = { _, _ -> },
                modifier = Modifier.padding(vertical = LargeSpacing)
            )
        }
    }
}