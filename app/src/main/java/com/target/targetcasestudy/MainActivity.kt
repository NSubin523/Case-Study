package com.target.targetcasestudy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.target.targetcasestudy.data.model.Deal
import com.target.targetcasestudy.ui.deal_detail.screens.DealDetailScreen
import com.target.targetcasestudy.ui.deal_list.screens.DealListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      var selectedDeal by remember { mutableStateOf<Deal?>(null) }

      if (selectedDeal == null) {
        DealListScreen(
          onItemClick = { deal ->
            selectedDeal = deal
          }
        )
      } else {
        DealDetailScreen(
          dealId = selectedDeal!!.id,
          onBackClick = { selectedDeal = null }
        )
      }
    }
  }
}
