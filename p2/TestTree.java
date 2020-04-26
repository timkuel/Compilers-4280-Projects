import java.util.ArrayList;


public class TestTree {

    public static void printTreePreorder(Node treeNode, int depth) {
        preorderPrint(treeNode, depth);
		
        for(Node node : treeNode.childNodes)
            if(node != null)
                printTreePreorder(node, depth + 2);
		
    }
	
	
    private static void preorderPrint(Node treeNode, int depth) {
        String output = "";
		
        if(depth > 0)
            output = String.format("%"+depth+"s", " ");	
		
        output += (treeNode.getNodeData() + " (");
		
        for(Token token : treeNode.tokenData)
            if(token != null)
                output += token.toString();
		
        output += ")";
		
        System.out.println(output);
    }
	
}