package mastermind

import java.util.concurrent.ThreadLocalRandom

val LETTERS = ('A'..'F').toList();
val LEN = 4

fun playMastermind(
        secret: String = generateSecret(),
        player: Player = RealPlayer()
) {
    var complete = false
    do {
        val guess = player.guess().toUpperCase()
        var matchedPositionsCount = 0
        var matchedLettersCount = 0
        if (guess.length != LEN) {
            println("Input must have length $LEN")
        } else if (secret == guess) {          // :)
            complete = true
        } else {
            for ((i, c) in guess.withIndex()) { // string is iterable :), but my intuition says that index is 2nd param
                if (guess[i] == secret[i]) {
                    matchedPositionsCount += 1
                } else if (secret.contains(guess[i])) {
                    matchedLettersCount += 1
                }
            }
        }
        player.receiveEvaluation(complete, matchedPositionsCount, matchedLettersCount)
    } while (!complete)
}

fun generateSecret(differentLetters: Boolean = true): String {
    return buildString {
        while (length != LEN) {
            val letterToAppend = LETTERS[ThreadLocalRandom.current().nextInt(LETTERS.size)]
            if (!contains(letterToAppend)) {
                append(letterToAppend)
            }
        }
    }
}
