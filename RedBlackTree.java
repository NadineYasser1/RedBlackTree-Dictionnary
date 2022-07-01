 
package rbTrees;

import java.io.*;
import java.util.Scanner;

public class RedBlackTree {
	private Node root;
	  private Node TNULL;

	  // Preorder
	  private void preOrderHelper(Node node) {
	    if (node != TNULL) {
	      System.out.print(node.data + " ");
	      preOrderHelper(node.left);
	      preOrderHelper(node.right);
	    }
	  }

	  // Inorder
	  private void inOrderHelper(Node node) {
	    if (node != TNULL) {
	      inOrderHelper(node.left);
	      System.out.print(node.data + " ");
	      inOrderHelper(node.right);
	    }
	  }

	  // Post order
	  private void postOrderHelper(Node node) {
	    if (node != TNULL) {
	      postOrderHelper(node.left);
	      postOrderHelper(node.right);
	      System.out.print(node.data + " ");
	    }
	  }

	  // Search the tree
	  private Node searchTreeHelper(Node node, String k) {
	    if (node == TNULL || k.equals(node.data)) {
	      return node;
	    }

	    if (k.compareTo(node.data)<0) {
	      return searchTreeHelper(node.left, k);
	    }
	    return searchTreeHelper(node.right, k);
	  }
public void searchRBTree(String k) {
	if(searchTree(k)!=TNULL) 
	System.out.println("YES");
	
	else System.out.println("NO");
}
	  // Balance the tree after deletion of a node
	  private void fixDelete(Node x) {
	    Node s;
	    while (x != root && x.color == 0) {
	      if (x == x.parent.left) {
	        s = x.parent.right;
	        if (s.color == 1) {
	          s.color = 0;
	          x.parent.color = 1;
	          leftRotate(x.parent);
	          s = x.parent.right;
	        }

	        if (s.left.color == 0 && s.right.color == 0) {
	          s.color = 1;
	          x = x.parent;
	        } else {
	          if (s.right.color == 0) {
	            s.left.color = 0;
	            s.color = 1;
	            rightRotate(s);
	            s = x.parent.right;
	          }

	          s.color = x.parent.color;
	          x.parent.color = 0;
	          s.right.color = 0;
	          leftRotate(x.parent);
	          x = root;
	        }
	      } else {
	        s = x.parent.left;
	        if (s.color == 1) {
	          s.color = 0;
	          x.parent.color = 1;
	          rightRotate(x.parent);
	          s = x.parent.left;
	        }

	        if (s.right.color == 0 && s.right.color == 0) {
	          s.color = 1;
	          x = x.parent;
	        } else {
	          if (s.left.color == 0) {
	            s.right.color = 0;
	            s.color = 1;
	            leftRotate(s);
	            s = x.parent.left;
	          }

	          s.color = x.parent.color;
	          x.parent.color = 0;
	          s.left.color = 0;
	          rightRotate(x.parent);
	          x = root;
	        }
	      }
	    }
	    x.color = 0;
	  }

	  private void rbTransplant(Node u, Node v) {
	    if (u.parent == null) {
	      root = v;
	    } else if (u == u.parent.left) {
	      u.parent.left = v;
	    } else {
	      u.parent.right = v;
	    }
	    v.parent = u.parent;
	  }

	  private void deleteNodeHelper(Node node, String key) {
	    Node z = TNULL;
	    Node x, y;
	    while (node != TNULL) {
	      if (node.data.equals(key)) {
	        z = node;
	      }

	      if (node.data.equals(key) || node.data.compareTo(key)<0) {
	        node = node.right;
	      } else {
	        node = node.left;
	      }
	    }

	    if (z == TNULL) {
	      System.out.println("Couldn't find key in the tree");
	      return;
	    }

	    y = z;
	    int yOriginalColor = y.color;
	    if (z.left == TNULL) {
	      x = z.right;
	      rbTransplant(z, z.right);
	    } else if (z.right == TNULL) {
	      x = z.left;
	      rbTransplant(z, z.left);
	    } else {
	      y = minimum(z.right);
	      yOriginalColor = y.color;
	      x = y.right;
	      if (y.parent == z) {
	        x.parent = y;
	      } else {
	        rbTransplant(y, y.right);
	        y.right = z.right;
	        y.right.parent = y;
	      }

	      rbTransplant(z, y);
	      y.left = z.left;
	      y.left.parent = y;
	      y.color = z.color;
	    }
	    if (yOriginalColor == 0) {
	      fixDelete(x);
	    }
	  }

	  // Balance the node after insertion
	  private void fixInsert(Node k) {
	    Node u;
	    while (k.parent.color == 1) {
	      if (k.parent == k.parent.parent.right) {
	        u = k.parent.parent.left;
	        if (u.color == 1) {
	          u.color = 0;
	          k.parent.color = 0;
	          k.parent.parent.color = 1;
	          k = k.parent.parent;
	        } else {
	          if (k == k.parent.left) {
	            k = k.parent;
	            rightRotate(k);
	          }
	          k.parent.color = 0;
	          k.parent.parent.color = 1;
	          leftRotate(k.parent.parent);
	        }
	      } else {
	        u = k.parent.parent.right;

	        if (u.color == 1) {
	          u.color = 0;
	          k.parent.color = 0;
	          k.parent.parent.color = 1;
	          k = k.parent.parent;
	        } else {
	          if (k == k.parent.right) {
	            k = k.parent;
	            leftRotate(k);
	          }
	          k.parent.color = 0;
	          k.parent.parent.color = 1;
	          rightRotate(k.parent.parent);
	        }
	      }
	      if (k == root) {
	        break;
	      }
	    }
	    root.color = 0;
	  }

	  private void printHelper(Node root, String indent, boolean last) {
	    if (root != TNULL) {
	      System.out.print(indent);
	      if (last) {
	        System.out.print("R----");
	        indent += "   ";
	      } else {
	        System.out.print("L----");
	        indent += "|  ";
	      }

	      String sColor = root.color == 1 ? "RED" : "BLACK";
	      System.out.println(root.data + "(" + sColor + ")");
	      printHelper(root.left, indent, false);
	      printHelper(root.right, indent, true);
	    }
	  }

	  public RedBlackTree() {
	    TNULL = new Node();
	    TNULL.color = 0;
	    TNULL.left = null;
	    TNULL.right = null;
	    root = TNULL;
	  }

	  public void preorder() {
	    preOrderHelper(this.root);
	  }

	  public void inorder() {
	    inOrderHelper(this.root);
	  }

	  public void postorder() {
	    postOrderHelper(this.root);
	  }

	  public Node searchTree(String k) {
	    return searchTreeHelper(this.root, k);
	  }

	  public Node minimum(Node node) {
	    while (node.left != TNULL) {
	      node = node.left;
	    }
	    return node;
	  }

	  public Node maximum(Node node) {
	    while (node.right != TNULL) {
	      node = node.right;
	    }
	    return node;
	  }

	  public Node successor(Node x) {
	    if (x.right != TNULL) {
	      return minimum(x.right);
	    }

	    Node y = x.parent;
	    while (y != TNULL && x == y.right) {
	      x = y;
	      y = y.parent;
	    }
	    return y;
	  }

	  public Node predecessor(Node x) {
	    if (x.left != TNULL) {
	      return maximum(x.left);
	    }

	    Node y = x.parent;
	    while (y != TNULL && x == y.left) {
	      x = y;
	      y = y.parent;
	    }

	    return y;
	  }

	  public void leftRotate(Node x) {
	    Node y = x.right;
	    x.right = y.left;
	    if (y.left != TNULL) {
	      y.left.parent = x;
	    }
	    y.parent = x.parent;
	    if (x.parent == null) {
	      this.root = y;
	    } else if (x == x.parent.left) {
	      x.parent.left = y;
	    } else {
	      x.parent.right = y;
	    }
	    y.left = x;
	    x.parent = y;
	  }

	  public void rightRotate(Node x) {
	    Node y = x.left;
	    x.left = y.right;
	    if (y.right != TNULL) {
	      y.right.parent = x;
	    }
	    y.parent = x.parent;
	    if (x.parent == null) {
	      this.root = y;
	    } else if (x == x.parent.right) {
	      x.parent.right = y;
	    } else {
	      x.parent.left = y;
	    }
	    y.right = x;
	    x.parent = y;
	  }

	  public void insert(String key) {
	    Node node = new Node();
	    node.parent = null;
	    node.data = key;
	    node.left = TNULL;
	    node.right = TNULL;
	    node.color = 1;

	    Node y = null;
	    Node x = this.root;
if(searchTree(key)!=TNULL) {
	System.out.println("ERROR:word already in the dictionnary!");
	return;
}
else {
	    while (x != TNULL) {
	      y = x;
	      if (node.data.compareTo(x.data) <0) {
	        x = x.left;
	      } else {
	        x = x.right;
	      }
	    }

	    node.parent = y;
	    if (y == null) {
	      root = node;
	    } else if (node.data.compareTo(y.data) < 0) {
	      y.left = node;
	    } else {
	      y.right = node;
	    }

	    if (node.parent == null) {
	      node.color = 0;
	      return;
	    }

	    if (node.parent.parent == null) {
	      return;
	    }

	    fixInsert(node);
	  }
	  }

	  public Node getRoot() {
	    return this.root;
	  }

	  public void deleteNode(String string) {
	    deleteNodeHelper(this.root, string);
	  }

	  public void printTree() {
	    printHelper(this.root, "", true);
	  }

	  static int maxLen;
	  static int maxHeight;
	     
	    // function to find the sum of nodes on the
	    // longest path from root to leaf node
	    public void treeHeight(Node root, int height, int len)
	    {
	        // if true, then we have traversed a
	        // root to leaf path
	        if (root == null) {
	            // update maximum length and maximum sum
	            // according to the given conditions
	            if (maxLen < len) {
	                maxLen = len;
	                maxHeight = height;
	            } else if (maxLen == len && maxHeight < height)
	                maxHeight = height;
	            return;
	        }
	         
	         
	        // recur for left subtree
	        treeHeight(root.left, height+1,len + 1);
	         //recur for right subtree
	        treeHeight(root.right, height+1,len + 1);
	         
	    }
	      
	    // utility function to find the sum of nodes on
	    // the longest path from root to leaf node
	    public int calculateTreeHeight()
	    {
	        // if tree is NULL, then sum is 0
	        if (root == null)
	            return 0;
	      
	        maxHeight = Integer.MIN_VALUE;
	        maxLen = 0;
	      
	        // finding the maximum sum 'maxHeight' for the
	        // maximum length root to leaf path
	        treeHeight(root, 0, 0);
	      
	        // required maximum Height
	        return maxHeight-1;
	    }

	    public int TreeSize(Node root){

	        //base case
	        if(root==TNULL)
	            return 0;

	        //recursive call to left child and right child and
	        // add the result of these with 1 ( 1 for counting the root)
	        else  return 1 + TreeSize(root.left) + TreeSize(root.right);
	    }
	    public int calculateTreeSize() {
	    	return TreeSize(this.root);
	    }
	  public static void main(String[] args) throws IOException {
		  
	    RedBlackTree bst = new RedBlackTree();

	    // pass the path to the file as a parameter
	    File file = new File("/Users/nadineyasser/Downloads/EN-US-Dictionary.txt/");
	    Scanner sc = new Scanner(file);
	    int count=0;
	 	    while (sc.hasNextLine()) {
	      bst.insert(sc.nextLine());
	      count=count+1;
	    }
	  //  bst.printTree();
	 		int x1;
	 	 System.out.println("ENGLISH DICTIONARY");
	 	 System.out.println("--------------------");
	 	do { System.out.println("PRESS:");
	 	System.out.println( "1 to print dictionnary size");
	 	System.out.println( "2 to height of resulting tree");
	 	System.out.println( "3 to print size of resulting tree");
	 	System.out.println( "4 to search for a word in the dictionary");
    	System.out.println( "5 to insert a word in the dictionary");
	 	System.out.println( "0 to EXIT");
	 	
	 	 
	 	Scanner sc1= new Scanner(System.in);
	 	int x= sc1.nextInt();
	 	switch(x) {
	 	case 1: System.out.println("Dictionnary Size="+count);
	 	break;
	 	case 2: System.out.println("tree Height ="  +bst.calculateTreeHeight());
	 	break;
	 	case 3: System.out.println("tree size ="  +bst.calculateTreeSize());
	 	break;
	 	case 4:
	 		System.out.println("Please enter the word you want to search for:");
	 		Scanner sc2= new Scanner(System.in);
	 		String s= sc2.nextLine();
	 		bst.searchRBTree(s);
	 		break;
	 	case 5:
	 		System.out.println("Please enter the word you want to insert:");
	 		Scanner sc3= new Scanner(System.in);
	 		String s1= sc3.nextLine();	
	 		bst.insert(s1);
	 		break;
	 	case 0: break;
	 	}
	 	 System.out.println("If you want to do any further operations PRESS 1");
	     System.out.println( "If you want to EXIT PRESS 0");
	 	Scanner sc4 =new Scanner(System.in);
	 x1= sc4.nextInt();
	
	 
	 	}while(x1==1);
	  }
	}


