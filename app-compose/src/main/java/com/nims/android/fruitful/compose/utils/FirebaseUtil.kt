package com.nims.android.fruitful.compose.utils

import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.nims.android.fruitful.compose.BuildConfig

/**
 * Utility class for initializing Firebase services and connecting them to the Firebase Emulator
 * Suite if necessary.
 */
object FirebaseUtil {
    /** Use emulators only in debug builds  */
    private val sUseEmulators: Boolean = BuildConfig.DEBUG
    private var AUTH: FirebaseAuth? = null
    private var AUTH_UI: AuthUI? = null
//    private var FIRESTORE: FirebaseFirestore? = null

    // Connect to the Firebase Auth emulator when appropriate. The host '10.0.2.2' is a
    // special IP address to let the Android emulator connect to 'localhost'.
    val auth: FirebaseAuth?
        get() {
            if (AUTH == null) {
                AUTH = FirebaseAuth.getInstance()

                // Connect to the Firebase Auth emulator when appropriate. The host '10.0.2.2' is a
                // special IP address to let the Android emulator connect to 'localhost'.
//                if (sUseEmulators) {
//                    AUTH!!.useEmulator("10.0.2.2", 9099)
//                }
            }
            return AUTH
        }

    // Connect to the Firebase Auth emulator when appropriate. The host '10.0.2.2' is a
    // special IP address to let the Android emulator connect to 'localhost'.

    val authUI: AuthUI?
        get() {
            if (AUTH_UI == null) {
                AUTH_UI = AuthUI.getInstance()

                // Connect to the Firebase Auth emulator when appropriate. The host '10.0.2.2' is a
                // special IP address to let the Android emulator connect to 'localhost'.
//                if (sUseEmulators) {
//                    AUTH_UI!!.useEmulator("10.0.2.2", 9099)
//                }
            }
            return AUTH_UI
        }

    // Connect to the Cloud Firestore emulator when appropriate. The host '10.0.2.2' is a
    // special IP address to let the Android emulator connect to 'localhost'.

//    val firestore: FirebaseFirestore?
//        get() {
//            if (FIRESTORE == null) {
//                FIRESTORE = FirebaseFirestore.getInstance()
//
//                // Connect to the Cloud Firestore emulator when appropriate. The host '10.0.2.2' is a
//                // special IP address to let the Android emulator connect to 'localhost'.
//                if (sUseEmulators) {
//                    FIRESTORE.useEmulator("10.0.2.2", 8080)
//                }
//            }
//            return FIRESTORE
//        }
}
