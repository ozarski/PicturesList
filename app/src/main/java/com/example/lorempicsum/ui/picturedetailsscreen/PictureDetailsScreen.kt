package com.example.lorempicsum.ui.picturedetailsscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.SubcomposeAsyncImage


@Composable
fun PictureDetailsScreen(
    viewModel: PictureDetailsViewModel = hiltViewModel(),
) {

    val state = viewModel.state.value

    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        if (state.isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        } else if (state.error.isNotBlank()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = state.error,
                )
            }
        } else {
            Scaffold(
                topBar = {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
                            .background(color = MaterialTheme.colorScheme.primaryContainer)
                            .fillMaxWidth(),
                    ) {
                        Text(
                            text = "Picture ID: ${state.picture?.id}",
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 16.dp, vertical = 32.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SubcomposeAsyncImage(
                        modifier = Modifier
                            .sizeIn(maxHeight = 500.dp)
                            .clip(MaterialTheme.shapes.small),
                        model = state.picture?.downloadUrl ?: "",
                        contentDescription = state.picture?.author ?: "",
                        loading = {
                            Column(
                                modifier = Modifier.size(250.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CircularProgressIndicator(modifier = Modifier.size(25.dp))
                            }
                        },
                        error = {
                            Column(
                                modifier = Modifier.size(250.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "Error loading image")
                            }
                        },
                    )
                    Spacer(modifier = Modifier.size(32.dp))
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.size(24.dp))
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            "Picture Details",
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.size(16.dp))
                        InfoRow("Author", state.picture?.author ?: "author unknown")
                        Spacer(modifier = Modifier.size(8.dp))
                        InfoRow("Width", state.picture?.width.toString())
                        Spacer(modifier = Modifier.size(8.dp))
                        InfoRow("Height", state.picture?.height.toString())
                        Spacer(modifier = Modifier.size(8.dp))
                        DownloadUrlRow(state.picture?.downloadUrl ?: "no download url") {
                            viewModel.copyUrlToClipboard()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun DownloadUrlRow(url: String, copyOnClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable {
            copyOnClick()
        },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Download URL",
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = url,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.sizeIn(maxWidth = 200.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}