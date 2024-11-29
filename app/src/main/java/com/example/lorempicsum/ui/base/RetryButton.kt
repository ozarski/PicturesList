package com.example.lorempicsum.ui.base

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
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
fun RetryButton(onRetryClick: () -> Unit) {
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