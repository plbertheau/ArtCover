package com.plbertheau.domain

import com.plbertheau.domain.model.Track
import com.plbertheau.domain.repository.ArtCoverTrackRepository
import com.plbertheau.domain.usecase.GetTracksUseCase
import com.plbertheau.domain.usecase.GetTracksUseCase.Result
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


@RunWith(MockitoJUnitRunner::class)
class GetTracksUseCaseTest {


    private var repository: ArtCoverTrackRepository = mock()

    private lateinit var getTracksUseCase: GetTracksUseCase

    @Before
    fun setUp() {
        getTracksUseCase = GetTracksUseCase(repository)
    }

    @Test
    fun `execute should emit Success when tracks from DB are non-empty`() = runTest {
        // when
        val mockTracks = listOf(track1, track2)
        val result: kotlin.Result<List<Track>> = kotlin.Result.success(mockTracks)

        whenever(repository.getTracksFromDatabase()).doReturn(result)

        // then
        val flow = getTracksUseCase()

        // Assert
        val result1 = flow.last()

        assertTrue(result1 is Result.Success<*>)
        assertEquals(mockTracks, (result1 as Result.Success<*>).data)

    }

    @Test
    fun `execute should fetch tracks from API when DB is empty`() = runTest {
        // when
        val mockTracks = listOf(track1, track2)
        val dbResult: kotlin.Result<List<Track>> = kotlin.Result.success(emptyList())
        val apiResult: kotlin.Result<List<Track>> = kotlin.Result.success(mockTracks)


        whenever(repository.getTracksFromDatabase()).doReturn(dbResult)
        whenever(repository.getArtCoverTracks()).doReturn(apiResult)

        // then
        val flow = getTracksUseCase()

        // Assert
        val result1 = flow.last()

        assertTrue(result1 is Result.Success<*>)
        assertEquals(mockTracks, (result1 as Result.Success<*>).data)

    }

    @Test
    fun `execute should emit Error when API fails`() = runTest {
        // when
        val dbResult: kotlin.Result<List<Track>> = kotlin.Result.success(emptyList())
        val apiResult: kotlin.Result<List<Track>> = kotlin.Result.failure(Exception("API Error"))

        whenever(repository.getTracksFromDatabase()).doReturn(dbResult)
        whenever(repository.getArtCoverTracks()).doReturn(apiResult)

        // then
        val flow = getTracksUseCase()

        // Assert
        val result1 = flow.last()
        assertTrue(result1 is Result.Error)
    }

    @Test
    fun `execute should emit Error when an exception occurs`() = runTest {
        // when
        val dbResult: kotlin.Result<List<Track>> = kotlin.Result.success(emptyList())
        val apiResult: kotlin.Result<List<Track>> = kotlin.Result.failure(Exception())

        whenever(repository.getTracksFromDatabase()).doReturn(dbResult)
        whenever(repository.getArtCoverTracks()).doReturn(apiResult)

        // then
        val flow = getTracksUseCase()

        // Assert
        val result1 = flow.last()
        assertTrue(result1 is Result.Error)
    }

    private val track1 = Track(
        1,
        1,
        "accusamus beatae ad facilis cum similique qui sunt",
        "https://placehold.co/600x600/92c952/white/png",
        "https://placehold.co/150x150/92c952/white/png"
    )
    private val track2 = Track(
        1,
        2,
        "reprehenderit est deserunt velit ipsam",
        "https://placehold.co/600x600/771796/white/png",
        "https://placehold.co/150x150/771796/white/png"
    )

}
