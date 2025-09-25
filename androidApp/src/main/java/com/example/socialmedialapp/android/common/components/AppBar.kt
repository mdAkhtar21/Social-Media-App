package com.example.socialmedialapp.android.common.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.socialmedialapp.android.R
import com.example.socialmedialapp.android.common.theming.SmallElevation
import com.example.socialmedialapp.android.destinations.EditProfileDestination
import com.example.socialmedialapp.android.destinations.FollowersDestination
import com.example.socialmedialapp.android.destinations.FollowingDestination
import com.example.socialmedialapp.android.destinations.HomeScreenDestination
import com.example.socialmedialapp.android.destinations.LoginDestination
import com.example.socialmedialapp.android.destinations.PostDetailDestination
import com.example.socialmedialapp.android.destinations.ProfileDestination
import com.example.socialmedialapp.android.destinations.SignUpDestination
import com.example.socialmedialapp.android.post.PostDetail
import com.ramcosta.composedestinations.utils.currentDestinationAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    val currentDestination = navHostController.currentDestinationAsState().value

    Surface(modifier = modifier, shadowElevation = SmallElevation) {
        TopAppBar(
            title = {
                Text(text = stringResource(id = getAppBarTitle(currentDestination?.route)))
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            actions = {
                IconButton(onClick = { /* Handle action */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.person_circle_icon),
                        contentDescription = null
                    )
                }
            },
            navigationIcon = {
                if (shouldShowNavigationIcon(currentDestination?.route)) {
                    IconButton(onClick = {navHostController.navigateUp() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.round_arrow_back),
                            contentDescription = null
                        )
                    }
                }
                else null
            }
        )
    }
}

private fun getAppBarTitle(currentDestination: String?): Int {
    return when (currentDestination) {
        LoginDestination.route -> R.string.login_destination_title
        SignUpDestination.route -> R.string.signUp_destinatin_title
        HomeScreenDestination.route -> R.string.home_destination_title
        PostDetailDestination.route ->R.string.post_detail_destination_title
        ProfileDestination.route->R.string.profile_destination_title
        EditProfileDestination.route->R.string.edit_profile_destination_title
        FollowingDestination.route->R.string.following_text
        FollowersDestination.route->R.string.followers_text
        else -> R.string.no_destination_title
    }
}

private fun shouldShowNavigationIcon(currentDestination: String?): Boolean {
    // The navigation icon should appear on screens you can navigate back from
    return !(
            currentDestination == LoginDestination.route ||
                    currentDestination == SignUpDestination.route ||
                    currentDestination == HomeScreenDestination.route
            )
}