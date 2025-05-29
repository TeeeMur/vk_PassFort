package com.example.passfort.designSystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.passfort.R
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleChoiceSegmentedButtonNew(
    options: ImmutableList<String>,
    selectedIndex: Int,
    switchAction: (Int) -> Unit
) {
    SingleChoiceSegmentedButtonRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
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
                onClick = {
                    switchAction(index) },
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


/*
@PreviewLightDark()
@Composable
fun SegmentsButtonPreview() {
    PassFortTheme{
        SingleChoiceSegmentedButton(120, {})
    }
}*/
