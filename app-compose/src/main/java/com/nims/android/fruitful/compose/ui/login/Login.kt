package com.nims.android.fruitful.compose.ui.login

import android.content.Context
import android.content.Intent
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
import com.nims.android.fruitful.compose.MainActivity
import com.nims.android.fruitful.compose.ui.FruitfulTheme
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues

@Composable
fun Login(
    viewModel: LoginViewModel
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
                    onClick = { launchMainActivity(context) },
                ) {
                    Text(text = "Login")
                }
            }
        }
    }
}

private fun launchMainActivity(context: Context) {
    val mainActivity = Intent(context, MainActivity::class.java)
    context.startActivity(mainActivity)
}
