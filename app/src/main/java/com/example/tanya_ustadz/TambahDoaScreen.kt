package com.example.tanya_ustadz

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.ui.res.painterResource
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tanya_ustadz.model.Doa
import com.example.tanya_ustadz.navigation.Screen
import com.example.tanya_ustadz.util.ViewModelFactory


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahDoaScreen(navController: NavHostController) {

    val isDark = isSystemInDarkTheme()
    val backgroundColor = if (isDark) Color(0xFF121212) else Color.White
    val cardColor = if (isDark) Color(0xFF1E1E1E) else Color.White
    val plusColor = if (isDark) Color.White else Color(0xFF1E1E1E)
    var showList by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .shadow(4.dp)
                    .height(70.dp)
            )
            {
                TopAppBar(
                    modifier = Modifier.height(80.dp),
                    title = {
                        Row(
                            modifier = Modifier.fillMaxHeight(),
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            Text(
                                text = stringResource(id = R.string.tambah_doa),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 17.sp,
                                fontStyle = FontStyle.Italic
                            )
                        }

                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = cardColor
                    ),
                    actions ={
                        IconButton(onClick = { showList = !showList }) {
                            Icon(
                                painter = painterResource(
                                    if (showList) R.drawable.baseline_grid_view_24
                                    else R.drawable.baseline_list_24
                                ),
                                contentDescription = stringResource(
                                    if (showList) R.string.grid
                                    else R.string.list
                                ),
                                tint = plusColor
                            )

                        }
                    }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.FormBaru.route)                }
            ){
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.tambah_doa),
                    tint = plusColor

                )
            }
        }
    )
    { innerPadding ->
        ScreenContent(
            showList,
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(innerPadding),
            navController = navController
        )
    }
}

@Composable
fun GridItem(doa: Doa, onClick: () -> Unit) {
    val isDark = isSystemInDarkTheme()
    val cardColor = if (isDark) Color(0xFF1E1E1E) else Color.White
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .border(1.dp, MaterialTheme.colorScheme.outline),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        )
    ) {
       Column (
           modifier = Modifier.padding(8.dp),
           verticalArrangement = Arrangement.spacedBy(8.dp),
       ){
          Text(
              text = doa.nama_doa,
              maxLines = 2,
              overflow = TextOverflow.Ellipsis,
              fontWeight = FontWeight.Bold
          )
           Text(
               text = doa.isi,
               maxLines = 4,
               overflow = TextOverflow.Ellipsis
           )
           Text(text = doa.tanggal)
       }
    }
}

@Composable
fun ScreenContent(showList:Boolean, modifier: Modifier = Modifier, navController: NavHostController){
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel : MainViewModel = viewModel(factory = factory)
    val data by viewModel.data.collectAsState()
    val isDark = isSystemInDarkTheme()
    val backgroundColor = if (isDark) Color(0xFF121212) else Color.White

    if (data.isEmpty()){
        Column (
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = stringResource(R.string.list_kosong))
        }
    }else{
        if(showList) {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 84.dp)
            ) {
                itemsIndexed(data) { _, doa ->
                    ListItem(doa = doa) {
                        navController.navigate(Screen.FormUbah.withId(doa.id))
                    }
                    HorizontalDivider()
                }
            }
        }else {
            LazyVerticalStaggeredGrid(
                modifier = modifier
                    .background(backgroundColor),
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 8.dp,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp, 8.dp, 8.dp,84.dp)
            ) {
                items(data){
                    GridItem(doa = it) {
                        navController.navigate(Screen.FormUbah.withId(it.id))
                    }
                }
            }
        }
    }
}

@Composable
fun ListItem(doa: Doa, onClick: () -> Unit){
    Column(
        modifier = Modifier.fillMaxWidth()
            .clickable{ onClick()}
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        Text(
            text = doa.nama_doa,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = doa.isi,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}