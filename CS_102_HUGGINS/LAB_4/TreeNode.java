/**
 * Jacob S. Howarth
 * Login ID: howa1643
 * CS-102, Fall 2008
 * Programming Assignment 4
 * TreeNode class: A blueprint for creating a TreeNode of generic type with
 * accessor and mutator methods for the nodes data, and left and right childs. 
 * @param T     Object of generic type used to specify datum type to be stored
 *              in each TreeNode.
 */
public class TreeNode<T extends Comparable<T>> {
    
    /* 
     * VARIABLE DECLARATIONS:
     * 
     * Generic Type:
     * T datum - Object of generic type to be stored in each TreeNode
     * 
     * TreeNode:
     * TreeNode<T> rightChild - Pointer to the right child TreeNode.
     * TreeNode<T> leftChild - Pointer to the left child TreeNode.
     *
     * String:
     * String key - represents the key value used to sort the airport objects
     *              in the tree when adding. The key can be two values:
     *                  1) The airport abbreviation
     *                  2) The airport name
     */ 
    T datum;
    TreeNode<T> rightChild;
    TreeNode<T> leftChild;
    String key;
    
    
    /**
     * Method: TreeNode
     * Purpose: This method creates an empty TreeNode with no datum and
     *          null left and right child pointers.
     * Parameters: N/A
     */
    public TreeNode() {
        this.datum = null;
        this.leftChild = null;
        this.rightChild = null;
    }

    /**
     * Method: TreeNode
     * Purpose: This method creates an empty TreeNode with a datum and
     *          null left and right child pointers. The datum is pasted
     *          as an argument.
     * Parameters:
     * @param datum             Object of generic type to be stored.
     */
    public TreeNode(T datum) {
        this.datum = datum;
        this.rightChild = null;
        this.leftChild = null;
    }
    
    /**
     * Method: TreeNode
     * Purpose: This method creates an empty TreeNode with a datum, sort key,
     *          null left and right child pointers. The datum and key are
     *          pasted as an argument.
     * Parameters:
     * @param datum             Object of generic type to be stored.
     * @param key     String containing the key value (either an airport 
     *                abbreviation or name), that the tree is sorted by.
     */
    public TreeNode(T datum, String key) {
        this.datum = datum;
        this.key = key;
    }
    
    /**
     * Method: getKey
     * Purpose: Accessor that returns the key value that a tree is sorted by
     *          The key can be two values:
     *                  1) The airport abbreviation
     *                  2) The airport name
     * Parameters:
     * @return key    String containing the key value (either an airport 
     *                abbreviation or name), that the tree is sorted by.
     */
    public String getKey() {
        return key;
    }
    
    /**
     * Method: setKey
     * Purpose: Mutator that sets the key value that the tree is sorted by
     *          for addition purposes. The key can be two values:
     *                  1) The airport abbreviation
     *                  2) The airport name
     * Parameters:
     * @param key     key value (either an airport abbreviation or name)
     *                that the tree is sorted by.
     */
    public void setKey(String key) {
        this.key = key;
    }
    
    /**
     * Method: getDatum
     * Purpose: Accessor that returns the generic type datum stored in 
     *          each TreeNode.
     * Parameters:
     * @return datum     Object of generic type stored in the TreeNode
     */
    public T getDatum() {
        return datum;
    }

    /**
     * Method: setDatum
     * Purpose: Mutator that sets/stores the generic type datum in each
     *          TreeNode.
     * @param datum      Object of generic type to be stored in the TreeNode.
     */
    public void setDatum(T datum) {
        this.datum = datum;
    }

    /**
     * Method: getLeftChild
     * Purpose: Accessor that returns the left child pointer of the parent
     *          TreeNode.
     * @return leftChild    left child node pointer.
     */
    public TreeNode<T> getLeftChild() {
        return leftChild;
    }

    /**
     * Method: setLeftChild
     * Purpose: Mutator that sets the left child pointer of the parent
     *          TreeNode.
     * Parameters:
     * @param leftChild    left child node pointer.
     */
    public void setLeftChild(TreeNode<T> leftChild) {
        this.leftChild = leftChild;
    }

    /**
     * Method: getRightChild
     * Purpose: Accessor that returns the right child pointer of the parent
     *          TreeNode.
     * @return rightChild    right child node pointer.
     */
    public TreeNode<T> getRightChild() {
        return rightChild;
    }

    /**
     * Method: setRightChild
     * Purpose: Mutator that sets the right child pointer of the parent
     *          TreeNode.
     * Parameters:
     * @param rightChild    right child node pointer.
     */
    public void setRightChild(TreeNode<T> rightChild) {
        this.rightChild = rightChild;
    }
    
    
    

}
