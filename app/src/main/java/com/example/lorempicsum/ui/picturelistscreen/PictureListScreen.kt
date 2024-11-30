package com.example.lorempicsum.ui.picturelistscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lorempicsum.R
import com.example.lorempicsum.ui.base.ErrorScreen
import com.example.lorempicsum.ui.base.LoadingScreen
import com.example.lorempicsum.ui.base.RetryButton
import com.example.lorempicsum.ui.theme.dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PictureListScreen(
    navController: NavController,
    viewModel: PictureListViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val gridCellSize = 150.dp

    PullToRefreshBox(
        isRefreshing = state.isRefreshing,
        state = rememberPullToRefreshState(),
        onRefresh = {
            viewModel.refresh()
        }
    ) {
        if (state.isLoading) {
            LoadingScreen()
        } else if (state.error.isNotBlank() && state.pictures.isEmpty()) {
            ErrorScreen(state.error) { viewModel.reload() }
        } else {

            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(MaterialTheme.dimens.paddingSmall)
            ) {

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

                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Adaptive(gridCellSize),
                    verticalItemSpacing = MaterialTheme.dimens.paddingExtraSmall,
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.paddingExtraSmall),
                    state = listState,
                ) {
                    items(state.pictures.size) { index ->
                        val picture = state.pictures[index]
                        ListItem(picture, navController)
                    }
                    if (state.loadingMore) {
                        item(span = StaggeredGridItemSpan.FullLine) {
                            LoadingMoreAppend()
                        }
                    } else if (state.error.isNotBlank()) {
                        item(span = StaggeredGridItemSpan.FullLine) {
                            ErrorAppend{
                                viewModel.loadMore()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingMoreAppend(){
    Row(
        modifier = Modifier
            .padding(MaterialTheme.dimens.paddingMedium)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorAppend(onRetry: () -> Unit){
    Column(
        modifier = Modifier
            .padding(MaterialTheme.dimens.paddingMedium)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = stringResource(R.string.loading_next_pictures_error),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.error
        )
        RetryButton {
            onRetry()
        }
    }
}