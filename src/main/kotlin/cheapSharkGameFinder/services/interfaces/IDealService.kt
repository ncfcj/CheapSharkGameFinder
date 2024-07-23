package cheapSharkGameFinder.services.interfaces

import cheapSharkGameFinder.models.Deal

interface IDealService {
    fun getBestDeal(dealList: List<Deal>) : Deal
}