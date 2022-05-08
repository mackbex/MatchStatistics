package com.match.statistics

import android.util.Log
import com.google.gson.GsonBuilder
import com.match.statistics.domain.model.lol.Champion
import org.junit.Assert.*
import org.junit.Test
import java.net.MalformedURLException
import java.net.URL

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun `url_modify`() {
        var failedUrl = "asdf//opgg-static.akamaized.net/images/lol/champion/Pantheon.png?image=q_auto&v=1591083841"

        failedUrl = failedUrl.substring(failedUrl.indexOf("//")+2)

        Log.d("##test",failedUrl)
//        var oldUrl:URL? = null
//        try {
//            oldUrl = URL(failedUrl)
//        }
//        catch (e:MalformedURLException) {
//            val u = URL("https",oldUrl.getHost(), oldUrl.getPort(), oldUrl.getFile())
//
//            Log.d("##test","${u.toURI()}")
//        }





    }
    @Test
    fun `sort_most_champtions`() {

        val json = "[{\"id\":112,\"key\":\"Viktor\",\"name\":\"빅토르\",\"imageUrl\":\"https://opgg-static.akamaized.net/images/lol/champion/Viktor.png?image=w_30&v=1\",\"games\":11,\"kills\":15,\"deaths\":6,\"assists\":5,\"wins\":7,\"losses\":4},{\"id\":106,\"key\":\"Volibear\",\"name\":\"볼리베어\",\"imageUrl\":\"//opgg-static.akamaized.net/images/lol/champion/Volibear.png?image=q_auto&v=1591083841\",\"games\":6,\"kills\":18,\"deaths\":13,\"assists\":4,\"wins\":1,\"losses\":5},{\"id\":90,\"key\":\"Malzahar\",\"name\":\"말자하\",\"imageUrl\":\"https://opgg-static.akamaized.net/images/lol/champion/Malzahar.png?image=w_30&v=1\",\"games\":3,\"kills\":3,\"deaths\":14,\"assists\":18,\"wins\":1,\"losses\":2}]"

        val gson = GsonBuilder()
            .create()

        val res = gson.fromJson<Champion>(json, Champion::class.java)

        //opgg-static.akamaized.net/images/lol/champion/Pantheon.png?image=q_auto&v=1591083841


        res
//        res.sortedByDescending { it.winRate }.take(2).map {
//            Champion(
//                imageUrl = it.imageUrl,
//                winRate = it.winRate
//            )
//        }
    }
}