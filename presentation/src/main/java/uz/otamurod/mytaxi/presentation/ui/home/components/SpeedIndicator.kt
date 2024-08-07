package uz.otamurod.mytaxi.presentation.ui.home.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.otamurod.mytaxi.presentation.R
import uz.otamurod.mytaxi.presentation.ui.theme.Green
import uz.otamurod.mytaxi.presentation.ui.theme.ImmutableDark
import uz.otamurod.mytaxi.presentation.ui.theme.MyTaxiTheme

@Composable
fun SpeedIndicator(
    modifier: Modifier = Modifier
) {
    val fontFamily = FontFamily(Font(R.font.inter_regular))

    Card(
        modifier = modifier
            .shadow(
                elevation = 12.dp,
                spotColor = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(8.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
    ) {
        Surface(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .padding(4.dp)
                .fillMaxSize()
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Green)
            ) {
                Text(
                    text = "95",
                    fontWeight = FontWeight.Bold,
                    color = ImmutableDark,
                    fontFamily = fontFamily,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .size(24.dp)
                )
            }
        }
    }
}

@Preview(name = "SpeedIndicator", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "SpeedIndicator", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewSpeedIndicator() {
    MyTaxiTheme {
        SpeedIndicator(modifier = Modifier.size(56.dp))
    }
}