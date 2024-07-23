package cheapSharkGameFinder.services

import cheapSharkGameFinder.models.Deal
import cheapSharkGameFinder.models.Store
import cheapSharkGameFinder.services.interfaces.IStoreService

class StoreService {
    companion object : IStoreService {
        override fun getStoreWithBestPrice(bestDeal: Deal, storeList : List<Store>) : Store {
            val dealStore = storeList.first { it.storeID == bestDeal.storeID }

            return dealStore
        }
    }

}