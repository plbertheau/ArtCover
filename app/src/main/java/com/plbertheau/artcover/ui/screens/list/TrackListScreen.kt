package com.plbertheau.artcover.ui.screens.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plbertheau.artcover.model.TrackUiModel
import com.plbertheau.artcover.ui.screens.error.ErrorState
import com.plbertheau.artcover.ui.screens.loading.LoadingState
import com.plbertheau.artcover.viewmodel.TrackViewModel
import com.plbertheau.artcover.viewmodel.UiState

@Composable
fun TrackListScreen(viewModel: TrackViewModel, modifier: Modifier = Modifier) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        is UiState.Loading -> LoadingState()
        is UiState.Success -> TrackList(
            tracks = (uiState as UiState.Success<List<TrackUiModel>>).data,
            modifier = modifier
        )
        is UiState.Error -> ErrorState(
            (uiState as UiState.Error).message,
            modifier = modifier.fillMaxSize(),
            onClickRetry = { viewModel.fetchTracks() })
    }
}

