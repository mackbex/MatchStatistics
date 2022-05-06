package com.match.statistics.domain.model

data class Summoner(
    val name:String,
    val level:Int,
    val profileImageUrl:String,
    val profileBorderImageUrl:String,
    val url:String,
    val leagues: List<League>,
    val profileBackgroundImageUrl:String
)
