package com.aloarte.shopwise.presentation.compose.commons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aloarte.shopwise.R
import com.aloarte.shopwise.presentation.compose.enums.ModifyType
import com.aloarte.shopwise.presentation.compose.enums.PriceRowType
import com.aloarte.shopwise.presentation.compose.enums.QuantityIconSizeType

@Composable
fun TitleText(title: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier.padding(horizontal = 14.dp)) {
        Text(
            fontSize = 30.sp,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.ExtraLight,
            text = title
        )
    }
}

@Composable
fun ModifyQuantityIcon(
    size: QuantityIconSizeType = QuantityIconSizeType.Normal,
    enabled: Boolean = true,
    type: ModifyType,
    onModification: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (enabled) MaterialTheme.colorScheme.primary else Color.LightGray)
            .height(
                when (size) {
                    QuantityIconSizeType.Normal -> 40.dp
                    QuantityIconSizeType.Small -> 25.dp
                }
            )
            .width(
                when (size) {
                    QuantityIconSizeType.Normal -> 40.dp
                    QuantityIconSizeType.Small -> 25.dp
                }
            )
            .clickable(onClick = onModification, enabled = enabled),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(
                id = when (type) {
                    ModifyType.Add -> R.drawable.ic_add
                    ModifyType.Remove -> R.drawable.ic_remove
                }
            ),
            modifier = Modifier
                .height(
                    when (size) {
                        QuantityIconSizeType.Normal -> 30.dp
                        QuantityIconSizeType.Small -> 15.dp
                    }
                )
                .width(
                    when (size) {
                        QuantityIconSizeType.Normal -> 30.dp
                        QuantityIconSizeType.Small -> 15.dp
                    }
                ),
            colorFilter = ColorFilter.tint(Color.Black),
            contentDescription = stringResource(id = R.string.img_desc_modify_quantity_icon)
        )
    }

}

@Composable
fun PriceRow(label: String, price: Double, type: PriceRowType = PriceRowType.Regular) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
    ) {
        val textSize = when (type) {
            PriceRowType.Regular, PriceRowType.Discount -> 18.sp
            PriceRowType.Total -> 22.sp
            PriceRowType.Checkout -> 26.sp
        }
        val priceTextColor = when (type) {
            PriceRowType.Regular -> Color.LightGray
            PriceRowType.Discount -> MaterialTheme.colorScheme.primary
            PriceRowType.Total, PriceRowType.Checkout -> Color.Black

        }
        Text(
            modifier = Modifier.align(Alignment.CenterStart),
            fontSize = textSize,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.ExtraLight,
            text = label
        )
        Text(
            modifier = Modifier.align(Alignment.CenterEnd),
            fontSize = textSize,
            style = MaterialTheme.typography.labelSmall,
            color = priceTextColor,
            fontWeight = FontWeight.ExtraLight,
            text = "$price €"
        )
    }
}


@Composable
fun TitleRow(titleText: String, onBackClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Icon(
            modifier = Modifier
                .padding(10.dp)
                .height(30.dp)
                .width(30.dp)
                .align(Alignment.CenterStart)
                .clickable(onClick = onBackClicked),
            imageVector = Icons.Default.ArrowBack,
            contentDescription = stringResource(id = R.string.img_desc_exit_checkout_icon)
        )

        TitleText(
            title = titleText,
            modifier = Modifier.align(Alignment.Center)
        )

    }
}

@Composable
fun CardItemWithLabel(
    hideValue: Boolean = false,
    itemValue: String,
    label: String,
    modifier: Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            fontSize = 14.sp,
            color = Color.White,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.ExtraLight,
            text = label
        )
        Text(
            fontSize = 20.sp,
            color = Color.White,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.ExtraLight,
            text = if (hideValue) itemValue.replace(itemValue[0],'*') else itemValue
        )

    }
}