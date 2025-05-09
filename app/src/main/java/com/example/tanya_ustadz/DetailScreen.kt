package com.example.tanya_ustadz

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tanya_ustadz.util.ViewModelFactory
import androidx.compose.material3.Icon


const val KEY_ID_DOA = "idDoa"
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Long? = null) {
    val context = LocalContext.current
    val factory = ViewModelFactory (context)
    val viewModel: DetailViewModel = viewModel(factory = factory)
    var nama_doa by remember { mutableStateOf("") }
    var isi by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    val isDark = isSystemInDarkTheme()
    val backgroundColor = if (isDark) Color(0xFF121212) else Color.White
    val cardColor = if (isDark) Color(0xFF1E1E1E) else Color.White

    LaunchedEffect(id) {
        if (id != null) {
            val data = viewModel.getDoa(id)
            if (data != null) {
                nama_doa = data.nama_doa
                isi = data.isi
            } else {
                Toast.makeText(context, "Doa tidak ditemukan", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }
        }
    }


    Scaffold(
        containerColor = backgroundColor, // Background scaffold
        topBar = {

            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = if (isDark) Color.White else Color.Black // Sesuaikan warna icon
                        )
                    }
                },
                title = {
                    if (id == null)
                        Text(stringResource(R.string.tambah_doa))
                    else
                        Text(stringResource(R.string.edit_doa))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = cardColor, // Warna top bar putih
                    titleContentColor = if (isDark) Color.White else Color.Black, // Warna teks
                    navigationIconContentColor = if (isDark) Color.White else Color.Black // Warna icon
                ),

                actions = {
                    IconButton(onClick = {
                        if (nama_doa == "" || isi == "" ){
                            Toast.makeText(context, R.string.invalid, Toast.LENGTH_SHORT).show()
                            return@IconButton
                        }
                        if (id == null){
                            viewModel.insert(nama_doa,isi)
                        }else {
                            viewModel.update(id,nama_doa, isi)
                        }
                        navController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    if (id != null){
                        DeleteAction {
                            showDialog = true
                        }
                    }
                }
            )
        }
    ) { padding ->
        FormDoa(
            title = nama_doa,
            onTitleChange = { nama_doa = it },
            desc = isi,
            onDescChange = { isi = it },
            modifier = Modifier.padding(padding)
        )
        if (id != null && showDialog){
            DisplayAlertDialog(
                onDismissRequest = {showDialog = false}
            ) {
                showDialog = false
                viewModel.delete(id)
                navController.popBackStack()
            }
        }

    }
}

@Composable
fun FormDoa(
    title: String, onTitleChange: (String) -> Unit,
    desc: String, onDescChange: (String) -> Unit,
    modifier: Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = onTitleChange,
            label = { Text(text = stringResource(R.string.nama_doa)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = desc,
            onValueChange = onDescChange,
            label = { Text(stringResource(R.string.isi_doa)) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}



@Composable
fun DeleteAction(delete: () -> Unit){
    var expanded by remember { mutableStateOf(false) }
    IconButton (onClick = {expanded = true}){
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(R.string.lainnya),
            tint = MaterialTheme.colorScheme.primary
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(id = R.string.hapus))
                },
                onClick = {
                    expanded = false
                    delete()
                }
            )
        }
    }
}


