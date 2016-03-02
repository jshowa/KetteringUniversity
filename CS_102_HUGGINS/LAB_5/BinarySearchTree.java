import java.util.NoSuchElementException;

/**
 * Name: Jacob S. Howarth
 * Login ID: howa1643
 * CS-102, Fall 2008
 * Programming Assignment 5
 * BinarySearchTree class: Creates a binary search tree with TreeNodes storing
 * data of generic type.
 * @param T     Object of generic type
 */
public class BinarySearchTree<T extends Comparable<T>> {
    
    /* 
     * VARIABLE DECLARATIONS:
     *
     * TreeNode<T> root - The root/highest node in the tree hierarchy (ie.
     *                    parent node of the tree).
     */
     TreeNode<T> root = null;

     /**
      * Method: BinarySearchTree
      * Purpose: Constructs a tree with a null root, left and right child.
      * Parameters: None
      */
     public BinarySearchTree() {
        this.root = null;
    }
    
     /**
      * Method: BinarySearchTree
      * Purpose: Constructs a tree with a root containing a value and null
      *          left and right child nodes.
      * Parameters:
      * @param datum        Data of generic type that is stored in the nodes.
      */
    public BinarySearchTree(T datum) {
        root.setDatum(datum);
        root.setLeftChild(null);
        root.setRightChild(null);
    }
    
    /**
     * Method: search
     * Purpose: Searches the entire tree, searching the left and right
     *           subtrees of each parent and returns the first instance
     *           of an item as a readable string.
     * Parameters:
     * @param target       Search parameter of generic data type that's
     *                      being searched for.
     * Return:
     * @return result       A string containing the readable string of the
     *                      found object. 
     */
    public String search(T target) {
        TreeNode<T> current = root;
        String result = "";
        while (current != null) {
            if (current.getDatum().compareTo(target) == 0)
                return (result += current.getDatum().toString());
            if (current.getDatum().compareTo(target) < 0)
                current = current.getRightChild();
            else
                current = current.getLeftChild();
        }
        return result;
    }
    
    /**
     * Method: inOrderSearch
     * Purpose: Traverses the tree in-order returning readable strings
     *           of multiple objec instances.
     * Parameters:
     * @param target       Data of generic type being searched for
     * Return:
     * @return result      Multiple string instances of the target object
     *                     in the tree if there is repeatability in the data.
     */
    public String inOrderSearch(T target) {
        String result = "";
        result += inOrderSearch(root, target, result);
        return result;
    }
    private String inOrderSearch(TreeNode<T> current, T target, String result) {
        if (current == null) 
            return result;
        
        if (current.getLeftChild() != null)
            result = inOrderSearch(current.getLeftChild(), target, result);
        if (current.getDatum().compareTo(target) == 0)
            result += current.getDatum().toString() + "\n";
        if (current.getRightChild() != null)
            result = inOrderSearch(current.getRightChild(), target, result);
        
        return result;
    }
    
    /**
     * Method: add
     * Purpose: Add's a node with a generic data value in the appropriate
     *           place in the tree specified by a key value given as the 
     *           argument.
     * Parameters:
     * @param target        data of generic type stored in the nodes.
     * @param key           String to add the new node so the data in the
     *                      tree follows a specific ordering. 
     * Return:
     * N/A
     */
    public void add(T target, String key) {
        TreeNode<T> current = root;
        TreeNode<T> previous = null;
        
        while (current != null) {
            previous = current;
            
            if (current.getKey().compareTo(key) < 0)
                current = current.getRightChild();
            else
                current = current.getLeftChild();
        }
        
        TreeNode<T> splice = new TreeNode<T>(target, key);
        
        if (previous == null)
            root = splice;
        else if (previous.getKey().compareTo(key) < 0)
            previous.setRightChild(splice);
        else
            previous.setLeftChild(splice);  
    }
    
    /**
     * Method: delete
     * Purpose: Deletes a node in the tree
     * Parameters:
     * @param target    Object of generic type representing data to be 
     *                   deleted.   
     */
    public void delete (T target) {
        TreeNode<T> current = root;
        TreeNode<T> previous = null;
        
        while (current != null) {
            if (current.getDatum().compareTo(target) == 0)
                break;
            
            previous = current;
            
            if (current.getDatum().compareTo(target) < 0)
                current = current.getRightChild();
            else
                current = current.getLeftChild();
        }
        if (current == null)
            throw new NoSuchElementException();
        
        if (current.getRightChild() == null) {
            if (previous == null)
                root = current.getLeftChild();
            else if (previous.getLeftChild() == current)
                previous.setLeftChild(current.getLeftChild());
            else
                previous.setRightChild(current.getLeftChild());
        }
        else if (current.getLeftChild() == null) {
            if (previous == null)
                root = current.getRightChild();
            else if (previous.getLeftChild() == current)
                previous.setLeftChild(current.getRightChild());
            else
                previous.setRightChild(current.getRightChild());
        }
        else {
            TreeNode<T> copy = current.getLeftChild();
            TreeNode<T> prevCopy = current;
            
            while (copy.getRightChild() != null) {
                prevCopy = copy;
                copy = copy.getRightChild();
            }
            
            current.setDatum(copy.getDatum());
            if (prevCopy.getLeftChild() == copy)
                prevCopy.setLeftChild(copy.getLeftChild());
            else
                prevCopy.setRightChild(copy.getLeftChild());
        }
            
    }
    
    /**
     * Method: printPreOrder
     * Purpose: Returns a binary tree in pre-order taversal form.
     *           This traversal allows for recreation of the tree at
     *           anytime.
     * Parameters:
     * None
     * Return: 
     * @return result
     */
    public String printPreOrder() {
        String result = "";
        result += printPreOrder(root, result);
        return result;
    }
    private String printPreOrder(TreeNode<T> current, String result) {
        if (current == null) // base case 
            return result;
        
        // Process parent node first
        Object temp = current.getDatum();
        result += ((Airport)temp).toString("1") + "\n";
        
        // Then process left child traveling to the left bottom most node
        // of the tree first through recursive calling.
        if (current.getLeftChild() != null)
            result = printPreOrder(current.getLeftChild(), result);
        // Then process the right children traveling to the right bottom most 
        // node of the tree first through recursive calling.
        if (current.getRightChild() != null)
            result = printPreOrder(current.getRightChild(), result);
        
        return result;
    }
    
    /**
     * Method: printInOrder
     * Purpose: Prints the data of a tree in least to highest order. Also
     *           known as in-order traversal where the left child node is
     *           evaluated first, then the parent, then the right child node.
     * Parameters:
     * None
     * Return:
     * @return result       String containing the list tree data in order.
     */
    public String printInOrder() {
        String result = "";
        result += printInOrder(root, result);
        return result;
    }
    private String printInOrder(TreeNode<T> current, String result) {
        if (current == null) // base case
            return result;
        
        // Process left child first, traveling to the left bottom most node
        // of the tree through recursive calling.
        if (current.getLeftChild() != null)
            result = printInOrder(current.getLeftChild(), result);
        
        // Then process parent node of the above subtree.
        result += current.getDatum().toString() + "\n";
        
        // Process right children last, traveling to the right bottom most node
        // of the tree through recursive calling, then back up.
        if (current.getRightChild() != null)
            result = printInOrder(current.getRightChild(), result);
        
        return result;
    }
    
    
    
     
    
    
     
}

