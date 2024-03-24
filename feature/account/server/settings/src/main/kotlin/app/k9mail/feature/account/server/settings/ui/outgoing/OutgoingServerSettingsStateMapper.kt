package app.k9mail.feature.account.server.settings.ui.outgoing

import app.k9mail.feature.account.common.domain.entity.AccountState
import app.k9mail.feature.account.common.domain.entity.toAuthType
import app.k9mail.feature.account.common.domain.entity.toAuthenticationType
import app.k9mail.feature.account.common.domain.entity.toConnectionSecurity
import app.k9mail.feature.account.common.domain.entity.toMailConnectionSecurity
import app.k9mail.feature.account.common.domain.input.NumberInputField
import app.k9mail.feature.account.common.domain.input.StringInputField
import app.k9mail.feature.account.server.settings.ui.outgoing.OutgoingServerSettingsContract.State
import com.fsck.k9.mail.ServerSettings

fun AccountState.toOutgoingServerSettingsState(): State {
    val password = getOutgoingServerPassword()

    return outgoingServerSettings?.toOutgoingServerSettingsState(password)
        ?: State(
            username = StringInputField(value = emailAddress ?: ""),
            password = StringInputField(value = password),

            isLoading = false,
        )
}

private fun AccountState.getOutgoingServerPassword(): String {
    return outgoingServerSettings?.password ?: incomingServerSettings?.password ?: ""
}

private fun ServerSettings.toOutgoingServerSettingsState(password: String): State {
    return State(
        server = StringInputField(value = host ?: ""),
        security = connectionSecurity.toConnectionSecurity(),
        port = NumberInputField(value = port.toLong()),
        authenticationType = authenticationType.toAuthenticationType(),
        requireSMTPEHLOFix = BooleanInputField(value = requireSMTPEHLOFix),
        username = StringInputField(value = username),
        password = StringInputField(value = password),

        isLoading = false,
        error = null,
    )
}

internal fun State.toServerSettings(): ServerSettings {
    return ServerSettings(
        type = "smtp",
        host = server.value.trim(),
        port = port.value!!.toInt(),
        connectionSecurity = security.toMailConnectionSecurity(),
        authenticationType = authenticationType.toAuthType(),
        requireSMTPEHLOFix = requireSMTPEHLOFix.value,
        username = if (authenticationType.isUsernameRequired) username.value.trim() else "",
        password = if (authenticationType.isPasswordRequired) password.value.trim() else null,
        clientCertificateAlias = clientCertificateAlias,
    )
}
