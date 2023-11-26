package com.nasywa.freshfusion.freshfusionapp.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nasywa.freshfusion.R
import com.nasywa.freshfusion.ViewModelFactory
import com.nasywa.freshfusion.di.Injection
import com.nasywa.freshfusion.menu.MySearchBar
import com.nasywa.freshfusion.ui.theme.background1
import com.nasywa.freshfusion.data.Result
import com.nasywa.freshfusion.menu.FusionItem
import com.nasywa.freshfusion.model.Fusion
import com.nasywa.freshfusion.ui.theme.JosefinSans
import com.nasywa.freshfusion.ui.theme.selectedItemColor

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    val context = LocalContext.current

    var query by remember { mutableStateOf("") }
    viewModel.getSearchFusion(query)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(background1)
    ) {
        MySearchBar(
            query = query,
            onQueryChange = { newQuery ->
                query = newQuery
                viewModel.getSearchFusion(newQuery)
            },
        )

        viewModel.result.collectAsState(initial = Result.Loading).value.let { result ->
            when (result) {
                is Result.Loading -> {
                    viewModel.getAllFusion()
                }

                is Result.Success -> {
                    if (result.data.isEmpty()) {
                        Text(
                            modifier = modifier
                                .align(Alignment.Center)
                                .testTag("home_error"),
                            text = stringResource(id = R.string.error_home),
                            color = selectedItemColor,
                            fontFamily = JosefinSans
                        )
                    } else {
                        HomeContent(
                            fusion = result.data,
                            modifier = modifier,
                            navigateToDetail = navigateToDetail,
                            onSave = { id ->
                                viewModel.saveFusion(fusionId = id)
                                viewModel.getSearchFusion(query)
                            }
                        )
                    }
                }

                is Result.Error -> {
                    Toast.makeText(
                        context,
                        stringResource(id = R.string.error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}

@Composable
fun HomeContent(
    fusion: List<Fusion>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
    onSave: (id: Long) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(18.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.testTag("FusionList")
    ) {
        item {
            Spacer(modifier = modifier.padding(top = 67.dp))
        }
        items(fusion, key = { it.id }) { data ->
            FusionItem(
                photoUrl = data.photo,
                id = data.id,
                name = data.name,
                isSaved = data.isFavorite,
                modifier = Modifier.clickable {
                    navigateToDetail(data.id)
                },
                onSave = onSave
            )
        }
    }
}