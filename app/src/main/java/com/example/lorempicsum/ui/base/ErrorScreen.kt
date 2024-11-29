package com.example.lorempicsum.ui.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.lorempicsum.R
import com.example.lorempicsum.ui.theme.dimens

@Composable
fun ErrorScreen(onRetryClick: () -> Unit) {
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
            text = stringResource(R.string.error_message),
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.size(MaterialTheme.dimens.spacerSmall))
        Button(onClick = onRetryClick) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.retry_button_label),
                    style = MaterialTheme.typography.labelLarge
                )
                Spacer(modifier = Modifier.size(MaterialTheme.dimens.spacerSmall))
                Icon(
                    imageVector = Icons.Outlined.Refresh,
                    contentDescription = stringResource(R.string.retry_button_label)
                )
            }
        }
    }
}