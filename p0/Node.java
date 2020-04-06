public class Node {
    int key;
    String data = "";
    Node left;
    Node right;
	
    Node(int key, String data){
        this.key = key;
        this.data = data;
        right = null;
        left = null;
    }
}
