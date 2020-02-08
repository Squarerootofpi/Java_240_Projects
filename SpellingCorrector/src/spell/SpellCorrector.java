package spell;

//import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class SpellCorrector implements ISpellCorrector {
    private Trie dictionary;
    public String main(String[] args) // Args is file name.
    {
        System.out.println("Image Editor Open.");
        try {
            System.out.println(args[0]);
            useDictionary(args[0]);
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString() + " was thrown, caught in SpellCorrector.java");
        }
        return null;
    }
            @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
                File inFile1 = new File(dictionaryFileName);
                Scanner scanner = new Scanner(inFile1);
                //Scanner sc = new Scanner(new File(file));
                //The file is delimited with newlines.
                //scanner.useDelimiter("\n");
                dictionary = new Trie();
                //Populate words!
                while (scanner.hasNext())
                {
                    dictionary.add(scanner.next());
                }
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        //make sure dictionary is populated....
        if (dictionary == null)
        {
            return null;
        }
        else if (dictionary.find(inputWord) != null) //check to see if word is in trie dictionary
        {
            return inputWord.toLowerCase();
        }
        else //see if we can suggest a similar one. If we can't, just return null.
        {
            //Get a list of all the 1steps!!!
            Set<String> _1StepWords = generate1Step(inputWord);
            //for each word
            HashMap<String, Integer> hash_map = mapGuessSetToDictionary(_1StepWords);//new HashMap<String, Integer>();
            if (!hash_map.isEmpty()) //if it is not empty
            {
                return closestWord(hash_map);
            }
            else { //if it is empty
                //Do the 2stepwords instead
                Set<String> _2StepWords = generate1Step(_1StepWords);
                //populate the map
                hash_map = mapGuessSetToDictionary(_2StepWords); //This should delete the last map.
                if (!hash_map.isEmpty()) //if the hashmap is not empty
                {
                    return closestWord(hash_map);
                }

            }

            return null;
        }
    }

    private HashMap<String, Integer> mapGuessSetToDictionary(Set<String> guessSet)
    {
        HashMap<String, Integer> hash_map = new HashMap<String, Integer>();
        INode tempNode = null;
        for (String guessWord : guessSet
        ) {
            tempNode = dictionary.find(guessWord);
            if (!(tempNode == null)) //if it finds the word in the dictionary,
            {
                hash_map.put(guessWord,tempNode.getValue());
            }
        }
        return hash_map;
    }

    private String closestWord(HashMap<String, Integer> hash_map)
    {
        Integer max = 0;
        Integer oldMax = 0;
        //This gets the largest value
        for (Integer i: hash_map.values()
        ) {
            max = Math.max(max, i);
        }
        //Get the strings with the largest index
        TreeSet<String> ts = new TreeSet<String>();
        for (Map.Entry<String,Integer> i: hash_map.entrySet())
        {
            if (i.getValue().equals(max))
            {
                ts.add(i.getKey());
            }
        }
        //Return the first element in the treeset, since it's already sorted ascending.
        return ts.first();
    }

    /**
     * Generate a hashset of strings that are one step from a given word, and returns the pointer to that.
     * @param word
     * @return
     */
    private Set<String> generate1Step(String word)
    {
        Set<String> hash_Set = new HashSet<String>();

        hash_Set.addAll(genAlterationSet(word));
        hash_Set.addAll(genDeletionSet(word));
        hash_Set.addAll(genTranspositionSet(word));
        hash_Set.addAll(genInsertionSet(word));

        return hash_Set;
    }
    private Set<String> generate1Step(Set<String> words)
    {
        Set<String> finalSet = new HashSet<String>();
        for (String word: words
             ) {
            finalSet.addAll(generate1Step(word));
        }
        return finalSet;
    }
    private Set<String> genDeletionSet(String word)
    {
        Set<String> hash_set = new HashSet<String>();
        for (int i = 0; i < word.length(); i++) {
            StringBuilder sb = new StringBuilder(word);
            sb.deleteCharAt(i);
            hash_set.add(sb.toString());
        }
        return hash_set;
    }
    /*Deletion Distance: A string s has a deletion distance 1 from another string t if and only if t
is equal to s with one character removed. The only strings that are a deletion distance of
1 from “bird” are “ird”, “brd”, “bid”, and “bir”. Note that if a string s has a deletion distance
of 1 from another string t then |s| = |t| -1. Also, there are exactly |t| strings that are a
deletion distance of 1 from t. The dictionary may contain 0 to n of the strings one deletion
distance from t.
*/
    public static String swap(String str, int i, int j)
    {
        if (j == str.length() - 1)
            return str.substring(0, i) + str.charAt(j)
                    + str.substring(i + 1, j) + str.charAt(i);

        return str.substring(0, i) + str.charAt(j)
                + str.substring(i + 1, j) + str.charAt(i)
                + str.substring(j + 1, str.length());
    }

    private Set<String> genTranspositionSet(String word)
    {

        Set<String> hash_set = new HashSet<String>();
        if (word.length() == 1)
        {
            return hash_set;
        }
        for (int i = 0; i < word.length() - 1; i++) {
            hash_set.add(swap(word,i, i+1));
        }
        return hash_set;
    }
    /*
● Transposition Distance: A string s has a transposition distance 1 from another string t if
and only if t is equal to s with two adjacent characters transposed. The only strings that
are a transposition Distance of 1 from “house” are “ohuse”, “huose”, “hosue” and
“houes”. Note that if a string s has a transposition distance of 1 from another string t then
|s| = |t|. Also, there are exactly |t| - 1 strings that are a transposition distance of 1 from t.
The dictionary may contain 0 to n of the strings one transposition distance from t.
*/
    private Set<String> genAlterationSet(String word)
    {
        Character c;
        Set<String> hash_set = new HashSet<String>();
        //StringBuilder sb = new StringBuilder(word);

        for (int i = 0; i < word.length(); i++) {
            StringBuilder sb = new StringBuilder(word);

            for (int z = 0; z < 26; z++) {
                c = Trie.charOf(z);
                sb.replace(i, i + 1, c.toString());
                hash_set.add(sb.toString());
            }
        }
        return hash_set;
    }
    /*
● Alteration Distance: A string s has an alteration distance 1 from another string t if and
only if t is equal to s with exactly one character in s replaced by a lowercase letter that is
not equal to the original letter. The only strings that are an alternation distance of 1 from
“top” are “aop”, “bop”, …, “zop”, “tap”, “tbp”, …, “tzp”, “toa”, “tob”, …, and “toz”. Note that
if a string s has an alteration distance of 1 from another string t then |s| = |t|. Also, there
are exactly 25* |t| strings that are an alteration distance of 1 from t. The dictionary may
contain 0 to n of the strings one alteration distance from t.
*/
    private Set<String> genInsertionSet(String word)
    {
        StringBuilder sb = new StringBuilder(word);
        Set<String> hash_set = new HashSet<String>();
        for (int i = 0; i < word.length() + 1; i++) {
            for (int z = 0; z < 26; z++) {
                sb.insert(i,Trie.charOf(z));
                hash_set.add(sb.toString());
                sb.replace(i,i+1,"");
            }
        }
            return hash_set;
    }
    /*
● Insertion Distance: A string s has an insertion distance 1 from another string t if and only
if t has a deletion distance of 1 from s. The only strings that are an insertion distance of 1
from “ask” are “aask”, “bask”, “cask”, … “zask”, “aask”, “absk”, “acsk”, … “azsk”, “asak”,
“asbk”, “asck”, … “aszk”, “aska”, “askb”, “askc”, … “askz”. Note that if a string s has an
insertion distance of 1 from another string t then |s| = |t|+1. Also, there are exactly 26* (|t|
+ 1) strings that are an insertion distance of
     */
}
