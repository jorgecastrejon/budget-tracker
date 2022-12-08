package org.jcastrejon.features.register.ui

import android.content.res.Resources
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation.Companion.None
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.jcastrejon.features.register.R

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    viewModel: RegistrationViewModel = viewModel(),
    onSuccess: () -> Unit = {}
) {
    val resources = LocalContext.current.resources
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val events: Flow<RegistrationEvent> = rememberFlow(viewModel.onEvent)

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        events.collectLatest { event ->
            when (event) {
                is RegistrationEvent.RegistrationCompleted -> onSuccess()
                is RegistrationEvent.RegistrationError -> {
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar(event.error.getMessage(resources))
                    }
                }
            }
        }
    }

    Box(modifier = modifier) {

        RegistrationContent(
            registrationState = state,
            onPasswordChanged = viewModel::onPasswordChanged,
            onConfirmPasswordChanged = viewModel::onConfirmPasswordChanged,
            onSessionCheckBoxChanged = viewModel::onSessionCheckBoxChanged,
            onButtonClicked = viewModel::onButtonClicked
        )

        SnackbarHost(hostState = snackBarHostState) { data ->
            Snackbar(
                modifier = Modifier.padding(vertical = 24.dp),
                snackbarData = data,
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            )
        }
    }
}

@Composable
fun RegistrationContent(
    modifier: Modifier = Modifier,
    registrationState: RegistrationState = RegistrationState(),
    onPasswordChanged: (String) -> Unit,
    onConfirmPasswordChanged: (String) -> Unit,
    onSessionCheckBoxChanged: (Boolean) -> Unit,
    onButtonClicked: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .background(MaterialTheme.colorScheme.background)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { focusManager.clearFocus() },
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(id = R.string.register_create_password),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(32.dp))
        val keyboardController = LocalSoftwareKeyboardController.current
        var isPasswordVisible by rememberSaveable { mutableStateOf(false) }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = registrationState.password,
            onValueChange = onPasswordChanged,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            visualTransformation = if (!isPasswordVisible) PasswordVisualTransformation() else None,
            textStyle = MaterialTheme.typography.bodyMedium,
            label = {
                Text(
                    text = stringResource(id = R.string.register_password_label),
                    style = MaterialTheme.typography.labelMedium,
                )
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.register_password_placeholder),
                    style = MaterialTheme.typography.bodyMedium,
                )
            },
            trailingIcon = {
                Icon(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(24.dp)
                        .clickable { isPasswordVisible = !isPasswordVisible },
                    imageVector = if (isPasswordVisible) {
                        Icons.Filled.VisibilityOff
                    } else {
                        Icons.Filled.Visibility
                    },
                    contentDescription = "toggle password visibility"
                )
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        var isConfirmPasswordVisible by rememberSaveable { mutableStateOf(false) }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = registrationState.confirmPassword,
            onValueChange = onConfirmPasswordChanged,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            visualTransformation = if (!isConfirmPasswordVisible) PasswordVisualTransformation() else None,
            textStyle = MaterialTheme.typography.bodyMedium,
            label = {
                Text(
                    text = stringResource(id = R.string.register_confirm_password_label),
                    style = MaterialTheme.typography.labelMedium,
                )
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.register_confirm_password_placeholder),
                    style = MaterialTheme.typography.bodyMedium,
                )
            },
            trailingIcon = {
                Icon(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(24.dp)
                        .clickable { isConfirmPasswordVisible = !isConfirmPasswordVisible },
                    imageVector = if (isConfirmPasswordVisible) {
                        Icons.Filled.VisibilityOff
                    } else {
                        Icons.Filled.Visibility
                    },
                    contentDescription = "toggle password visibility"
                )
            }
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    onSessionCheckBoxChanged(!registrationState.authenticateEverySession)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = registrationState.authenticateEverySession,
                onCheckedChange = { value -> onSessionCheckBoxChanged(value) },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary
                )
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(id = R.string.register_authenticate_each_session),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(8.dp),
            enabled = registrationState.isValid && registrationState.isLoading.not(),
            onClick = {
                onButtonClicked()
                keyboardController?.hide()
                focusManager.clearFocus()

            }
        ) {
            Text(
                text = stringResource(id = R.string.register_button),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Composable
fun <T> rememberFlow(
    flow: Flow<T>,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
): Flow<T> =
    remember(key1 = flow, key2 = lifecycleOwner) {
        flow.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

fun FormFieldError.getMessage(resources: Resources): String = when (this) {
    FormFieldError.Length -> resources.getString(R.string.register_error_field_length)
    FormFieldError.FieldsDoNotMatch -> resources.getString(R.string.register_error_field_match)
}