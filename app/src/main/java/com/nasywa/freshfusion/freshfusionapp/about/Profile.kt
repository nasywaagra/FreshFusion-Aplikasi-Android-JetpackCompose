package com.nasywa.freshfusion.freshfusionapp.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nasywa.freshfusion.R
import com.nasywa.freshfusion.ui.theme.FreshFusionTheme
import com.nasywa.freshfusion.ui.theme.JosefinSans
import com.nasywa.freshfusion.ui.theme.background1
import com.nasywa.freshfusion.ui.theme.selectedItemColor

@Composable
fun CustomProfileScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        CustomColumn(
            modifier = modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomImage(
                modifier = modifier
                    .padding(start = 85.dp, end = 85.dp)
                    .border(7.dp, selectedItemColor, CircleShape)
                    .padding(12.dp)
                    .clip(CircleShape)
                    .background(selectedItemColor),
                painter = painterResource(id = R.drawable.mypic),
                contentDescription = "about me",
            )
            CustomText(
                modifier = modifier
                    .padding(top = 12.dp, bottom = 7.dp)
                    .clip(RoundedCornerShape(7.dp))
                    .background(selectedItemColor)
                    .padding(12.dp),
                text = stringResource(id = R.string.name),
                color = background1,
                fontFamily = JosefinSans,
                fontSize = 22.sp
            )
            CustomText(
                modifier = modifier
                    .padding(top = 7.dp, bottom = 12.dp)
                    .clip(RoundedCornerShape(7.dp))
                    .background(selectedItemColor)
                    .padding(12.dp),
                text = stringResource(id = R.string.email),
                color = background1,
                fontFamily = JosefinSans,
                fontSize = 20.sp
            )
        }
    }
}


@Composable
fun CustomColumn(modifier: Modifier = Modifier, horizontalAlignment: Alignment.Horizontal = Alignment.Start, content: @Composable ColumnScope.() -> Unit) {
    Column(modifier = modifier, horizontalAlignment = horizontalAlignment, content = content)
}

@Composable
fun CustomImage(modifier: Modifier = Modifier, painter: Painter, contentDescription: String?) {
    Image(modifier = modifier, painter = painter, contentDescription = contentDescription)
}

@Composable
fun CustomText(modifier: Modifier = Modifier, text: String, color: Color, fontFamily: FontFamily, fontSize: TextUnit) {
    Text(modifier = modifier, text = text, color = color, fontFamily = fontFamily, fontSize = fontSize)
}

@Preview
@Composable
fun CustomProfilePreview() {
    FreshFusionTheme {
        CustomProfileScreen()
    }
}
