package mastermind

import java.util.concurrent.ThreadLocalRandom
import kotlin.system.exitProcess

val LETTERS = ('A'..'F').toList();
val LEN = 4

fun playMastermind(
        secret: String = generateSecret(),
        player: Player = RealPlayer()
) {
    do {
        val guess = player.guess().toUpperCase()
        checkInput(guess, player)
        val (positions, letters) = processGuess(guess, secret)
        val complete = positions == LEN
        player.receiveEvaluation(complete, positions, letters)
    } while (!complete)
}

fun processGuess(guess: String, secret: String): Pair<Int, Int> {
    val positionIntersection = guess.withIndex().intersect(secret.withIndex())
    val guessF = filterEqualPositions(guess, positionIntersection)
    val secretF = filterEqualPositions(secret, positionIntersection)
    val matchedLettersCount = LETTERS
            .map { l -> Math.min(guessF.count { c -> c == l }, secretF.count { c -> c == l }) }
            .sum()
    return Pair(positionIntersection.count(), matchedLettersCount)
}

fun filterEqualPositions(s: String, intersection: Set<IndexedValue<Char>>): String {
    return s.withIndex()
            .filter { c -> !intersection.contains(c) }
            .map { c -> c.value }
            .joinToString("")
}

fun checkInput(input: String, player: Player) {
    if (!(input.length == LEN && input.all { c -> LETTERS.contains(c) })) {
        player.incorrectInput(input);
        exitProcess(1);
    }
}

fun generateSecret(differentLetters: Boolean = false): String {
    return buildString {
        while (length != LEN) {
            val letterToAppend = LETTERS[ThreadLocalRandom.current().nextInt(LETTERS.size)]
            if (!(differentLetters && contains(letterToAppend))) {
                append(letterToAppend)
            }
        }
    }
}
