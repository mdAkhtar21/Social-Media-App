package com.example.socialmedialapp.android.post.create_post

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material3.IconButton
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.socialmedialapp.android.R
import com.example.socialmedialapp.android.common.components.ScreenLevelLoadingView
import com.example.socialmedialapp.android.common.theming.ButtonHeight
import com.example.socialmedialapp.android.common.theming.ExtraLargeSpacing
import com.example.socialmedialapp.android.common.theming.Gray
import com.example.socialmedialapp.android.common.theming.LargeSpacing

@Composable
fun CreatePostScreen(
    modifier: Modifier = Modifier,
    createPostUiState: CreatePostUiState,
    onPostCreated: () -> Unit,
    onUiAction: (CreatePostUiAction) -> Unit
) {
    val context = LocalContext.current
    var selectedImage by rememberSaveable { mutableStateOf<Uri?>(null) }
    val pickImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImage = uri }
    )

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(
                    color = if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.background
                    } else {
                        MaterialTheme.colorScheme.surface
                    }
                )
                .padding(ExtraLargeSpacing),
            verticalArrangement = Arrangement.spacedBy(LargeSpacing),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PostCaptionTextField(
                caption = createPostUiState.caption,
                onCaptionChange = {
                    onUiAction(CreatePostUiAction.CaptionChanged(it))
                }
            )

            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(LargeSpacing),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.select_post_image_label),
                    style = MaterialTheme.typography.titleMedium
                )

                selectedImage?.let {
                    AsyncImage(
                        model = it,
                        contentDescription = null,
                        modifier = modifier
                            .size(70.dp)
                            .clip(MaterialTheme.shapes.medium)
                            .clickable {
                                pickImage.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            },
                        contentScale = ContentScale.Crop
                    )
                } ?: run {
                    IconButton(
                        onClick = {
                            pickImage.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        },
                        modifier = modifier
                            .size(32.dp)
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.add_image_icon),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = modifier.fillMaxSize()
                        )
                    }
                }
            }

            Button(
                onClick = {
                    selectedImage?.let {
                        onUiAction(CreatePostUiAction.CreatePostAction(it))
                    }
                },
                modifier = modifier
                    .fillMaxWidth()
                    .height(ButtonHeight),
                elevation = ButtonDefaults.elevatedButtonElevation(0.dp),
                shape = MaterialTheme.shapes.medium,
                enabled = createPostUiState.caption.isNotBlank()
                        && !createPostUiState.isLoading
                        && selectedImage != null
            ) {
                Text(text = stringResource(id = R.string.create_post_button_label))
            }
        }

        if (createPostUiState.isLoading) {
            ScreenLevelLoadingView()
        }
    }
    if (createPostUiState.postCreated) {
        onPostCreated()
    }
    if (createPostUiState.errorMessage != null) {
        Toast.makeText(context, createPostUiState.errorMessage, Toast.LENGTH_SHORT).show()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PostCaptionTextField(
    modifier: Modifier = Modifier,
    caption: String,
    onCaptionChange: (String) -> Unit
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp),
        value = caption,
        onValueChange = onCaptionChange,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = if (isSystemInDarkTheme()) {
                MaterialTheme.colorScheme.surface
            } else {
                Gray
            },
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        )
,
        textStyle = MaterialTheme.typography.bodyMedium,
        placeholder = {
            Text(
                text = stringResource(id = R.string.post_caption_hint),
                style = MaterialTheme.typography.bodyMedium
            )
        },
        shape = MaterialTheme.shapes.medium
    )
}