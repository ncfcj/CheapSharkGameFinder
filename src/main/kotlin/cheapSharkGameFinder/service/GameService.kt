package cheapSharkGameFinder.service

import cheapSharkGameFinder.httpClient.CheapSharkHttpClient
import cheapSharkGameFinder.model.Game
import cheapSharkGameFinder.model.GameInfo
import cheapSharkGameFinder.model.apiModel.Deal
import cheapSharkGameFinder.model.apiModel.Store
import com.google.gson.Gson
import java.util.*

class GameService {
    private val httpClient = CheapSharkHttpClient()
    private val gson = Gson()
    private val scanner = Scanner(System.`in`)
    private val storeService = StoreService()
    private val dealService = DealService()

    fun getGameDetailsById(){
        val gameId = readGameId()

        var responseJson : String? = null

        runCatching {
            responseJson = httpClient.getGameById(gameId)
        }.onFailure { return }

        val gameInfo = getGameInfoFromJson(responseJson)
        val storeList = storeService.getStores()
        val bestDeal = dealService.getBestDeal(gameInfo.deals)
        val storeWithBestPrice = storeService.getStoreWithBestPrice(bestDeal, storeList)

        writeGameInfo(gameInfo, storeWithBestPrice, bestDeal)
    }

    fun wantToSearchAnotherGame() : Boolean {
        var answer : String? = null

        while(invalidAnswer(answer)){
            println("Do another search? (Y/N)")
            answer = scanner.nextLine()

            if (invalidAnswer(answer))
                println("Invalid answer, it must be Y or N")
        }

        return doAnotherSearch(answer)
    }

    private fun readGameId() : String {
        println("Please type the game id:")

        val gameId = scanner.nextLine()

        return gameId
    }

    private fun getGameInfoFromJson(json:String?) : GameInfo {
        val gameInfo = gson.fromJson(json, GameInfo::class.java)
        return gameInfo
    }

    private fun writeGameInfo(gameInfo: GameInfo, store: Store, bestDeal: Deal){
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

        println("""
                Game
                
                Title: ${game?.title}
                SteamAppId: ${game?.steamAppId}
                Thumb: ${game?.thumb}
                Steam Game Page: ${game?.steamGamePage}
                Retail Price: $${bestDeal.retailPrice}
                
                Store With Best Price Now: ${store.storeName} at $${bestDeal.price} with ${bestDeal.savings.toInt()}% discount
                Deal Page: ${bestDeal.getDealPage()}
                Cheapest Price Ever: $${game?.price}
               
                """.trimIndent())
    }

    private fun invalidAnswer(answer: String?) : Boolean {
        return !answer.equals("Y") && !answer.equals("y") && !answer.equals("N") && !answer.equals("n")
    }

    private fun doAnotherSearch(answer: String?) : Boolean {
        return answer.equals("Y") || answer.equals("y")
    }
}