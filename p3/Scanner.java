import java.util.ArrayList;


public class Scanner {
		
    /* Setting initial state of scanner location */
    private int index = 0;
    private int lineNumber = 1;
  

    /* Function that will build a token from incoming characters until the nextState
     * is final or an error state.  When final, the token will stop building and the token
     * information will be updated */
    public Token getToken(ArrayList<Character> tempList) {
        Tables tables = new Tables();
        String tokenBuilder = "";
        char charCheck = ' ';
        
        Boolean isFinalState = false;
        int currentState = 0;
        
        
        /* While the token is not in a final, keep building the token */
        while(!isFinalState) {
            charCheck = tempList.get(index);
        	
            currentState = tables.getFSATableState(currentState, charCheck);
        	
            /* States greater than 1000 are final */
            if(currentState > 1000)
                isFinalState = true;
        	
            /* Next state generated an error */
            else if(currentState == -2) {
                System.out.println("\nError: " + tables.getTokenHashTable().get(currentState) + " '" + charCheck + "' on line number " + lineNumber + "...Terminating Program");
                System.exit(-1);
            }
            
            /* A '@' was detected, assuming a comment was attempted */
            else if(currentState == -3) {
            	System.out.println("\nError: " + tables.getTokenHashTable().get(currentState) + " detected with the presence of the character '" + tokenBuilder + charCheck + "' on line number " + lineNumber);
            	System.out.println("Comments start with @ andEndWithWithoutSpaces@...Terminating Program");
                System.exit(-1);
            }
        	
            /* Building token */
            else {
                tokenBuilder += charCheck;
                index++;
            }
        	
        }
        
        
        /* Incrementing lineNumber if the tokenBuilder contains a newLine  */
        for(char test: tokenBuilder.toCharArray())
            if(test == '\n')
                lineNumber++;
        
        
        /* Creating a token */
        Token token = new Token(tables.getTokenHashTable().get(currentState), tokenBuilder.trim(), lineNumber);
        
        
        /* Incrementing lineNumber if the last item was a newLine */
        if(charCheck == '\n') {
            lineNumber++;
            index++;
        }
        
  
        /* EOF does not have an 'instance', so this is giving EOF some 'instance' */
        if(token.getTokenID().equals(tables.getTokenHashTable().get(1022)))
            token.setTokenInstance("EOF");
        
        /* Checking if the token is a 'idTK'  If it is,
         * a keywordCheck will be done on the 'idTK' */
        if(token.getTokenID().equals(tables.getTokenHashTable().get(1001)))
            tables.checkToken(token);
        
        return token;
    }
        
}
