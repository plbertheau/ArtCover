package com.plbertheau.data

import com.plbertheau.data.local.TrackDao
import com.plbertheau.data.local.TrackEntity
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class TrackDaoTest {

    private val trackDao: TrackDao = mock()

    @Test
    fun insertTracks_shouldInsertTracksIntoDb() = runBlocking {
        // When
        val trackEntity1 = track1
        val trackEntity2 = track2
        whenever(trackDao.getAllTracks()).thenReturn(listOf(track1, track2))
        // Act
        trackDao.insertTracks(listOf(trackEntity1, trackEntity2))

        // Assert
        val tracksFromDb = trackDao.getAllTracks()
        assertEquals(2, tracksFromDb.size)
        assertTrue(tracksFromDb.contains(trackEntity1))
        assertTrue(tracksFromDb.contains(trackEntity2))
    }

    @Test
    fun getAllTracks_shouldReturnEmptyListWhenNoTracksInserted() = runBlocking {
        // Act
        val tracksFromDb = trackDao.getAllTracks()

        // Assert
        assertTrue(tracksFromDb.isEmpty())
    }

    @Test
    fun insertTracks_shouldNotInsertWhenListIsEmpty() = runBlocking {
        // When
        val emptyList = emptyList<TrackEntity>()

        // Act
        trackDao.insertTracks(emptyList)

        // Assert
        val tracksFromDb = trackDao.getAllTracks()
        assertTrue(tracksFromDb.isEmpty())
    }

    @Test
    fun getAllTracks_shouldReturnCorrectTrackData() = runBlocking {
        // When
        val trackEntity1 = track1
        val trackEntity2 = track2
        whenever(trackDao.getAllTracks()).thenReturn(listOf(track1, track2))

        trackDao.insertTracks(listOf(trackEntity1, trackEntity2))

        // Act
        val tracksFromDb = trackDao.getAllTracks()

        // Assert
        assertEquals(2, tracksFromDb.size)
        assertEquals("accusamus beatae ad facilis cum similique qui sunt", tracksFromDb[0].title)
        assertEquals("reprehenderit est deserunt velit ipsam", tracksFromDb[1].title)
    }

    private val track1 = TrackEntity(
        1,
        1,
        "accusamus beatae ad facilis cum similique qui sunt",
        "https://placehold.co/600x600/92c952/white/png",
        "https://placehold.co/150x150/92c952/white/png"
    )
    private val track2 = TrackEntity(
        1,
        2,
        "reprehenderit est deserunt velit ipsam",
        "https://placehold.co/600x600/771796/white/png",
        "https://placehold.co/150x150/771796/white/png"
    )
}
