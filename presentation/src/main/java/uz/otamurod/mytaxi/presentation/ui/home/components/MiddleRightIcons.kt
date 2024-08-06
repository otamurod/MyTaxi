package uz.otamurod.mytaxi.presentation.ui.home.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.otamurod.mytaxi.presentation.R
import uz.otamurod.mytaxi.presentation.ui.theme.LightAccent
import uz.otamurod.mytaxi.presentation.ui.theme.MyTaxiTheme

@Composable
fun MiddleRightIcons(
    modifier: Modifier = Modifier,
    onZoomIn: () -> Unit,
    onZoomOut: () -> Unit,
    onNavigator: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        // Zoom In
        Card(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .size(56.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
        ) {
            IconButton(
                onClick = onZoomIn,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    painterResource(id = R.drawable.plus),
                    contentDescription = "Zoom In",
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }
        }

        // Zoom Out
        Card(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .size(56.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        ) {
            IconButton(
                onClick = onZoomOut,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    painterResource(id = R.drawable.minus),
                    contentDescription = "Zoom Out",
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }
        }

        // Navigator (Center to Car Icon)
        Card(
            modifier = Modifier
                .size(56.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        ) {
            IconButton(
                onClick = onNavigator,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    painterResource(id = R.drawable.navigate),
                    contentDescription = "Navigate to Car",
                    tint = LightAccent
                )
            }
        }
    }
}

@Preview(name = "MiddleRightIcons", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "MiddleRightIcons", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewMiddleRightIcons() {
    MyTaxiTheme {
        MiddleRightIcons(
            onZoomIn = {},
            onZoomOut = {},
            onNavigator = {},
            modifier = Modifier.width(56.dp)
        )
    }
}