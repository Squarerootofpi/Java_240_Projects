package hangman;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class EvilHangmanGame implements IEvilHangmanGame {
    private SortedSet<Character> guessedLetters;
    private TreeSet<String> dictionary;
    private String shownWord;
    private Integer lettersAdded;

    public EvilHangmanGame()
    {
        guessedLetters = new TreeSet<Character>();
        dictionary = new TreeSet<String>();
        shownWord = "";
        lettersAdded = 0;
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
        String partitionKey = null;
        Integer max = -1;
        for (Map.Entry<String, TreeSet<String>> partition: partitions.entrySet()
             ) {
            if (max < partition.getValue().size())
            {
                max = partition.getValue().size();
                returnSet = partition.getValue();
                partitionKey = partition.getKey();
            }
            else if (max == partition.getValue().size())
            {
                partitionKey = tieBreaker(partitionKey, partition.getKey(), finGuess);
                returnSet = partitions.get(partitionKey);
            }
        }
        dictionary = returnSet; //Get rid of old dictionary.
        setShownWord(mergeUnderscoreStr(partitionKey));
        partitions = null; //Don't need them anymore.
        return returnSet;
    }

    public Boolean didWin()
    {
        if (shownWord.indexOf('-') == -1) { return true; }
        return false;
    }

    public String getAWord()
    {
        return dictionary.first();
    }

    /**\
     * @param s2
     * @return
     */
    private String mergeUnderscoreStr(String s2)
    {
        lettersAdded = 0;
        if (shownWord.length() != s2.length())
        {
            return "Error merging input strings";
        }
        StringBuilder sb = new StringBuilder(shownWord);
        for (int i = 0; i < s2.length(); i++) {
            Character c = s2.charAt(i);
            if (!c.equals('-')) //If it's not an underscore, merge that char with s2
            {
                sb.replace(i, i+1,String.valueOf(c));
                lettersAdded++;
            }
        }
        return sb.toString();
    }

    public Integer getLettersAdded() {
        return lettersAdded;
    }

    //This breaks ties according to specs.
    private String tieBreaker(String s1, String s2, char guess)
    {
        /**
         * Choose the group in which the letter does not appear at all.
         * 2. If each group has the guessed letter, choose the one with the
         * fewest letters.
         * 3. If this still has not resolved the issue, choose the one with the
         * rightmost guessed letter.
         * 4. If there is still more than one group, choose the one with the next
         * rightmost letter. Repeat this step (step 4) until a group is
         * chosen.
         */
        Boolean s1b = s1.contains(String.valueOf(guess));
        Boolean s2b = s2.contains(String.valueOf(guess));
        if (s1b && !s2b) { return s2; }
        else if (!s1b && s2b) {return s1;}
        else {
            //We know they both have guess's char in them.
            //Iterate backwards, with rightmost char!
            ArrayList<Integer> s1Set = new ArrayList<Integer>();
            ArrayList<Integer> s2Set = new ArrayList<Integer>();
            for (int i = s1.length() - 1; i > -1; i--) {
                if (s1.charAt(i) == guess) { s1Set.add(i); }
                if (s2.charAt(i) == guess) { s2Set.add(i); }
            }
            if (s1Set.size() == s2Set.size()) //Size is the same, Do the rightmost one then! :)
            {
                //Go through the backward list set
                for (int i = 0; i < s1Set.size(); i++) {
                    if (!s1Set.get(i).equals(s2Set.get(i))) //if they aren't the same index
                    {
                        if (s1Set.get(i) > s2Set.get(i)) //If the last index is bigger than this one.
                        {
                            return s1;
                        }
                        else {return s2; }
                    }
                }
            }
            //We know that the sets have different amounts: so,
            else if (s1Set.size() > s2Set.size()) { return s2; }
            else { return s1; }
        }
        return "";
    }
    @Override
    public SortedSet<Character> getGuessedLetters() {
        return guessedLetters;
    }

    public String getShownWord() {
        return shownWord;
    }
    public void setUnderscoredWord(int wordLength) {
        char[] charArray = new char[wordLength];
        Arrays.fill(charArray, '-');
        this.shownWord = new String(charArray);

        this.shownWord = shownWord;
    }
    public void setShownWord(String shownWord) {
        this.shownWord = shownWord;
    }
    //Drop dictionary
}
