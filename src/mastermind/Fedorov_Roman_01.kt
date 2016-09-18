package mastermind

fun playMastermind(
        secret: String = generateSecret(),
        player: Player = RealPlayer()
) {
    var complete = false
    do {
        val guess = player.guess()
        // TODO
        player.receiveEvaluation(complete, 0, 0)
    } while (!complete)
}

fun generateSecret(
        differentLetters: Boolean = true
): String = "ABCD"