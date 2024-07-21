package br.com.nilton.CheapSharkGameFinder

class Game(
    private val title: String,
    private val steamAppId : String,
    private val thumb: String,
    private val price: String) {

    private val steamGamePage = "https://store.steampowered.com/app/$steamAppId"

    override fun toString(): String {
        return """
                Game
                
                Title: $title
                SteamAppId: $steamAppId
                Thumb: $thumb
                Steam Game Page: $steamGamePage
                Cheapest Price Ever: $$price
                
                """.trimIndent()
    }
}