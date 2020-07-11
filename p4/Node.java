import java.util.ArrayList;


public class Node {
    private String nodeData;
    public ArrayList<Token> tokenData = new ArrayList<>();
    public ArrayList<Node> childNodes = new ArrayList<>();
    
    
    public Node(String nodeData){ this.nodeData = nodeData; }
    
    public String getNodeData() { return this.nodeData; }
    
}