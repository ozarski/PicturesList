package com.example.lorempicsum.ui.picturelistscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.SubcomposeAsyncImage
import com.example.lorempicsum.ui.base.ErrorScreen
import com.example.lorempicsum.ui.base.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PictureListScreen(
    navController: NavController,
    viewModel: PictureListViewModel = hiltViewModel()
) {

    val state = viewModel.state.value

    PullToRefreshBox(
        isRefreshing = state.isRefreshing,
        state = rememberPullToRefreshState(),
        onRefresh = {
            viewModel.refresh()
        }
    ) {
        if (state.isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CircularProgressIndicator()
            }
        } else if (state.error.isNotBlank() && state.pictures.isEmpty()) {
            ErrorScreen { viewModel.reload() }
        } else {
            val buffer = 4
            val listState = rememberLazyStaggeredGridState()
            val reachedBottom: Boolean by remember {
                derivedStateOf {
                    val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
                    if (lastVisibleItem != null) {
                        lastVisibleItem.index != 0
                                && lastVisibleItem.index >= listState.layoutInfo.totalItemsCount - buffer
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


            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(5.dp)
            ) {

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
                            modifier = Modifier.clip(MaterialTheme.shapes.small).clickable {
                                navController.navigate(Screen.PictureDetailScreen.route + "/${picture.id}")
                            },
                            loading = {
                                Column(
                                    modifier = Modifier.size(
                                        150.dp
                                    ),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    CircularProgressIndicator(modifier = Modifier.size(25.dp))
                                }
                            },
                            error = {
                                Column(
                                    modifier = Modifier.size(
                                        150.dp
                                    ),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = "Error loading image")
                                }
                            },
                        )
                    }
                    if (state.loadingMore) {
                        item(span = StaggeredGridItemSpan.FullLine) {
                            Row(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    } else if (state.error.isNotBlank()) {
                        item(span = StaggeredGridItemSpan.FullLine) {
                            Row(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                            ) {
                                Text(text = state.error, textAlign = TextAlign.Center)
                            }
                        }
                    }
                }
            }
        }
    }

}