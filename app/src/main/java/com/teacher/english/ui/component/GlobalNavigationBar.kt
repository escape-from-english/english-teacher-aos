package com.teacher.english.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.teacher.english.R
import com.teacher.english.ui.navigate.MainNavRoutes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun GlobalNavigationBar(modifier: Modifier, pagerState: PagerState) {
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            NavigationButton(
                activeIconResourceId = MainNavRoutes.Quiz.activeIconResId,
                inactiveIconResourceId = MainNavRoutes.Quiz.inactiveIconResId,
                labelResourceId = MainNavRoutes.Quiz.titleResId,
                pageIndex = MainNavRoutes.Quiz.ordinal,
                pagerState = pagerState,
                isEnable = true,
                coroutineScope = coroutineScope
            )
            NavigationButton(
                activeIconResourceId = MainNavRoutes.Profile.activeIconResId,
                inactiveIconResourceId = MainNavRoutes.Profile.inactiveIconResId,
                labelResourceId = MainNavRoutes.Profile.titleResId,
                pageIndex = MainNavRoutes.Profile.ordinal,
                pagerState = pagerState,
                isEnable = true,
                coroutineScope = coroutineScope
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NavigationButton(
    activeIconResourceId: Int,
    inactiveIconResourceId: Int,
    labelResourceId: Int,
    pageIndex: Int,
    pagerState: PagerState,
    isEnable: Boolean,
    coroutineScope: CoroutineScope
) {
    IconButton(modifier = Modifier.fillMaxHeight(), onClick = {
        coroutineScope.launch {
            pagerState.animateScrollToPage(pageIndex)
        }
    }, enabled = isEnable) {
        Column {
            Image(
                painter = painterResource(
                    id = if (pagerState.currentPage == pageIndex) activeIconResourceId else inactiveIconResourceId
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(26.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(id = labelResourceId),
                color = colorResource(
                    id = if (pagerState.currentPage == pageIndex) R.color.hex_FFFFFF else R.color.hex_515151
                ),
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.W400,
            )
        }
    }
}