package com.robustdev.airlineexplorer.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.robustdev.airlineexplorer.R
import com.robustdev.airlineexplorer.data.model.Airline
import com.robustdev.airlineexplorer.ui.viewmodel.AirlinesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AirlinesListScreen(
    navigateToDetailScreen: (Airline) -> Unit,
    viewModel: AirlinesViewModel = hiltViewModel(),
) {
    val airlines by viewModel.airlines.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    val searchQuery = remember { mutableStateOf("") }

    val filteredAirlines = airlines.filter {
        it.name.contains(searchQuery.value, ignoreCase = true) ||
                it.country.contains(searchQuery.value, ignoreCase = true)

    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(title = { Text("Airlines Explorer") })

                TextField(
                    value = searchQuery.value,
                    onValueChange = {
                        searchQuery.value = it
                    },
                    placeholder = { Text(text = "Search Airlines...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    singleLine = true
                )
            }
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
                    items(filteredAirlines.size) { index ->
                        val airline = filteredAirlines[index]
                        AirlineItem(
                            airline = filteredAirlines[index],
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
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(/*
                * These image url is not working. So, using static image.
                * We can use AsyncImagePainter from coil library to load image
                * directly form the url.
                * painter = rememberAsyncImagePainter(it.logo_url),
                **/
                painter = painterResource(id = R.drawable.flight_color),
                contentDescription = airline.name,
                modifier = Modifier.size(48.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = airline.name,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic,
                    ),
                )
                Text(
                    text = airline.country,
                    style = TextStyle(
                        fontSize = 16.sp
                    ),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AirlinesListScreenPreview() {
    val airline = Airline(
        name = "EasyJet",
        country = "United Kingdom",
        headquarters = "London Luton Airport",
        fleetSize = 342,
        website = "https://www.easyjet.com",
        logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/EasyJet_logo.svg/2560px-EasyJet_logo.svg.png"
    )

    AirlineItem(airline = airline, onNavigate = {})
}