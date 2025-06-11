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
import coil.compose.rememberAsyncImagePainter
import com.target.targetcasestudy.data.model.Deal
import com.target.targetcasestudy.ui.deal_list.DealListUiState
import com.target.targetcasestudy.ui.deal_list.viewmodel.DealListViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.target.targetcasestudy.utils.Constants
import com.target.targetcasestudy.utils.CustomColors
import com.target.targetcasestudy.utils.Dimension

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
          shadowElevation = Dimension.Padding.xs,
          modifier = Modifier.fillMaxWidth(),
          color = CustomColors.ELEVATION_BG_COLOR
        ) {
          Column(modifier = Modifier.padding(bottom = Dimension.Padding.s)) {
            Text(
              text = Constants.NAVBAR_TITLE_MAIN_SCREEN,
              fontSize = Dimension.FontSize.display,
              fontWeight = Dimension.Weight.bold,
              color = CustomColors.DEAL_TEXT_BASE_COLOR,
              modifier = Modifier
                .padding(start = Dimension.Padding.xl, top = Dimension.Padding.xl, bottom = Dimension.Padding.xxl)
            )
          }
        }

        LazyColumn(
          modifier = Modifier
            .fillMaxSize()
            .padding(Dimension.Padding.xxs),
          verticalArrangement = Arrangement.spacedBy(Dimension.Padding.l)
        ) {
          items(deals) { deal ->
            DealItemCard(dealItem = deal, onClick = { onItemClick(deal) })

            HorizontalDivider(
              modifier = Modifier
                .fillMaxWidth()
                .padding(start = Dimension.Padding.xl),
              thickness = Dimension.Padding.dividerSlim,
              color = CustomColors.BORDER_GREY
            )
          }
        }
      }

    }

    is DealListUiState.Error -> {
      Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(Constants.ERROR_TEXT_MAIN_SCREEN, color = CustomColors.ERROR)
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
      .padding(horizontal = Dimension.Padding.l, vertical = Dimension.Padding.s),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Image(
      painter = rememberAsyncImagePainter(dealItem.imageUrl.toString()),
      contentDescription = dealItem.title,
      modifier = Modifier
        .size(Dimension.Padding.dealListImageWidth, Dimension.Padding.dealListImageHeight)
        .padding(end = Dimension.Padding.l)
        .clip(RoundedCornerShape(Dimension.Padding.l)),
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
            fontWeight = Dimension.Weight.bold,
            fontSize = Dimension.FontSize.titleLarge,
            color = CustomColors.TARGET_RED
          )

          dealItem.regularPrice.let { regular ->
            Spacer(modifier = Modifier.width(Dimension.Padding.xs))
            Text(
              text = "reg. ${regular.displayString ?: ""}",
              fontSize = Dimension.FontSize.titleSmall,
              color = CustomColors.DEAL_TEXT_BASE_COLOR
            )
          }
        } ?: run {
            Text(
              text = dealItem.regularPrice.displayString ?: "Price Unavailable",
              fontWeight = Dimension.Weight.bold,
              fontSize = Dimension.FontSize.titleLarge,
              color = CustomColors.DEAL_TEXT_BASE_COLOR
            )
        }
      }


      Text(
        text = dealItem.fulfillment,
        fontSize = Dimension.FontSize.titleSmall,
        color = CustomColors.BORDER_GREY,
        modifier = Modifier.padding(top = Dimension.Padding.textPadding, bottom = Dimension.Padding.textPadding),
      )
      Spacer(modifier = Modifier.height(Dimension.Padding.m))

      Text(
        text = dealItem.title,
        fontWeight = Dimension.Weight.regular,
        fontSize = Dimension.FontSize.titleMedium,
        modifier = Modifier.padding(bottom = Dimension.Padding.textPadding)
      )
      Spacer(modifier = Modifier.height(Dimension.Padding.m))

      Text(
        text = buildAnnotatedString {
          if (dealItem.availability.isEmpty()) {
            withStyle(style = SpanStyle(color = CustomColors.DEAL_TEXT_BASE_COLOR)) {
              append("Unavailable")
            }
          } else {
            withStyle(style = SpanStyle(color = CustomColors.DEAL_AVAILABILITY_COLOR)) {
              append(dealItem.availability)
            }
            append(" in aisle ")
            append(dealItem.aisle.uppercase())
          }
        },
        fontSize = Dimension.FontSize.titleSmall
      )

    }
  }
}