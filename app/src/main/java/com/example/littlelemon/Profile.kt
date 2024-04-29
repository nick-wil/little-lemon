package com.example.littlelemon

import android.content.SharedPreferences
import android.icu.lang.UCharacter.VerticalOrientation
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.littlelemon.ui.theme.LittleLemonColor

@Composable
fun Profile(navController: NavHostController? = null, sharedPref: SharedPreferences? = null) {
    var first = "";
    var last = "";
    var email = "";

    if (sharedPref != null) {
        first = sharedPref.getString(MainActivity.KEY_FIRSTNAME, "").toString()
        last = sharedPref.getString(MainActivity.KEY_LASTNAME, "").toString()
        email = sharedPref.getString(MainActivity.KEY_EMAIL, "").toString()
    }

    Column(modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)) {
        Header()
        Box(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomStart
        ) {
            Text(
                text = "Profile Information",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = LittleLemonColor.black,

            )
        }
        Column(modifier = Modifier.padding(0.dp, 40.dp, 0.dp, 5.dp)) {
            Text(
                text = stringResource(id = R.string.first_name),
                fontSize = 20.sp,
                color = LittleLemonColor.black,
            )
        }
        Column(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp)) {
            Text(
                text = first,
                modifier = Modifier.fillMaxWidth()
                    .padding(10.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
        }
        Column(modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 5.dp)) {
            Text(
                text = stringResource(id = R.string.last_name),
                fontSize = 20.sp,
                color = LittleLemonColor.black
            )
        }
        Column(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp)) {
            Text(
                text = last,
                modifier = Modifier.fillMaxWidth()
                    .padding(10.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
        }
        Column(modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 5.dp)) {
            Text(
                text = stringResource(id = R.string.email_address),
                fontSize = 20.sp,
                color = LittleLemonColor.black
            )
        }
        Column {
            Text(
                text = email,
                modifier = Modifier.fillMaxWidth()
                    .padding(10.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
        }
        Column(modifier = Modifier.padding(0.dp, 60.dp, 0.dp, 0.dp)) {
            Button(
                onClick = {
                    if (sharedPref != null) {
                        with (sharedPref.edit()) {
                            putString(MainActivity.KEY_FIRSTNAME, "")
                            putString(MainActivity.KEY_LASTNAME, "")
                            putString(MainActivity.KEY_EMAIL, "")
                            apply()
                        }
                    }
                    navController?.navigate(Onboarding.route)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(LittleLemonColor.yellow, contentColor = LittleLemonColor.black),
            ) {
                Text(text = "Log out", fontSize = 20.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    Profile()
}