package com.example.littlelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.map
import com.example.littlelemon.ui.theme.LittleLemonTheme
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.room.Room
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.http.ContentType
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
    }

    private val database by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "menuDb").build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = getSharedPreferences(
            getString(R.string.app_name), MODE_PRIVATE)

        val firstName = sharedPref.getString(KEY_FIRSTNAME, "");
        val lastName = sharedPref.getString(KEY_LASTNAME, "");
        val email = sharedPref.getString(KEY_EMAIL, "");
        var menuItems: List<MenuItemRoom> = emptyList()

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
                        Home(navController, menuItems)
                    }
                    composable(Profile.route) {
                        Profile(navController, sharedPref)
                    }
                }
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            clearDatabase()
            val menus = fetchMenu()
            menuItems = saveMenuToDatabase(menus)
        }
    }

    private suspend fun fetchMenu(): List<MenuItemNetwork> {
        val response: MenuNetwork = httpClient
            .get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
            .body<MenuNetwork>()

        return response.menu
    }

    private fun clearDatabase() {
        database.menuItemDao().delete()
    }

    private fun saveMenuToDatabase(menuItemsNetwork: List<MenuItemNetwork>) : List<MenuItemRoom> {
        val menuItemsRoom = menuItemsNetwork.map { it.toMenuItemRoom() }
        database.menuItemDao().insertAll(*menuItemsRoom.toTypedArray())

        return menuItemsRoom
    }

    companion object {
        const val KEY_FIRSTNAME = "firstName"
        const val KEY_LASTNAME = "lastName"
        const val KEY_EMAIL = "email"
    }
}