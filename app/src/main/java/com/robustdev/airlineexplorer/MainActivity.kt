package com.robustdev.airlineexplorer

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.robustdev.airlineexplorer.data.local.JsonUtils
import com.robustdev.airlineexplorer.data.model.Airline
import com.robustdev.airlineexplorer.ui.AirlinesViewModel
import com.robustdev.airlineexplorer.ui.theme.AirlineExplorerTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AirlineExplorerTheme {
                NavigationGraph()
//                Text("Hello Airlines Explorer")
            }
        }
    }
}


@SuppressLint("ViewModelConstructorInComposable")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AirlinesListScreen(
    navigateToDetailScreen: (Airline) -> Unit,
) {

    val viewModel = AirlinesViewModel()
    val airlines by viewModel.airlines.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Airlines Explorer") },
            )
        },
    ) { padding ->
        Box(
            modifier = Modifier.padding(padding),
        ) {
            when {
                loading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                )

                error != null -> Text(
                    text = "Error: $error",
                    modifier = Modifier.align(Alignment.Center),
                )

                else -> LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    items(airlines.size) { index ->
                        val airline = airlines[index]
                        AirlineItem(
                            airline = airlines[index],
                            onNavigate = { navigateToDetailScreen(airline) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AirlineItem(
    airline: Airline,
    onNavigate: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onNavigate() },
    ) {
        Row(
            modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(airline.logo_url),
                contentDescription = airline.name,
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = airline.name, style = MaterialTheme.typography.labelSmall
                )
                Text(
                    text = airline.country, style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun AirlinesListScreenPreview() {
//    val airline = Airline(
//        id = "1",
//        name = "EasyJet",
//        country = "United Kingdom",
//        headquarters = "London Luton Airport",
//        fleet_size = 342,
//        website = "https://www.easyjet.com",
//        logo_url = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/EasyJet_logo.svg/2560px-EasyJet_logo.svg.png"
//    )
//
//    AirlineItem(airline = airline, onNavigate = {})
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AirlineDetailScreen(airline: Airline) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Airline Details") })
        }) { padding ->
        airline.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(padding),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(it.logo_url),
                    contentDescription = it.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentScale = ContentScale.Fit
                )
                Text("Name: ${it.name}", style = MaterialTheme.typography.labelSmall)
                Text("Country: ${it.country}")
                Text("Headquarters: ${it.headquarters}")
                Text("Fleet Size: ${it.fleet_size}")
                Text(
                    text = "Website: ${it.website}", color = MaterialTheme.colorScheme.primary
                )
            }
        } ?: Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

sealed class Screen(val route: String) {
    data object List : Screen("airlines_list")
    data object Detail : Screen("airline_detail")
}

@Composable
fun NavigationGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.List.route,
    ) {

        var selectedAirline: Airline? = null

        composable(Screen.List.route) {
            AirlinesListScreen(navigateToDetailScreen = {
                selectedAirline = it
                navController.navigate(Screen.Detail.route)
            })
        }
        composable("airline_detail") {
            AirlineDetailScreen(selectedAirline!!)
        }
    }
}