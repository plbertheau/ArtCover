package com.plbertheau.data

import com.plbertheau.data.local.TrackDao
import com.plbertheau.data.local.TrackEntity
import com.plbertheau.data.repository.ArtCoverTrackRepositoryImpl
import com.plbertheau.data.service.ArtCoverApi
import com.plbertheau.data.service.TrackDTO
import com.plbertheau.domain.model.Track
import com.plbertheau.domain.repository.ArtCoverTrackRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.any
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class ArtCoverTrackRepositoryImplTest {

    private lateinit var repository: ArtCoverTrackRepository
    private val mockApi: ArtCoverApi = mock()
    private val mockTrackDao: TrackDao = mock()

    // Test data
    private val track1 = Track(
        id = 1,
        albumId = 1,
        title = "accusamus beatae ad facilis cum similique qui sunt",
        url = "https://placehold.co/600x600/92c952/white/png",
        thumbnailUrl = "https://placehold.co/150x150/92c952/white/png"
    )
    private val track2 = Track(
        id = 2,
        albumId = 2,
        title = "reprehenderit est deserunt velit ipsam",
        url = "https://placehold.co/600x600/771796/white/png",
        thumbnailUrl = "https://placehold.co/150x150/771796/white/png"
    )

    private val trackDTO1 = TrackDTO(
        id = 1,
        albumId = 1,
        title = "accusamus beatae ad facilis cum similique qui sunt",
        url = "https://placehold.co/600x600/92c952/white/png",
        thumbnailUrl = "https://placehold.co/150x150/92c952/white/png"
    )
    private val trackDTO2 = TrackDTO(
        id = 2,
        albumId = 2,
        title = "reprehenderit est deserunt velit ipsam",
        url = "https://placehold.co/600x600/771796/white/png",
        thumbnailUrl = "https://placehold.co/150x150/771796/white/png"
    )

    private val trackEntity1 = TrackEntity(
        id = 1,
        albumId = 1,
        title = trackDTO1.title,
        url = trackDTO1.url,
        thumbnailUrl = trackDTO1.thumbnailUrl
    )

    private val trackEntity2 = TrackEntity(
        id = 2,
        albumId = 2,
        title = trackDTO2.title,
        url = trackDTO2.url,
        thumbnailUrl = trackDTO2.thumbnailUrl
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.IO)

        repository = ArtCoverTrackRepositoryImpl(mockApi, mockTrackDao)
    }

    @Test
    fun `getArtCoverTracks should return success when API is successful`() = runBlocking {
        // When
        val apiResponse = listOf(trackDTO1, trackDTO2)
        whenever(mockApi.getTrackList()).thenReturn(apiResponse)

        // Act
        val result = repository.getArtCoverTracks()

        // Assert
        assertTrue(result.isSuccess)
        verify(mockTrackDao).insertTracks(any())
    }

    @Test
    fun `getArtCoverTracks should return failure when API fails`() = runBlocking {
        // When
        whenever(mockApi.getTrackList()).doThrow(RuntimeException("API error"))

        // Act
        val result = repository.getArtCoverTracks()

        // Assert
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is Exception)
    }

    @Test
    fun `getTracksFromDatabase should return success when tracks exist in the database`() = runBlocking {
        // When
        whenever(mockTrackDao.getAllTracks()).thenReturn(listOf(trackEntity1, trackEntity2))

        // Act
        val result: Result<List<Track>> = repository.getTracksFromDatabase()

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrNull()?.size)
        assertTrue(result.getOrNull()?.contains(track1) == true)
        assertTrue(result.getOrNull()?.contains(track2) == true)
    }

    @Test
    fun `getTracksFromDatabase should return empty list when no tracks exist in the database`() = runBlocking {
        // When
        whenever(mockTrackDao.getAllTracks()).thenReturn(emptyList())

        // Act
        val result = repository.getTracksFromDatabase()

        // Assert
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()?.isEmpty() == true)
    }
}
