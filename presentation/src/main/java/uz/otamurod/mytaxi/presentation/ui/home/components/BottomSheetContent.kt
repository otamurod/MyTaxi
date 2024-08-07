package uz.otamurod.mytaxi.presentation.ui.home.components

import android.content.res.Configuration
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.otamurod.mytaxi.presentation.R
import uz.otamurod.mytaxi.presentation.ui.theme.MyTaxiTheme

@Composable
fun BottomSheetContent(isExpanded: Boolean) {
    val animatedHeight by animateDpAsState(
        targetValue = if (isExpanded) 200.dp else 145.dp,
        animationSpec = tween(durationMillis = 500), label = "BottomSheet Content Animation"
    )

    Column {
        CustomSheetDragHandle()

        Surface(
            modifier = Modifier
                .height(animatedHeight),
            shape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp)
        ) {
            Surface(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 12.dp, vertical = 16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(size = 12.dp)
            ) {
                Box(
                    modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    LazyColumn {
                        itemsIndexed(items = rowItems) { index, rowItem ->
                            RowItem(itemDetails = rowItem, index = index)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RowItem(
    itemDetails: RowItemDetails,
    index: Int
) {
    val fontFamily = FontFamily(Font(R.font.inter_regular))

    Column {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(id = itemDetails.leadingIcon),
                contentDescription = "Leading Icon",
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = itemDetails.title,
                modifier = Modifier
                    .height(22.dp),
                fontWeight = FontWeight.SemiBold,
                fontFamily = fontFamily,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = itemDetails.subtitle,
                modifier = Modifier
                    .height(22.dp),
                fontFamily = fontFamily,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.width(2.dp))
            Icon(
                painter = painterResource(id = itemDetails.trailingIcon),
                contentDescription = "Trailing Icon",
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.onSecondary
            )
        }
        if (index != rowItems.lastIndex) {
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Preview(
    name = "BottomSheetContent",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "BottomSheetContent",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun PreviewBottomSheetContent() {
    MyTaxiTheme {
        BottomSheetContent(isExpanded = false)
    }
}

@Preview(
    name = "BottomSheetContent",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "BottomSheetContent",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun PreviewBottomSheetContentExpanded() {
    MyTaxiTheme {
        BottomSheetContent(isExpanded = true)
    }
}