package com.example.littlelemon

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.littlelemon.ui.theme.LittleLemonColor
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun Onboarding(navController: NavHostController? = null, sharedPref: SharedPreferences? = null) {

    val firstName: MutableState<String> = remember { mutableStateOf("") }
    val lastName: MutableState<String> = remember { mutableStateOf("") }
    val email: MutableState<String> = remember { mutableStateOf("") }
    val hasError = remember { mutableStateOf(false) }
    val hasSubmit = remember { mutableStateOf(false) }
    val message = if (hasError.value)
        stringResource(id = R.string.onboard_error)
    else
        stringResource(id = R.string.onboard_success)

    Column {
        Header()
        Box(
            modifier = Modifier
                .height(75.dp)
                .fillMaxWidth()
                .background(LittleLemonColor.green),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.get_to_know),
                fontSize = 24.sp,
                color = LittleLemonColor.white
            )
        }
        Column(modifier = Modifier.padding(10.dp, 60.dp, 0.dp, 0.dp)) {
            Box(
                Modifier
                    .height(100.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.info),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = LittleLemonColor.black
                )
            }
            if (hasSubmit.value) {
                Row(modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 30.dp)) {
                    Text(
                        text = message,
                        fontSize = 20.sp,
                        color = LittleLemonColor.red,
                    )
                }
            }
            Row {
                Text(
                    text = stringResource(id = R.string.first_name),
                    fontSize = 16.sp,
                    color = LittleLemonColor.black,
                )
            }
            Row(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 30.dp)) {
                OutlinedTextField(
                    value = firstName.value,
                    onValueChange = { firstName.value = it },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Row {
                Text(
                    text = stringResource(id = R.string.last_name),
                    fontSize = 16.sp,
                    color = LittleLemonColor.black
                )
            }
            Row(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 30.dp)) {
                OutlinedTextField(
                    value = lastName.value,
                    onValueChange = { lastName.value = it },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Row {
                Text(
                    text = stringResource(id = R.string.email_address),
                    fontSize = 16.sp,
                    color = LittleLemonColor.black
                )
            }
            Row {
                OutlinedTextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
            }
            Row(modifier = Modifier.padding(0.dp, 60.dp, 0.dp, 0.dp)) {
                Button(
                    onClick = {
                        if (firstName.value.isBlank() ||
                            lastName.value.isBlank() ||
                            email.value.isBlank()) {
                            hasError.value = true;
                        } else {
                            if (sharedPref != null) {
                                with (sharedPref.edit()) {
                                    putString(MainActivity.KEY_FIRSTNAME, firstName.value)
                                    putString(MainActivity.KEY_LASTNAME, lastName.value)
                                    putString(MainActivity.KEY_EMAIL, email.value)
                                    apply()
                                }
                            }
                        }
                        navController?.navigate(Profile.route)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(LittleLemonColor.yellow, contentColor = LittleLemonColor.black),
                ) {
                    Text(text = "Register", fontSize = 20.sp)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    Onboarding()
}