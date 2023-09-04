@file:OptIn(ExperimentalMaterial3Api::class)

package com.fullrandomstudio.designsystem.theme.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fullrandomstudio.designsystem.theme.token.SnackbarTokens

@Composable
fun TdsSnackbarHost(
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    SnackbarHost(snackbarHostState) { data ->
        val isError = (data.visuals as? SnackbarState)?.isError ?: false
        val buttonColors = if (isError) {
            ButtonDefaults.textButtonColors(
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.onErrorContainer
            )
        } else {
            ButtonDefaults.textButtonColors(
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        }

        val dismissState = rememberDismissState(
            confirmValueChange = {
                data.dismiss()
                true
            }
        )

        SwipeToDismiss(
            state = dismissState,
            background = { },
            dismissContent = {
                Snackbar(
                    modifier = Modifier
                        .padding(SnackbarTokens.ScreenPadding),
                    action = {
                        TextButton(
                            onClick = { data.performAction() },
                            colors = buttonColors,
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Text(
                                data.visuals.actionLabel?.uppercase() ?: "",
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                ) {
                    Text(data.visuals.message, style = MaterialTheme.typography.bodyMedium)
                }
            }
        )
    }
}

data class SnackbarState(
    override val message: String,
    override val actionLabel: String,
    override val withDismissAction: Boolean = false,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    val isError: Boolean = false,
) : SnackbarVisuals
