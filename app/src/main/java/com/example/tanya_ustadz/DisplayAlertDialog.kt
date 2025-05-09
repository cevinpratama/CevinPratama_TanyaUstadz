package com.example.tanya_ustadz

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun DisplayAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
){
    AlertDialog(
        text = { Text(stringResource(R.string.pesan_hapus)) },
        confirmButton = {
            TextButton(onClick = {onConfirmation() }) {
                Text(stringResource(R.string.tombol_hapus))
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(stringResource(R.string.tombol_batal))
            }
        },
        onDismissRequest = {onDismissRequest() }

    )
}