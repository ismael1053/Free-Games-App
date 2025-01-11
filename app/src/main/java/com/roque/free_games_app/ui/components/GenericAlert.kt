package com.roque.free_games_app.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun ShowGenericDialog(
    title: String,
    description: String,
    onConfirm: () -> Unit,
    openDialog: MutableState<Boolean>
) {
    GenericAlertDialog(
        title = title,
        description = description,
        onConfirm = { onConfirm.invoke() },
        onDismiss = { openDialog.value = false })
}