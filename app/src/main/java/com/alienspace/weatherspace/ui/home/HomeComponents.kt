package com.alienspace.weatherspace.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleDown
import androidx.compose.material.icons.filled.ArrowCircleUp
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.alienspace.weatherspace.ui.theme.Sizing

@Composable
fun DropDownSelector(
    modifier: Modifier,
    selectedLocation: String,
    editable: Boolean = false,
    readOnly: Boolean = true,
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    OutlinedCard(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expanded = !expanded
                }
        ) {
            Row(modifier = Modifier.padding(5.dp), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(.8f),
                    value = selectedLocation,
                    onValueChange = {},
                    readOnly = readOnly,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    )
                )

                IconButton(modifier = Modifier
                    .size(30.dp),
                    onClick = { expanded = !expanded }) {
                    Icon(
                        modifier = Modifier.padding(end = 3.dp),
                        imageVector = if (expanded) Icons.Filled.ArrowCircleDown else Icons.Filled.ArrowCircleUp,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            DropdownMenu(
                modifier = Modifier
                    .fillMaxWidth(),
                expanded = expanded,
                onDismissRequest = { expanded = !expanded },
                shape = RoundedCornerShape(10.dp)
            ) {
            }
        }
    }


//    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = {
//
//    }) {
//        DropdownMenuItem(text = { Text(text = "Hello") }, onClick = { /*TODO*/ })
//    }

}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
) {
    var text by remember {
        mutableStateOf("")
    }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var isExpanded by remember { mutableStateOf(false) }

    fun closeMenu() {
        isExpanded = false
        focusManager.clearFocus()
    }

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        OutlinedTextField(
            modifier = modifier
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    isExpanded = focusState.isFocused

                },
            value = text,
            label = {
                Text(
                    modifier = Modifier,
                    textAlign = TextAlign.Start,
                    text = "Pick Your City!",
                    style = MaterialTheme.typography.titleLarge
                )
            },
            maxLines = 1,
            onValueChange = { text = it },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            trailingIcon = {
                IconButton(onClick = {
                    focusManager.clearFocus()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Cancel,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )
        DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = isExpanded,
            onDismissRequest = { closeMenu() }) {

            repeat(5) {
                DropdownMenuItem(text = {
                    Text(text = "Hello Aliens $it")
                }, onClick = {
                    closeMenu()
                })
            }


        }
    }

}