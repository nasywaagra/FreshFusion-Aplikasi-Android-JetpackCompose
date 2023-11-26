package com.nasywa.freshfusion.freshfusionapp.detail

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.nasywa.freshfusion.R
import com.nasywa.freshfusion.ViewModelFactory
import com.nasywa.freshfusion.di.Injection
import com.nasywa.freshfusion.data.Result
import com.nasywa.freshfusion.ui.theme.JosefinSans
import com.nasywa.freshfusion.ui.theme.background1
import com.nasywa.freshfusion.ui.theme.selectedItemColor

@Composable
fun DetailScreen(
    fusionId: Long,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
) {
    val context = LocalContext.current
    viewModel.result.collectAsState(initial = Result.Loading).value.let { result ->
        when (result) {
            is Result.Loading -> {
                viewModel.getFusionById(fusionId)
            }

            is Result.Success -> {
                val data = result.data
                DetailFusion(
                    data.name,
                    data.photo,
                    data.desc,
                    data.materials,
                    data.steps,
                    onBackClick = navigateBack,
                )
            }

            is Result.Error -> {
                Toast.makeText(
                    context, stringResource(id = R.string.error), Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

@Composable
fun DetailFusion(
    name: String,
    photoUrl: String,
    description: String,
    materials: String,
    steps: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = modifier
                .fillMaxHeight(0.33f)
                .fillMaxWidth()
                .zIndex(1f)
        ) {
            AsyncImage(
                model = photoUrl,
                contentDescription = name,
                modifier = modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.back),
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { onBackClick() }
                    .clip(CircleShape)
                    .background(selectedItemColor)
                    .padding(5.dp),
                tint = background1
            )

            ElevatedCard(
                modifier = modifier
                    .wrapContentWidth()
                    .align(Alignment.BottomCenter)
                    .offset(y = 42.dp)
                    .padding(start = 32.dp, end = 32.dp),
                colors = CardDefaults.cardColors(selectedItemColor)
            ) {
                Text(
                    text = name,
                    textAlign = TextAlign.Center,
                    modifier = modifier.padding(22.dp),
                    fontSize = 27.sp,
                    fontFamily = JosefinSans,
                    fontWeight = FontWeight.Bold,
                    color = background1
                )
            }
        }
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(background1)
                .padding(horizontal = 18.dp)
                .verticalScroll(rememberScrollState())
                .padding(top = 52.dp),
        ) {
            DetailComponent(section = stringResource(id = R.string.desc), value = description)
            DetailComponent(section = stringResource(id = R.string.materials), value = materials)
            DetailComponent(section = stringResource(id = R.string.steps), value = steps)
        }
    }
}

@Composable
fun DetailComponent(
    modifier: Modifier = Modifier, section: String, value: String
) {
    Text(
        text = section,
        fontWeight = FontWeight.Bold,
        fontFamily = JosefinSans,
        fontSize = 22.sp,
        modifier = modifier.padding(bottom = 7.dp),
        color = Color.Black
    )
    Divider(
        color = Color.Black, thickness = 3.dp, modifier = modifier.padding(bottom = 7.dp)
    )

    Text(
        text = value,
        fontFamily = JosefinSans,
        fontSize = 18.sp,
        modifier = modifier.padding(bottom = 18.dp),
        color = Color.Black
    )
}
