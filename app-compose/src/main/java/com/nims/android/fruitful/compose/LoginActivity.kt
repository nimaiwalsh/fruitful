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

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.nims.android.fruitful.compose.ui.login.Login
import com.nims.android.fruitful.compose.utils.FirebaseUtil
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    // MOVE TO STRINGS FILE
    // WebApp Auth0 client id
    val RELEASE_DEFAULT_WEB_CLIENT_ID = "815771546206-iptp0k074gnsi4udjgh5sm7gvtvls8sd.apps.googleusercontent.com"
    val DEBUG_DEFAULT_WEB_CLIENT_ID = "212877791041-u48o1nmebmatdvqvk7n0ggdoojfrqugi.apps.googleusercontent.com"

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    // Initialize Firebase Auth
    private val auth = FirebaseUtil.auth
    private val authUI = FirebaseUtil.authUI

    val RC_SIGN_IN = 78906

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent { Login(hiltViewModel()) { signIn2() } }

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(DEBUG_DEFAULT_WEB_CLIENT_ID)
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    override fun onStart() {
        super.onStart()

        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth?.currentUser

        //SIGNED IN
        if (currentUser != null) {
            Log.d(TAG, "currentUserSignedIn: success")
            launchMainActivity()
        }
    }

    private fun launchMainActivity() {
        val mainActivity = Intent(this, MainActivity::class.java)
        this.startActivity(mainActivity)
    }

    //SIGN IN
    ///////////////////////////

    private val signInLauncher = registerForActivityResult(FirebaseAuthUIActivityResultContract()) { res ->
        this.onSignInResult(res)
    }

    // Choose authentication providers
    private val providers = arrayListOf(
        AuthUI.IdpConfig.GoogleBuilder().build()
    )

    private fun signIn2() {
        // Create and launch sign-in intent
        val signInIntent = authUI
            ?.createSignInIntentBuilder()
            ?.setAvailableProviders(providers)
            ?.build()

        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val user = auth?.currentUser
            launchMainActivity()
            // ...
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }
}
