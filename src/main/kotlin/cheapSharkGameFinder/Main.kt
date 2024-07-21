package br.com.nilton.CheapSharkGameFinder

import CheapSharkGameFinder.model.Game
import CheapSharkGameFinder.model.GameInfo
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers
import com.google.gson.Gson
import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)

    println("Please type the game id:")

    val gameId = scanner.nextLine()
    val requestAddress = "https://www.cheapshark.com/api/1.0/games?id=$gameId"

    val client : HttpClient = HttpClient.newHttpClient()
    val request : HttpRequest = HttpRequest.newBuilder()
        .uri(URI.create(requestAddress))
        .build()

    val response = client.send(request, BodyHandlers.ofString())
    val responseJson = response.body()

    val gson = Gson()

    if (response.statusCode() == 404){
        println("The game Id $gameId does not exist, please try another Id.")
        return
    }

    val gameInfo = gson.fromJson(responseJson, GameInfo::class.java)

    var game : Game? = null;

    val result = runCatching {
        game = Game(gameInfo.info.title,
            gameInfo.info.steamAppID,
            gameInfo.info.thumb,
            gameInfo.price.price)
    }

    result.onFailure{
        println(""" 
            An error has occurred, try another game.
            """.trimIndent())

        println(result)
    }

    result.onSuccess {
        println(game)
        println("Thank you for using our service, see you next time!")
    }

}