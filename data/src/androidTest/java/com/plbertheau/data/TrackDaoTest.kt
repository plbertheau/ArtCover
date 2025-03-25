package com.plbertheau.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.plbertheau.data.local.TrackDao
import com.plbertheau.data.local.TrackEntity
import com.plbertheau.data.local.TrackLocalDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals

/**
 * While backticks are valid in Kotlin for identifiers, they can cause issues in Android instrumentation tests.
 */
@RunWith(AndroidJUnit4::class)
class TrackDaoTest {

    private lateinit var trackDao: TrackDao
    private lateinit var database: TrackLocalDB
    private val testDispatcher = StandardTestDispatcher()
    private val context: Context = ApplicationProvider.getApplicationContext()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        // Create an in-memory database before each test
        database = Room.inMemoryDatabaseBuilder(
            context = context,
            TrackLocalDB::class.java
        ).allowMainThreadQueries().build()
        trackDao = database.getTrackDao()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        // Close the database after each test
        database.close()
        Dispatchers.resetMain()
    }

    @Test
    fun insertTracks_shouldInsertTracksIntoDb() = runTest {
        // Given
        val trackEntity1 = track1
        val trackEntity2 = track2

        // When
        trackDao.insertTracks(listOf(trackEntity1, trackEntity2))

        // Then
        val tracksFromDb = trackDao.getAllTracks()
        assertEquals(2, tracksFromDb.size)
        assertEquals(trackEntity1, tracksFromDb[0])
        assertEquals(trackEntity2, tracksFromDb[1])
    }

    @Test
    fun getAllTracks_shouldReturnEmptyListWhenNoTracksInserted() = runTest {
        // Given no tracks in the DB

        // When
        val tracksFromDb = trackDao.getAllTracks()

        // Then
        assertEquals(0, tracksFromDb.size)
    }

    @Test
    fun insertTracks_shouldNotInsertWhenListIsEmpty() = runTest {
        // Given an empty list
        val emptyList = emptyList<TrackEntity>()

        // When
        trackDao.insertTracks(emptyList)

        // Then
        val tracksFromDb = trackDao.getAllTracks()
        assertEquals(0, tracksFromDb.size)
    }

    @Test
    fun getAllTracks_shouldReturnCorrectTrackData() = runTest {
        // Given
        val trackEntity1 = track1
        val trackEntity2 = track2
        trackDao.insertTracks(listOf(trackEntity1, trackEntity2))
        // When
        val tracksFromDb = trackDao.getAllTracks()

        // Then
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
        2,
        2,
        "reprehenderit est deserunt velit ipsam",
        "https://placehold.co/600x600/771796/white/png",
        "https://placehold.co/150x150/771796/white/png"
    )
}
