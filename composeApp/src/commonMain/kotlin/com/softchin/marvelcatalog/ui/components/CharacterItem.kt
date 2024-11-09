package com.softchin.marvelcatalog.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.softchin.marvelcatalog.domain.model.Character

@Composable
fun CharacterItem(character: Character, modifier: Modifier = Modifier ) {
    var tap by remember { mutableStateOf(false) }

    if (tap)
        CharacterDialogInfo(character = character, onTouchCloseButton = { tap = !tap })

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            shape = RoundedCornerShape(7.dp),
            modifier = modifier
                .padding(vertical = 3.dp, horizontal = 7.dp)
                .border(width = 2.dp, color = Color.Black, RoundedCornerShape(7.dp))
                .clickable {
                    tap = !tap
                }
        ) {
            AsyncImage(
                model = ImageRequest
                    .Builder(LocalPlatformContext.current)
                    .data(character.thumbnailUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Character image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )
        }

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = character.name,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis
        )
    }
}
