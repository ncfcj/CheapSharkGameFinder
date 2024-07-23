package cheapSharkGameFinder

import cheapSharkGameFinder.services.MenuService

fun main() {
    var continueProgram = true

    while(continueProgram) {
        MenuService.showMainMenu()
        continueProgram = MenuService.wantToSearchAnotherGame()
    }
}

