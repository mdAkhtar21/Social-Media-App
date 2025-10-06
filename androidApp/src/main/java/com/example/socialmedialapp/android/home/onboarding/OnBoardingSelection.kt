package com.example.socialmedialapp.android.home.onboarding

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.socialmedialapp.android.R
import com.example.socialmedialapp.android.common.theming.LargeSpacing
import com.example.socialmedialapp.android.common.theming.MediumSpacing
import com.example.socialmedialapp.android.common.theming.SocialAppTheme
import com.example.socialmedialapp.android.common.fake_data.SampleFollowsUser
import com.example.socialmedialapp.android.common.fake_data.sampleUsers

@Composable
fun OnBoardingSelection(
    modifier: Modifier = Modifier,
    users: List<SampleFollowsUser>,
    onUserClick: (SampleFollowsUser) -> Unit,
    onFollowButtonClick: (Boolean, SampleFollowsUser) -> Unit,
    onboardingFinished: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.onboarding_title),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MediumSpacing),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = stringResource(id = R.string.onboarding_description),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LargeSpacing),
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(LargeSpacing)) // ✅ fixed

        UsersRow(
            users = users,
            onUserClick = onUserClick,
            onFollowButtonClick = onFollowButtonClick
        )

        OutlinedButton(
            onClick = onboardingFinished,
            modifier = Modifier
                .fillMaxWidth(fraction = 0.5f)
                .align(Alignment.CenterHorizontally)
                .padding(vertical = LargeSpacing),
            shape = RoundedCornerShape(percent = 50)
        ) {
            Text(text = stringResource(id = R.string.onboarding_done_button))
        }
    }
}

@Composable
fun UsersRow(
    modifier: Modifier = Modifier,
    users: List<SampleFollowsUser>, // ✅ renamed correctly
    onUserClick: (SampleFollowsUser) -> Unit,
    onFollowButtonClick: (Boolean, SampleFollowsUser) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(LargeSpacing),
        contentPadding = PaddingValues(horizontal = LargeSpacing),
        modifier = modifier
    ) {
        items(
            items = users,
            key = { followUser -> followUser.id }
        ) { followUser ->
            OnBoardingUserItem(
                followsUser = followUser,
                onUserClick = onUserClick,
                onFollowButtonClick = onFollowButtonClick
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun onBoardingSelectionPreview() {
    SocialAppTheme {
        OnBoardingSelection(
            users = sampleUsers,
            onUserClick = {},
            onFollowButtonClick = {_,_->},
            onboardingFinished = {}
        )
    }
}