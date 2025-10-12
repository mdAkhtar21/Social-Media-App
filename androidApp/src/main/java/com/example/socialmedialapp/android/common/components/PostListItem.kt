package com.example.socialmedialapp.android.common.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.socialmedialapp.android.R
import com.example.socialmedialapp.android.common.dummy_data.samplePosts
import com.example.socialmedialapp.android.common.theming.DarkGray
import com.example.socialmedialapp.android.common.theming.ExtraLargeSpacing
import com.example.socialmedialapp.android.common.theming.LargeSpacing
import com.example.socialmedialapp.android.common.theming.LightGray
import com.example.socialmedialapp.android.common.theming.MediumSpacing
import com.example.socialmedialapp.android.common.theming.SocialAppTheme
import com.example.socialmedialapp.android.common.util.toCurrentUrl
import com.example.socialmedialapp.common.domain.model.Post

@Composable
fun PostListItem(
    modifier: Modifier = Modifier,
    post: Post,
    onPostClick: ((Post) -> Unit)? = null,
    onProfileClick: (userId: Long) -> Unit,
    onLikeClick: (Post) -> Unit,
    onCommentClick: (Post) -> Unit,
    maxLines: Int = Int.MAX_VALUE
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            //.aspectRatio(ratio = 0.7f)
            .background(color = MaterialTheme.colorScheme.surface)
            //.clickable { onPostClick(post) }
            .let { mod ->
                if (onPostClick != null) {
                    mod.clickable { onPostClick(post) }.padding(bottom = ExtraLargeSpacing)
                } else {
                    mod
                }
            }
    ) {
        PostHeader(
            name = post.userName,
            profileUrl = post.userImageUrl,
            date = post.createdAt,
            onProfileClick = {
                onProfileClick(
                    post.userId
                )
            }
        )

        AsyncImage(
            model = post.imageUrl.toCurrentUrl(),
            contentDescription = null,
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(ratio = 1.0f),
            contentScale = ContentScale.Crop,
            placeholder = if (isSystemInDarkTheme()) {
                painterResource(id = R.drawable.light_image_place_holder)
            } else {
                painterResource(id = R.drawable.dark_image_place_holder)
            }
        )

        PostLikesRow(
            likesCount = post.likesCount,
            commentCount = post.commentsCount,
            onLikeClick = { onLikeClick(post) },
            isPostLiked = post.isLiked,
            onCommentClick = { onCommentClick(post) }
        )

        Text(
            text = post.caption,
            style = MaterialTheme.typography.bodyMedium,
            modifier = modifier.padding(horizontal = LargeSpacing),
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis
        )
    }
}


@Composable
fun PostHeader(
    modifier: Modifier = Modifier,
    name: String,
    profileUrl: String?,
    date: String,
    onProfileClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = LargeSpacing,
                vertical = MediumSpacing
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MediumSpacing)
    ) {
        CircleImage(
            modifier = modifier.size(30.dp),
            url = profileUrl?.toCurrentUrl(),
            onClick = onProfileClick
        )

        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Box(
            modifier = modifier
                .size(4.dp)
                .clip(CircleShape)
                .background(
                    color = if (isSystemInDarkTheme()) {
                        LightGray
                    } else {
                        DarkGray
                    }
                )
        )

        Text(
            text = date,
            style = MaterialTheme.typography.labelMedium.copy(
                textAlign = TextAlign.Start,
                fontSize = 12.sp,
                color = if (isSystemInDarkTheme()) {
                    LightGray
                } else {
                    DarkGray
                }
            ),
            modifier = modifier.weight(1f)
        )

        Icon(
            painter = painterResource(id = R.drawable.round_more_horizontal),
            contentDescription = null,
            tint = if (isSystemInDarkTheme()) {
                LightGray
            } else {
                DarkGray
            }
        )
    }
}


@Composable
fun PostLikesRow(
    modifier: Modifier = Modifier,
    likesCount: Int,
    commentCount: Int,
    onLikeClick: () -> Unit,
    isPostLiked: Boolean,
    onCommentClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = 0.dp,
                horizontal = MediumSpacing
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onLikeClick
        ) {
            Icon(
                painter = if (isPostLiked) {
                    painterResource(id = R.drawable.like_icon_filled)
                } else {
                    painterResource(id = R.drawable.like_icon_outlined)
                },
                contentDescription = null,
                tint = if (isPostLiked) Red else DarkGray
            )
        }

        Text(
            text = "$likesCount",
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp)
        )

        Spacer(modifier = modifier.width(MediumSpacing))

        IconButton(
            onClick = onCommentClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.chat_icon_outlined),
                contentDescription = null,
                tint = if (isSystemInDarkTheme()) {
                    LightGray
                } else {
                    DarkGray
                }
            )
        }

        Text(
            text = "$commentCount",
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp)
        )
    }
}


@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PostListItemPreview() {
    SocialAppTheme {
        Surface(color = MaterialTheme.colorScheme.surface) {
            PostListItem(
                post = samplePosts.first().toDomainPost(),
                onPostClick = {},
                onProfileClick = {},
                onCommentClick = {},
                onLikeClick = {}
            )
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PostHeaderPreview() {
    SocialAppTheme {
        Surface(color = MaterialTheme.colorScheme.surface) {
            PostHeader(
                name = "Mr Dip",
                profileUrl = "",
                date = "20 min",
                onProfileClick = {}
            )
        }
    }
}


@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PostLikesRowPreview() {
    SocialAppTheme {
        Surface(color = MaterialTheme.colorScheme.surface) {
            PostLikesRow(
                likesCount = 12,
                commentCount = 2,
                onLikeClick = {},
                isPostLiked = true,
                onCommentClick = {}
            )
        }
    }
}