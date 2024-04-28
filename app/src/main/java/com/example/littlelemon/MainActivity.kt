package com.example.littlelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.littlelemon.ui.theme.LittleLemonTheme
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = getSharedPreferences(
            getString(R.string.app_name), MODE_PRIVATE)

        val firstName = sharedPref.getString(KEY_FIRSTNAME, "");
        val lastName = sharedPref.getString(KEY_LASTNAME, "");
        val email = sharedPref.getString(KEY_EMAIL, "");

        setContent {
            LittleLemonTheme {
                val navController = rememberNavController()
                val startRoute =
                    if (firstName == "" && lastName == "" && email == "")
                        Onboarding.route
                    else
                        Home.route

                NavHost(navController = navController, startDestination = startRoute) {
                    composable(Onboarding.route) {
                        Onboarding(navController, sharedPref)
                    }
                    composable(Home.route) {
                        Home(navController, sharedPref)
                    }
                    composable(Profile.route) {
                        Profile(navController, sharedPref)
                    }
                }
            }
        }
    }

    companion object {
        const val KEY_FIRSTNAME = "firstName"
        const val KEY_LASTNAME = "lastName"
        const val KEY_EMAIL = "email"
    }
}