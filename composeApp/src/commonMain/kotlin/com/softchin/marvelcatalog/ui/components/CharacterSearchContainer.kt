package com.softchin.marvelcatalog.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
fun CharacterSearchContainer(modifier: Modifier = Modifier, character: Character) {

    var tapCharacterResult by remember { mutableStateOf(false) }

    if (tapCharacterResult)
        CharacterDialogInfo(
            character = character,
            onTouchCloseButton = { tapCharacterResult = !tapCharacterResult })
    Column {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.padding(10.dp)
                .clickable { tapCharacterResult = !tapCharacterResult },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalPlatformContext.current)
                    .data(character.thumbnailUrl)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.FillBounds,
                contentDescription = "Image of ${character.name}",
                modifier = Modifier.padding(10.dp).size(50.dp).clip(CircleShape)
            )

            Text(
                modifier = Modifier.padding(horizontal = 20.dp).fillMaxWidth(),
                text = character.name,
                color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}