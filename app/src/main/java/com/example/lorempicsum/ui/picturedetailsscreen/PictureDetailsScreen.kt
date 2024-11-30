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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lorempicsum.R
import com.example.lorempicsum.ui.base.ErrorScreen
import com.example.lorempicsum.ui.base.LoadingScreen
import com.example.lorempicsum.ui.base.Picture
import com.example.lorempicsum.ui.theme.dimens


@Composable
fun PictureDetailsScreen(
    viewModel: PictureDetailsViewModel = hiltViewModel(),
) {

    val state = viewModel.state.value
    val pictureMaxHeight = 500.dp

    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        DetailsContainer(
            pictureID = if (state.id != null) {
                state.id.toString()
            } else stringResource(
                id = R.string.unknown_value
            )
        ) {
            if (state.isLoading) {
                LoadingScreen()
            } else if (state.error.isNotBlank()) {
                ErrorScreen(state.error) {
                    viewModel.reload()
                }
            } else {
                Picture(
                    pictureMaxHeight = pictureMaxHeight,
                    url = state.picture?.downloadUrl ?: ""
                )
                Spacer(modifier = Modifier.size(MaterialTheme.dimens.spacerExtraLarge))
                HorizontalDivider(
                    thickness = MaterialTheme.dimens.dividerThickness,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.size(MaterialTheme.dimens.spacerLarge))
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        stringResource(id = R.string.picture_details_label),
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.size(MaterialTheme.dimens.spacerMedium))
                    InfoRow(
                        stringResource(id = R.string.picture_author_label),
                        state.picture?.author ?: stringResource(id = R.string.unknown_value)
                    )
                    Spacer(modifier = Modifier.size(MaterialTheme.dimens.spacerSmall))
                    InfoRow(
                        stringResource(id = R.string.picture_width_label),
                        if (state.picture?.width != null) {
                            state.picture.width.toString()
                        } else stringResource(id = R.string.unknown_value)
                    )
                    Spacer(modifier = Modifier.size(MaterialTheme.dimens.spacerSmall))
                    InfoRow(
                        stringResource(id = R.string.picture_height_label),
                        if (state.picture?.height != null) {
                            state.picture.height.toString()
                        } else stringResource(id = R.string.unknown_value)
                    )
                    Spacer(modifier = Modifier.size(MaterialTheme.dimens.spacerSmall))
                    DownloadUrlRow(
                        state.picture?.downloadUrl
                            ?: stringResource(id = R.string.no_download_url)
                    ) {
                        viewModel.copyUrlToClipboard()
                    }
                }
            }
        }
    }
}

@Composable
fun DetailsContainer(pictureID: String, content: @Composable () -> Unit) {

    Scaffold(
        topBar = {
            DetailsTopBar(
                pictureID
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(
                    horizontal = MaterialTheme.dimens.paddingMedium,
                    vertical = MaterialTheme.dimens.paddingExtraLarge
                ),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content()
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
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                copyOnClick()
            },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.picture_download_url_label),
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

@Composable
fun DetailsTopBar(id: String) {
    Row(
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    bottomStart = MaterialTheme.dimens.cornerRadiusMedium,
                    bottomEnd = MaterialTheme.dimens.cornerRadiusMedium
                )
            )
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .fillMaxWidth(),
    ) {
        Text(
            text = stringResource(
                id = R.string.details_picture_id,
                id
            ),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(MaterialTheme.dimens.paddingMedium)
        )
    }
}