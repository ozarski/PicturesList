package com.example.lorempicsum.ui.picturelistscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.SubcomposeAsyncImage
import com.example.lorempicsum.data.datasource.DEFAULT_PAGE_SIZE

@Composable
fun PictureListScreen(
    navController: NavController,
    viewModel: PictureListViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val loadedImages = remember { mutableIntStateOf(0) }

    if (state.isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else if (state.error.isNotBlank()) {
        Text(text = state.error)
    } else {
        val buffer = 2
        val listState = rememberLazyStaggeredGridState()
        val reachedBottom: Boolean by remember {
            derivedStateOf {
                val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
                if (lastVisibleItem != null) {
                    lastVisibleItem.index != 0
                            && lastVisibleItem.index >= listState.layoutInfo.totalItemsCount - buffer
                            && loadedImages.intValue >= DEFAULT_PAGE_SIZE
                } else {
                    false
                }
            }
        }

        LaunchedEffect(reachedBottom) {
            if (reachedBottom) {
                viewModel.loadMore()
            }
        }

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(minSize = 150.dp),
            verticalItemSpacing = 5.dp,
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            state = listState,
        ) {
            items(state.pictures.size) { index ->
                val picture = state.pictures[index]
                SubcomposeAsyncImage(
                    model = picture.downloadUrl,
                    contentDescription = "unsplash picture",
                    modifier = Modifier.clip(shape = RoundedCornerShape(10.dp)),
                    loading = {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(25.dp))
                        }
                    },
                    onSuccess = {
                        loadedImages.value += 1
                    }
                )
            }
        }
    }
}