package com.example.bookkyandroid.data.model

data class BookDetailTestModel(val name : String, val contents : String, val date : String, val rating : Float, val like : Int, var isExpanded : Boolean = false)
