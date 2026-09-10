package com.helium4.theme.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import com.helium4.theme.LocalAppTheme
import com.helium4.theme.bodyTextStyle
import com.helium4.theme.headingTextStyle
import com.helium4.theme.shapes
import com.helium4.theme.spacing
import com.helium4.theme.themedSurface

/**
 * Modal confirmation dialog. When [isDestructive] the confirm action is rendered as a
 * DESTRUCTIVE [AppButton], which pairs the error color with an icon and a label.
 */
@Composable
public fun AppDialog(
    onDismissRequest: () -> Unit,
    title: String,
    text: String,
    confirmLabel: String,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
    dismissLabel: String? = null,
    onDismiss: (() -> Unit)? = null,
    isDestructive: Boolean = false
) {
    val theme = LocalAppTheme.current
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .themedSurface(
                    theme = theme,
                    shape = theme.shapes.large,
                    fill = theme.dialogBackground,
                    borderColor = theme.cardBorderColor,
                    elevation = theme.cardElevation
                )
                .padding(theme.spacing.lg),
            verticalArrangement = Arrangement.spacedBy(theme.spacing.md)
        ) {
            Text(text = title, style = theme.headingTextStyle)
            Text(
                text = text,
                style = theme.bodyTextStyle.copy(color = theme.textSecondary)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(theme.spacing.sm, Alignment.End)
            ) {
                if (dismissLabel != null) {
                    AppButton(
                        text = dismissLabel,
                        onClick = { onDismiss?.invoke() ?: onDismissRequest() },
                        style = AppButtonVariant.GHOST
                    )
                }
                AppButton(
                    text = confirmLabel,
                    onClick = onConfirm,
                    style = if (isDestructive) AppButtonVariant.DESTRUCTIVE else AppButtonVariant.PRIMARY
                )
            }
        }
    }
}
