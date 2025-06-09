package com.target.targetcasestudy.data.model

data class Deal(
  val id: String,

  val title: String,

  val aisle: String,

  val description: String,

  val salePrice: Price
)