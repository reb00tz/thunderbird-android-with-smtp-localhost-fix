package app.k9mail.feature.onboarding.permissions.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.k9mail.core.ui.compose.common.image.ImageWithOverlayCoordinate
import app.k9mail.core.ui.compose.designsystem.atom.button.ButtonFilled
import app.k9mail.core.ui.compose.designsystem.atom.icon.Icon
import app.k9mail.core.ui.compose.designsystem.atom.icon.Icons
import app.k9mail.core.ui.compose.designsystem.atom.icon.IconsWithBottomRightOverlay
import app.k9mail.core.ui.compose.designsystem.atom.text.TextBody2
import app.k9mail.core.ui.compose.designsystem.atom.text.TextHeadline6
import app.k9mail.core.ui.compose.theme.K9Theme
import app.k9mail.core.ui.compose.theme.MainTheme
import app.k9mail.feature.onboarding.permissions.R
import app.k9mail.feature.onboarding.permissions.ui.PermissionsContract.UiPermissionState

private val MAX_WIDTH = 500.dp

@Composable
internal fun PermissionBox(
    icon: ImageWithOverlayCoordinate,
    permissionState: UiPermissionState,
    title: String,
    description: String,
    onAllowClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .width(MAX_WIDTH)
            .padding(horizontal = MainTheme.spacings.double),
    ) {
        Row {
            Box(
                modifier = Modifier.padding(
                    end = MainTheme.spacings.double,
                    top = MainTheme.spacings.default,
                    bottom = MainTheme.spacings.default,
                ),
            ) {
                IconWithPermissionStateOverlay(icon, permissionState)
            }
            Column {
                TextHeadline6(text = title)
                TextBody2(text = description)
            }
        }

        val buttonAlpha by animateFloatAsState(
            targetValue = if (permissionState == UiPermissionState.Granted) 0f else 1f,
            label = "AllowButtonAlpha",
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End,
        ) {
            Spacer(modifier = Modifier.height(MainTheme.spacings.default))

            ButtonFilled(
                text = stringResource(R.string.onboarding_permissions_allow_button),
                onClick = onAllowClick,
                modifier = Modifier.alpha(buttonAlpha),
            )
        }
    }
}

@Composable
private fun IconWithPermissionStateOverlay(
    icon: ImageWithOverlayCoordinate,
    permissionState: UiPermissionState,
) {
    Box {
        val iconSize = MainTheme.sizes.largeIcon
        val overlayIconSize = iconSize / 2
        val overlayIconOffset = overlayIconSize / 2
        val scalingFactor = iconSize / icon.image.defaultHeight
        val overlayOffsetX = (icon.overlayOffsetX * scalingFactor) - overlayIconOffset
        val overlayOffsetY = (icon.overlayOffsetY * scalingFactor) - overlayIconOffset

        Icon(
            imageVector = icon.image,
            modifier = Modifier.size(iconSize),
        )

        when (permissionState) {
            UiPermissionState.Unknown -> Unit
            UiPermissionState.Granted -> {
                Icon(
                    imageVector = Icons.Filled.check,
                    tint = MainTheme.colors.success,
                    modifier = Modifier
                        .size(overlayIconSize)
                        .offset(
                            x = overlayOffsetX,
                            y = overlayOffsetY,
                        ),
                )
            }

            UiPermissionState.Denied -> {
                Icon(
                    imageVector = Icons.Filled.cancel,
                    tint = MainTheme.colors.warning,
                    modifier = Modifier
                        .size(overlayIconSize)
                        .offset(
                            x = overlayOffsetX,
                            y = overlayOffsetY,
                        ),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun PermissionBoxUnknownStatePreview() {
    K9Theme {
        PermissionBox(
            icon = IconsWithBottomRightOverlay.person,
            permissionState = UiPermissionState.Unknown,
            title = "Contacts",
            description = "Allow access to be able to display contact names and photos.",
            onAllowClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
internal fun PermissionBoxGrantedStatePreview() {
    K9Theme {
        PermissionBox(
            icon = IconsWithBottomRightOverlay.person,
            permissionState = UiPermissionState.Granted,
            title = "Contacts",
            description = "Allow access to be able to display contact names and photos.",
            onAllowClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
internal fun PermissionBoxDeniedStatePreview() {
    K9Theme {
        PermissionBox(
            icon = IconsWithBottomRightOverlay.person,
            permissionState = UiPermissionState.Denied,
            title = "Contacts",
            description = "Allow access to be able to display contact names and photos.",
            onAllowClick = {},
        )
    }
}
