package com.aloarte.shopwise.presentation.compose.commons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun ModifyQuantityIcon(size: QuantityIconSizeType = QuantityIconSizeType.Normal, enabled: Boolean = true, type: ModifyType, onModification: () -> Unit) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (enabled) MaterialTheme.colorScheme.primary else Color.LightGray)
            .height(when(size){
                QuantityIconSizeType.Normal -> 40.dp
                QuantityIconSizeType.Small -> 25.dp
            })
            .width(when(size){
                QuantityIconSizeType.Normal -> 40.dp
                QuantityIconSizeType.Small -> 25.dp
            })
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
                .height(when(size){
                    QuantityIconSizeType.Normal -> 30.dp
                    QuantityIconSizeType.Small -> 15.dp
                })
                .width(when(size){
                    QuantityIconSizeType.Normal -> 30.dp
                    QuantityIconSizeType.Small -> 15.dp
                }),
            colorFilter = ColorFilter.tint(Color.Black),
            contentDescription = stringResource(id = R.string.img_desc_modify_quantity_icon)
        )
    }

}