package com.target.targetcasestudy.ui.deal_detail.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.target.targetcasestudy.data.model.Deal
import com.target.targetcasestudy.ui.deal_detail.DealDetailUiState
import com.target.targetcasestudy.ui.deal_detail.viewmodel.DealDetailViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.target.targetcasestudy.utils.Constants
import com.target.targetcasestudy.utils.CustomColors
import com.target.targetcasestudy.utils.Dimension

@Composable
fun DealDetailScreen(
    dealId: Int,
    onBackClick: () -> Unit,
    viewModel: DealDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(dealId) {
        viewModel.getDealById(dealId)
    }

    DisposableEffect(viewModel) {
        onDispose {
            viewModel.resetUiState()
        }
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        is DealDetailUiState.InitialState -> {
            Box(Modifier.fillMaxSize()){}
        }

        is DealDetailUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is DealDetailUiState.Error -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(Constants.ERROR_TEXT_DEAL_DETAIL_SCREEN, color = CustomColors.ERROR)
            }
        }

        is DealDetailUiState.Success -> {
            DealDetailContent(
                deal = (uiState as DealDetailUiState.Success).deal,
                onBackClick = onBackClick
            )
        }
    }
}

@Composable
fun DealDetailContent(
    deal: Deal,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Top Bar
        Surface(
            shadowElevation = Dimension.Padding.xs,
            modifier = Modifier.fillMaxWidth(),
            color = CustomColors.ELEVATION_BG_COLOR
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = Dimension.Padding.xl, top = Dimension.Padding.xl, bottom = Dimension.Padding.xl),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back",
                        tint = CustomColors.TARGET_RED
                    )
                }
                Text(
                    text = Constants.NAVBAR_TITLE_DETAILS,
                    fontSize = Dimension.FontSize.display,
                    fontWeight = Dimension.Weight.bold,
                    color = CustomColors.DEAL_TEXT_BASE_COLOR,
                    modifier = Modifier.padding(start = Dimension.Padding.s)
                )
            }
        }

        // Product Image
        Image(
            painter = rememberAsyncImagePainter(deal.imageUrl),
            contentDescription = deal.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimension.Padding.dealDetailImageMinHeight)
                .padding(Dimension.Padding.xl)
                .clip(RoundedCornerShape(Dimension.Padding.l))
        )

        // Title
        Text(
            text = deal.title,
            fontSize = Dimension.FontSize.titleLarge,
            modifier = Modifier.padding(horizontal = Dimension.Padding.xl, vertical = Dimension.Padding.s)
        )

        Spacer(modifier = Modifier.height(Dimension.Padding.xxl))

        // Price Section
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = Dimension.Padding.xl)
        ) {
            deal.salePrice?.let { sale ->
                Text(
                    text = sale.displayString ?: "",
                    fontSize = Dimension.FontSize.display,
                    fontWeight = Dimension.Weight.bold,
                    color = CustomColors.TARGET_RED
                )
                deal.regularPrice.let { regular ->
                    Spacer(modifier = Modifier.width(Dimension.Padding.s))
                    Text(
                        text = "reg. ${regular.displayString ?: ""}",
                        fontSize = Dimension.FontSize.titleSmall,
                        color = CustomColors.DEAL_TEXT_BASE_COLOR
                    )
                }
            } ?: run {
                Text(
                    text = deal.regularPrice.displayString ?: "",
                    fontSize = Dimension.FontSize.display,
                    fontWeight = Dimension.Weight.bold,
                    color = CustomColors.DEAL_TEXT_BASE_COLOR
                )
            }
        }

        Text(
            text = deal.fulfillment,
            fontSize = Dimension.FontSize.titleMedium,
            modifier = Modifier.padding(horizontal = Dimension.Padding.xl, vertical = Dimension.Padding.s)
        )

        Spacer(modifier = Modifier.height(Dimension.Padding.s))
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = Dimension.Padding.xxl,
            color = Color(0xFFF0F0F0)
        )

        Text(
            text = Constants.PRODUCT_DESCRIPTION_TITLE_TEXT,
            fontSize = Dimension.FontSize.titleLarge,
            color = CustomColors.DEAL_TEXT_BASE_COLOR,
            fontWeight = Dimension.Weight.bold,
            modifier = Modifier.padding(horizontal = Dimension.Padding.xl, vertical = Dimension.Padding.xxl)
        )

        Spacer(modifier = Modifier.height(Dimension.Padding.s))

        // Description
        Text(
            text = deal.description,
            fontSize = Dimension.FontSize.titleMedium,
            color = CustomColors.DEAL_DESCRIPTION_TEXT_COLOR,
            modifier = Modifier.padding(horizontal = Dimension.Padding.xl, vertical = Dimension.Padding.s)
        )

        Spacer(modifier = Modifier.height(Dimension.Padding.s))
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = Dimension.Padding.dividerSlim,
            color = CustomColors.BORDER_GREY
        )


        // Bottom Add to Cart Button
        Box(
            modifier = Modifier.fillMaxWidth().padding(Dimension.Padding.xl).clip(RoundedCornerShape(Dimension.Padding.m)),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = { /* TODO: Add cart action */ },
                modifier = Modifier.fillMaxWidth().height(Dimension.Padding.buttonMinHeight),
                shape = RoundedCornerShape(Dimension.Padding.s),
                colors = ButtonDefaults.buttonColors(containerColor = CustomColors.TARGET_RED)
            ) {
                Text(text = "Add to Cart", fontSize = Dimension.FontSize.titleMedium)
            }
        }
    }
}
