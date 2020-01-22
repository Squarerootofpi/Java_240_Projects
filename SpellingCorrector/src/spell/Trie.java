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
//        for (int i = 0; i < word.length(); i++)
//        {
//            word.charAt(i);
//        }
        int level = 0;
        if (level >= word.length())
        {
            //Word is the empty string here. Don't add!
            return;
        }
        addHelper(level,word,root);

        wordCount++;
    }

    /**
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
                //tN = new TrieNode();
            }
            addHelper(level,word,node.getChildAt(nodeIndex));
        }
    }


    @Override
    public INode find(String word) {
        return null;
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
        for (int i = 0; i < 26; i++)
        {
            if (rootNodes[i] != null)
            {
                nonNullNodes++;
            }
        }
        return nonNullNodes*wordCount*nodeCount;
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
                if (!equalsHelper(tNThis, tN)) //if the subnodes at this one are not equal, then return false!
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
}