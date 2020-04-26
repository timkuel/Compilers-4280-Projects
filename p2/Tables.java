import java.util.Hashtable;


public class Tables {

    private Hashtable<Integer, String> tokenHashTable;
    private Hashtable<String, String> keywordHashTable;
    private Hashtable<Character, Integer> columnHashTable;
    
    
    /* Following table is a representation of the FSA.  The columns represent the incoming character, the rows
     * are the current state of the token.  The incoming character determines what the next state of the token
     * will be.  If the number is greater than 1000, then it is a FINAL STATE. Anything less than 1000 is still
     * a token building, unless it is not one of the representing column characters (below), then it will be 
     * an invalid character (-2).  An invalid comment can also be recognized under the 25th column */
    
    /*cols: {WS, UpperCase Letter, LowerCase Letter, Digit, =, <, >, :, +, -, *, /, %, ., (, ), ,, {, }, ;, [, ], \n, EOF, invalid character, invalid comment}*/
    private int fsaTable[][] = {
        /* Initial state-0 */
        {0, 1, 1, 2, 3, 4, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 0, 1022, -2, -3},
        /* Identifier state-1 */
        {1001, 1, 1, 1, 1001, 1001, 1001, 1001, 1001, 1001, 1001, 1001, 1001, 1001, 1001, 1001, 1001, 1001, 1001, 1001, 1001, 1001, 1001, 1001, -2, -3},
        /* Number state-2 */
        {1002, 1002, 1002, 2, 1002, 1002, 1002, 1002, 1002, 1002, 1002, 1002, 1002, 1002, 1002, 1002, 1002, 1002, 1002, 1002, 1002, 1002, 1002, 1002, -2, -3},
        /* = state-3 (This has the path to == state-6)*/
        {1003, 1003, 1003, 1003, 6, 1003, 1003, 1003, 1003, 1003, 1003, 1003, 1003, 1003, 1003, 1003, 1003, 1003, 1003, 1003, 1003, 1003, 1003, 1003, -2, -3},
        /* < state-4 */
        {1004, 1004, 1004, 1004, 1004, 1004, 1004, 1004, 1004, 1004, 1004, 1004, 1004, 1004, 1004, 1004, 1004, 1004, 1004, 1004, 1004, 1004, 1004, 1004, -2, -3},
        /* > state-5 */
        {1005, 1005, 1005, 1005, 1005, 1005, 1005, 1005, 1005, 1005, 1005, 1005, 1005, 1005, 1005, 1005, 1005, 1005, 1005, 1005, 1005, 1005, 1005, 1005, -2, -3},
        /* == state-6 */
        {1006, 1006, 1006, 1006, 1006, 1006, 1006, 1006, 1006, 1006, 1006, 1006, 1006, 1006, 1006, 1006, 1006, 1006, 1006, 1006, 1006, 1006, 1006, 1006, -2, -3},
        /* : state-7 */
        {1007, 1007, 1007, 1007, 1007, 1007, 1007, 1007, 1007, 1007, 1007, 1007, 1007, 1007, 1007, 1007, 1007, 1007, 1007, 1007, 1007, 1007, 1007, 1007, -2, -3},
        /* + state-8 */
        {1008, 1008, 1008, 1008, 1008, 1008, 1008, 1008, 1008, 1008, 1008, 1008, 1008, 1008, 1008, 1008, 1008, 1008, 1008, 1008, 1008, 1008, 1008, 1008, -2, -3},
        /* - state-9 */
        {1009, 1009, 1009, 1009, 1009, 1009, 1009, 1009, 1009, 1009, 1009, 1009, 1009, 1009, 1009, 1009, 1009, 1009, 1009, 1009, 1009, 1009, 1009, 1009, -2, -3},
        /* * state-10 */
        {1010, 1010, 1010, 1010, 1010, 1010, 1010, 1010, 1010, 1010, 1010, 1010, 1010, 1010, 1010, 1010, 1010, 1010, 1010, 1010, 1010, 1010, 1010, 1010, -2, -3},
        /* / state-11 */
        {1011, 1011, 1011, 1011, 1011, 1011, 1011, 1011, 1011, 1011, 1011, 1011, 1011, 1011, 1011, 1011, 1011, 1011, 1011, 1011, 1011, 1011, 1011, 1011, -2, -3},
        /* % state-12 */
        {1012, 1012, 1012, 1012, 1012, 1012, 1012, 1012, 1012, 1012, 1012, 1012, 1012, 1012, 1012, 1012, 1012, 1012, 1012, 1012, 1012, 1012, 1012, 1012, -2, -3},
        /* . state-13 */
        {1013, 1013, 1013, 1013, 1013, 1013, 1013, 1013, 1013, 1013, 1013, 1013, 1013, 1013, 1013, 1013, 1013, 1013, 1013, 1013, 1013, 1013, 1013, 1013, -2, -3},
        /* ( state-14 */
        {1014, 1014, 1014, 1014, 1014, 1014, 1014, 1014, 1014, 1014, 1014, 1014, 1014, 1014, 1014, 1014, 1014, 1014, 1014, 1014, 1014, 1014, 1014, 1014, -2, -3},
        /* ) state-15 */
        {1015, 1015, 1015, 1015, 1015, 1015, 1015, 1015, 1015, 1015, 1015, 1015, 1015, 1015, 1015, 1015, 1015, 1015, 1015, 1015, 1015, 1015, 1015, 1015, -2, -3},
        /* , state-16 */
        {1016, 1016, 1016, 1016, 1016, 1016, 1016, 1016, 1016, 1016, 1016, 1016, 1016, 1016, 1016, 1016, 1016, 1016, 1016, 1016, 1016, 1016, 1016, 1016, -2, -3},
        /* { state-17 */
        {1017, 1017, 1017, 1017, 1017, 1017, 1017, 1017, 1017, 1017, 1017, 1017, 1017, 1017, 1017, 1017, 1017, 1017, 1017, 1017, 1017, 1017, 1017, 1017, -2, -3},
        /* } state-18 */
        {1018, 1018, 1018, 1018, 1018, 1018, 1018, 1018, 1018, 1018, 1018, 1018, 1018, 1018, 1018, 1018, 1018, 1018, 1018, 1018, 1018, 1018, 1018, 1018, -2, -3},
        /* ; state-19 */
        {1019, 1019, 1019, 1019, 1019, 1019, 1019, 1019, 1019, 1019, 1019, 1019, 1019, 1019, 1019, 1019, 1019, 1019, 1019, 1019, 1019, 1019, 1019, 1019, -2, -3},
        /* [ state-20 */
        {1020, 1020, 1020, 1020, 1020, 1020, 1020, 1020, 1020, 1020, 1020, 1020, 1020, 1020, 1020, 1020, 1020, 1020, 1020, 1020, 1020, 1020, 1020, 1020, -2, -3},
        /* ] state-21 */
        {1021, 1021, 1021, 1021, 1021, 1021, 1021, 1021, 1021, 1021, 1021, 1021, 1021, 1021, 1021, 1021, 1021, 1021, 1021, 1021, 1021, 1021, 1021, 1021, -2, -3}
    };
    
   
    Tables () {
    	setupTokenHashTable();
    	setupKeywordHashTable();
    	setupColumnHashTable();
    }
	
	   
    /* Hashtable that is used to reference the types of tokens available */
    private void setupTokenHashTable() {
        this.tokenHashTable = new Hashtable<>();
    	
        /* Identifier Token */
        this.getTokenHashTable().put(1001, "idTK");
        this.getTokenHashTable().put(1002, "integerTK");
    	
        /* Operators and Delimiters */
        this.getTokenHashTable().put(1003, "equalsTK");
        this.getTokenHashTable().put(1004, "lessThanTK");
        this.getTokenHashTable().put(1005, "greaterThanTK");
        this.getTokenHashTable().put(1006, "compareEqualTK");
        this.getTokenHashTable().put(1007, "colonTK");
        this.getTokenHashTable().put(1008, "plusTK");
        this.getTokenHashTable().put(1009, "minusTK");
        this.getTokenHashTable().put(1010, "multiplyTK");
        this.getTokenHashTable().put(1011, "divideTK");
        this.getTokenHashTable().put(1012, "modulusTK");
        this.getTokenHashTable().put(1013, "periodTK");
        this.getTokenHashTable().put(1014, "leftParenTK");
        this.getTokenHashTable().put(1015, "rightParenTK");
        this.getTokenHashTable().put(1016, "commaTK");
        this.getTokenHashTable().put(1017, "leftBraceTK");
        this.getTokenHashTable().put(1018, "rightBraceTK");
        this.getTokenHashTable().put(1019, "semicolonTK");
        this.getTokenHashTable().put(1020, "leftBracketTK");
        this.getTokenHashTable().put(1021, "rightBracketTK");
        
        /* Extra Tokens */
        this.getTokenHashTable().put(1022, "EofTK");
        this.getTokenHashTable().put(-2, "Invalid Character");
        this.getTokenHashTable().put(-3, "Invalid Comment");
    }
    
    
    /* Hashtable that is used to reference idTk to keywords */
    private void setupKeywordHashTable() {
    	this.keywordHashTable = new Hashtable<>();

        this.keywordHashTable.put("begin", "BeginKwTK");
        this.keywordHashTable.put("end", "EndKwTK");
        this.keywordHashTable.put("loop", "LoopKwTK");
        this.keywordHashTable.put("void", "VoidKwTK");
        this.keywordHashTable.put("var", "VarKwTK");
        this.keywordHashTable.put("return", "ReturnKwTK");
        this.keywordHashTable.put("in", "InKwTK");
        this.keywordHashTable.put("out", "OutKwTK");
        this.keywordHashTable.put("program", "ProgramKwTK");
        this.keywordHashTable.put("iffy", "IffyKwTK");
        this.keywordHashTable.put("then", "ThenKwTK");
        this.keywordHashTable.put("let", "LetKwTK");
        this.keywordHashTable.put("data", "DataKwTK");
    }
    
    
    /* Hashtable used to reference which character represented which column */
    private void setupColumnHashTable() {
        this.columnHashTable = new Hashtable<>();
    	
        /* Column representations */
        this.columnHashTable.put((char)0x20, 0);
        this.columnHashTable.put('=', 4);
        this.columnHashTable.put('<', 5);
        this.columnHashTable.put('>', 6);
        this.columnHashTable.put(':', 7);
        this.columnHashTable.put('+', 8);
        this.columnHashTable.put('-', 9);
        this.columnHashTable.put('*', 10);
        this.columnHashTable.put('/', 11);
        this.columnHashTable.put('%', 12);
        this.columnHashTable.put('.', 13);
        this.columnHashTable.put('(', 14);
        this.columnHashTable.put(')', 15);
        this.columnHashTable.put(',', 16);
        this.columnHashTable.put('{', 17);
        this.columnHashTable.put('}', 18);
        this.columnHashTable.put(';', 19);
        this.columnHashTable.put('[', 20);
        this.columnHashTable.put(']', 21);
        this.columnHashTable.put('\n', 22);
        this.columnHashTable.put((char)0x7f, 23);
        this.columnHashTable.put('@', 25);
    }
	
    
    /* Public getters for the tables */
    public Hashtable<Integer, String> getTokenHashTable() { return this.tokenHashTable; }
	
    public Hashtable<String, String> getKeywordHashTable() { return this.keywordHashTable; }

    public Hashtable<Character, Integer> getColumnHashTable() { return this.columnHashTable; }
	
    public int getFSATableState(int currentState, char character) { return this.fsaTable[currentState][getCharacterColumnNumber(character)]; }

	
    /* Reference to which columns represent which valid characters
     * default leads to an invalid character */
    private int getCharacterColumnNumber(char character) {    	
        if(Character.isUpperCase(character))
            return 1;
        else if(Character.isLowerCase(character))
            return 2;
        else if(Character.isDigit(character))
            return 3;
        else if(Character.isWhitespace(character))
            return 0;
        else if (this.columnHashTable.get(character) != null)
            return this.columnHashTable.get(character);
        else
            return 24;	
    }
	
	
    /* Function that checks if a Token is a Keyword
     * If a Keyword, the Tokens ID will be changed to match the Keyword */
    public void checkToken(Token token) {
        if(keywordHashTable.containsKey(token.getTokenInstance()))
            token.setTokenID(keywordHashTable.get(token.getTokenInstance()));
    }
  
}