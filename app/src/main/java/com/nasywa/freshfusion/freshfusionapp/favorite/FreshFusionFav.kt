package com.nasywa.freshfusion.freshfusionapp.favorite

import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nasywa.freshfusion.R
import com.nasywa.freshfusion.ViewModelFactory
import com.nasywa.freshfusion.di.Injection
import com.nasywa.freshfusion.menu.FusionItem
import com.nasywa.freshfusion.model.Fusion
import com.nasywa.freshfusion.data.Result
import com.nasywa.freshfusion.ui.theme.JosefinSans
import com.nasywa.freshfusion.ui.theme.background1
import com.nasywa.freshfusion.ui.theme.background2
import com.nasywa.freshfusion.ui.theme.selectedItemColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    val context = LocalContext.current

    Column(modifier = modifier.fillMaxSize()) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.fav_fusion),
                    color = selectedItemColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = JosefinSans
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(background2),
            modifier = Modifier
        )

        Box(
            modifier = modifier
                .fillMaxSize()
                .background(background1)
        ) {
            viewModel.result.collectAsState(initial = Result.Loading).value.let { result ->
                when (result) {
                    is Result.Loading -> {
                        viewModel.getSavedFusion()
                    }

                    is Result.Success -> {
                        if (result.data.isEmpty()) {
                            Text(
                                modifier = modifier
                                    .align(Alignment.Center)
                                    .testTag("saved_error"),
                                text = stringResource(id = R.string.error_saved),
                                fontFamily = JosefinSans,
                                color = selectedItemColor
                            )
                        } else {
                            FavoriteContent(
                                fusion = result.data,
                                modifier = modifier,
                                navigateToDetail = navigateToDetail,
                                onSave = { id ->
                                    viewModel.saveFusion(fusionId = id)
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
    viewModel.observeSavedFusionChanges {
        viewModel.getSavedFusion()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoriteContent(
    fusion: List<Fusion>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
    onSave: (id: Long) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(18.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.testTag("FavoriteList")
    ) {
        items(fusion, key = { it.id }) { data ->
            FusionItem(
                photoUrl = data.photo,
                id = data.id,
                name = data.name,
                isSaved = data.isFavorite,
                modifier = Modifier
                    .clickable {
                        navigateToDetail(data.id)
                    }
                    .animateItemPlacement(tween(durationMillis = 250)),
                onSave = onSave
            )
        }
    }
}