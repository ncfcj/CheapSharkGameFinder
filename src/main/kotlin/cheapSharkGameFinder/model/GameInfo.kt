package br.com.nilton.CheapSharkGameFinder

data class GameInfo(val info : InfoApiShark, val price: CheapestPriceEverApiShark){
    override fun toString(): String {
        return info.toString()
    }
}