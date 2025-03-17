package com.plbertheau.artcover.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.plbertheau.artcover.viewmodel.TrackViewModel
import com.plbertheau.data.model.TrackResponse

@Composable
fun TrackListScreen(viewModel: TrackViewModel, modifier: Modifier) {
    val tracks by remember { viewModel.tracks }
    val configuration = LocalConfiguration.current

    val columns = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 5 else 3
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(tracks.size) { index ->
            TrackItem(tracks[index])
        }
    }
}

@Composable
fun TrackItem(trackResponse: TrackResponse) {
    Column(modifier = Modifier.padding(16.dp)) {
        AsyncImage(
            model = trackResponse.url,
            modifier = Modifier
                .wrapContentWidth(),
            contentScale = ContentScale.FillHeight,
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = trackResponse.title, style = MaterialTheme.typography.titleLarge)
    }
}