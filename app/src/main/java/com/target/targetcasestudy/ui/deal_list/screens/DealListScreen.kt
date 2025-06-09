package com.target.targetcasestudy.ui.deal_list.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.target.targetcasestudy.data.model.Deal
import com.target.targetcasestudy.ui.deal_list.DealListUiState
import com.target.targetcasestudy.ui.deal_list.viewmodel.DealListViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue

@Composable
fun DealListScreen(
  viewModel: DealListViewModel = hiltViewModel(),
  onItemClick: (Deal) -> Unit = {}
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  when (uiState) {
    is DealListUiState.Loading -> {
      Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
      }
    }

    is DealListUiState.Success -> {
      val deals = (uiState as DealListUiState.Success).deals
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        items(deals) { deal ->
          DealItemCard(dealItem = deal, onClick = { onItemClick(deal) })
        }
      }
    }

    is DealListUiState.Error -> {
      Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Failed to load deals", color = MaterialTheme.colorScheme.error)
      }
    }
  }
}

@Composable
fun DealItemCard(
  dealItem: Deal,
  onClick: () -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onClick() }
      .padding(12.dp)
  ) {
    Image(
      painter = rememberAsyncImagePainter("https://google.com"),
      contentDescription = dealItem.title,
      modifier = Modifier
        .size(80.dp)
        .padding(end = 12.dp),
      contentScale = ContentScale.Crop
    )

    Column(
      modifier = Modifier
        .align(Alignment.CenterVertically)
        .padding(end = 8.dp)
    ) {
      Text(
        text = dealItem.title,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
      )
      Text(
        text = dealItem.salePrice.displayString,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        color = MaterialTheme.colorScheme.primary
      )
      Text(
        text = "In stock in aisle ${dealItem.aisle}",
        fontSize = 12.sp,
        color = MaterialTheme.colorScheme.tertiary
      )
    }
  }
}
