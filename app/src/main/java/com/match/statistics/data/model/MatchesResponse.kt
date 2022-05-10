package com.match.statistics.data.model

import com.google.gson.annotations.SerializedName
import com.match.statistics.domain.model.lol.Match

/**
 * 데이터 엔티티
 */
data class MatchesResponse(
    @SerializedName("games")
    val games:List<GameModel>,
    @SerializedName("champions")
    val champions:List<ChampionModel>,
    @SerializedName("positions")
    val positions: List<PositionModel>
)

data class GameModel(
    @SerializedName("gameId")
    val gameId:String,
    @SerializedName("champion")
    val champion:GameChampionModel,
    @SerializedName("spells")
    val spells:List<SpellModel>,
    @SerializedName("items")
    val items:List<ItemModel>,
    @SerializedName("createDate")
    val createDate:Long,
    @SerializedName("gameType")
    val gameType:String,
    @SerializedName("gameLength")
    val gameLength:Int,
    @SerializedName("isWin")
    val isWin:Boolean,
    @SerializedName("peak")
    val peak:List<String>,
    @SerializedName("stats")
    val stats:StatsModel,

)

data class GameChampionModel(
    @SerializedName("imageUrl")
    val imageUrl:String
)

data class SpellModel(
    @SerializedName("imageUrl")
    val imageUrl:String
)

data class ItemModel(
    @SerializedName("imageUrl")
    val imageUrl:String
)


data class StatsModel(
    @SerializedName("general")
    val general:GeneralModel
)

data class GeneralModel(
    @SerializedName("kill")
    val kill:Int,
    @SerializedName("death")
    val death:Int,
    @SerializedName("assist")
    val assist:Int,
    @SerializedName("opScoreBadge")
    val opScoreBadge:String,
    @SerializedName("contributionForKillRate")
    val contributionForKillRate:String,
    @SerializedName("largestMultiKillString")
    val largestMultiKillString:String
)

data class ChampionModel(
    @SerializedName("name")
    val name:String,
    @SerializedName("imageUrl")
    val imageUrl:String,
    @SerializedName("games")
    val games:Int,
    @SerializedName("wins")
    val wins:Int,
)

data class PositionModel(
    @SerializedName("position")
    val position:String,
    @SerializedName("games")
    val games:String,
    @SerializedName("wins")
    val wins:String,
)

/**
 * 도메인 매퍼
 */
fun MatchesResponse.mapToDomain():List<Match> {
    return this.games.map {
        Match(
            gameId = it.gameId,
            championImageUrl = it.champion.imageUrl,
            spellsImageUrlList = it.spells.map { spell -> spell.imageUrl },
            itemsImageUrlList = it.items.dropLast(1).map { item -> item.imageUrl },
            wardIconUrl = it.items.last().imageUrl,
            createDate = it.createDate,
            gameType = it.gameType,
            gameLength = it.gameLength.toString(),
            isWin = it.isWin,
            peaksImageUrlList = it.peak,
            kill = it.stats.general.kill,
            death = it.stats.general.death,
            assist = it.stats.general.assist,
            opScoreBadge = it.stats.general.opScoreBadge,
            contributionForKillRate = it.stats.general.contributionForKillRate,
            largestMultiKillString = it.stats.general.largestMultiKillString
        )
    }
}