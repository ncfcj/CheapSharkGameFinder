package cheapSharkGameFinder.model

class Game(
        val title: String,
        val steamAppId : String,
        val thumb: String,
        val price: String) {

    var steamGamePage = "https://store.steampowered.com/app/$steamAppId"
        private set
}