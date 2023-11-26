package com.nasywa.freshfusion.menu

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nasywa.freshfusion.R
import com.nasywa.freshfusion.ui.theme.JosefinSans
import com.nasywa.freshfusion.ui.theme.background2
import com.nasywa.freshfusion.ui.theme.selectedItemColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySearchBar(
    query: String, onQueryChange: (String) -> Unit, modifier: Modifier = Modifier
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {},
        active = false,
        onActiveChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search, contentDescription = null, tint = selectedItemColor
            )
        },
        placeholder = {
            Text(
                stringResource(R.string.found_fusion), color = selectedItemColor, fontFamily = JosefinSans
            )
        },
        shape = MaterialTheme.shapes.large,
        colors = SearchBarDefaults.colors(background2),
        modifier = modifier
            .padding(18.dp)
            .fillMaxWidth()
            .heightIn(min = 50.dp)
            .testTag("Search"),
    ) {}
}