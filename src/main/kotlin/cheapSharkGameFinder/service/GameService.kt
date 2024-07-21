package cheapSharkGameFinder.service

import cheapSharkGameFinder.model.Game
import cheapSharkGameFinder.model.GameInfo
import cheapSharkGameFinder.model.apiModel.Store
import com.google.gson.Gson
import java.util.*

class GameInfoService {
    private val httpClient = CheapSharkHttpClient()
    private val gson = Gson()

    fun getGameDetails(){
        val gameId = readGameId()

        var responseJson : String? = null

        runCatching {
            responseJson = httpClient
                .getGame(gameId)
        }.onFailure { return }

        val gameInfo = getGameInfoFromJson(responseJson)

        writeGameInfo(gameInfo)
    }

    fun getStores() : List<Store>{
        val responseJson = httpClient.getStores()


    }

    private fun readGameId() : String {
        val scanner = Scanner(System.`in`)

        println("Please type the game id:")

        val gameId = scanner.nextLine()

        return gameId
    }

    private fun getGameInfoFromJson(json:String?) : GameInfo {
        val gameInfo = gson.fromJson(json, GameInfo::class.java)
        return gameInfo
    }

    private fun getStoresFromJson(json: String?) : List<Store> {
        val stores = gson.fromJson(json, Store::class.java)
    }

    private fun writeGameInfo(gameInfo: GameInfo){
        var game : Game? = null

        val result = runCatching {
            game = Game(gameInfo.info.title,
                gameInfo.info.steamAppID,
                gameInfo.info.thumb,
                gameInfo.cheapestPriceEver.price)
        }

        result.onFailure{
            println(""" 
            An error has occurred, try another game.
            """.trimIndent())

            println(result)
            return
        }

        println(game)
    }
}