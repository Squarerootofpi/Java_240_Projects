package hangman;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.SortedSet;



public class EvilHangmanGame implements IEvilHangmanGame {
    private SortedSet<Character> guessedLetters;
    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
        //0 out the guessed letters and dictionary first. Then add those new words to the dictionary etc...


    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        return null;
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        return null;
    }

    //Drop dictionary
}
