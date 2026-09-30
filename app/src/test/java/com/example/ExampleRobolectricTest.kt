package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
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
    assertEquals("OmniTools", appName)
  }

  @Test
  fun `verify tool registry contains required categories`() {
    val tools = com.example.model.ToolRegistry.allTools
    assertTrue(tools.isNotEmpty())
    assertTrue(tools.any { it.id == "wedding_card" })
    assertTrue(tools.any { it.id == "invoice_maker" })
    assertTrue(tools.any { it.id == "voice_changer" })
    assertTrue(tools.any { it.id == "qr_generator" })
  }
}
