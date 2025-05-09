package com.example.tanya_ustadz

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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

    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel: MainViewModel = viewModel(factory = factory)
    val showFavoritesOnly by viewModel.showFavoritesOnly

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .shadow(4.dp)
                    .height(70.dp)
            ) {
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
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = cardColor),
                    actions = {
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

                        IconButton(onClick = { viewModel.toggleFavoritesFilter() }) {
                            Icon(
                                imageVector = if (showFavoritesOnly) Icons.Filled.Star else Icons.Outlined.StarBorder,
                                contentDescription = "Filter Favorit",
                                tint = if (showFavoritesOnly) Color.Yellow else plusColor
                            )
                        }
                        IconButton(onClick = {
                            navController.navigate("recycle_bin")
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Delete, // Atau pakai icon lain seperti `Icons.Default.DeleteOutline`
                                contentDescription = "Recycle Bin",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                    }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.FormBaru.route) }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.tambah_doa),
                    tint = plusColor
                )
            }
        }
    ) { innerPadding ->
        ScreenContent(
            showList = showList,
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(innerPadding),
            navController = navController,
            viewModel = viewModel
        )
    }
}


@Composable
fun ScreenContent(
    showList: Boolean,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: MainViewModel
) {
    val data by viewModel.filteredData.collectAsState()
    val isDark = isSystemInDarkTheme()
    val backgroundColor = if (isDark) Color(0xFF121212) else Color.White

    if (data.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(R.string.list_kosong))
        }
    } else {
        if (showList) {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 84.dp)
            ) {
                itemsIndexed(data) { _, doa ->
                    ListItem(
                        doa = doa,
                        onClick = { navController.navigate(Screen.FormUbah.withId(doa.id)) },
                        onFavoriteClick = { isFav -> viewModel.updateFavorite(doa.id, isFav) }
                    )
                    HorizontalDivider()
                }
            }
        } else {
            LazyVerticalStaggeredGrid(
                modifier = modifier.background(backgroundColor),
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 8.dp,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp, 8.dp, 8.dp, 84.dp)
            ) {
                items(data) {
                    GridItem(
                        doa = it,
                        onClick = { navController.navigate(Screen.FormUbah.withId(it.id)) },
                        onFavoriteClick = { isFav -> viewModel.updateFavorite(it.id, isFav) }
                    )
                }
            }
        }
    }
}


@Composable
fun ListItem(doa: Doa, onClick: () -> Unit, onFavoriteClick: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = doa.nama_doa, fontWeight = FontWeight.Bold)
            Text(text = doa.isi, maxLines = 2, overflow = TextOverflow.Ellipsis)
        }
        IconButton(onClick = { onFavoriteClick(!doa.isFavorite) }) {
            Icon(
                imageVector = if (doa.isFavorite) Icons.Filled.Star else Icons.Outlined.StarBorder,
                contentDescription = "Favorit",
                tint = if (doa.isFavorite) Color.Yellow else Color.Gray
            )
        }
    }
}

@Composable
fun GridItem(doa: Doa, onClick: () -> Unit, onFavoriteClick: (Boolean) -> Unit) {
    val isDark = isSystemInDarkTheme()
    val cardColor = if (isDark) Color(0xFF1E1E1E) else Color.White
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .border(1.dp, MaterialTheme.colorScheme.outline),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
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
            IconButton(onClick = { onFavoriteClick(!doa.isFavorite) }) {
                Icon(
                    imageVector = if (doa.isFavorite) Icons.Filled.Star else Icons.Outlined.StarBorder,
                    contentDescription = "Favorit",
                    tint = if (doa.isFavorite) Color.Yellow else Color.Gray
                )
            }
        }
    }
}
