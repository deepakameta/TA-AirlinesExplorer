package com.robustdev.airlineexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.robustdev.airlineexplorer.ui.navigation.NavigationGraph
import com.robustdev.airlineexplorer.ui.theme.AirlineExplorerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AirlineExplorerTheme {
                NavigationGraph()
            }
        }
    }
}