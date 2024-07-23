package cheapSharkGameFinder.services

import cheapSharkGameFinder.models.Deal
import cheapSharkGameFinder.services.interfaces.IDealService

class DealService {
    companion object : IDealService {
        override fun getBestDeal(dealList: List<Deal>) : Deal {
            val bestDeal = dealList.minBy { it.price }
            return bestDeal
        }
    }

}