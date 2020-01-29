package hangman;

import java.io.File;
import java.io.IOException;

public class EvilHangman {

    public static void main(String[] args) {
        //Check for valid arguments
        /**
         * Command arguments look like:
         *  java [your main class name] dictionary wordLength guesses
        * @dictionary is the path to a text file with whitespace separated words (no numbers,
         * punctuation, etc.)
         * @wordLength is an integer ≥ 2.
        * @guesses is an integer ≥ 1
        */
        String mainClassName = args[1];
        String dictionary = args[2];
        File dictionaryFile = new File(dictionary);
        int wordLength = Integer.parseInt(args[3]);
        int guesses = Integer.parseInt(args[4]);
        //Make an evilhangmangame instance
        EvilHangmanGame game = new EvilHangmanGame();
        //Do the start game function (loop functionality will be inside that!
        try {
            game.startGame(dictionaryFile,wordLength);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EmptyDictionaryException e) {
            e.printStackTrace();
            System.out.println("The dictionary does not contain words of that length. My condolences.");
        }

    }

}
