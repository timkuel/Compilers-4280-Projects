import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.FormatFlagsConversionMismatchException;
import java.util.List;

public class Tree {
	
    private Node root;
	
    public Tree() {
        root = null;
    }
	
    /* Function used to build the binary tree from data stored in a list */
    public void buildTree(List<String> data) {
        for(String dataString: data)
    	    insert(dataString.length(), dataString);
    }
	
	
    /* This method mainly calls insertRec() */
    public void insert(int key, String data) { 
       root = insertRec(root, key, data); 
    } 
      
    /* A recursive function to insert a new key in BST */
    private Node insertRec(Node root, int key, String data) { 
  
        /* If the tree is empty, return a new node */
        if (root == null) { 
            root = new Node(key, data); 
            return root; 
        } 
  
        /* If the key's are equal, append to that tree node */
        if(key == root.key && !isInNode(root.data, data)) {
            root.data += ("," + data);
            return root;
        }
        
        /* Otherwise, recur down the tree */
        if (key < root.key) 
            root.left = insertRec(root.left, key, data); 
        else if (key > root.key) 
            root.right = insertRec(root.right, key, data);
       
  
        /* return the (unchanged) node pointer */
        return root; 
    }
		
	
    /* This method mainly calls inorderRec() */
    public void printInorder(String outFile) throws IOException { 
    	BufferedWriter writer = new BufferedWriter(new FileWriter(outFile + ".inorder"));
        inorderRec(root, writer, 0); 
        writer.close();
    } 
 

    /* Traverses the binary tree, writing all data to 'outFile'.inorder */ 
    private void inorderRec(Node root, BufferedWriter writer, int indent) throws IOException { 
        if (root == null)
            return;
            
        inorderRec(root.left, writer, indent + 1); 

        writer.write(getOutputString(indent, root.key, root.data));
 
        inorderRec(root.right, writer, indent + 1); 
    } 
    
    
    /* This method mainly calls preorderRec() */
    public void printPreorder(String outFile) throws IOException { 
        BufferedWriter writer = new BufferedWriter(new FileWriter(outFile + ".preorder"));
        preorderRec(root, writer, 0); 
        writer.close();
    }
    

    /* Traverses the binary tree, writing all data to 'outFile'.preorder */
    private void preorderRec(Node root, BufferedWriter writer, int indent) throws IOException {
        if(root == null)
    	    return;
    
        writer.write(getOutputString(indent, root.key, root.data));
	
        preorderRec(root.left, writer, indent + 1);
        preorderRec(root.right, writer, indent + 1);
    }
    
    
    /* This method mainly calls postorderRec() */
    public void printPostorder(String outFile) throws IOException { 
        BufferedWriter writer = new BufferedWriter(new FileWriter(outFile + ".postorder"));
        postorderRec(root, writer, 0);
        writer.close();
    }
    
   
    /* Traverses the binary tree, writing all data to 'outFile'.postorder */ 
    private void postorderRec(Node root, BufferedWriter writer, int indent) throws IOException {
        if (root == null)
            return;
    	
        postorderRec(root.left, writer, indent + 1);
        postorderRec(root.right, writer, indent + 1);
   
        writer.write(getOutputString(indent, root.key, root.data)); 
    }


    /* This method will try to format an output string with the desired
     * amount of indention based off of node depth */
    private String getOutputString(int indent, int key, String data) {
        String output = "";
    	
        try{
            output = String.format("%"+indent+"s", " ") + key + " " + data + "\n";	
        }
        catch(FormatFlagsConversionMismatchException e){
            output = key + " " + data + "\n";
        }
    	
    	return output;
    }

 
    /* This method returns true if the data is contained within the
     * root data already. */   
    private Boolean isInNode(String rootData, String data) {
        String [] rootDataArray = rootData.split(",");
    	
        for(String rootStrings: rootDataArray)
            if(rootStrings.equals(data))
    	        return true;
    		
        return false;
    }

}

