package com.helium4.theme.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.helium4.theme.LocalAppTheme
import com.helium4.theme.shapes
import com.helium4.theme.spacing
import com.helium4.theme.themedSurface

/** Themed modal bottom sheet. Content is a plain [ColumnScope]; callers add their own padding. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun AppBottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val theme = LocalAppTheme.current
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        sheetState = sheetState,
        shape = theme.shapes.large,
        containerColor = theme.dialogBackground,
        contentColor = theme.textPrimary,
        scrimColor = theme.colorSurfaceVariant,
        dragHandle = { AppSheetDragHandle() },
        content = content
    )
}

/** Drag handle drawn through `themedSurface` so BORDER_ONLY themes still render it. */
@Composable
private fun AppSheetDragHandle() {
    val theme = LocalAppTheme.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = theme.spacing.sm),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                // Structural handle size — deliberately fixed, not a spacing token.
                .size(width = 36.dp, height = 4.dp)
                .themedSurface(
                    theme = theme,
                    shape = RoundedCornerShape(theme.cornerRadiusSmall),
                    fill = theme.colorBorder,
                    borderColor = theme.colorBorder
                )
        )
    }
}
