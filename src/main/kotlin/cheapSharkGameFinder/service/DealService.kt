package cheapSharkGameFinder.service

import cheapSharkGameFinder.model.apiModel.Deal

class DealService {
    fun getBestDeal(dealList: List<Deal>) : Deal {
        val bestDeal = dealList.minBy { it.price }
        return bestDeal
    }
}