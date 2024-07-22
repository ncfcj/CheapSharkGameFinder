package cheapSharkGameFinder.service

import cheapSharkGameFinder.httpClient.CheapSharkHttpClient
import cheapSharkGameFinder.model.apiModel.Deal
import cheapSharkGameFinder.model.apiModel.Store
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StoreService {
    private val gson = Gson()
    private val httpClient = CheapSharkHttpClient()

    fun getStores() : List<Store>{
        val responseJson = httpClient.getStores()
        val storeList = getStoresFromJson(responseJson)
        return storeList
    }

    fun getStoreWithBestPrice(bestDeal: Deal, storeList : List<Store>) : Store{
        val dealStore = storeList.first { it.storeID == bestDeal.storeID }

        return dealStore
    }

    private fun getStoresFromJson(json: String?) : List<Store> {
        val storeListType = object : TypeToken<List<Store>>() {}.type
        val storeList = gson.fromJson<List<Store>>(json, storeListType)
        return storeList
    }
}