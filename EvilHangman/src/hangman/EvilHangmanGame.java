package hangman;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class EvilHangmanGame implements IEvilHangmanGame {
    private SortedSet<Character> guessedLetters;
    private TreeSet<String> dictionary;
    private StringBuilder shownWord;

    public EvilHangmanGame()
    {
        guessedLetters = new TreeSet<Character>();
        dictionary = new TreeSet<String>();
    }


    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
        //0 out the guessed letters and dictionary first. Then add those new words to the dictionary etc...
        this.dictionary.clear();
        this.guessedLetters.clear();
        //make scanner
        Scanner scanner = new Scanner(dictionary);
        //Add everything to dictionary
        StringBuilder tempSB = new StringBuilder();
        while (scanner.hasNext())
        {
            tempSB.delete(0,tempSB.length());
            tempSB.append(scanner.next());
            if (tempSB.length() == wordLength)
            {
                this.dictionary.add(tempSB.toString());
            }
        }
        tempSB = null; //delete
        //Catch if dictionary empty
        if (this.dictionary.isEmpty())
        {
            EmptyDictionaryException ex = new EmptyDictionaryException();
            throw ex;
        }
        //if there are no strings of that size, throw it!!!
        Boolean containsOne = false;
        for (String s: this.dictionary
             ) {
            if (s.length() == wordLength)
            {
                containsOne = true;
                break;
            }
        }
        if (!containsOne) {
            EmptyDictionaryException ex = new EmptyDictionaryException();
            throw ex;
        }
        containsOne = null;

        playGame();


    }
    private void playGame()
    {
        /**
         * You have 1 guess left
         * Used letters: a e f i l m o s t u
         * Word: -u---
         * Enter guess: b
         * You lose!
         * The word was: vuggy
         */
        System.out.println();

    }
    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        char finGuess = Character.toLowerCase(guess);
        if (!guessedLetters.add(finGuess))
        {
            GuessAlreadyMadeException ex = new GuessAlreadyMadeException();
            throw ex;
        }
        //Find all the words that have that letter in them
        HashMap<String, TreeSet<String>> partitions = new HashMap<String, TreeSet<String>>();
        for (String word: dictionary
             ) {
            //if word has letter,

            //Convert to a partition string.
            StringBuilder sb = new StringBuilder(word);
            for (int i = 0; i < sb.length(); i++) {
                if (finGuess != sb.charAt(i)) {
                    sb.replace(i, i + 1, "-");
                }
            } //If it's already a partition in the set: add the word
            if (partitions.keySet().contains(sb.toString()))
            {
                partitions.get(sb.toString()).add(word);
            }
            else
            // No partition made. If not, make it!
            {
                TreeSet<String> newPartition = new TreeSet<String>();
                newPartition.add(word);
                partitions.put(sb.toString(),newPartition);
            }
        }

        //Get greatest partition:
        //ArrayList<Integer> pvals = new ArrayList<Integer>();
        TreeSet<String> returnSet = new TreeSet<String>();
        Integer max = -1;
        for (Map.Entry<String, TreeSet<String>> partition: partitions.entrySet()
             ) {
            if (max < partition.getValue().size())
            {
                max = partition.getValue().size();
                returnSet = partition.getValue();
            }
        }
        dictionary = returnSet; //Get rid of old dictionary.
        partitions = null; //Don't need them anymore.
        return returnSet;
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        return guessedLetters;
    }

    //Drop dictionary
}
