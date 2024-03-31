package app.k9mail.core.ui.compose.designsystem.atom.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import app.k9mail.core.ui.compose.theme2.MainTheme
import androidx.compose.material3.Text as Material3Text
import androidx.compose.material3.TextButton as Material3TextButton

@Composable
fun ButtonText(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    color: Color? = null,
    contentPadding: PaddingValues = buttonContentPadding(),
) {
    Material3TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.textButtonColors(
            contentColor = color ?: MainTheme.colors.primary,
        ),
        contentPadding = contentPadding,
    ) {
        Material3Text(text = text)
    }
}
