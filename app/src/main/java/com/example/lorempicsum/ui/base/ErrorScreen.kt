package com.example.lorempicsum.ui.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.lorempicsum.R
import com.example.lorempicsum.ui.theme.dimens

@Composable
fun ErrorScreen(message: String, onRetryClick: () -> Unit) {
    val textWidth = 400.dp

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Icon(
            imageVector = Icons.Default.Clear,
            contentDescription = stringResource(R.string.error_icon_content_description),
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(MaterialTheme.dimens.iconLarge)
        )
        Spacer(modifier = Modifier.size(MaterialTheme.dimens.spacerSmall))
        Text(
            text = message,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.sizeIn(maxWidth = textWidth)
        )
        Spacer(modifier = Modifier.size(MaterialTheme.dimens.spacerSmall))
        RetryButton(onRetryClick = onRetryClick)
    }
}