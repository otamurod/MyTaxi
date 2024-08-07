package uz.otamurod.mytaxi.presentation.ui.home.components

import android.content.res.Configuration
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.otamurod.mytaxi.presentation.R
import uz.otamurod.mytaxi.presentation.ui.theme.MyTaxiTheme

@Composable
fun CustomSheetDragHandle(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "Drag Handle Transition")

    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -5f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 600, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "Drag Handle Animation"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(16.dp)
            .background(color = Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.sheet_drag_handle),
            contentDescription = "Custom Sheet Drag Handle Icon",
            modifier = modifier
                .width(32.dp)
                .height(5.dp)
                .offset(y = offsetY.dp),
            tint = MaterialTheme.colorScheme.outlineVariant
        )
    }
}

@Preview(
    name = "CustomSheetDragHandle",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Preview(
    name = "CustomSheetDragHandle",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = false
)
@Composable
private fun PreviewCustomSheetDragHandle() {
    MyTaxiTheme {
        CustomSheetDragHandle()
    }
}
