package com.example.littlelemon

import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.littlelemon.ui.theme.LittleLemonColor

@Composable
fun Home(navController: NavHostController? = null, sharedPref: SharedPreferences? = null) {
    Box(modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Little Lemon Logo",
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .height(100.dp)
                    .width(150.dp),
            )
            Button(
                onClick = {
                    navController?.navigate(Profile.route)
                },
                modifier = Modifier.align(Alignment.CenterVertically)
                    .padding(60.dp, 0.dp, 0.dp, 0.dp),
                colors = ButtonDefaults.buttonColors(LittleLemonColor.yellow, contentColor = LittleLemonColor.black),
            ) {
                Text(text = "Profile", fontSize = 10.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    Home()
}