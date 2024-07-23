package cheapSharkGameFinder.models

data class Deal(val storeID: String, val price: Double, val retailPrice : Double, val savings : Double, val dealID : String){
    fun getDealPage() : String {
        return "https://www.cheapshark.com/redirect?dealID=${dealID}&k=1"
    }
}