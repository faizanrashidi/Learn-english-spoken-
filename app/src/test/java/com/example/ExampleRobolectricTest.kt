package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.model.GrammarCatalogData
import com.example.model.LevelDataRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Bol English AI", appName)
  }

  @Test
  fun `verify 101 levels and 10 stages exist`() {
    assertEquals(101, LevelDataRepository.ALL_LEVELS.size)
    assertEquals(10, LevelDataRepository.STAGES.size)
    assertEquals(0, LevelDataRepository.ALL_LEVELS.first().levelNumber)
    assertEquals(100, LevelDataRepository.ALL_LEVELS.last().levelNumber)
  }

  @Test
  fun `verify A to Z grammar topics exist`() {
    assertEquals(26, GrammarCatalogData.ALL_TOPICS.size)
    assertTrue(GrammarCatalogData.ALL_TOPICS.any { it.title.contains("Tenses") })
  }
}

