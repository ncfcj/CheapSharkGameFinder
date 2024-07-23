package cheapSharkGameFinder.httpClients

import cheapSharkGameFinder.httpClients.interfaces.ICheapSharkHttpClient
import cheapSharkGameFinder.models.GameData
import cheapSharkGameFinder.models.GameInfoByName
import cheapSharkGameFinder.models.Store
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class CheapSharkHttpClient {
    companion object : ICheapSharkHttpClient{
        private const val BASE_ADDRESS = "https://www.cheapshark.com/api/1.0"
        private val client : HttpClient = HttpClient.newHttpClient()
        private val gson = Gson()

        override fun getGameById(gameId: String) : GameData? {
            val endpointAddress = "$BASE_ADDRESS/games?id=$gameId"

            val response = makeRequest(endpointAddress)

            if (response.statusCode() == 404){
                println("The game Id $gameId does not exist, please try another Id.")
                throw Exception("Game was not found")
            }

            val gameInfo = gson.fromJson(response.body(), GameData::class.java)
            return gameInfo
        }

        override fun getGamesByName(gameName: String) : List<GameInfoByName> {
            val endpointAddress = "$BASE_ADDRESS/games?title=$gameName"

            val response = makeRequest(endpointAddress)

            if (response.statusCode() == 404){
                println("The game $gameName does not exist, please try another name.")
                throw Exception("Game was not found")
            }

            val gameListType = object : TypeToken<List<GameInfoByName>>() {}.type
            val gameList = gson.fromJson<List<GameInfoByName>>(response.body(), gameListType)
            return gameList
        }

        override fun getStores() : List<Store> {
            val endpointAddress = "$BASE_ADDRESS/stores"

            val response = makeRequest(endpointAddress)

            val storeListType = object : TypeToken<List<Store>>() {}.type
            val storeList = gson.fromJson<List<Store>>(response.body(), storeListType)

            return storeList
        }

        private fun makeRequest(endpointAddress: String) :  HttpResponse<String> {
            val request : HttpRequest = HttpRequest.newBuilder()
                .uri(URI.create(endpointAddress))
                .build()

            val response = client.send(request, HttpResponse.BodyHandlers.ofString())

            return response
        }

    }
}