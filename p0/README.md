## Author: Timothy Kuelker ##
### Date: February 10, 2020 ##
### Course: CmpSci ##

#### **Project Description** ####

                This program takes in input from either a keyboard or a file (explained below).  It will then take the
        data, parse the data by all white space characters (space, tab, enter) and build a binary tree form the
        data.  The key for the nodes are based off of the length of the strings passed in.  All strings of the
        same length will be stored in the same node, exluding duplicate strings.  The program will then write
        inorder, preorder, and postorder traversals of the tree to output files with the traversal type as the
        file extension.

#### **Executing** ####

                To execute the program, from the project root directory type 'make' or 'Make' into the command line.
        This invokes a make file that will comipile all the '.java' programs into '.class' object files.  From there,
        a script named P0 will be executed which executes the actual java program.  The following will be some proper
        invocations of the program:


*  **./P0**
    * This invocation will take in keyboard input from the user
    * It will continue to do so until the user enters ** Ctrl-D **
*  **./P0 < 'file.extension'**
    * This invocation will do the same as above, but redirected from a file
    * All file opening, reading, and error handling gets taken care of by the shell, not the program
*  **./P0 'file'**
    * This invocation will read from a file, where the file extionsion ** .sp2020 ** is implicite
    * All file opening, reading, and error handling gets taken care of by the program, not the shell


#### **Resources Used** ####
*  [Java Makefile](https://www.cs.swarthmore.edu/~newhall/unixhelp/javamakefiles.html)
*  [Script to Run Compiled Java Class](https://stackoverflow.com/questions/38064801/writing-a-bash-script-to-run-a-java-program)
*  [Java How to Detect End of File From Keyboard Input (Ctrl - D)](https://stackoverflow.com/questions/4208502/how-to-determine-when-end-of-file-has-been-reached)
*  [Java Binary Tree](https://www.geeksforgeeks.org/binary-search-tree-set-1-search-and-insertion/)
*  [Java Binary Tree Printing](https://www.geeksforgeeks.org/tree-traversals-inorder-preorder-and-postorder/)
*  [Java Package Structure](https://docs.oracle.com/javase/tutorial/java/package/namingpkgs.html)             
