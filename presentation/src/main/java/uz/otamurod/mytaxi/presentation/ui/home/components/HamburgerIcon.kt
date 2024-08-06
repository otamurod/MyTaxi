package uz.otamurod.mytaxi.presentation.ui.home.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.otamurod.mytaxi.presentation.R
import uz.otamurod.mytaxi.presentation.ui.theme.MyTaxiTheme

@Composable
fun HamburgerIcon(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
    ) {
        IconButton(
            onClick = {},
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                painterResource(id = R.drawable.hamburger_icon),
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = "Hamburger Icon"
            )
        }
    }
}

@Preview(name = "HamburgerIcon", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "HamburgerIcon", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewHamburgerIcon() {
    MyTaxiTheme {
        HamburgerIcon(modifier = Modifier.size(56.dp))
    }
}