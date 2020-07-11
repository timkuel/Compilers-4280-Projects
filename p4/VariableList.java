import java.util.ArrayList;

public class VariableList {
    
    private ArrayList<String> variableList = null;
    
    
    public VariableList() {
        this.variableList = new ArrayList<>();
    }
    
    
    public ArrayList<String> getVariableList() { return this.variableList; }
    
    
    private void printErrorMessage(Token variable) {
        System.out.println("Static Semantics Error: Line " + variable.getLineNumber() + 
                ": " + variable.getTokenInstance() + " has already been defined");
        
        System.exit(-1);
    }
    
    
    /* If the token has not already been assigned, add it to the list */
    public void insert(Token idTK) {
        String tokenInstance = idTK.getTokenInstance();
         
        if(!variableList.contains(tokenInstance))
            variableList.add(tokenInstance);
        else
            printErrorMessage(idTK);
        
    }
    
    
    /* Function returns true if the identifier token has already been defined,
     * so it can be used. */
    public Boolean verify(Token idTK) {
        String tokenInstance = idTK.getTokenInstance();
            
        for(String variable : variableList) 
            if(variable.equals(tokenInstance)) 
                return true;
            
        return false;
    }
    
}
