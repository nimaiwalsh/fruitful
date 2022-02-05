/*
 * Copyright (C) 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nims.android.fruitful.compose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.nims.android.fruitful.compose.ui.Main
import com.nims.android.fruitful.compose.utils.FirebaseUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // Initialize Firebase Auth
    private val auth = FirebaseUtil.auth
    private val authUI = FirebaseUtil.authUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent { Main(onLogoutClick = { signOut() }) }
    }

    override fun onStart() {
        super.onStart()

        // Determine if current user already exists
        val currentUser = auth?.currentUser
        if (currentUser == null) {
            signIn()
        }
    }

    //SIGN IN
    ///////////////////////////

    private val signInLauncher = registerForActivityResult(FirebaseAuthUIActivityResultContract()) { res ->
        this.onSignInResult(res)
    }

    // Choose authentication providers
    private val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build(),
        AuthUI.IdpConfig.GoogleBuilder().build()
    )

    private fun signIn() {
        // Create and launch sign-in intent
        val signInIntent = authUI
            ?.createSignInIntentBuilder()
            ?.setAvailableProviders(providers)
            ?.build()

        signInLauncher.launch(signInIntent)
    }

    private fun signOut() {
        authUI?.signOut(this)?.addOnCompleteListener {
            Log.d("SIGN_OUT_SUCCESS", "User successfully signed out")
            this.onStart()
        }
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == ComponentActivity.RESULT_OK) {
            // Successfully signed in
            Log.d("SIGN_IN_SUCCESS", "User signed in")
            val user = auth?.currentUser
            // ...
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
            Log.e("SIGN_IN_ERROR", "${response?.error}")
            response?.error
        }
    }
}
