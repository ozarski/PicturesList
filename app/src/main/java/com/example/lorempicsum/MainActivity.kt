package com.example.lorempicsum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lorempicsum.ui.base.PICTURE_ID_NAV_PARAM
import com.example.lorempicsum.ui.base.Screen
import com.example.lorempicsum.ui.picturedetailsscreen.PictureDetailsScreen
import com.example.lorempicsum.ui.picturelistscreen.PictureListScreen
import com.example.lorempicsum.ui.theme.LoremPicsumTheme
import com.example.lorempicsum.ui.theme.statusBarColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Box(Modifier.fillMaxSize()) {
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

                Spacer(
                    modifier = Modifier
                        .windowInsetsTopHeight(WindowInsets.statusBars)
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .background(
                            statusBarColor
                        )
                )
            }
        }
    }
}