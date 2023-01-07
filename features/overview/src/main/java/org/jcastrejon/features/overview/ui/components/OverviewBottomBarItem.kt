package org.jcastrejon.features.overview.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun RowScope.OverviewBottomBarItem(
    modifier: Modifier = Modifier,
    title: String,
    imageVector: ImageVector,
    isSelected: Boolean = false,
    selectedColor: Color = MaterialTheme.colorScheme.onBackground,
    unselectedColor: Color = MaterialTheme.colorScheme.onBackground.copy(alpha = .24f),
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .weight(1f)
            .height(56.dp)
            .padding(horizontal = 4.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true, color = MaterialTheme.colorScheme.primary),
                onClick = onClick
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val weight by animateFloatAsState(targetValue = if (isSelected) 1f else 0f)

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .fillMaxWidth(0.8f)
                .height(2.dp)
                .graphicsLayer(scaleX = weight)
                .background(MaterialTheme.colorScheme.primary)
        )
        Spacer(modifier = Modifier.height(6.dp))

        val color by rememberUpdatedState(newValue = if (isSelected) selectedColor else unselectedColor)

        Icon(
            modifier = Modifier
                .size(24.dp),
            imageVector = imageVector,
            contentDescription = title,
            tint = color
        )

        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            color = color
        )
    }
}