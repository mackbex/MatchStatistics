package com.match.statistics.domain.model.lol


data class Match(
    val gameId:String,
    val champion:String,
    val spells:List<String>,
    val items:List<String>,
    val createDate:Long,
    val gameType:String,
    val gameLength:Int,
    val isWin:Boolean,
    val peak:List<String>,
    val kill:Int,
    val death:Int,
    val assist:Int,
    val opScoreBadge:String,
    val contributionForKillRate:String,
)
