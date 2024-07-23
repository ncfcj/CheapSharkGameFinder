package cheapSharkGameFinder.services

import cheapSharkGameFinder.services.interfaces.IMenuService
import java.util.*

class MenuService {
    companion object : IMenuService {
        private val scanner = Scanner(System.`in`)

        override fun showMainMenu() {
            println("Welcome to CheapSharkAPI Game Discount Finder! \n")
            GameService.getGameDetailsByName()
        }

        override fun wantToSearchAnotherGame() : Boolean {
            var answer : String? = null

            while(invalidAnswer(answer)){
                println("Do another search? (Y/N)")
                answer = scanner.nextLine()

                if (invalidAnswer(answer))
                    println("Invalid answer, it must be Y or N")
            }

            return doAnotherSearch(answer)
        }


        private fun invalidAnswer(answer: String?) : Boolean {
            return !answer.equals("Y") && !answer.equals("y") && !answer.equals("N") && !answer.equals("n")
        }

        private fun doAnotherSearch(answer: String?) : Boolean {
            return answer.equals("Y") || answer.equals("y")
        }
    }
}