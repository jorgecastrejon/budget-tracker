package org.jcastrejon.features.login.ui

import android.content.Context
import android.content.res.Resources
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.CancellationSignal
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.jcastrejon.features.login.R

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel(),
    onSuccess: () -> Unit = {}
) {
    val resources = LocalContext.current.resources
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val events: Flow<LoginEvent> = rememberFlow(viewModel.onEvent)
    val context = LocalContext.current
    val showFingerprintPromptFunction = remember {
        { context.showBiometricPrompt { succeed -> if (succeed) onSuccess() } }
    }

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        events.collectLatest { event ->
            when (event) {
                is LoginEvent.Succeed -> onSuccess()
                is LoginEvent.Error -> {
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar(event.error.getMessage(resources))
                    }
                }
            }
        }
    }

    Box(modifier = modifier) {

        LoginContent(
            loginState = state,
            onPasswordChanged = viewModel::onPasswordChanged,
            onFingerprintClicked = showFingerprintPromptFunction,
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
fun LoginContent(
    modifier: Modifier = Modifier,
    loginState: LoginState = LoginState(),
    onPasswordChanged: (String) -> Unit,
    onFingerprintClicked: () -> Unit,
    onButtonClicked: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    Box(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .background(MaterialTheme.colorScheme.background)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { focusManager.clearFocus() },
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(id = R.string.login_introduce_password),
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
                value = loginState.password,
                onValueChange = onPasswordChanged,
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                visualTransformation = if (!isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
                textStyle = MaterialTheme.typography.bodyMedium,
                label = { Text(text = stringResource(id = R.string.login_password_label)) },
                placeholder = { Text(text = stringResource(id = R.string.login_password_placeholder)) },
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
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(8.dp),
                enabled = loginState.isValid && loginState.isLoading.not(),
                onClick = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    onButtonClicked()
                }
            ) {
                Text(
                    text = stringResource(id = R.string.login_button),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
        AnimatedVisibility(visible =  isFingerprintSupported) {
            Icon(
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(8.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onFingerprintClicked)
                    .padding(8.dp)
                    .size(48.dp),
                imageVector = Icons.Filled.Fingerprint,
                contentDescription = stringResource(id = R.string.login_fingerprint_content_description),
                tint = MaterialTheme.colorScheme.primary
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
    FormFieldError.Length -> resources.getString(R.string.login_error_field_length)
    FormFieldError.FieldsDoNotMatch -> resources.getString(R.string.login_error)
}

fun Context.showBiometricPrompt(onResult: (Boolean) -> Unit) {
    if (!isFingerprintSupported) return
    val callback = AuthenticationSucceededCallback { succeed -> onResult(succeed) }
    val executor = ContextCompat.getMainExecutor(this)
    val signal = CancellationSignal()

    BiometricPrompt.Builder(this)
        .setTitle(getString(R.string.login_fingerprint_title))
        .setSubtitle(getString(R.string.login_fingerprint_subtitle))
        .setNegativeButton(
            getString(R.string.login_fingerprint_negative_button),
            executor
        ) { _, _ ->
            onResult(false)
        }
        .build()
        .authenticate(signal, executor, callback)
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.P)
private val isFingerprintSupported = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
