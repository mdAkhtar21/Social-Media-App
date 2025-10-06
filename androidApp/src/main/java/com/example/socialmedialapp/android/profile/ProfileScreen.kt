package com.example.socialmedialapp.android.profile

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.socialmedialapp.android.R
import com.example.socialmedialapp.android.common.components.CircleImage
import com.example.socialmedialapp.android.common.components.FollowsButton
import com.example.socialmedialapp.android.common.components.PostListItem
import com.example.socialmedialapp.android.common.theming.LargeSpacing
import com.example.socialmedialapp.android.common.theming.MediumSpacing
import com.example.socialmedialapp.android.common.theming.SmallSpacing
import com.example.socialmedialapp.android.common.theming.SocialAppTheme
import com.example.socialmedialapp.android.common.fake_data.Post

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    userInfoUiState: UserInfoUiState,
    profilePostsUiState: ProfilePostsUiState,
    onButtonClick: () -> Unit,
    onFollowerClick: () -> Unit,
    onFollowingClick: () -> Unit,
    onPostClick: (Post) -> Unit,
    onLikeClick: (String) -> Unit,
    onCommentClick: (String) -> Unit,
    fetchData: () -> Unit
) {
    when {
        userInfoUiState.isLoading && profilePostsUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        else -> {
            LazyColumn(
                modifier = modifier.fillMaxSize()
            ) {
                item(key = "header_section") {
                    ProfileHeaderSection(
                        imageUrl = userInfoUiState.profile?.profileUrl ?: "",
                        name = userInfoUiState.profile?.name ?: "",
                        bio = userInfoUiState.profile?.bio ?: "",
                        followersCount = userInfoUiState.profile?.followersCount ?: 0,
                        followingCount = userInfoUiState.profile?.followingCount ?: 0,
                        isCurrentUser = false,
                        isFollowing = false,
                        onButtonClick = onButtonClick,
                        onFollowerClick = onFollowerClick,
                        onFollowingClick = onFollowingClick
                    )
                }
                items(
                    items = profilePostsUiState.posts,
                    key = { post -> post.id }
                ) { post ->
                    PostListItem(
                        post = post,
                        onPostClick = onPostClick,
                        onProfileClick = {},
                        onLikeClick = {onLikeClick},
                        onCommentClick = {onCommentClick}
                    )
                }
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        fetchData()
    }
}

@Composable
fun ProfileHeaderSection(
    modifier: Modifier = Modifier,
    imageUrl: String,
    name: String,
    bio: String,
    followersCount: Int,
    followingCount: Int,
    isCurrentUser: Boolean = false,
    isFollowing: Boolean = false,
    onButtonClick: () -> Unit,
    onFollowerClick: () -> Unit,
    onFollowingClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = MediumSpacing)
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(all = LargeSpacing)
    ) {
        CircleImage(
            modifier = modifier.size(90.dp),
            imageUrl = imageUrl,
            onClick = {}
        )

        Spacer(modifier = modifier.height(SmallSpacing))

        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(text = bio, style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = modifier.height(MediumSpacing))

        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FollowsText(
                    count = followersCount,
                    text = R.string.followers_text,
                    onClick = onFollowerClick
                )

                Spacer(modifier = modifier.width(MediumSpacing))

                FollowsText(
                    count = followingCount,
                    text = R.string.following_text,
                    onClick = onFollowingClick
                )
            }
            FollowsButton(
                text = R.string.follow_button_text,
                onClick = onButtonClick,
                modifier = modifier
                    .heightIn(30.dp)
                    .widthIn(100.dp),
                isOutline = isCurrentUser || isFollowing
            )
        }
    }
}

@Composable
fun FollowsText(
    modifier: Modifier = Modifier,
    count: Int,
    @StringRes text: Int,
    onClick: () -> Unit
) {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            ) {
                append("$count ")
            }
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
            ) {
                append(stringResource(id = text))
            }
        },
        modifier = modifier.clickable { onClick() }
    )
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ProfileHeaderPreview() {
    SocialAppTheme {
        Surface(color = MaterialTheme.colorScheme.surface) {
            ProfileHeaderSection(
                imageUrl = "",
                name = "Md Akhtar",
                bio = "Hey there, welcome to Md Akhtar Profile",
                followersCount = 9,
                followingCount = 2,
                onButtonClick = {},
                onFollowerClick = {},
                onFollowingClick = {}
            )
        }
    }
}
