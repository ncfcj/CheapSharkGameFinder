package cheapSharkGameFinder

import cheapSharkGameFinder.service.GameService

fun main() {
    val gameService = GameService()

    var continueProgram = true

    while(continueProgram == true) {
        gameService.getGameDetails()
        continueProgram = gameService.wantToSearchAnotherGame()
    }
}