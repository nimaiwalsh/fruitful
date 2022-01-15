package com.nims.android.fruitful.compose.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.nims.android.fruitful.compose.ui.FruitfulTheme

@Composable
fun Login(
    viewModel: LoginViewModel,
    signIn: () -> Unit,
) {
    FruitfulTheme {
        ProvideWindowInsets {

            val padding = rememberInsetsPaddingValues(LocalWindowInsets.current.systemBars)
            val context = LocalContext.current

            BoxWithConstraints(
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                    onClick = { signIn() },
                ) {
                    Text(text = "Login")
                }
            }
        }
    }
}
