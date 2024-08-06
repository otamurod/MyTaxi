package uz.otamurod.mytaxi.presentation.ui.home.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.otamurod.mytaxi.presentation.R
import uz.otamurod.mytaxi.presentation.ui.theme.Green
import uz.otamurod.mytaxi.presentation.ui.theme.ImmutableDark
import uz.otamurod.mytaxi.presentation.ui.theme.MyTaxiTheme
import uz.otamurod.mytaxi.presentation.ui.theme.Red

@Composable
fun TabBar(
    activeTab: MutableState<String> = mutableStateOf(stringResource(R.string.faol)),
    modifier: Modifier = Modifier
) {
    val fontFamily = FontFamily(Font(R.font.inter_regular))

    Card(
        modifier = modifier.height(56.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
    ) {
        Row {
            listOf(
                stringResource(R.string.band) to Red, stringResource(R.string.faol) to Green
            ).forEach { (text, color) ->
                Button(
                    modifier = Modifier
                        .padding(all = 4.dp)
                        .fillMaxHeight(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (activeTab.value == text) color else MaterialTheme.colorScheme.background
                    ),
                    onClick = { activeTab.value = text },
                    shape = RoundedCornerShape(size = 10.dp)
                ) {
                    Text(
                        text = text,
                        fontFamily = fontFamily,
                        fontWeight = if (activeTab.value == text) FontWeight.Bold else FontWeight.Normal,
                        color = if (activeTab.value == text) ImmutableDark else MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}

@Preview(name = "TabBar", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "TabBar", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewTabBarActive() {
    MyTaxiTheme {
        TabBar(activeTab = remember {
            mutableStateOf("Faol")
        })
    }
}

@Preview(name = "TabBar", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "TabBar", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewTabBar() {
    MyTaxiTheme {
        TabBar(activeTab = remember {
            mutableStateOf("Band")
        })
    }
}