package com.match.statistics.domain.model.lol


data class Match(
    val gameId:String,
    val championImageUrl:String,
    val spellsImageUrlList:List<String>,
    val itemsImageUrlList:List<String>,
    val createDate:Long,
    val gameType:String,
    val gameLength:Int,
    val isWin:Boolean,
    val peaksImageUrlList:List<String>,
    val kill:Int,
    val death:Int,
    val assist:Int,
    val opScoreBadge:String,
    val contributionForKillRate:String,
)
