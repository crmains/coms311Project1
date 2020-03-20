import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *  Project 1 Team Members
 *   Christian Mains
 *    Sam Jungman
 */
public class IntervalTreap {

    Node root;
    int size;
    int height;

    /**
     * An interval treap that contains reference to root node, reference to size being number of nodes in this tree,
     * reference to height being number of levels in this tree where root node is level 0.
     */
    public IntervalTreap(){
        this.root = null;
        this.size = 0;
        this.height = 0;
    }

    /**
     * This should print out the tree with nodes in there own levels starting with root at level 0.
     * This is for testing so we can see the tree.
     * @return String of the tree with nodes in there proper level
     */
    public String tostring(){
        if (this.root == null) { return "NULL"; }
        Queue<Node> first = new LinkedList<Node>();
        Queue<Node> second = new LinkedList<Node>();
        String answer = ""; Node f;
        first.add(this.root);
        while (!first.isEmpty() || !second.isEmpty()){
            while (!first.isEmpty()){
                f = first.poll();
                answer = answer + f.toString() + ", ";
                if (f.leftChild != null) {second.add(f.leftChild); }
                if (f.rightChild != null) {second.add(f.rightChild); }
            }
            answer = "\n";
            while (!second.isEmpty()){
                f = second.poll();
                answer = answer + f.toString() + ", ";
                if (f.leftChild != null) {first.add(f.leftChild); }
                if (f.rightChild != null) {first.add(f.rightChild); }
            }
            answer = "\n";
        }
        return answer;
    }

    /**
     * Returns a reference to the root node.
     * @return reference to root node
     */
    public Node getRoot(){
        return this.root;
    }

    /**
     * Returns the number of Nodes in the treap.
     * @return number of nodes in treap
     */
    public int getSize(){
        return this.size;
    }

    /**
     * Returns the height of the treap.
     * @return number of treap levels
     */
    public int getHeight(){
        return this.height;
    }

    /**
     * adds node z, whose interv attribute references an Interval object, to the interval treap.
     * @param z node to be added to treap
     */
    public void intervalInsert(Node z){                    // needs testing and fine tuning
        this.size += 1; int h = 0;
        Node y = null;
        Node x = this.root;
        while ( x != null) {
            y = x;
            if (z.i.low < x.i.low) {x = x.leftChild; }
            else { x = x.rightChild; }
        }
        z.parent = y;
        if (y == null) {this.root = z; }
        else if (z.i.low < y.i.low) {y.leftChild = z; }
        else {
            y.rightChild = z;
            y.setimax();
        }                                                    // end of insertion
        while (y != null && y.priority > z.priority) {         //Start Rotation
            if (y.leftChild == z) {rightUpRotate(y, z);}
            if (y.rightChild == z) {leftUpRotate(y, z);}
            y = z.parent;
        }
        if(y != null){h = y.getheight() + 1;}
        this.height = max(reorder_height(z, h), this.height);//reorder height at and under z
    }
    private void rightUpRotate(Node y, Node z){
        if (z.rightChild != null) {
            y.leftChild = z.rightChild;
            y.leftChild.parent = y;
        }
        z.parent = y.parent;
        z.rightChild = y;                 // "collecting data" i dont know whats wrong hear
        y.parent = z;
        y.setimax(); z.setimax();
        if (z.parent != null) {
            if (z.parent.leftChild == y) { z.parent.leftChild = z; }
            if (z.parent.rightChild == y) {z.parent.rightChild = z; }
            z.parent.setimax();
        }
    }
    private void leftUpRotate(Node y, Node z){
        if (z.leftChild != null) {
            y.rightChild = z.leftChild;
            y.rightChild.parent = y;
        }
        z.parent = y.parent;
        z.leftChild = y;                // "collecting data" i dont know whats wrong hear
        y.parent = z;
        y.setimax(); z.setimax();
        if (z.parent != null) {
            if (z.parent.leftChild == y) { z.parent.leftChild = z; }
            if (z.parent.rightChild == y) {z.parent.rightChild = z; }
            z.parent.setimax();
        }
    }

    /**
     * reorders height setting of x and all nodes under x. maxheight must be the current height of x or method
     * will not work. only called by insert and delete.
     * @param x Node
     * @param maxheight current height of x
     * @return The height of this subtree rooted at x.
     */
    private int reorder_height(Node x, int maxheight) {
        x.setHeight(maxheight);
        if(x.leftChild != null){maxheight = max(reorder_height(x.leftChild, maxheight +1), maxheight);}
        if(x.rightChild != null){maxheight = max(reorder_height(x.rightChild, maxheight +1), maxheight);}
        return maxheight;
    }

    /**
     * Helper method, returns greater of two integers.
     * @param a integer
     * @param b integer
     * @return greater of two integers
     */
    private int max(int a, int b){
        if(a > b){return a;}
        else{ return b;}
    }
    /**
     * removes node z from the interval treap.
     * @param z node to be deleted from treap
     */
    public void intervalDelete(Node z){  // not done needs work
        this.size -= 1;
        Node y;
        if (z.leftChild == null) { transplant(z, z.rightChild); }
        else if (z.rightChild == null) {transplant(z, z.leftChild); }
        else {
            y = z.rightChild;
            while (y.leftChild != null) {
                y = y.leftChild;
            }
            if (y.parent != z) {
                transplant(y, y.rightChild);
                y.rightChild = z.rightChild;
                y.rightChild.parent = y;
            }
            transplant(z, y);
            y.leftChild = z.leftChild;
            y.leftChild.parent = y;
        }
    }

    /**
     * transplant replaces the subtree rooted at node u with the subtree rooted at node v.
     * @param u Node to be replaced
     * @param v Node to place in u's place
     */
    public void transplant(Node u, Node v){

    }

    /**
     * returns a reference to a node x in the interval treap such that x.interv overlaps interval i, or null if no such element is on the treap.
     * @param i interval looking for overlap
     * @return node containing interval that overlaps i
     */
    public Node intervalSearch(Interval i){  //needs tested
        Node x = this.root;
        while (x != null && x.getInterv().intervalOverlap(i) ){
            if ( x.leftChild != null && x.leftChild.iMax >= i.low ) { x = x.leftChild; }
            else { x = x.rightChild; }
        }
        return x;
    }

    /**
     * Returns a reference to a Node object x in the treap such that x.interval.low = i.low and x.interv.high = i.high, or null if no such node exists.
     * @param i exact interval you are looking for
     * @return the node containing interval you are looking for
     */
    public Node intervalSearchExactly(Interval i){  //extra credit
        Node x = null;
        return x;
    }

    /**
     * reutrns a list all intervals in the treap that overlap i.
     * @param i interval looking for overlap
     * @return list of nodes who's intervals overlap i
     */
    public List<Interval> overlappingIntervals(Interval i){   //extra credit
        List<Interval> x = new ArrayList<Interval>();
        return x;
    }
}
