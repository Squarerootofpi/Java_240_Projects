package spell;

public class Trie implements ITrie {
    private Integer wordCount;
    private Integer nodeCount;
    private TrieNode root;

    public Trie()
    {
        root = new TrieNode();
        nodeCount = 1;
        wordCount = 0;
    }

    @Override
    public void add(String word) {

        int level = 0;
        if (level >= word.length())
        {
            //Word is the empty string here. Don't add!
            return;
        }
        addHelper(level,word.toLowerCase(),root);

        //wordCount++;
    }

    /**
     * Need to add the case in in which the last one is not null etc....
     *
     * @param level the index of the word we are on in the add
     * @param word the word we are adding
     * @param node The current node we are adding into.
     */
    private void addHelper(int level, String word, TrieNode node)
    {
        level++;
        if (word.length() == level) //This means that we add one more no
        {
            //Get the character index from the word/level
            char c = word.charAt(level - 1);
            int nodeIndex = indexOf(c);
            //TrieNode tN = node.getChildAt(nodeIndex); //Grab the node at that index
            //Make sure trieNode is not null
            if (node.getChildAt(nodeIndex) == null)
            {
                node.newNodeAt(nodeIndex);
                nodeCount++;

            }
            Integer numValue = node.getChildAt(nodeIndex).getValue();
            if (numValue.equals(0))
            {
                wordCount++;
            }
            node.getChildAt(nodeIndex).addWord();

        }
        else //if (word.length() > level)
        { //We have more traversing to do
            char c = word.charAt(level - 1);
            int nodeIndex = indexOf(c);
            //Grab the node at that index
            //TrieNode tN = node.getChildAt(nodeIndex);
            //Make sure trieNode is not null
            if (node.getChildAt(nodeIndex) == null)
            {
                node.newNodeAt(nodeIndex);
                nodeCount++;
                //tN = new TrieNode();
            }
            addHelper(level,word,node.getChildAt(nodeIndex));
        }
    }


    @Override
    public INode find(String word) {
        if (word.length() == 0)
        {
            return null;
        }
        return findHelper(root,word.toLowerCase(),0);
    }
    private TrieNode findHelper(TrieNode tN, String word, Integer index)
    {
        if (index == word.length()) //We are at the right index then. this should be the word.
        {
            if (tN.getValue() == 0)
            {
                return null;
            }
            return tN;
        }
        int nodeIndex = indexOf(word.charAt(index));
        if (tN.getChildAt(nodeIndex) == null)
        {
            return null;
        }
        //Continue to the next childnode...
        return findHelper(tN.getChildAt(nodeIndex), word, index + 1);
    }


    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    @Override
    public int hashCode() {
        //Get the nonnullrootnodes
        TrieNode[] rootNodes = root.getNodes();
        int nonNullNodes = 0;
        Integer firstIndexFound = -1;
        boolean foundFirst = false;
        for (int i = 0; i < 26; i++)
        {
            if (rootNodes[i] != null)
            {
                nonNullNodes++;
                if (!foundFirst)
                {
                    firstIndexFound = i;
                    foundFirst = true;
                }
            }
        }
        if (firstIndexFound.equals(-1))
        {
            firstIndexFound = 26; //We just choose index 26, since 0-25 will be used...
        }
        return nonNullNodes*wordCount*nodeCount*firstIndexFound;
    }

    public TrieNode getRoot() {
        return root;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) //if it points to nothing....
        {
            return false;
        }
        if (obj == this) //if we are comparing to ourselves
        {
            return true;
        }
        if (obj.getClass() != this.getClass()) //If it's not the same class
        {
            return false;
        }

        //Now we know it's not a different class, ourselves, or null.
        Trie t = ((Trie) obj);
        if ( !this.wordCount.equals(t.getWordCount()) || !this.nodeCount.equals(t.getNodeCount()) )
            //if the wordcounts don't match up or the nodecounts don't match up, exit!
        {
            return false;
        }
        //Let's traverse the whole tree now!!!!
        return equalsHelper(this.root, t.getRoot());
    }

    private boolean equalsHelper(TrieNode tNThis, TrieNode tN)
    {
        //if the nodes are equal, try it on each of the other nonnull nodes.
        //if wordcount is not equal on this word
        if (tNThis.getValue() != tN.getValue())
        {
            return false;
        }
        //if any element is not both null or other, and the recursive thing brings false
        for (int i = 0; i < 26; i++) {
            //if both elements are null
            if (tNThis.getChildAt(i) == null && tN.getChildAt(i) == null)
            {
                continue;
            }
            //if both are not null, then go inside them to check if they are not null
            else if (tNThis.getChildAt(i) != null && tN.getChildAt(i) != null)
            {
                if (!equalsHelper(tNThis.getChildAt(i), tN.getChildAt(i))) //if the subnodes at this one are not equal, then return false!
                {
                    return false;
                }
            }
            else //One of them are null, and the other is not, so return false!!!
            {
                return false;
            }
        }
        //If it makes it through all 26 nodes, it's equal on this node at least.
        return true;
    }
    //Returns the character of a certain node index.
    public static char charOf(int index)
    {
//        char c = (char) (index - 'a');
//        return c;
        return (char) ('a' + index);
    }
    public static int indexOf(char c)
    {
        return (c - 'a');
    }

    @Override
    public String toString() {
        StringBuilder thisWord = new StringBuilder();
        StringBuilder output = new StringBuilder();
        toStringHelper(root,thisWord,output);
        return output.toString();
    }
    private void toStringHelper(TrieNode n, StringBuilder thisWord, StringBuilder output)
    {
        if (n == null) { return;}
        if (n.getValue() > 0) {
            output.append(thisWord.toString() + "\n");
        }
        TrieNode[] children = n.getNodes();
        for (int i = 0; i < 26; i++) {
            //For each non null child:
            if (children[i] != null)
            {
                //Add the current character
                thisWord.append(charOf(i));
                //Do the toString recursive helper on this word
                toStringHelper(children[i],thisWord,output);
                //Delete the last character as we leave the function.
                if (thisWord.length() > 0) {
                    thisWord.setLength(thisWord.length() - 1);
                }
            }
        }

    }
}
