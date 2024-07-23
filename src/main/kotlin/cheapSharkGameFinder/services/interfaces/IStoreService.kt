package cheapSharkGameFinder.services.interfaces

import cheapSharkGameFinder.models.Deal
import cheapSharkGameFinder.models.Store

interface IStoreService {
    fun getStoreWithBestPrice(bestDeal: Deal, storeList : List<Store>) : Store
}