package org.jcastrejon.features.overview.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Analytics
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.jcastrejon.features.overview.R
import org.jcastrejon.features.overview.ui.OverviewSection

@Composable
fun OverviewBottomBar(
    modifier: Modifier = Modifier,
    currentRoute: String? = OverviewSection.Summary.route,
    onSectionSelected: (OverviewSection) -> Unit,
    onButtonClicked: () -> Unit
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(MaterialTheme.colorScheme.background)
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OverviewBottomBarItem(
                title = stringResource(id = R.string.overview_bottom_bar_summary),
                imageVector = Icons.Rounded.List,
                isSelected = currentRoute == OverviewSection.Summary.route,
                onClick = { onSectionSelected(OverviewSection.Summary) }
            )
            OverviewBottomBarItem(
                title = stringResource(id = R.string.overview_bottom_bar_stats),
                imageVector = Icons.Rounded.Analytics,
                isSelected = currentRoute == OverviewSection.Stats.route,
                onClick = { onSectionSelected(OverviewSection.Stats) }
            )
            Spacer(modifier = Modifier.width(56.dp))
            OverviewBottomBarItem(
                title = stringResource(id = R.string.overview_bottom_bar_balance),
                imageVector = Icons.Rounded.CalendarMonth,
                isSelected = currentRoute == OverviewSection.Balance.route,
                onClick = { onSectionSelected(OverviewSection.Balance) }
            )
            OverviewBottomBarItem(
                title = stringResource(id = R.string.overview_bottom_bar_settings),
                imageVector = Icons.Rounded.Settings,
                isSelected = currentRoute == OverviewSection.Settings.route,
                onClick = { onSectionSelected(OverviewSection.Settings) }
            )
        }

        FloatingActionButton(
            modifier = Modifier
                .size(56.dp),
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            onClick = onButtonClicked
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp),
                imageVector = Icons.Filled.Add,
                contentDescription = stringResource(id = R.string.overview_bottom_bar_button)
            )
        }
    }
}