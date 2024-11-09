package com.softchin.marvelcatalog.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.softchin.marvelcatalog.domain.model.Character

@Composable
fun CharacterDialogInfo(
    modifier: Modifier = Modifier,
    character: Character,
    onTouchCloseButton: () -> Unit
) {
    Dialog(onDismissRequest = { onTouchCloseButton() }) {
        Card(
            modifier = Modifier
                .border(width = 0.5.dp, color = Color.Black, shape = RoundedCornerShape(12.dp))
                .wrapContentSize()
                .clip(RoundedCornerShape(12.dp))

        ) {

            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.background(
                    color = Color(red = 255, green = 245, blue = 246),
                    shape = RoundedCornerShape(12.dp)
                )
            ) {
                Row(
                    modifier = Modifier.zIndex(100F).fillMaxWidth().padding(start = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = character.name, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)

                    IconButton(
                        onClick = { onTouchCloseButton() },
                        modifier = Modifier
                            .padding(end = 10.dp, top = 5.dp, bottom = 5.dp)
                            .background(color = Color.Black, shape = CircleShape)
                            .size(23.dp)
                    ) {
                        Icon(
                            Icons.Filled.Close,
                            contentDescription = "Cerrar",
                            modifier = Modifier.size(30.dp),
                            tint = Color.White
                        )
                    }
                }

                AsyncImage(
                    model = ImageRequest.Builder(LocalPlatformContext.current)
                        .data(character.thumbnailUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Imagen del personage ${character.name}",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.padding(bottom = 10.dp).fillMaxWidth().height(250.dp)
                        .align(Alignment.CenterHorizontally)
                )

                if (character.description.isNotEmpty()) {
                    Divider(
                        modifier = modifier.padding(vertical = 7.dp),
                        thickness = Dp.Hairline,
                        color = Color.Black
                    )

                    Text(
                        text = character.description,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        textAlign = TextAlign.Start,
                        fontStyle = FontStyle.Italic,
                    )
                }
            }
        }
    }
}