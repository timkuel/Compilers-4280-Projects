package com.umsl.kuelkertim.Generator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.umsl.kuelkertim.DataStructures.*;

public class CodeGenerator {
    
    private String outFile = null;
    private Node rootNode = null;
    private ArrayList<String> variableList = null;
    private ArrayList<String> tempVariableList = null;
    private int outLabelCount = 0;
    private int loopLabelCount = 0;
    private int tempVarCount = 0;
    
    
    public CodeGenerator(Node rootNode, VariableList variableList, String outFile) throws IOException {
        this.rootNode = rootNode;
        this.variableList = variableList.getVariableList();
        this.outFile = outFile;
        this.variableList = new ArrayList<>();
        this.tempVariableList = new ArrayList<>();

        /* Creating new output file, over writing all data previously stored */
        BufferedWriter writer = new BufferedWriter(new FileWriter(outFile, false));
        writer.write("");
        writer.close();
        
    }
    
    
    public void startCodeGenerator() { 
        preorderTraversalCodeGenerator(rootNode);
        writeToTarget("STOP");
        writeVariablesToTarget(variableList);
        writeVariablesToTarget(tempVariableList);  
    }

       
    private void preorderTraversalCodeGenerator(Node treeNode) {        
        switch(treeNode.getNodeData()) {
            case "<program>":
                noCodeGenerationTraversalContinuation(treeNode);
                break;
            case "<block>":
                noCodeGenerationTraversalContinuation(treeNode);
                break;
            case "<vars>":
                generateVars(treeNode);
                break;
            case "<expr>":
                generateExpr(treeNode);
                break;
            case "<N>":
                generateN(treeNode);
                break;
            case "<A>":
                generateA(treeNode);
                break;
            case "<M>":
                generateM(treeNode);
                break;
            case "<R>":
                generateR(treeNode);
                break;
            case "<stats>":
                noCodeGenerationTraversalContinuation(treeNode);
                break;
            case "<mStat>":
                noCodeGenerationTraversalContinuation(treeNode);
                break;
            case "<stat>":
                noCodeGenerationTraversalContinuation(treeNode);
                break;
            case "<in>":
                generateIn(treeNode);
                break;
            case "<out>":
                generateOut(treeNode);
                break;
            case "<if>":
                generateIf(treeNode);
                break;
            case "<loop>":
                generateLoop(treeNode);
                break;
            case "<assign>":
                generateAssign(treeNode);
                break;
            case "<RO>":
                noCodeGenerationTraversalContinuation(treeNode);
                break;
            default:
                System.out.println("Code Generation Error: Unrecognized Token " + treeNode.getNodeData());
                System.exit(-1);
        }
    }
    

    private void noCodeGenerationTraversalContinuation(Node treeNode) {
        for(Node childNode : treeNode.childNodes)
            if(childNode != null)
                preorderTraversalCodeGenerator(childNode);
    }
        

    private void generateVars(Node treeNode) {
        String variable = getTokenInstance(treeNode, "idTK");
        String value = getTokenInstance(treeNode, "integerTK");
        
        if(variable == null || value == null)
            return;
        
        variableList.add(variable);
        
        writeToTarget("LOAD " + value);
        writeToTarget("STORE " + variable);
        
        for(Node childNode : treeNode.childNodes) {
            if(childNode != null)
                preorderTraversalCodeGenerator(childNode);
        }
        
    }
    

    private void generateExpr(Node treeNode) {
        String temp = null;
        
        if(treeNode.childNodes.size() == 1)
            preorderTraversalCodeGenerator(treeNode.childNodes.get(0));
        else {
            preorderTraversalCodeGenerator(treeNode.childNodes.get(1));
            temp = generateTempVariable();
            writeToTarget("STORE " + temp);
            preorderTraversalCodeGenerator(treeNode.childNodes.get(0));
            writeToTarget("SUB " + temp);
        }
    }
    
   
    private void generateN(Node treeNode) {
        String operator = null;
        String temp = null;
        
        if(treeNode.childNodes.size() == 1)
            preorderTraversalCodeGenerator(treeNode.childNodes.get(0));
        else {
            preorderTraversalCodeGenerator(treeNode.childNodes.get(1));
                      
            for(int i = 0; i < treeNode.tokenData.size(); i++)
                if(treeNode.tokenData.get(i) != null)
                    operator = treeNode.tokenData.get(i).getTokenID();
                       
            temp = generateTempVariable();
            writeToTarget("STORE " + temp);
            
            preorderTraversalCodeGenerator(treeNode.childNodes.get(0));
            
            if(operator.equals("divideTK"))
                writeToTarget("DIV " + temp);
            else if(operator.equals("multiplyTK"))
                writeToTarget("MULT " + temp);
        }
   
    }
    
    
    private void generateA(Node treeNode) {
        String temp = null;

        if(treeNode.childNodes.size() == 1)
            preorderTraversalCodeGenerator(treeNode.childNodes.get(0));
        else {
            preorderTraversalCodeGenerator(treeNode.childNodes.get(1));
            temp = generateTempVariable();
            writeToTarget("STORE " + temp);
            preorderTraversalCodeGenerator(treeNode.childNodes.get(0));
            writeToTarget("ADD " + temp);
        }
     
    }
    
    
    private void generateM(Node treeNode) {
        /* If a * token exists, invert sign */
        if(treeNode.tokenData.size() > 0) {
            preorderTraversalCodeGenerator(treeNode.childNodes.get(0));
            writeToTarget("MULT -1");
        }
        else 
            preorderTraversalCodeGenerator(treeNode.childNodes.get(0));
        
    }
    
    
    private void generateR(Node treeNode) {
        String variable = getTokenInstance(treeNode, "idTK");
        String value = getTokenInstance(treeNode, "integerTK");
        
        if(treeNode.childNodes.size() > 0)
            preorderTraversalCodeGenerator(treeNode.childNodes.get(0));
        else if(variable != null) 
            writeToTarget("LOAD " + variable);
        else if(value != null) {
            writeToTarget("LOAD " + value);
        }
    }
    
        
    private void generateIn(Node treeNode) {
        String temp = null;
        
        if(treeNode.tokenData.size() == 0)
            temp = generateTempVariable();
        else
            temp = treeNode.tokenData.get(0).getTokenInstance();
        
        /* Read input for keyboard */
        writeToTarget("READ " + temp);
        
        /* Load previous input into accumulator */
        writeToTarget("LOAD " + temp);
    }
    
    
    private void generateOut(Node treeNode) {
        String temp = generateTempVariable();       
        
        for(Node childNode : treeNode.childNodes)
            if(childNode != null)
                preorderTraversalCodeGenerator(childNode);
        
        /* Store accumulator to temp variable */
        writeToTarget("STORE " + temp);
        
        /* Write temp variable to the screen */
        writeToTarget("WRITE " + temp);
    }
    
    
    private void generateIf(Node treeNode) {
        int expr1 = -1;
        int RO = -1;
        int expr2 = -1;
        int stat = -1;
        
        String temp1 = null;
        String temp2 = null;
        String operator = null;
        String outLabel = null;
        
        for(int i = 0; i < treeNode.childNodes.size(); i++) {
            if(treeNode.childNodes.get(i).getNodeData().equals("<expr>")) {
                expr1 = i;
                
                for(int j = i + 1; j < treeNode.childNodes.size(); j++) {
                    if(treeNode.childNodes.get(j).getNodeData().equals("<RO>")) {
                        RO = j;
                        
                        for(int k = j + 1; k < treeNode.childNodes.size(); k++) {
                            if(treeNode.childNodes.get(k).getNodeData().equals("<expr>")) {
                                expr2 = k;
                                
                                for(int l = k + 1; l < treeNode.childNodes.size(); l++) {
                                    if(treeNode.childNodes.get(l).getNodeData().equals("<stat>")) {
                                        stat = l;
                                        break;
                                    }
                                }
                                
                                break;
                            }
                        }
                        
                        break;
                    }
                }
                
                break;
            }
        }
        
        temp1 = generateTempVariable();
        temp2 = generateTempVariable();
        
        if(expr1 != -1)
            preorderTraversalCodeGenerator(treeNode.childNodes.get(expr1));
        writeToTarget("STORE " + temp1);
        
        if(expr2 != -1)
            preorderTraversalCodeGenerator(treeNode.childNodes.get(expr2));
        writeToTarget("STORE " + temp2);
        
   
        operator = "";
        
        if(RO != -1) {
            for(Token tkn : treeNode.childNodes.get(RO).tokenData)
                if(tkn != null)
                    operator += tkn.getTokenInstance();
            
            
            for(Node node : treeNode.childNodes.get(RO).childNodes)
                if(node != null)
                    for(Token tkn : node.tokenData)
                        if(tkn != null)
                            operator += tkn.getTokenInstance();
            
        }
               
              
        outLabel = generateNewOutLabel();
        writeToTarget("LOAD " + temp1);
        writeToTarget("SUB " + temp2);
        
        generateRO(operator, outLabel);
        
        if(stat != -1)
            preorderTraversalCodeGenerator(treeNode.childNodes.get(stat));
        writeToTarget(outLabel + ": NOOP");
  
    }
    

    private void generateLoop(Node treeNode) {
        int expr1 = -1;
        int RO = -1;
        int expr2 = -1;
        int stat = -1;
        
        String temp1 = null;
        String temp2 = null;
        String operator = null;
        String outLabel = null;
        String loopLabel = null;
        
        for(int i = 0; i < treeNode.childNodes.size(); i++) {
            if(treeNode.childNodes.get(i).getNodeData().equals("<expr>")) {
                expr1 = i;
                
                for(int j = i + 1; j < treeNode.childNodes.size(); j++) {
                    if(treeNode.childNodes.get(j).getNodeData().equals("<RO>")) {
                        RO = j;
                        
                        for(int k = j + 1; k < treeNode.childNodes.size(); k++) {
                            if(treeNode.childNodes.get(k).getNodeData().equals("<expr>")) {
                                expr2 = k;
                                
                                for(int l = k + 1; l < treeNode.childNodes.size(); l++) {
                                    if(treeNode.childNodes.get(l).getNodeData().equals("<stat>")) {
                                        stat = l;
                                        break;
                                    }
                                }
                                
                                break;
                            }
                        }
                        
                        break;
                    }
                }
                
                break;
            }
        }
        
        
        operator = "";
        
        if(RO != -1) {
            for(Token tkn : treeNode.childNodes.get(RO).tokenData)
                if(tkn != null)
                    operator += tkn.getTokenInstance();
            
            
            for(Node node : treeNode.childNodes.get(RO).childNodes)
                if(node != null)
                    for(Token tkn : node.tokenData)
                        if(tkn != null)
                            operator += tkn.getTokenInstance();
            
        }
        
        outLabel = generateNewOutLabel();
        loopLabel = generateNewLoopLabel();
        writeToTarget(loopLabel + ": NOOP");
        temp1 = generateTempVariable();
        temp2 = generateTempVariable();
        
        if(expr1 != -1)
            preorderTraversalCodeGenerator(treeNode.childNodes.get(expr1));
        writeToTarget("STORE " + temp1);
        
        if(expr2 != -1)
            preorderTraversalCodeGenerator(treeNode.childNodes.get(expr2));
        writeToTarget("STORE " + temp2);
        writeToTarget("LOAD " + temp1);
        writeToTarget("SUB " + temp2);
        
        generateRO(operator, outLabel);
        
        if(stat != -1)
            preorderTraversalCodeGenerator(treeNode.childNodes.get(stat));
        writeToTarget("BR " + loopLabel);
        writeToTarget(outLabel + ": NOOP");        
    }
    

    private void generateAssign(Node treeNode) {
        String variable = getTokenInstance(treeNode, "idTK");
  
        preorderTraversalCodeGenerator(treeNode.childNodes.get(0));
        
        writeToTarget("STORE " + variable);
    }
      
    
    private void generateRO(String operator, String outLabel) {        
        switch(operator) {
            case "<":
                writeToTarget("BRZPOS " + outLabel);
                break;
            case "<<":
                writeToTarget("BRPOS " + outLabel);
                break;
            case ">":
                writeToTarget("BRZNEG " + outLabel);
                break;
            case ">>":
                writeToTarget("BRNEG " + outLabel);
                break;
            case "==":
                writeToTarget("BRPOS " + outLabel);
                writeToTarget("BRNEG " + outLabel);
                break;
            case "<>":
                writeToTarget("BRZERO " + outLabel);
                break;    
        }
    }
    
       
    /* Returns the token instance based off of the desired tokenID (idTK, integerTK,  */
    private String getTokenInstance(Node treeNode, String tokenIdentifier) {
        for(int i = 0; i < treeNode.tokenData.size(); i++)
            if(treeNode.tokenData.get(i).getTokenID().equals(tokenIdentifier))
                return treeNode.tokenData.get(i).getTokenInstance();
        
        return null;
    }
    
    
    
    /* Function that generates a temporary variable with some count, and
     * adds it to a list of temporary variables. */
    private String generateTempVariable() {
        String tempVar = "T" + tempVarCount;
        tempVariableList.add(tempVar);
        tempVarCount++;
        return tempVar;
    }
    
    
    
    
    
    /* Function that generates a new label for the current condition to break to if satisfied */
    private String generateNewOutLabel() {
        String label = "OUT" + outLabelCount;
        outLabelCount++;
        return label;
    }
    
    
    
    /* Function that generates a new label for the current loop to break out of */
    private String generateNewLoopLabel() {
        String label = "LOOP" + loopLabelCount;
        loopLabelCount++;
        return label;
    }
    
    
    /* Function used to access a target file, and append data to it */
    private void writeToTarget(String data) {       
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outFile, true));
            writer.write(data + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
    
    
    
    /* Function used to append all the stored variables in varList to the target file, 
     * and set all variable to 0 */
    private void writeVariablesToTarget(ArrayList<String> varList) {
        String variableListString = null;
        
        if(varList.size() <= 0)
            return;
        
        variableListString = varList.get(0) + " 0";
        
        for(int i = 1; i < varList.size(); i++)
            variableListString += "\n" + varList.get(i) + " 0";
        
        writeToTarget(variableListString);
    }
    
       
}
