package com.softchin.marvelcatalog.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun SearchbarCharacter(query: String, onValueChange: (String) -> Unit, clearQuery: () -> Unit) {
    val keyboard = LocalSoftwareKeyboardController.current

    TextField(
        value = query,
        onValueChange = { onValueChange(it) },
        leadingIcon = {
            Icon(
                Icons.Outlined.Search,
                contentDescription = "Search",
                modifier = Modifier.padding(start = 20.dp, end = 18.dp)
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) Icon(
                Icons.Default.Close,
                contentDescription = "Borrar texto",
                modifier = Modifier.padding(end = 15.dp).clickable { clearQuery() },
                tint = Color.LightGray
            )
        },
        singleLine = true,
        placeholder = { Text("Busca un personaje...") },
        textStyle = TextStyle(fontSize = 15.sp),
        keyboardActions = KeyboardActions(onDone = { keyboard?.hide() }),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            backgroundColor = Color.Transparent,
            cursorColor = Color.White,
            placeholderColor = Color.White,
            leadingIconColor = Color.White,
            disabledTrailingIconColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedLabelColor = Color.Transparent,
            focusedLabelColor = Color.Transparent,
            disabledLabelColor = Color.Transparent,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 10.dp, end = 10.dp, top = 30.dp, bottom = 10.dp)
            .clip(RoundedCornerShape(60.dp))
            .border(width = 0.5.dp, color = Color.LightGray, shape = RoundedCornerShape(60.dp))
    )
}