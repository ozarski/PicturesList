package com.example.lorempicsum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lorempicsum.ui.Screen
import com.example.lorempicsum.ui.picturelistscreen.PictureListScreen
import com.example.lorempicsum.ui.theme.LoremPicsumTheme
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.Route

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
                                startDestination = Screen.PictureListScreen.route
                            ) {
                                composable(Screen.PictureListScreen.route) {
                                    PictureListScreen(
                                        navController = navController,
                                    )
                                }
                                composable(Screen.PictureDetailScreen.route) {
                                    Text(text = "Detail Screen")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}