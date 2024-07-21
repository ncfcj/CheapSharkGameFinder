package cheapSharkGameFinder.model

import cheapSharkGameFinder.model.apiModel.CheapestPriceEver
import cheapSharkGameFinder.model.apiModel.Deal
import cheapSharkGameFinder.model.apiModel.InfoApiShark

data class GameInfo(val info : InfoApiShark, val cheapestPriceEver: CheapestPriceEver, val deals : List<Deal>)