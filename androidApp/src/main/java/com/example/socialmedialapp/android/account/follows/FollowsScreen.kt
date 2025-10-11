package com.example.socialmedialapp.android.account.follows

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun FollowsScreen(
    modifier: Modifier=Modifier,
    uiState: FollowsUiState,
    fetchFollows:()->Unit,
    onItemClick:(Int)->Unit
) {

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            items(items=uiState.followsUser,key={user->user.id}){
                FollowsListItem(name = it.name, bio = it.bio, imageUrl = it.profileUrl) {
                    onItemClick(it.id)
                }
            }
        }

        if(uiState.isLoading && uiState.followsUser.isEmpty()){
            CircularProgressIndicator()
        }
    }
    LaunchedEffect(key1=Unit, block = {fetchFollows()})
}