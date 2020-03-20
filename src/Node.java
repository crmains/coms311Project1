import java.util.Random;

/**
 * Project 1 Team Members
 *  Christian Mains
 *   Sam Jungman
 */
public class Node {

    public Interval i;
    public int iMax;
    public int priority;
    public int height;
    public Node parent;
    public Node leftChild;
    public Node rightChild;

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
        this.iMax = i.high;
        this.priority = 1 + dice.nextInt(Integer.MAX_VALUE - 1);
        this.height = 0;
        this.parent = null;
        this.leftChild = null;
        this.rightChild = null;
    }

    /**
     * Returns string representation of this node.
     * @return String
     */
    public String toString(){
        return "[" + this.i.toString() + ", " + this.iMax + ", " + this.priority + "]";
    }

    /**
     * Re-evaluates the iMax starting at this node and goes to root evaluating iMax.
     */
    public void setimax(){
        Node next = this;
        while(next != null){
            next.iMax = next.i.high;
            if (next.leftChild != null) { next.iMax = max(next.leftChild.iMax, next.iMax); }
            if (next.rightChild != null){ next.iMax = max(next.rightChild.iMax, next.iMax); }
            next = next.parent;
        }
    }

    /**
     *  Returns the greater of the two integers.
     * @param a integer
     * @param b integer
     * @return the greater of the two integer
     */
    public int max(int a, int b) {
        if (a > b) {return a; }
        else {return b; }
    }

    /**
     * Sets the height of the node.
     */
    public void setheight(){
        if (this.parent == null){this.height = 0; }
        else { this.height = this.parent.height + 1; }
    }

    /**
     * Returns the height of this node.
     * @return height
     */
    public int getheight(){ return this.height; }
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
