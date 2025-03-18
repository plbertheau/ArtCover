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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
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
        val context = LocalContext.current
        val build = ImageRequest.Builder(context)
            .data(trackResponse.url)
            .crossfade(true)  // Optional: Adds a fade animation
            .diskCachePolicy(CachePolicy.ENABLED)  // Enables disk caching
            .memoryCachePolicy(CachePolicy.ENABLED) // Enables memory caching
            .networkCachePolicy(CachePolicy.ENABLED) // Enables network caching
            .transformations(CircleCropTransformation()) // Optional: Crop image
            .addHeader("User-Agent", "ArtCover/1.0") // Optional additional headers
            .build()
        AsyncImage(
            model = build,
            modifier = Modifier
                .wrapContentWidth(),
            contentScale = ContentScale.FillHeight,
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = trackResponse.title,
            style = MaterialTheme.typography.titleSmall,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
        )
    }
}