package com.sun.sschat.model

data class UserModel(
    val uid : String ?= null,
    val name : String ?= null,
    val number : String ?= null,
    val imageUrl : String ?= null
)
