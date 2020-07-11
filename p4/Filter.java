import java.util.ArrayList;


public class Filter {
    
    private ArrayList<Character> filteredList = null;
    
    
    /* Constructor for filter */
    Filter() { this.filteredList = new ArrayList<>(); }
    
    
    /* In case a getter is needed for the filteredList */
    public ArrayList<Character> getFilteredData() { return this.filteredList; }
    
    
    /* This function is used to filter out comments from an already parsed array list.  
     * it will filter out comments with the form ->      @ this is a comment and it needs no space at end@   */
    public ArrayList<Character> filterData(ArrayList<Character> parsedList) {
        Boolean isFiltering = false;
        Character previous = null;
        String filteredString = "";
        int filteredLines = 0;
        
    
        for(char item: parsedList) {
            
            /* If the item != '@' and parsedList is not already being filtered,
             * add the items to the filteredList */
            if(item != '@' && !isFiltering)
                filteredList.add(item);
            
            /* If the item = '@', is being filtered, and the previous item was a space,
             * then there was a proper comment detected. Comment will be removed, and new-lines
             * will be added back in place */
            else if(item == '@' && isFiltering && previous != ' ') {
                filteredString = "";
                isFiltering = false;
                
                /* Adding the new-lines that were filtered out */
                for(int i = 0; i < filteredLines; i++)
                    filteredList.add('\n');
                
            }
            
            /* If the list is filtering and the item = new-line, count that new-line,
             * and also append it to the filteredString */
            else if(isFiltering && item == '\n') {
                filteredLines++;
                filteredString += '\n';
                isFiltering = true;
            }
        
            /* If the list is filtering and the item != '@', keep building the filteredString */
            else {
                filteredString += item;
                isFiltering = true;
            }
                            
            previous = item;
        }
        
        
        /* If the list was still filtering when it completed, there was not a proper comment. 
         * Add the filteredString back to the filteredList, and leave it to the scanner to detect
         * the error. (There will be an '@' character which is not recognized by the scanner) */
        if(isFiltering)
            for(char item: filteredString.toCharArray())
                filteredList.add(item);


        /* Adding a hexadecimal value that has no actual character representation to
         * represent the EOF.  More info can be found in README.md over my choice */     
        filteredList.add((char)0x7f); 
        
        
        return filteredList;
    }

}