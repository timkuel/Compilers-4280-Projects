public class SemanticsCheck {
    
    private Node rootNode = null;
    private VariableList variableList = null;
    

    public SemanticsCheck (Node rootNode) {
        this.rootNode = rootNode;
        this.variableList = new VariableList();
    }
    
    
    private void printErrorMessage(Token variable, String message) {
        System.out.println("Static Semantics Error: Line " + variable.getLineNumber() + 
                ": " + variable.getTokenInstance() + " " + message);
        
        System.exit(-1);
    }
     
    
    public void checkStaticSemantics() {
        treeTraversalPreorder(rootNode);
    }
    
    
    public void treeTraversalPreorder(Node treeNode) {
        checkNode(treeNode);
        
        for(Node node : treeNode.childNodes)
            if(node != null)
                treeTraversalPreorder(node);
        
    }
    
    
    private void checkNode(Node treeNode) {
        for(Token token : treeNode.tokenData) {
            if(treeNode.getNodeData().equals("<vars>")) { /* Checking if variable can be defined */
                if(token.getTokenID().equals("idTK")) {
                   variableList.insert(token); 
                }
            }
            else {
               if(token.getTokenID().equals("idTK")) { /* Checking if variable can be used */
                   if(!variableList.verify(token)) /* If the variable is not contained in the list, error */
                       printErrorMessage(token, "has not been defined");
                }
            }
        }            
    }    

}
