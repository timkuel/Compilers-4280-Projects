import java.util.ArrayList;


public class Parser {

    private ArrayList<Character> filteredList;
    private Scanner scanner;
    private Token token;

    
    
    public Parser(ArrayList<Character> filteredList){
        this.filteredList = filteredList;
        this.scanner = new Scanner();
        this.token = null;
    }
    
    
    /* Function consumes the current token and get the next token from the filtered list */
    private void getNextToken() { this.token = scanner.getToken(this.filteredList); }

    
    /* Function checks to see if the passed in string matches the tokenID */
    private boolean isCurrentToken(String nextToken) { return this.token.getTokenID().equals(nextToken); }
    
    
    private void printErrorMessage(String expectedToken) {
        System.out.println("Parser Error: Line " + this.token.getLineNumber() + 
                ": Expectd " + expectedToken + " got " + this.token.getTokenInstance());
        
        System.exit(-1);
    }
    
    
    public Node startParser() {
        /* Retrieving the first token */
        getNextToken();
        
        Node rootNode = nonterminalProgram();
        
        /* If the current token is not an EofTK, there is an error */
        if(!isCurrentToken("EofTK"))
            printErrorMessage("EofTK");
            
        return rootNode;    
    }

    
    private Node nonterminalProgram() {
        Node treeNode = new Node("<program>");
        
        /* If the token is a VarKwTk or a BeginKwTK, call both
         * nonterminalVars() and nonterminalBlock(), if there is not
         * any nonterminalVars() before the block, null will be placed there,
         * if the block is nonexistent, then there is an error */
        if(isCurrentToken("DataKwTK") || isCurrentToken("BeginKwTK")) {
            treeNode.childNodes.add(nonterminalVars());
            treeNode.childNodes.add(nonterminalBlock());
        }
        else 
            printErrorMessage("DataKwTK or BeginKwTK");
    
                
        return treeNode;    
    }
    
    
    private Node nonterminalBlock() {
        Node treeNode = new Node("<block>");
        
        if(isCurrentToken("BeginKwTK"))
            getNextToken();
        else
            printErrorMessage("BeginKwTK");
        
        treeNode.childNodes.add(nonterminalVars());
        treeNode.childNodes.add(nonterminalStats());
        
        if(isCurrentToken("EndKwTK")) 
            getNextToken();
        else    
            printErrorMessage("EndKwTK");
            
        return treeNode;
    }
    
    
    private Node nonterminalVars() {
        Node treeNode = new Node("<vars>");
        
        if(isCurrentToken("DataKwTK")) {
            getNextToken();
            
            if(isCurrentToken("idTK")) {
                treeNode.tokenData.add(token);
                getNextToken();
                
                if(isCurrentToken("equalsTK")) {
                    getNextToken();
                    
                    if(isCurrentToken("integerTK")) {
                        treeNode.tokenData.add(token);
                        getNextToken();
                        
                        if(isCurrentToken("periodTK")) {
                            getNextToken();
                            treeNode.childNodes.add(nonterminalVars());
                            
                            return treeNode;
                        }
                        else
                            printErrorMessage("periodTK");
                    }
                    else
                        printErrorMessage("integerTK");
                }
                else
                    printErrorMessage("equalsTK");
            }
            else
                printErrorMessage("idTK");
        }

        
        return null; /* Returning empty set */
            
    }
    
    
    private Node nonterminalExpr() {
        Node treeNode = new Node("<expr>");
        
        /* nonterminalN() happens regardless of whether it is followed by the minusTK */
        treeNode.childNodes.add(nonterminalN());
        
        if(isCurrentToken("minusTK")) {
            treeNode.tokenData.add(token);
            getNextToken();
            treeNode.childNodes.add(nonterminalExpr());
        }
        
        return treeNode;
    }
    
    
    private Node nonterminalN() {
        Node treeNode = new Node("<N>");
        
        /* nonterminalA() happens regardless of whether it is followed by the multiplyTK or divideTK */
        treeNode.childNodes.add(nonterminalA());
        
        if(isCurrentToken("divideTK") || isCurrentToken("multiplyTK")) {
            treeNode.tokenData.add(token);
            getNextToken();
            treeNode.childNodes.add(nonterminalN());
        }
        
        return treeNode;
    }
    
    
    private Node nonterminalA() {
        Node treeNode = new Node("<A>");
        
        /* nonterminalM() happens regardless of whether it is followed by the plusTK */
        treeNode.childNodes.add(nonterminalM());
        
        if(isCurrentToken("plusTK")) {
            treeNode.tokenData.add(token);
            getNextToken();
            treeNode.childNodes.add(nonterminalA());
        }
        
        return treeNode;
    }
    
    
    private Node nonterminalM() {
        Node treeNode = new Node("<M>");
        
        if(isCurrentToken("multiplyTK")) {
            treeNode.tokenData.add(token);
            getNextToken();
            treeNode.childNodes.add(nonterminalM());
        }
        else if(isCurrentToken("leftParenTK") || isCurrentToken("idTK") || isCurrentToken("integerTK"))
            treeNode.childNodes.add(nonterminalR());
        
        else
            printErrorMessage("multiplyTK or leftParenTK or idTK or integerTK");
        
        return treeNode;
    }
    
    
    private Node nonterminalR() {
        Node treeNode = new Node("<R>");
        
        if(isCurrentToken("leftParenTK")) {
            getNextToken();
            
            treeNode.childNodes.add(nonterminalExpr());
            
            if(isCurrentToken("rightParenTK"))
                getNextToken();
            else
                printErrorMessage("rightParenTK");
        }
        else if(isCurrentToken("idTK")) {
            treeNode.tokenData.add(token);
            getNextToken();
        }
        else if(isCurrentToken("integerTK")) {
            treeNode.tokenData.add(token);
            getNextToken();
        }
        else
            printErrorMessage("leftParenTK or idTK or integerTK");

        
        return treeNode;
    }
    
    
    private Node nonterminalStats() {
        Node treeNode = new Node("<stats>");
        
        if(isCurrentToken("InKwTK") || isCurrentToken("OutKwTK") ||
                isCurrentToken("BeginKwTK") || isCurrentToken("IffyKwTK") ||
                isCurrentToken("LoopKwTK") || isCurrentToken("idTK")) {
            
            treeNode.childNodes.add(nonterminalStat());
            treeNode.childNodes.add(nonterminalMStat());
        }
        else
            printErrorMessage("InKwTK or OutKwTK or BeginKwTK or IffyKwTK or LoopKwTK or idTK");
        
        return treeNode;
    }
    
    
    private Node nonterminalMStat() {
        Node treeNode = new Node("<mStat>");
        
        if(isCurrentToken("InKwTK") || isCurrentToken("OutKwTK") ||
                isCurrentToken("BeginKwTK") || isCurrentToken("IffyKwTK") ||
                isCurrentToken("LoopKwTK") || isCurrentToken("idTK")) {
            
            treeNode.childNodes.add(nonterminalStat());
            treeNode.childNodes.add(nonterminalMStat());
            
            return treeNode;
        }
        
        return null;
    }
    
    
    private Node nonterminalStat() {
        Node treeNode = new Node("<stat>");
        
        if(isCurrentToken("InKwTK"))
            treeNode.childNodes.add(nonterminalIn());
        
        else if(isCurrentToken("OutKwTK"))
            treeNode.childNodes.add(nonterminalOut());
        
        else if(isCurrentToken("BeginKwTK")) {
            treeNode.childNodes.add(nonterminalBlock());
            return treeNode;
        }
        
        else if(isCurrentToken("IffyKwTK"))
            treeNode.childNodes.add(nonterminalIf());
        
        else if(isCurrentToken("LoopKwTK"))
            treeNode.childNodes.add(nonterminalLoop());
        
        else if(isCurrentToken("idTK"))
            treeNode.childNodes.add(nonterminalAssign());
        
        else
            printErrorMessage("InKwTK or OutKwTK or BeginKwTK or IffyKwTK or LoopKwTK or idTK");
        

        if(isCurrentToken("periodTK"))
            getNextToken();
        else
            printErrorMessage("periodTK");
        
        return treeNode;
    }
    
    
    private Node nonterminalIn() {
        Node treeNode = new Node("<in>");
        
        if(isCurrentToken("InKwTK")) {
            getNextToken();
            
            if(isCurrentToken("idTK")) {
                treeNode.tokenData.add(token);
                getNextToken();
            }
            else
                printErrorMessage("idTK");
        }
        else
            printErrorMessage("InKwTK");
        
        return treeNode;
    }
    
    
    private Node nonterminalOut() {
        Node treeNode = new Node("<out>");
        
        if(isCurrentToken("OutKwTK")) {
            getNextToken(); 
            treeNode.childNodes.add(nonterminalExpr());
        }
        else
            printErrorMessage("OutKwTK");
        
        return treeNode;
    }
    
    
    private Node nonterminalIf() {
        Node treeNode = new Node("<if>");
        
        if(isCurrentToken("IffyKwTK")) {
            getNextToken();
            
            if(isCurrentToken("leftBracketTK")) {
                getNextToken();
                
                treeNode.childNodes.add(nonterminalExpr());
                treeNode.childNodes.add(nonterminalRO());
                treeNode.childNodes.add(nonterminalExpr());
                
                if(isCurrentToken("rightBracketTK")) {
                    getNextToken();
                    
                    if(isCurrentToken("ThenKwTK")) {
                        getNextToken();
                        
                        treeNode.childNodes.add(nonterminalStat());
                    }
                    else
                        printErrorMessage("ThenKwTK");
                }
                else
                    printErrorMessage("rightBracketTK");
                
            }
            else
                printErrorMessage("leftBracketTK");
        }
        else
            printErrorMessage("IffyKwTK");
        
        return treeNode;
    }
    
    
    private Node nonterminalLoop() {
        Node treeNode = new Node("<loop>");
        
        if(isCurrentToken("LoopKwTK")) {
            getNextToken();
            
            if(isCurrentToken("leftBracketTK")) {
                getNextToken();
                
                treeNode.childNodes.add(nonterminalExpr());
                treeNode.childNodes.add(nonterminalRO());
                treeNode.childNodes.add(nonterminalExpr());
                
                if(isCurrentToken("rightBracketTK")) {
                    getNextToken();
                    treeNode.childNodes.add(nonterminalStat());
                }
                else
                    printErrorMessage("rightBracketTK");
            }
            else
                printErrorMessage("leftBracketTK");
        }
        else
            printErrorMessage("LoopKwTK");
        
        
        return treeNode;
    }
    
    
    private Node nonterminalAssign() {
        Node treeNode = new Node("<assign>");
        
        if(isCurrentToken("idTK")) {
            treeNode.tokenData.add(token);
            getNextToken();
            
            if(isCurrentToken("equalsTK")) {
                treeNode.tokenData.add(token);
                getNextToken();
                treeNode.childNodes.add(nonterminalExpr());
            }
            else
                printErrorMessage("equalsTK");
        }
        else
            printErrorMessage("idTK");
        
        return treeNode;
    }
    
    
    private Node nonterminalRO() {
        Node treeNode = new Node("<RO>");
        
        /* If '==' token */
        if(isCurrentToken("compareEqualTK")) {
            treeNode.tokenData.add(token);
            getNextToken();
        }
        /* If '>' token */
        else if(isCurrentToken("greaterThanTK")) {
            treeNode.tokenData.add(token);
            getNextToken();
            
            /* If '> >' token */
            if(isCurrentToken("greaterThanTK")) {
                treeNode.tokenData.add(token);
                getNextToken();
            }
        }
        /* If '<' token */
        else if(isCurrentToken("lessThanTK")) {
            treeNode.tokenData.add(token);
            getNextToken();
            
            /* If '< <' token or '< >' token */
            if(isCurrentToken("lessThanTK") || isCurrentToken("greaterThanTK")) {
                treeNode.tokenData.add(token);
                getNextToken();
            }
            
        }
        else
            printErrorMessage("compareEqualTK or greaterThanTK or lessThanTK");
        
        return treeNode;
    }

}
