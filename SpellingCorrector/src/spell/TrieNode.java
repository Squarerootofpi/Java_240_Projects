package spell;
import java.util.Arrays;

public class TrieNode implements INode {
    private int words;
    private TrieNode[] nodes;

    @Override
    public int getValue() {
        return words;
    }

    public TrieNode[] getNodes() {
        return nodes;
    }

    TrieNode()
    {
        nodes = new TrieNode[26];
        Arrays.setAll(nodes, i -> null); //Setting all the indexes to null
    }
    public TrieNode getChildAt(int index)
    {
        return nodes[index];
    }
    public void newNodeAt(int index)
    {
        nodes[index] = new TrieNode();
    }

    public void addWord()
    {
        words++;
    }
//    TrieNode(String str)
//    {
//
//    }
}
