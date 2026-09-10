package com.helium4.theme.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.helium4.theme.LocalAppTheme
import com.helium4.theme.bodyTextStyle
import com.helium4.theme.spacing

/**
 * Tab row where the selected tab is marked three ways — color, a bolder weight and an
 * underline indicator — so it never relies on color alone.
 */
@Composable
public fun AppTabRow(
    tabs: List<String>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val theme = LocalAppTheme.current

    TabRow(
        selectedTabIndex = selectedIndex.coerceIn(0, (tabs.size - 1).coerceAtLeast(0)),
        modifier = modifier.fillMaxWidth(),
        containerColor = Color.Transparent,
        contentColor = theme.textPrimary,
        indicator = { tabPositions ->
            if (selectedIndex in tabPositions.indices) {
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedIndex]),
                    // Off the divider token, not the border one, so a theme with no card
                    // outline still marks its selected tab.
                    height = theme.dividerThickness * 2,
                    color = theme.bottomNavSelectedColor
                )
            }
        },
        divider = {
            HorizontalDivider(
                thickness = theme.dividerThickness,
                color = theme.dividerColor
            )
        }
    ) {
        tabs.forEachIndexed { index, title ->
            val selected = index == selectedIndex
            Tab(
                selected = selected,
                onClick = { onSelect(index) },
                selectedContentColor = theme.bottomNavSelectedColor,
                unselectedContentColor = theme.bottomNavUnselectedColor
            ) {
                Column(
                    modifier = Modifier.padding(
                        horizontal = theme.spacing.md,
                        vertical = theme.spacing.sm
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = title,
                        style = theme.bodyTextStyle.copy(
                            color = if (selected) {
                                theme.bottomNavSelectedColor
                            } else {
                                theme.bottomNavUnselectedColor
                            }
                        ),
                        fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                    )
                }
            }
        }
    }
}
