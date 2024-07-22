package cheapSharkGameFinder.httpClient

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class CheapSharkHttpClient {
    private val baseAddress = "https://www.cheapshark.com/api/1.0"
    private val client : HttpClient = HttpClient.newHttpClient()

    fun getGameById(gameId: String) : String {
        val endpointAddress = "$baseAddress/games?id=$gameId"

        val request : HttpRequest = HttpRequest.newBuilder()
            .uri(URI.create(endpointAddress))
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        val responseJson = response.body()

        if (response.statusCode() == 404){
            println("The game Id $gameId does not exist, please try another Id.")
            throw Exception("Game was not found")
        }

        return responseJson
    }

    fun getStores() : String {
        val endpointAddress = "$baseAddress/stores"

        val request : HttpRequest = HttpRequest.newBuilder()
            .uri(URI.create(endpointAddress))
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        val responseJson = response.body()

        return responseJson
    }

}