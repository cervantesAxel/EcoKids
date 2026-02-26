package com.example.eco_kids.ui.partials

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    colorContainer: Long,
    colorBorder: Long
) {

    val customBorderColor = Color(colorBorder)
    val customContainerColor = Color(colorContainer)

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder) },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = customBorderColor,
            unfocusedBorderColor = customBorderColor,
            disabledBorderColor = customBorderColor,
            errorBorderColor = customBorderColor,

            focusedContainerColor = customContainerColor,
            unfocusedContainerColor = customContainerColor,
            disabledContainerColor = customContainerColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 20.dp)
            .height(56.dp)
            .border(
                width = 3.dp,
                color = customBorderColor,
                shape = RoundedCornerShape(8.dp)
            )
    )
}

