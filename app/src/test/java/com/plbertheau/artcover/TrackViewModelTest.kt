package com.plbertheau.artcover

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.plbertheau.artcover.model.TrackUiModel
import com.plbertheau.artcover.viewmodel.TrackViewModel
import com.plbertheau.artcover.viewmodel.UiState
import com.plbertheau.domain.model.Track
import com.plbertheau.domain.usecase.GetTracksUseCase
import com.plbertheau.domain.usecase.GetTracksUseCase.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class TrackViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: TrackViewModel


    private var mockGetTracksUseCase: GetTracksUseCase = mock()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        // Set the dispatcher for testing
        Dispatchers.setMain(testDispatcher)

        viewModel = TrackViewModel(mockGetTracksUseCase)
        whenever(mockGetTracksUseCase()).thenReturn(flowOf(Result.Loading))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchTracks should emit Loading state initially`() {
        // Act
        val currentState = viewModel.uiState.value

        // Assert
        assertTrue(currentState is UiState.Loading)
    }

    @Test
    fun `fetchTracks should emit Loading state when fetching tracks`() = runTest {
        // Given
        val trackResultUiModel = listOf(trackUiModel1)
        val trackList = listOf(track1)
        // Assert loading
        assertTrue(viewModel.uiState.value is UiState.Loading)
        whenever(mockGetTracksUseCase()).thenReturn(
            flow {
                emit(Result.Success(trackList))
            }
        )
        // When
        viewModel.fetchTracks()

        advanceUntilIdle()
        // Assert
        // After loading, it should be Success
        val successState = viewModel.uiState.value as UiState.Success
        assertEquals(trackResultUiModel, successState.data)
    }

    @Test
    fun `fetchTracks should emit Success state when tracks are fetched successfully`() = runTest {
        // Given
        val trackListUiModel = listOf(trackUiModel1, trackUiModel2)
        val trackList = listOf(track1, track2)

        whenever(mockGetTracksUseCase()).thenReturn(
            flow {
                emit(Result.Success(trackList))
            }
        )

        // When
        viewModel.fetchTracks()

        // Run coroutines and move forward in time
        advanceUntilIdle()

        // Assert
        val currentState = viewModel.uiState.value
        assertTrue(currentState is UiState.Success)
        val successState = currentState as UiState.Success
        assertEquals(trackListUiModel, successState.data)
    }


    @Test
    fun `fetchTracks should emit Error state when an error occurs`() = runTest {
        // Given
        val errorMessage = "Network error"

        whenever(mockGetTracksUseCase()).thenReturn(
            flow {
                emit(Result.Error(Exception(errorMessage)))
            }
        )

        // When
        viewModel.fetchTracks()

        // Run coroutines and move forward in time
        advanceUntilIdle()

        // Assert
        val currentState = viewModel.uiState.value
        assertTrue(currentState is UiState.Error)
        val errorState = currentState as UiState.Error
        assertEquals("Erreur : $errorMessage", errorState.message)
    }

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

    private val trackUiModel1 = TrackUiModel(
        id = 1,
        albumId = 1,
        title = "accusamus beatae ad facilis cum similique qui sunt",
        url = "https://placehold.co/600x600/92c952/white/png",
        thumbnailUrl = "https://placehold.co/150x150/92c952/white/png"
    )
    private val trackUiModel2 = TrackUiModel(
        id = 2,
        albumId = 2,
        title = "reprehenderit est deserunt velit ipsam",
        url = "https://placehold.co/600x600/771796/white/png",
        thumbnailUrl = "https://placehold.co/150x150/771796/white/png"
    )
}
