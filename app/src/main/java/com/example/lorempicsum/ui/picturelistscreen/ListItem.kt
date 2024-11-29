package com.example.lorempicsum.ui.picturelistscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.lorempicsum.R
import com.example.lorempicsum.domain.model.PictureListItem
import com.example.lorempicsum.ui.base.Picture
import com.example.lorempicsum.ui.base.Screen
import com.example.lorempicsum.ui.theme.dimens

@Composable
fun ListItem(picture: PictureListItem, navController: NavController){
    Column(
        modifier = Modifier
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colorScheme.surfaceBright)
            .clickable {
                navController.navigate(Screen.PictureDetailScreen.route + "/${picture.id}")
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Picture(url = picture.downloadUrl)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.dimens.paddingExtraSmall)
        ) {
            Text(stringResource(R.string.picture_list_item_picture_id_label))
            Text(picture.id.toString())
        }
    }
}