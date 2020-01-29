package hangman;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

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
        //String mainClassName = args[1];
        String dictionary = args[0];
        File dictionaryFile = new File(dictionary);
        int wordLength = Integer.parseInt(args[1]);
        int guesses = Integer.parseInt(args[2]);
        String currentWord;
        //Make an evilhangman game instance
        EvilHangmanGame game = new EvilHangmanGame();
        StringBuilder letterSB = new StringBuilder();
        //Do the start game function (loop functionality will be inside that!
        try {
            game.startGame(dictionaryFile,wordLength);
            game.setUnderscoredWord(wordLength);
            Scanner io = new Scanner(System.in);
            //SortedSet<Character> guessedLetters;
            for (int i = guesses; i > 0; i--) {
                /**
                 * You have 1 guess left
                 * Used letters: a e f i l m o s t u
                 * Word: -u---
                 * Enter guess: b
                 * You lose!
                 * The word was: vuggy
                 */
                game.getGuessedLetters();
                letterSB.delete(0, letterSB.length());
                System.out.println("You have " + String.valueOf(i) + " guess left");
                for (Character c: game.getGuessedLetters()
                     ) {
                    if (letterSB.length() == 0)
                    {
                        letterSB.append(c);
                    }
                    else {
                        letterSB.append(" " + String.valueOf(c));
                    }
                }
                currentWord = game.getShownWord();
                System.out.println("Used letters: " + letterSB.toString());
                System.out.println("Word: " + currentWord);
                System.out.println("Enter guess: ");
                String read = io.next();

                /**
                 * Prompt the user for his/her next letter guess. If the guess is not an upperor lower-case letter then print “Invalid input” on the next line and reprompt.
                 * If the user has already guessed the letter print “You already used that
                 * letter” on the next line and reprompt.
                 */
                try {

                    if (read.length() > 1 || read.length() == 0)
                    {
                        System.out.println("Invalid input");
                        ++i; //Do this so we get the right amount of guesses still
                        continue;
                    }
                    Character input = read.charAt(0);
                    if (!Character.isAlphabetic(input))
                    {
                        System.out.println("Invalid input");
                        ++i; //Do this so we get the right amount of guesses still
                        continue;
                    }
                    //finally....
                    game.makeGuess(input);
                    //If we reach this point, we know that it was a valid input.
                    /**
                     * Report the number of position(s) in which the letter appears. If the current
                     * word list doesn't contain any word with that letter, decrement the number
                     * of remaining guesses and print “Sorry, there are no <inputLetter>’s” on a
                     * new line (replace inputLetter with the actual letter input). If all words in the
                     * list contain at least one word with that letter, print “Yes, there is <number>
                     * <letter>” where letter is the letter guessed and numbers is the number of
                     * times letter appears in the word with the fewest number of those letters.
                     */
                    if (game.getLettersAdded()>0)
                    {
                        System.out.println("Yes, there is " + game.getLettersAdded() + " " + input);
                    }
                    else {
                        System.out.println("Sorry, there are no " + input + "’s");
                    }
                    //check if they won:
                    if (game.didWin())
                    {
                        System.out.println("You Win!");
                    }
                    else {
                        System.out.println("");
                    }
                    /**
                     * if they win: “You Win!” and display the correct word.
                     *
                     * You have 7 guesses left
                     * Used letters: a e i
                     * Word: -----
                     * Enter guess: o
                     * Sorry, there are no o's
                     *
                     * You have 6 guesses left
                     * Used letters: a e i o
                     * Word: -----
                     * Enter guess: u
                     * Yes, there is 1 u
                     */
                } catch (GuessAlreadyMadeException ex)
                {
                    System.out.println("You already used that letter");
                    ++i; //Do this so we get the right amount of guesses still
                    continue;
                }

            }
            if (!game.didWin()) {
                System.out.println("You lose!");
            }
            System.out.println("The word was: " + game.getAWord());
        } catch (IOException e) {
            e.printStackTrace();

        } catch (EmptyDictionaryException e) {
            e.printStackTrace();
            System.out.println("The dictionary does not contain words of that length. My condolences, please run program with a different file.");
        }

    }

}
