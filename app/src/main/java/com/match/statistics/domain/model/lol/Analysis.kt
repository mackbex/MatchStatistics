package com.match.statistics.domain.model.lol

data class Analysis(
    val games:Int,
    val wins:Int,
    val losses:Int,
    val kills:Float,
    val deaths:Float,
    val assists:Float,
    val kda:Float,
    val killContrib:Int,
    val mostChampions:List<Champion>,
    val mostPosition:String,
    val positionWinRate:Int,
)

