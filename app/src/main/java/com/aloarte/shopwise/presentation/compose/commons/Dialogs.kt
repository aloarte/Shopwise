package com.aloarte.shopwise.presentation.compose.commons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.aloarte.shopwise.R
import com.aloarte.shopwise.presentation.compose.ModifyType

@Composable
fun AddProductDialog(onDismiss: (Int?) -> Unit) {
    var quantity by remember { mutableStateOf(0) }
    var enableAdd by remember { mutableStateOf(false) }
    Dialog(
        onDismissRequest = { onDismiss.invoke(null) },
        content = {
            Card(
                modifier = Modifier.width(150.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(15.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black,
                        fontWeight = FontWeight.ExtraLight,
                        text = stringResource(id = R.string.list_add_amount_title)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    //Add / remove quantity
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        ModifyQuantityIcon(enabled = enableAdd, type = ModifyType.Remove) {
                            //Disable the button if the quantity came from 1 to 0
                            if (quantity == 1) {
                                enableAdd = false
                                quantity = 0
                            } else if (quantity > 0) {
                                quantity -= 1
                            }

                        }

                        Text(
                            modifier = Modifier.padding(horizontal = 10.dp),
                            color = Color.Black,
                            fontSize = 20.sp,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.ExtraLight,
                            text = quantity.toString()
                        )
                        ModifyQuantityIcon(type = ModifyType.Add) {
                            quantity += 1
                            enableAdd = true
                        }

                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedButton(
                        enabled = enableAdd,
                        modifier = Modifier.fillMaxWidth(),
                        border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(10.dp),
                        onClick = { onDismiss.invoke(quantity) }
                    ) {
                        Text(
                            color = Color.Black,
                            fontSize = 14.sp,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.ExtraLight,
                            text = stringResource(id = R.string.list_add_amount_btn)
                        )
                    }
                }
            }
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    )
}