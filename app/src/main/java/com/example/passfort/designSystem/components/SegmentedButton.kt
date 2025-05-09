package com.example.passfort.designSystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.passfort.R

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SingleChoiceSegmentedButton() {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val options = listOf("60", "120", "180")

    SingleChoiceSegmentedButtonRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 30.dp)
    ) {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = colorResource(R.color.text_field_color),
                        RoundedCornerShape(50.dp)
                    ),
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size,

                ),
                icon = {},
                onClick = { selectedIndex = index },
                selected = index == selectedIndex,
                label = {
                    Text(
                        label,
                        fontSize = 18.sp,
                        color =
                            if (index == selectedIndex) MaterialTheme.colorScheme.inversePrimary
                            else MaterialTheme.colorScheme.secondary
                    )
                },
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = MaterialTheme.colorScheme.primary,
                    activeBorderColor = Color.Transparent,
                    inactiveBorderColor = Color.Transparent,
                ),
            )
        }
    }
}