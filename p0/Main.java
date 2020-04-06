import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;


public class Main {
	
    public static void main(String[] args) throws IOException {	
    	Tree tree = new Tree();
        Scanner scanner = null;
    	String outFile = null;
       
        switch(args.length) {
            case(0):
                outFile = "output";
            
                System.out.println("Input data from keyboard, seperate the data by white spaces and press enter at the end of each line.\n"
                                + "When finished with input, press ENTER then press CTRL-D (Represents EOF), or press CTRL-D twice.\n");
                
                /* Setting the scanner to read from 'System.in' (keyboard) */
                scanner = new Scanner(System.in);
                
                break;
            case(1):
                String inputFileName = args[0] + ".sp2020";
                outFile = args[0];
                
                System.out.println("Attempting to open file " + inputFileName + " and store the data inside...\n");
               
                /* Setting the scanner to read from 'inputFile' (a file) */
                try {
                    scanner = new Scanner(new File(inputFileName));
                } catch (Exception e) {
                    System.out.println("Error: " + e.getClass().getCanonicalName() + " was caught with the message '" + e.getMessage() + "'...Terminating Program");
                    System.exit(-1);
                }

                break;
            default:
                System.out.println("Error: Expected at most 1 argument, received " + args.length + "...Terminating Program");
                System.exit(-1);
        }
        
        /* Building the tree from the ArrayList returned by parseData, which received a string of data form readData */
        tree.buildTree(parseData(readData(scanner)));
        
        System.out.println("Done building the binary tree from parsed data, now traversing with different methods...\n");
        
        printTraversals(tree, outFile);

	System.out.println("Done traversing the binary tree...\n\nCheck the output files with the prefix '" + outFile + "' for more information about the binary tree traversals.");    
    }
    
    
    /* Function that uses a Scanner to read from a file or keyboard data 
     * (which is simulated by the EOF shortcut CTRL-D handled by 'hasNextLine()') */
    private static String readData(Scanner input) {
    	String line = " ";
    	
        while(input.hasNextLine()) 
            line += (" " + input.nextLine());
        
        return line;
    }
    
    
    /* Function that will parse a string by white-space characters
     * after removing any leading or trailing white-spaces. */
    private static List<String> parseData(String line) {
    	List<String> dataList = new ArrayList<String>();
    	String[] dataArray = line.trim().split("\\s+");

    	for(String dataString: dataArray)
    	    dataList.add(dataString);
    	
    	return dataList;
    }
   

    /* Function that does all the traversal methods located in Tree.java */
    private static void printTraversals(Tree tree, String outFile) throws IOException {
        tree.printInorder(outFile);
        tree.printPreorder(outFile);
        tree.printPostorder(outFile);
    } 
   
}

