package com.robustdev.airlineexplorer

import android.content.Context
import android.content.res.AssetManager
import com.robustdev.airlineexplorer.data.datasource.AirlineLocalDataSource
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayInputStream

class AirlineLocalDataSourceTest {

    private lateinit var context: Context
    private lateinit var assets: AssetManager
    private lateinit var dataSource: AirlineLocalDataSource

    private val fakeJson = """
        [
            {
                "id": "1",
                "name": "Air India",
                "country": "India",
                "headquarters": "Delhi",
                "fleet_size": 100,
                "website": "https://airindia.com",
                "logo_url": "logo.png"
            }
        ]
    """.trimIndent()

    @Before
    fun setup() {
        context = mockk()
        assets = mockk()
        every { context.assets } returns assets

        val inputStream = ByteArrayInputStream(fakeJson.toByteArray(Charsets.UTF_8))
        every { assets.open("airlines.json") } returns inputStream

        dataSource = AirlineLocalDataSource(context)
    }

    @Test
    fun `getAirlines should return parsed airline list`() = runTest {
        val result = dataSource.getAirlines().first()

        assert(result.size == 1)
        assert(result[0].name == "Air India")
        assert(result[0].country == "India")
    }
}
