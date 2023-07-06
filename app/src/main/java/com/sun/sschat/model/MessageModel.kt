package com.sun.sschat.model

data class MessageModel(
    var message : String ?= null,
    var senderId : String? = null,
    var timeStamp : Long ?= 0
)
