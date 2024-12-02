package com.example.lorempicsum.ui.picturelistscreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lorempicsum.R
import com.example.lorempicsum.domain.model.PictureListItem
import com.example.lorempicsum.ui.base.Picture
import com.example.lorempicsum.ui.base.Screen
import com.example.lorempicsum.ui.theme.dimens

@Composable
fun ListItem(picture: PictureListItem, navController: NavController) {
    val contentPadding = 5.dp
    Card(
        onClick = {
            navController.navigate(Screen.PictureDetailScreen.route + "/${picture.id}")
        },
        shape = MaterialTheme.shapes.medium,
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
        )
    ) {
        Column(
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Picture(url = picture.downloadUrl)
            Spacer(modifier = Modifier.size(MaterialTheme.dimens.spacerExtraSmall))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    stringResource(R.string.picture_list_item_picture_id_label),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(picture.id.toString(), color = MaterialTheme.colorScheme.onPrimaryContainer)
            }
        }
    }
}