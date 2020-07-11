package com.umsl.kuelkertim;

import com.umsl.kuelkertim.ParserParts.*;
import com.umsl.kuelkertim.StaticSemantics.SemanticsCheck;
import com.umsl.kuelkertim.DataStructures.Node;
import com.umsl.kuelkertim.Generator.CodeGenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) throws IOException {
        Parser parser = null;
        Node treeNode = null;
        Scanner scanner = null;
        SemanticsCheck checkSemantics = null;
        CodeGenerator generator = null;
        String outFile = null;

        switch(args.length) {
            case(0):
                outFile = "kb.asm";
                
                System.out.println("Input data from keyboard, seperate the data by white spaces and press enter at the end of each line.\n"
                                + "When finished with input, press ENTER then press CTRL-D (Represents EOF), or press CTRL-D twice.\n");

                /* Setting the scanner to read from 'System.in' (keyboard) */
                scanner = new Scanner(System.in);

                break;
            case(1):
                String inputFileName = args[0] + ".sp2020";
                outFile = args[0] + ".asm";

                System.out.println("Attempting to open file " + inputFileName + " to do work with the data inside...\n");

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
   
        
        /* Reading the data, parsing it, then filtering out the comments */
        ArrayList<Character> filteredList =  new Filter().filterData(parseData(readData(scanner)));
       
        System.out.println();
        
        /* Initializing the parser with the filtered list, and then starting
         * the parser, returning the root node into treeNode */
        parser = new Parser(filteredList);
        treeNode = parser.startParser();
        
        
        System.out.println("\nData was successfully parsed...checking static semantics\n");
        
        
        /* Initializing the Semantics Check with treeNode as the root node, and
         * starting the semantic check on the program */
        checkSemantics = new SemanticsCheck(treeNode);
        checkSemantics.checkStaticSemantics();
        
        
        System.out.println("\nDone checking the static semantics...generating target file " + outFile);
        
        
        /* Initializing the Code Generator's output file to the proper name, and
         * then starting the code generation with treeNode as the root node */
        generator = new CodeGenerator(treeNode, checkSemantics.getVariableList(), outFile);
        generator.startCodeGenerator();
      
        
        System.out.println("\nDone generating target file...now run the file '" + outFile + "' on the VirtualMachine (Example Below)");
        
        System.out.println("\n./VirtMach " + outFile + "\n");
     
        
        System.exit(0);
    }
    
    
    /* Function that uses a Scanner to read from a file or keyboard data 
     * (which is simulated by the EOF shortcut CTRL-D handled by 'hasNextLine()') */
    private static String readData(Scanner input) {
        String line = "";

        while(input.hasNextLine())
            line += (input.nextLine() + "\n");

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
