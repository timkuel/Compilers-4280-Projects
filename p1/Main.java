import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
	
    public static void main(String[] args) throws IOException {
        Scanner scanner = null;

        switch(args.length) {
            case(0):
                System.out.println("Input data from keyboard, seperate the data by white spaces and press enter at the end of each line.\n"
                                + "When finished with input, press ENTER then press CTRL-D (Represents EOF), or press CTRL-D twice.\n");

                /* Setting the scanner to read from 'System.in' (keyboard) */
                scanner = new Scanner(System.in);

                break;
            case(1):
                String inputFileName = args[0] + ".sp2020";

                System.out.println("Attempting to open file " + inputFileName + " to do work with the data inside...\n");

                /* Setting the scanner to read from 'inputFile' (a file) */
                try {
                    scanner = new Scanner(new File(inputFileName));
                } catch (Exception e) {
                    System.out.println("ERROR: " + e.getClass().getCanonicalName() + " was caught with the message '" + e.getMessage() + "'...Terminating Program");
                    System.exit(-1);
                }

                break;
            default:
                System.out.println("ERROR: Expected at most 1 argument, received " + args.length + "...Terminating Program");
                System.exit(-1);
        }
   
        
        /* Reading the data, parsing it, then filtering out the comments */
        ArrayList<Character> filteredList =  new Filter().filterData(parseData(readData(scanner)));
       
        System.out.println("\nThe data has been parsed and filtered.  Attempting to scan and display the data as tokens...\n");
        
        /* Starting the scanner */
        TestScanner.RunScanner(filteredList);
        
        System.out.println("\nDone scanning and displaying the data as tokens...Progam Successful");
         
        System.exit(0);
    }
	
	
    /* Function that uses a Scanner to read from a file or keyboard data 
     * (which is simulated by the EOF shortcut CTRL-D handled by 'hasNextLine()') */
    private static String readData(Scanner input) {
        String line = "";

        while(input.hasNextLine())
            line += (input.nextLine() + "\n");

        if(line == ""){
            System.out.println("ERROR: No data was read, be sure the file was not empty or input was provided through the keyboard...Terminating Program");
            System.exit(-1);
        }

        return line;
    }


    /* Function that will parse a string by horizontal white-space characters
     * after removing any leading or trailing white-spaces. It will then
     * parse that string into individual characters, being sure to add spaces
     * where necessary */
    private static ArrayList<Character> parseData(String line) {
        ArrayList<Character> dataList = new ArrayList<Character>();
                
        String[] dataArray = line.trim().split("\\h+");

        for(String dataString: dataArray) {
            for(Character dataChar: dataString.toCharArray())
                dataList.add(dataChar);
            dataList.add(' ');
        }
       
        return dataList;
    }
        
}
