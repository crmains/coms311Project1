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
                answer += f.tostring() + ", ";
                if (f.leftChild != null) {second.add(f.leftChild); }
                if (f.rightChild != null) {second.add(f.rightChild); }
            }
            answer += "\n";
            while (!second.isEmpty()){
                f = second.poll();
                answer += f.tostring() + ", ";
                if (f.leftChild != null) {first.add(f.leftChild); }
                if (f.rightChild != null) {first.add(f.rightChild); }
            }
            answer += "\n";
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
    public void intervalInsert(Node z){
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
        if(y != null){h = y.getheight() + 1;}         //reorder height of nodes at and under z
        h = reorder_height(z, h);
        if(h > this.height){ this.height = h;}
    }

    /**
     * Helper method to intervalInsert. will rotate z up to the right and y down.
     * @param y node to rotate down to the right
     * @param z node to rotate up to the right
     */
    private void rightUpRotate(Node y, Node z){
        if (z.rightChild != null) {               //if z has a right child
            y.leftChild = z.rightChild;
            y.leftChild.parent = y;
        }
        else{y.leftChild = null;}
        z.parent = y.parent;              // rotate z up and y down
        z.rightChild = y;
        y.parent = z;
        y.setimax(); z.setimax();
        if(z.parent == null){this.root = z;}           // if y has a parent or is root
        else{
            if (z.parent.leftChild == y) { z.parent.leftChild = z; }
            if (z.parent.rightChild == y) {z.parent.rightChild = z; }
            z.parent.setimax();
        }
    }

    /**
     * Helper method to intervalInsert. will rotate z up to the left and y down.
     * @param y node to rotate down to the left
     * @param z node to rotate up to the left
     */
    private void leftUpRotate(Node y, Node z){
        if (z.leftChild != null) {               //if z has a left child
            y.rightChild = z.leftChild;
            y.rightChild.parent = y;
        }
        else{y.rightChild = null;}
        z.parent = y.parent;                   // rotate z up and y down
        z.leftChild = y;
        y.parent = z;
        y.setimax(); z.setimax();
        if(z.parent == null){this.root = z;}      // if y has a parent or is root
        else{
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
        x.setHeight(maxheight); int l = 0, r = 0;
        if(x.leftChild != null){ l = reorder_height(x.leftChild, maxheight +1);}
        if(x.rightChild != null){ r = reorder_height(x.rightChild, maxheight +1);}
        if(l != 0 || r != 0){
            if(l > r){return l;}
            else{return r;}
        }
        return maxheight;
    }

    /**
     * removes node z from the interval treap.
     * @param z node to be deleted from treap
     */
    public void intervalDelete(Node z) {         // not done needs work

    }

    /**
     * returns a reference to a node x in the interval treap such that x.interv overlaps interval i, or null if no such element is on the treap.
     * @param i interval looking for overlap
     * @return node containing interval that overlaps i
     */
    public Node intervalSearch(Interval i){
        Node x = this.root;
        while (x != null && !x.getInterv().intervalOverlap(i) ){
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
        Node x = this.root;
        while(x != null && !x.i.intervalEquals(i) ){
            if ( x.leftChild != null && i.low < x.i.low ) { x = x.leftChild; }
            else { x = x.rightChild; }
        }
        return x;
    }

    /**
     * reutrns a list all intervals in the treap that overlap i.
     * @param i interval looking for overlap
     * @return list of nodes who's intervals overlap i
     */
    public List<Interval> overlappingIntervals(Interval i){   //extra credit
        List<Interval> x = new ArrayList<Interval>();
        Node n = this.root;
        x = overlappingRecycle(n, i, x);
        return x;
    }

    /**
     * Helper method for overlappingIntervals Recursively calls each node and if interval overlaps i adds
     * interval to list.
     * @param n current node being looked at
     * @param i Interval of interest
     * @param x list of overlaping intervals
     * @return
     */
    private List<Interval> overlappingRecycle(Node n, Interval i, List<Interval> x){
        if(n.i.intervalOverlap(i)) {x.add(n.i); }
        if(n.leftChild != null) { overlappingRecycle(n.leftChild, i, x); }
        if(n.rightChild != null) { overlappingRecycle(n.rightChild, i, x); }
        return x;
    }
}
