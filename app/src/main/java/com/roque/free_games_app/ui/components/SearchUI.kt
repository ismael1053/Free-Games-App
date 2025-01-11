package com.roque.free_games_app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.roque.free_games_app.data.local.model.GameModel
import com.roque.free_games_app.navigation.Screen
import com.roque.free_games_app.ui.theme.DefaultBackgroundColor
import com.roque.free_games_app.ui.theme.FontColor

@Composable
fun SearchUI(
    navController: NavController,
    games: List<GameModel>,
    itemClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = DefaultBackgroundColor)
            .padding(top = 8.dp)

    ) {
        items(items = games, itemContent = { game ->
            Row(
                modifier = Modifier
                    .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
                    .clickable {
                        itemClick.invoke()
                        navController.navigate(
                            Screen.Detail.route.plus(
                                "/${game.id}"
                            )
                        )
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(game.thumbnail),
                    contentDescription = game.title,
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Gray)
                        .aspectRatio(16 / 9f),
                    contentScale = ContentScale.Crop
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Text(
                        text = game.title,
                        color = FontColor,
                        modifier = Modifier.padding(
                            start = 8.dp,
                            top = 4.dp
                        ),
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = game.releaseDate,
                        color = FontColor,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        })
    }
}