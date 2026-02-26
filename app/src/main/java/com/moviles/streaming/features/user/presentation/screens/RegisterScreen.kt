package com.moviles.streaming.features.user.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moviles.streaming.features.user.presentation.viewmodel.RegisterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onBackToLogin: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val username by viewModel.username.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    val rol by viewModel.rol.collectAsStateWithLifecycle()
    var expanded by remember { mutableStateOf(false) }
    val roles = listOf("follower", "streamer")

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onRegisterSuccess()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Crear Cuenta",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
                
                Spacer(modifier = Modifier.height(32.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = { viewModel.onUsernameChange(it) },
                    label = { Text("Usuario", color = Color.Gray) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.DarkGray,
                        cursorColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Selector de Rol (Exposed Dropdown Menu)
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = rol,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Rol", color = Color.Gray) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.DarkGray
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(Color(0xFF1A1A1A))
                    ) {
                        roles.forEach { selectionOption ->
                            DropdownMenuItem(
                                text = { Text(selectionOption, color = Color.White) },
                                onClick = {
                                    viewModel.onRolChange(selectionOption)
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    label = { Text("Contraseña", color = Color.Gray) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.DarkGray,
                        cursorColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))

                if (uiState.isLoading) {
                    CircularProgressIndicator(color = Color.White)
                } else {
                    Button(
                        onClick = { viewModel.onRegisterClick() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        enabled = username.isNotBlank() && password.isNotBlank(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        )
                    ) {
                        Text("REGISTRARSE", fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                TextButton(onClick = onBackToLogin) {
                    Text("¿Ya tienes cuenta? Inicia sesión", color = Color.White)
                }

                AnimatedVisibility(visible = uiState.error != null) {
                    Text(
                        text = uiState.error ?: "",
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        }
    }
}
