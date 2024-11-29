package com.example.lorempicsum.ui.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import coil3.compose.SubcomposeAsyncImage
import com.example.lorempicsum.R
import com.example.lorempicsum.ui.theme.dimens

@Composable
fun Picture(pictureMaxHeight: Dp, url: String){
    SubcomposeAsyncImage(
        modifier = Modifier
            .sizeIn(maxHeight = pictureMaxHeight)
            .clip(MaterialTheme.shapes.small),
        model = url,
        contentDescription = stringResource(id = R.string.picture_image_content_description),
        loading = {
            Column(
                modifier = Modifier.size(MaterialTheme.dimens.imageNotLoadedSize),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        },
        error = {
            Column(
                modifier = Modifier.size(MaterialTheme.dimens.imageNotLoadedSize),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(id = R.string.loading_image_error))
            }
        },
    )
}