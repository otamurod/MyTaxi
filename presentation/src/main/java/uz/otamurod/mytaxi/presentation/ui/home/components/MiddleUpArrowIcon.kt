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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.otamurod.mytaxi.presentation.R
import uz.otamurod.mytaxi.presentation.ui.theme.MyTaxiTheme

@Composable
fun MiddleUpArrowIcon(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
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
                    .background(color = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.9f)
                ) {
                    Icon(
                        painterResource(id = R.drawable.up_arrow),
                        contentDescription = "Up Arrow",
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
        }
    }
}

@Preview(name = "MiddleUpArrowIcon", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "MiddleUpArrowIcon", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewMiddleUpArrowIcon() {
    MyTaxiTheme {
        MiddleUpArrowIcon(modifier = Modifier.size(56.dp))
    }
}