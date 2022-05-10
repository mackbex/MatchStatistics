package com.match.statistics.util.gson

import com.google.gson.*
import com.match.statistics.data.model.LeagueModel
import com.match.statistics.data.model.SummonerModel
import com.match.statistics.data.model.TierRankModel
import java.lang.Exception
import java.lang.reflect.Type

/**
 * Gson 커스텀 Deserializer (사용안함
 */
class SummonerDeserializer: JsonDeserializer<SummonerModel> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): SummonerModel {
        try {
            json?.let { el ->
                val summoner = el.asJsonObject["summoner"].asJsonObject
                return SummonerModel(
                        name = summoner["name"].asString,
                        level = summoner["level"].asInt,
                        profileImageUrl = summoner["profileImageUrl"].asString,
                        leagues = summoner["leagues"].asJsonArray.map { league ->
                            val leagueObj = league.asJsonObject
                            val tierRankObj = leagueObj["tierRank"].asJsonObject
                            val winRate = (leagueObj["wins"].asFloat / (leagueObj["wins"].asFloat + leagueObj["losses"].asFloat) * 100).toInt()
                            LeagueModel(
                                wins = leagueObj["wins"].asInt,
                                losses = leagueObj["losses"].asInt,
                                winRate = winRate,
                                tierRank = TierRankModel(
                                    name = tierRankObj["name"].asString,
                                    tier = tierRankObj["tier"].asString,
                                    imageUrl = tierRankObj["imageUrl"].asString,
                                    lp = tierRankObj["lp"].asInt
                                ))
                        })
            } ?: kotlin.run {
                return context!!.deserialize(json, SummonerModel::class.java)
            }
        }
        catch (e:Exception) {
            throw e
        }
    }
}