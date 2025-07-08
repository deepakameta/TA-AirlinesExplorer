package com.robustdev.airlineexplorer.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.robustdev.airlineexplorer.R
import com.robustdev.airlineexplorer.data.model.Airline

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AirlineDetailScreen(airline: Airline) {
    val uriHandler = LocalUriHandler.current

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Airline Details") })
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            /*
            * These image url is not working. So, using static image.
            * We can use AsyncImagePainter from coil library to load image
            * directly form the url.
            * painter = rememberAsyncImagePainter(it.logo_url),
            **/
            Image(
                painter = painterResource(id = R.drawable.flight_color),
                contentDescription = airline.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Fit
            )

            Text(
                text = airline.name,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
            )

            InfoRow(label = "Country", value = airline.country)
            InfoRow(label = "Headquarters", value = airline.headquarters)
            InfoRow(label = "Fleet Size", value = airline.fleetSize.toString())

            Text(
                text = "Visit Website",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier
                    .clickable {
                        uriHandler.openUri(airline.website)
                    }
            )
        }
    }
}


@Composable
fun InfoRow(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun AirlineDetailScreenPreview() {
    val airline = Airline(
        name = "EasyJet",
        country = "United Kingdom",
        headquarters = "London Luton Airport",
        fleetSize = 342,
        website = "https://www.easyjet.com",
        logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/EasyJet_logo.svg/2560px-EasyJet_logo.svg.png"
    )

    AirlineDetailScreen(airline = airline)
}