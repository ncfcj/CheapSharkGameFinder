package cheapSharkGameFinder.models

class Game(
        val title: String,
        steamAppId : String?,
        val thumb: String,
        val price: String)
{
    private var _steamAppId: String = steamAppId ?: ""

    var steamAppId: String
        get() = _steamAppId
        set(value) {
            _steamAppId = value
        }

    val steamGamePage: String = if (_steamAppId.isNotBlank()) "https://store.steampowered.com/app/$_steamAppId" else ""
}