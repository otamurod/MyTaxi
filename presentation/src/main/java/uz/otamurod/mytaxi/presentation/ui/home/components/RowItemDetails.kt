package uz.otamurod.mytaxi.presentation.ui.home.components

import uz.otamurod.mytaxi.presentation.R

data class RowItemDetails(
    val leadingIcon: Int,
    val title: String,
    val subtitle: String,
    val trailingIcon: Int
)

val rowItems = listOf(
    RowItemDetails(
        leadingIcon = R.drawable.leading_icon_1,
        title = "Tarif",
        subtitle = "6 / 8",
        trailingIcon = R.drawable.forward_icon
    ),
    RowItemDetails(
        leadingIcon = R.drawable.leading_icon_2,
        title = "Buyurtmalar",
        subtitle = "0",
        trailingIcon = R.drawable.forward_icon
    ),
    RowItemDetails(
        leadingIcon = R.drawable.leading_icon_3,
        title = "Bordur",
        subtitle = "",
        trailingIcon = R.drawable.forward_icon
    )
)
