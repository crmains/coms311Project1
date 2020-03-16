import java.util.Random;

public class Node {

    Interval i;
    int iMax;
    int priority;
    Node parent;
    Node leftChild;
    Node rightChild;

    /**
     * Node contains "Node parent" parent of this node, "Interval i" the key of this node,
     * "int iMax" the maximum value of any interval endpoint in the subtree rooted at x,
     * "int priority" priority of this node, "Node leftChild" this nodes left child,
     * "Node rightChild" this nodes right child.
     * @param i Interval of this new node
     */
    public Node(Interval i){
        Random dice = new Random();
        this.i = i;
        this.iMax = 0;
        this.priority = 1 + dice.nextInt(Integer.MAX_VALUE - 1);
        this.parent = null;
        this.leftChild = null;
        this.rightChild = null;
    }
    public String toString(){
        return "[" + this.i.toString() + ", " + this.iMax + ", " + this.priority + "]";
    }
    /**
     * Returns the parent of this node.
     * @return Parent of Node
     */
    public Node getParent(){ return this.parent; }

    /**
     * Returns the left child of this node.
     * @return left child of this node
     */
    public Node getLeft(){ return this.leftChild; }

    /**
     * Returns the right child of this node.
     * @return right child of this node
     */
    public Node getRight(){ return this.rightChild; }

    /**
     * Returns the interval object stored in this node.
     * @return interval object of this node
     */
    public Interval getInterv(){ return this.i; }

    /**
     * Returns the value of the imax field of this node.
     * @return iMax of this node
     */
    public int getIMax(){
        return this.iMax;
    }

    /**
     * Returns the priority of this node.
     * @return priority of this node
     */
    public int getPriority(){
        return this.priority;
    }
}
