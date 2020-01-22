package spell;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector {

    public static void main(String[] args) // Args is file name.
    {
        System.out.println("Image Editor Open.");
        try {
            System.out.println(args[0]);

            File inFile1 = new File(args[0]);
            Scanner scanner = new Scanner(inFile1);
            //Scanner sc = new Scanner(new File(file));
            //The file is delimited with newlines.
            scanner.useDelimiter("\n");
            Trie trie = new Trie();
            //Populate words!
            while (scanner.hasNext())
            {
                trie.add(scanner.next());
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString() + " was thrown, caught in SpellCorrector.java");
        }
    }
            @Override
    public void useDictionary(String dictionaryFileName) throws IOException {

    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        return null;
    }
}
