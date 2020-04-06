## Author: Timothy Kuelker ##
### Date: March 1, 2020 ##
### Course: CmpSci ##

### **Project Selection** ###
		I chose to implement my scanner using a FSA table and a driver function.  The FSA table can be located in the
	'Tables.java' file, while the driver function is located in the 'Scanner.java' file. The driver function is named 'getToken'.

#### **Project Description** ####

                This program takes in input from either a keyboard or a file (explained below).  It will then take the
        data, parse the data by all horizontal white-space characters.  The parsed data will have comments filtered out,
	and replaced with new-line characters if necessary.  After proper comments have been filtered out, the data will be sent through
	a scanner.  The scanner will display the data as tokens.  A list of proper tokens will be below under Lexical Definitions.
	There was an issue with trying to get the end-of-file recognized by the scanner.  Since the EOF isn't recognized, I had to
	tack on a character to represent the EOF.  With the resources listed below, I selected a hexadecimal value that had no
	character representation.  Without the character representation, it will not be displayed on the screen at any point, unless
	tokenized.

#### **Executing** ####

                To execute the program, from the project root directory type 'make' or 'Make' into the command line.
        This invokes a make file that will comipile all the '.java' programs into '.class' object files.  From there,
        a script named scanner will need to be executed which will execute the actual java program.  The following will
	be some proper invocations of the program:


*  **./scanner**
    * This invocation will take in keyboard input from the user
    * It will continue to do so until the user enters ** Ctrl-D **
*  **./scanner < 'file.extension'**
    * This invocation will do the same as above, but redirected from a file
    * All file opening, reading, and error handling gets taken care of by the shell, not the program
*  **./scanner 'file'**
    * This invocation will read from a file, where the file extionsion ** .sp2020 ** is implicite
    * All file opening, reading, and error handling gets taken care of by the program, not the shell

### **Lexical Definitions** ###
*  **Alphabet**
    * All English letters (upper and lower), digits, WS, plus the extra characters seen below
    * All other characters generate an error
*  **Identifiers**
    * Begin with a letter and continue with any number of letters or digits
*  **Keywords**
    * Reserved, suggested individual tokens
    * **begin**
    * **end**
    * **loop**
    * **void**
    * **var**
    * **return**
    * **in**
    * **out**
    * **program**
    * **iffy**
    * **then**
    * **let**
    * **data**
*  **Operators and Delimiters**
    * **=**
    * **<**
    * **>**
    * **==**
    * **:**
    * **+**
    * **-**
    * *
    * **/**
    * **%**
    * **.**
    * **(**
    * **)**
    * **,**
    * **{**
    * **}**
    * **;**
    * **[**
    * **]**
*  **Integers**
    * Any sequence of decimal digits, no sign, no decimal point
* **Comments**
    * Start with a @ andEndWithWithoutSpaces@


#### **Resources Used** ####
*  [Java Makefile](https://www.cs.swarthmore.edu/~newhall/unixhelp/javamakefiles.html)
*  [Script to Run Compiled Java Class](https://stackoverflow.com/questions/38064801/writing-a-bash-script-to-run-a-java-program)
*  [Java How to Detect End of File From Keyboard Input (Ctrl - D)](https://stackoverflow.com/questions/4208502/how-to-determine-when-end-of-file-has-been-reached)
*  [Java Package Structure](https://docs.oracle.com/javase/tutorial/java/package/namingpkgs.html)
*  [ASCII Table - Used to locate a Hexidecimal value that did not have a representing character](https://www.eso.org/~ndelmott/ascii.html)
*  [Converter - Used to check if Hexidecimal value has a representing decimal form](https://www.branah.com/ascii-converter)                                                                                                     
