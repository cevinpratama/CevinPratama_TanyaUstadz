package com.example.tanya_ustadz

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
@Composable
fun DisplayAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(stringResource(R.string.hapus)) },
        text = { Text(stringResource( R.string.peringatan_hapus)) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(stringResource( R.string.tombol_hapus), color = MaterialTheme.colorScheme.error)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(R.string.tombol_batal))
            }
        }
    )
}
