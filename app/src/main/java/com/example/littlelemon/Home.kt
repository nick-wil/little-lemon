@file:OptIn(ExperimentalGlideComposeApi::class)

package com.example.littlelemon

import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.room.Room
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.ui.theme.LittleLemonColor

@Composable
fun Home(navController: NavHostController? = null, databaseMenuItems: List<MenuItemRoom>? = null) {
    val searchPhrase: MutableState<String> = remember { mutableStateOf("") }

    var menuItems = if (databaseMenuItems != null) databaseMenuItems else emptyList()
    menuItems = if (!searchPhrase.value.isNullOrBlank()) {
        menuItems.filter { it.category.contains(searchPhrase.value)}
    } else {
        menuItems
    }

    Row(horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Little Lemon Logo",
            modifier = Modifier.fillMaxWidth(0.7F)
                .padding(horizontal = 20.dp)
                .height(100.dp)
                .width(200.dp),
            alignment = Alignment.Center
        )
        Button(
            onClick = {
                navController?.navigate(Profile.route)
            },
            modifier = Modifier.align(Alignment.CenterVertically)
                .padding(0.dp, 0.dp, 0.dp, 0.dp),
            colors = ButtonDefaults.buttonColors(LittleLemonColor.yellow, contentColor = LittleLemonColor.black),
        ) {
            Text(text = "Profile", fontSize = 14.sp)
        }
    }
    Column(
        modifier = Modifier
            .padding(top = 100.dp, bottom = 16.dp)
            .background(LittleLemonColor.green)
    ) {
        Text(
            text = "Little Lemon",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = LittleLemonColor.yellow
        )
        Text(
            text = "Chicago",
            fontSize = 24.sp,
            color = LittleLemonColor.cloud
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(top = 20.dp)
        ) {
            Text(
                text = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist",
                modifier = Modifier
                    .padding(bottom = 28.dp, end = 20.dp)
                    .fillMaxWidth(0.6f),
                color = LittleLemonColor.cloud,
                fontSize = 20.sp,
            )
            Image(
                painter = painterResource(id = R.drawable.upperpanelimage),
                contentDescription = "Upper Panel Image",
                modifier = Modifier.clip(RoundedCornerShape(10.dp))
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(top = 10.dp, bottom = 20.dp)
        ) {
            OutlinedTextFieldBackground(LittleLemonColor.grey) {
                OutlinedTextField(
                    value = searchPhrase.value,
                    onValueChange = { searchPhrase.value = it },
                    label = { Text("Enter search phrase") },
                    modifier = Modifier
                        .fillMaxWidth(),
                )
            }
        }
    }
    Column(
        modifier = Modifier
            .padding(top = 500.dp, bottom = 16.dp)
    ) {
        Text(
            text = "ORDER FOR DELIVERY",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = LittleLemonColor.black
        )
        HorizontalDivider(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 20.dp),
            thickness = 1.dp,
        )
    }
    MenuItemsList(menuItems)
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    Home()
}

@Composable
fun OutlinedTextFieldBackground(
    color: Color,
    content: @Composable () -> Unit
) {
    // This box just wraps the background and the OutlinedTextField
    Box {
        // This box works as background
        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(top = 8.dp, start = 10.dp, end = 10.dp) // adding some space to the label
                .background(
                    color,
                    // rounded corner to match with the OutlinedTextField
                    shape = RoundedCornerShape(4.dp)
                )
        )
        // OutlineTextField will be the content...
        content()
    }
}

@Composable
private fun MenuItemsList(items: List<MenuItemRoom>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 600.dp)
    ) {
        items(
            items = items,
            itemContent = { menuItem ->
                Column {
                    Text(
                        text = menuItem.title,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = LittleLemonColor.black
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.width(200.dp)){
                            Text(
                                text = menuItem.description,
                                fontSize = 16.sp,
                                color = LittleLemonColor.black
                            )
                            Text(
                                text = "%.2f".format(menuItem.price),
                                fontSize = 16.sp,
                                color = LittleLemonColor.black
                            )
                        }

                        GlideImage(model=menuItem.image, contentDescription = "")
                    }
                }
            }
        )
    }
}