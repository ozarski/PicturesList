package com.example.lorempicsum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lorempicsum.ui.base.PICTURE_ID_NAV_PARAM
import com.example.lorempicsum.ui.base.Screen
import com.example.lorempicsum.ui.picturedetailsscreen.PictureDetailsScreen
import com.example.lorempicsum.ui.picturelistscreen.PictureListScreen
import com.example.lorempicsum.ui.theme.LoremPicsumTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoremPicsumTheme {
                Scaffold { paddingValues ->
                    Surface(modifier = Modifier.padding(paddingValues)) {
                        Column(
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            val navController = rememberNavController()
                            NavHost(
                                navController = navController,
                                startDestination = Screen.PictureListScreen.route,
                                enterTransition = { EnterTransition.None },
                                exitTransition = { ExitTransition.None },
                            ) {
                                composable(Screen.PictureListScreen.route) {
                                    PictureListScreen(
                                        navController = navController,
                                    )
                                }
                                composable(
                                    Screen.PictureDetailScreen.route + "/{$PICTURE_ID_NAV_PARAM}",
                                    enterTransition = {
                                        slideInVertically(
                                            initialOffsetY = { it },
                                        )
                                    },
                                    exitTransition = {
                                        slideOutVertically(
                                            targetOffsetY = { it },
                                        )
                                    }
                                ) {
                                    PictureDetailsScreen()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}