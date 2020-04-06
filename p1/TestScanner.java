import java.util.ArrayList;


/* TestScanner is needed to run the scanner since a parser is not implemented at this point 
 * The function RunScanner will continue to try and tokenize the data until an EOF token is reached */
public class TestScanner {
	
    public static void RunScanner(ArrayList<Character> filteredList){
		
        Scanner scanner = new Scanner();
        Token token = null;
        
        /* Needs to be do-while.  Logic works with while-loop, but the EOF token
         * does not get displayed. */
        do {
            token = scanner.getToken(filteredList);
            System.out.println(token.toString());
			
        } while(!token.getTokenID().equals("EofTK")); 
        
    }
    
}
