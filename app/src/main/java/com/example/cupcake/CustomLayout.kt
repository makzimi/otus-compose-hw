package com.example.cupcake

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp

@Composable
fun CustomLayout(
    modifier: Modifier = Modifier,
    columns: Int = 1,
    content: @Composable () -> Unit,
) {
    Layout(modifier = modifier, content = content) { measurables, constraints ->
        val raws = measurables.size / columns + 1
        val placeables = measurables.map { measurable ->
            measurable.measure(
                Constraints(
                    maxWidth = constraints.maxWidth / columns,
                    maxHeight = constraints.maxHeight / raws,
                    minHeight = 0,
                    minWidth = 0
                )
            )
        }


        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight
        ) {
            val columnWidth = constraints.maxWidth / columns
            var xPos = 0
            var yPos = 0
            var columnIndex = 1
            var rawHeight = 0
            placeables.forEach { placeable ->
                placeable.place(xPos, yPos)
                rawHeight = maxOf(rawHeight, placeable.height)
                if (columnIndex < columns) {
                    xPos += columnWidth
                    columnIndex++
                } else {
                    yPos += rawHeight
                    xPos = 0
                    columnIndex = 1
                    rawHeight = 0
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomLayoutPreview() {
    Surface {
        CustomLayout(columns = 3) {
            for (i in 0..6) {
                Image(
                    modifier = Modifier
                        .size((100..130).random().dp)
                        .padding(5.dp),
                    painter = painterResource(R.drawable.catanddot),
                    contentDescription = null
                )
            }
        }
    }
}