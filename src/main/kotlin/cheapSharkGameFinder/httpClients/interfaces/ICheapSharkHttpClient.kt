package cheapSharkGameFinder.httpClients.interfaces

import cheapSharkGameFinder.models.GameData
import cheapSharkGameFinder.models.GameInfoByName
import cheapSharkGameFinder.models.Store

interface ICheapSharkHttpClient {
    fun getGameById(gameId: String) : GameData?
    fun getGamesByName(gameName: String) : List<GameInfoByName>
    fun getStores() : List<Store>
}