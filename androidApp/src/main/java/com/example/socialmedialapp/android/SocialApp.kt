package com.example.socialmedialapp.android

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.socialmedialapp.android.common.components.AppBar
import com.example.socialmedialapp.android.destinations.HomeScreenDestination
import com.example.socialmedialapp.android.destinations.LoginDestination
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SocialApp(
    token:String?
) {
    val navHostController = rememberNavController()
    val systemUiController = rememberSystemUiController()
    val isSystemInDark = isSystemInDarkTheme()
    val statusBarColor = if (isSystemInDark) {
        MaterialTheme.colorScheme.surface
    } else {
        MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
    }

    SideEffect {
        systemUiController.setStatusBarColor(
            color = statusBarColor,
            darkIcons = !isSystemInDark
        )
    }

    Scaffold(
        topBar = {
            AppBar(navHostController = navHostController)
        }
    ) { innerPadding ->
        DestinationsNavHost(
            modifier = Modifier.padding(innerPadding),
            navGraph = NavGraphs.root,
            navController = navHostController
        )
    }
    LaunchedEffect(key1 = token, block = {
        if(token!=null && token.isBlank()){
            navHostController.navigate(LoginDestination.route){
                popUpTo(HomeScreenDestination.route){
                    inclusive=true
                }
            }
        }
    })
}