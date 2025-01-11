package com.alexius.talktale.presentation.talktalenav.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Article
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexius.talktale.presentation.talktalenav.BottomNavigationItem
import com.alexius.talktale.ui.theme.Grey
import com.alexius.talktale.ui.theme.Poppins
import com.alexius.talktale.ui.theme.TalkTaleTheme
import com.alexius.talktale.ui.theme.WhitePale

@Composable
fun TalkTaleBottomNavigation(
    modifier: Modifier = Modifier,
    items: List<BottomNavigationItem>,
    selected: Int,
    onItemClick: (Int) -> Unit
) {

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(
            topStart = 20.dp,
            topEnd = 20.dp,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 20.dp
        )
    ) {
        NavigationBar(
            modifier = modifier.fillMaxWidth(),
            containerColor = WhitePale,
            tonalElevation = 20.dp
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = index == selected,
                    onClick = { onItemClick(index) },
                    icon = {
                        Column(horizontalAlignment = CenterHorizontally) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.text,
                                modifier = modifier.size(25.dp)
                            )
                            Text(text = item.text, fontFamily = Poppins, fontSize = 9.sp)
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = Grey,
                        unselectedTextColor = Grey,
                        indicatorColor = Color.Transparent,
                    )
                )
            }
        }
    }

}

@Preview
@Composable
private fun Prev() {
    TalkTaleTheme {
        TalkTaleBottomNavigation(
            items =  listOf(
                BottomNavigationItem(Icons.Default.Home, "Beranda"),
                BottomNavigationItem(Icons.Outlined.Book, "StoryScape"),
                BottomNavigationItem(Icons.AutoMirrored.Outlined.Article, "Report Card"),
                BottomNavigationItem(Icons.Outlined.Person, "Profil")
            ),
            selected = 0,
            onItemClick = { }
        )
    }
}