package com.target.targetcasestudy.ui.deal_list.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

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

      Column(modifier = Modifier.fillMaxSize()) {

        Surface(
          shadowElevation = 6.dp,
          modifier = Modifier.fillMaxWidth(),
          color =  Color(0xFFF5F5F5)
        ) {
          Column(modifier = Modifier.padding(bottom = 8.dp)) {
            Text(
              text = "List",
              fontSize = 25.sp,
              fontWeight = FontWeight.Bold,
              color = Color.Black,
              modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, bottom = 20.dp)
            )
          }
        }

        LazyColumn(
          modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          items(deals) { deal ->
            DealItemCard(dealItem = deal, onClick = { onItemClick(deal) })

            HorizontalDivider(
              modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
              thickness = 1.dp,
              color = Color.Gray
            )
          }
        }
      }

    }

    is DealListUiState.Error -> {
      Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Failed to load deals", color = Color.Red)
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
      .padding(horizontal = 12.dp, vertical = 8.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Image(
      painter = rememberAsyncImagePainter(dealItem.imageUrl.toString()),
      contentDescription = dealItem.title,
      modifier = Modifier
        .size(180.dp, 160.dp)
        .padding(end = 12.dp)
        .clip(RoundedCornerShape(12.dp)),
      contentScale = ContentScale.Crop
    )

    Column(
      modifier = Modifier
        .weight(1f)
        .align(Alignment.CenterVertically)
    ) {

      Row(
        verticalAlignment = Alignment.CenterVertically
      ) {
        dealItem.salePrice?.let { sale ->
          Text(
            text = sale.displayString ?: "",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.Red
          )

          dealItem.regularPrice.let { regular ->
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "reg. ${regular.displayString ?: ""}",
              fontSize = 12.sp,
              color = Color.Black
            )
          }
        } ?: run {
            Text(
              text = dealItem.regularPrice.displayString ?: "Price Unavailable",
              fontWeight = FontWeight.Bold,
              fontSize = 20.sp,
              color = Color.Black
            )
        }
      }


      Text(
        text = dealItem.fulfillment,
        fontSize = 12.sp,
        color = Color.Gray,
        modifier = Modifier.padding(top = 2.dp, bottom = 2.dp),
      )
      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = dealItem.title,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        modifier = Modifier.padding(bottom = 2.dp)
      )
      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = buildAnnotatedString {
          if (dealItem.availability.isEmpty()) {
            withStyle(style = SpanStyle(color = Color.Black)) {
              append("Unavailable")
            }
          } else {
            withStyle(style = SpanStyle(color = Color.Green)) {
              append(dealItem.availability)
            }
            append(" in aisle ")
            append(dealItem.aisle.uppercase())
          }
        },
        fontSize = 12.sp
      )

    }
  }
}