package cheapSharkGameFinder.models

data class GameData(val info : GameInfoById, val cheapestPriceEver: CheapestPriceEver, val deals : List<Deal>)