package com.target.targetcasestudy.ui.deal_detail.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.target.targetcasestudy.data.model.Deal
import com.target.targetcasestudy.ui.deal_detail.DealDetailUiState
import com.target.targetcasestudy.ui.deal_detail.viewmodel.DealDetailViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DealDetailScreen(
    dealId: Int,
    onBackClick: () -> Unit,
    viewModel: DealDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getDealById(dealId)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        is DealDetailUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is DealDetailUiState.Error -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Failed to load deal", color = Color.Red)
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
            shadowElevation = 6.dp,
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFFF5F5F5)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text(
                    text = "Details",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 8.dp)
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
                .height(400.dp)
                .padding(16.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        // Title
        Text(
            text = deal.title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        // Price Section
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            deal.salePrice?.let { sale ->
                Text(
                    text = sale.displayString ?: "",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
                deal.regularPrice.let { regular ->
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "reg. ${regular.displayString ?: ""}",
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                }
            } ?: run {
                Text(
                    text = deal.regularPrice.displayString ?: "",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }

        Text(
            text = deal.fulfillment,
            fontSize = 16.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        Text(
            text = "Product Details",
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp)
        )

        // Description
        Text(
            text = deal.description,
            fontSize = 14.sp,
            color = Color.DarkGray,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        // Bottom Add to Cart Button
        Box(
            modifier = Modifier.fillMaxWidth().padding(16.dp).clip(RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = { /* TODO: Add cart action */ },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text(text = "Add to Cart", fontSize = 16.sp)
            }
        }
    }
}
