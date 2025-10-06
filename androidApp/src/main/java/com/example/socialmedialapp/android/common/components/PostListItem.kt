package com.example.socialmedialapp.android.common.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.UiMode
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.socialmedialapp.android.R
import com.example.socialmedialapp.android.common.theming.DarkGray
import com.example.socialmedialapp.android.common.theming.ExtraLargeSpacing
import com.example.socialmedialapp.android.common.theming.LargeSpacing
import com.example.socialmedialapp.android.common.theming.LightGray
import com.example.socialmedialapp.android.common.theming.MediumSpacing
import com.example.socialmedialapp.android.common.theming.SmallSpacing
import com.example.socialmedialapp.android.common.theming.SocialAppTheme
import com.example.socialmedialapp.android.common.fake_data.Post

@Composable
fun PostListItem(
    modifier: Modifier = Modifier,
    post: Post,
    onPostClick: (Post) -> Unit,
    onProfileClick: (Int) -> Unit,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
    isDetailScreen: Boolean = false
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface)
            .clickable { onPostClick(post) }
            .padding(bottom = ExtraLargeSpacing)
    ) {
        PostItemHeader(
            name = post.authorName,
            profileUrl = post.authorImage,
            date = post.createdAt,
            onProfileClick = { onProfileClick(post.authorId) }
        )

        AsyncImage(
            model = post.imageUrl, // ✅ fixed
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ratio = 1.0f),
            contentScale = ContentScale.Crop,
            placeholder = if (isSystemInDarkTheme()) {
                painterResource(id = R.drawable.dark_image_place_holder)
            } else {
                painterResource(id = R.drawable.light_image_place_holder)
            }
        )

        PostLikesRow(
            likesCount = post.likesCount,
            commentsCount = post.commentCount,
            onLikeClickClick = onLikeClick,
            onCommentClick = onCommentClick
        )

        Text(
            text = post.text,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = LargeSpacing),
            maxLines = if (isDetailScreen) 20 else 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun PostItemHeader(
    modifier: Modifier = Modifier,
    name: String,
    profileUrl: String,
    date: String,
    onProfileClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = LargeSpacing, vertical = MediumSpacing),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MediumSpacing)
    ) {
        CircleImage(
            imageUrl = profileUrl,
            modifier = Modifier.size(30.dp)
        ) {
            onProfileClick()
        }

        Text(
            text = name,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface
        )

        Box(
            modifier = Modifier
                .size(4.dp)
                .clip(CircleShape)
                .background(
                    color = if (isSystemInDarkTheme()) DarkGray else LightGray
                )
        )

        Text(
            text = date,
            style = MaterialTheme.typography.labelSmall.copy(
                textAlign = TextAlign.Start,
                fontSize = 12.sp,
                color = if (isSystemInDarkTheme()) LightGray else DarkGray
            ),
            modifier = Modifier.weight(1f)
        )

        Icon(
            painter = painterResource(id = R.drawable.round_more_horizontal),
            contentDescription = null,
            tint = if (isSystemInDarkTheme()) LightGray else DarkGray
        )
    }
}

@Composable
fun PostLikesRow(
    modifier: Modifier = Modifier,
    likesCount: Int,
    commentsCount: Int,
    onLikeClickClick: () -> Unit,
    onCommentClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 0.dp, horizontal = MediumSpacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onLikeClickClick) {
            Icon(
                painter = painterResource(id = R.drawable.like_icon_outlined),
                contentDescription = null,
                tint = if (isSystemInDarkTheme()) LightGray else DarkGray
            )
        }

        Text(
            text = "$likesCount", // ✅ fixed to show likes
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 18.sp
            )
        )

        Spacer(modifier = Modifier.width(SmallSpacing))

        IconButton(onClick = onCommentClick) {
            Icon(
                painter = painterResource(id = R.drawable.chat_icon_outlined),
                contentDescription = null,
                tint = if (isSystemInDarkTheme()) LightGray else DarkGray
            )
        }

        Text(
            text = "$commentsCount",
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 18.sp
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PostListItemPreview() {
    SocialAppTheme {
        PostListItem(
            post = Post(
                id = "1",
                authorId = 101,
                authorName = "John Doe",
                authorImage = "https://example.com/profile.jpg",
                imageUrl = "https://example.com/post.jpg",
                text = "This is a sample post for preview purposes.",
                likesCount = 20,
                commentCount = 30,
                createdAt = "2h ago"
            ),
            onPostClick = {},
            onProfileClick = {},
            onLikeClick = {},
            onCommentClick = {}
        )
    }
}
