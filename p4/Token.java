public class Token {
	
    private String tokenID = null;
    private String tokenInstance = null;
    private int lineNumber = -1;
	
	
    /* Constructor for a Token */
    public Token(String tokenID, String tokenInstance, int lineNumber) {
        this.tokenID = tokenID;
        this.tokenInstance = tokenInstance;
        this.lineNumber = lineNumber;
    }


    /* Public Setter Methods */
    public void setTokenID(String tokenID) { this.tokenID = tokenID; }
    public void setTokenInstance(String tokenInstance) { this.tokenInstance = tokenInstance; }
    public void setLineNumber(int lineNumber) { this.lineNumber = lineNumber; }
	
	
    /* Public Getter Methods */
    public String getTokenID() { return this.tokenID; }
    public String getTokenInstance() { return this.tokenInstance; };
    public int getLineNumber() { return this.lineNumber; }	
	
    
    /* Overriding the default toString method
     * Returns token as a triplet {tokenID,tokenInstance,lineNumber} */
    @Override
    public String toString() {
        return "{" + this.tokenID + ", " + this.tokenInstance + ", " + this.lineNumber + "}";
    }

}