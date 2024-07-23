package cheapSharkGameFinder.services

import cheapSharkGameFinder.httpClients.CheapSharkHttpClient
import cheapSharkGameFinder.models.*
import cheapSharkGameFinder.services.interfaces.IGameService
import java.util.*

class GameService {
    companion object : IGameService {
        private val scanner = Scanner(System.`in`)

        override fun getGameDetailsById(){
            val gameId = getGameId()
            val gameInfo = getGameById(gameId)
            val storeList = CheapSharkHttpClient.getStores()
            val bestDeal = DealService.getBestDeal(gameInfo!!.deals)
            val storeWithBestPrice = StoreService.getStoreWithBestPrice(bestDeal, storeList)

            writeGameInfo(gameInfo, storeWithBestPrice, bestDeal)
        }

        override fun getGameDetailsByName() {
            val gameName = getGameName()
            val gamesFound = getFoundGamesList(gameName)
            val chosenGame = chooseAGameFromFoundGamesList(gamesFound) ?: return

            val gameInfo = getGameById(chosenGame.gameID)
            val storeList = CheapSharkHttpClient.getStores()
            val bestDeal = DealService.getBestDeal(gameInfo!!.deals)
            val storeWithBestPrice = StoreService.getStoreWithBestPrice(bestDeal, storeList)

            writeGameInfo(gameInfo, storeWithBestPrice, bestDeal)
        }


        private fun getGameId() : String {
            println("Please type the game id:")

            val gameId = scanner.nextLine()

            return gameId
        }

        private fun getGameName() : String {
            println("Please type the game name:")

            val gameName = scanner.nextLine()

            return formatGameName(gameName)
        }

        private fun writeGameInfo(gameInfo: GameData, store: Store, bestDeal: Deal){
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

                println(result) //TODO: Create exception logging
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

        private fun getFoundGamesList(gameName: String) : List<GameInfoByName>{
            runCatching {
                return CheapSharkHttpClient.getGamesByName(gameName)
            }.onFailure {
                return emptyList()
            }

            return emptyList()
        }

        private fun chooseAGameFromFoundGamesList(gameList: List<GameInfoByName>) : GameInfoByName? {
            if (gameList.isEmpty()){
                println("No game with this name was found!")
                return null
            }

            println("Games found: \n")

            gameList.forEachIndexed { index, game ->
                println("${index + 1} - ${game.external}")
            }

            var chosenIndex = ""
            val totalGamesFound = gameList.size

            while (!chosenGameIsValid(chosenIndex, totalGamesFound)){
                chosenIndex = scanner.nextLine()

                if (!chosenGameIsValid(chosenIndex, totalGamesFound))
                    println("Please, choose an valid option from the list.")
            }

            return gameList[chosenIndex.toInt() - 1]
        }

        private fun chosenGameIsValid(chosenValue: String?, totalGamesFound: Int) : Boolean {
            if (chosenValue.isNullOrBlank())
                return false

            if (!chosenValue.matches(Regex("^\\d+$"))) // Checks if string has digits only
                return false

            val chosenValueAsInteger = chosenValue.toInt()

            return chosenValueAsInteger !in (totalGamesFound + 1)..0
        }

        private fun getGameById(gameId: String) : GameData? {
            var gameInfo : GameData? = null

            runCatching {
                gameInfo = CheapSharkHttpClient.getGameById(gameId)
            }.onFailure {
                return gameInfo //TODO: Create exception logging
            }

            return gameInfo
        }

        private fun formatGameName(gameName: String) : String {

            if (gameName.startsWith(' ')){
                gameName.drop(1)
            }

            val formattedGameName : String = gameName.replace(" ", "%20")

            return formattedGameName
        }

    }
}