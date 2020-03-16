import java.util.ArrayList;
import java.util.List;

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
        Node y = null; Node x = this.root;
        while ( x != null) {
            y = x;
            if (z.i.low <= x.i.low) {x = x.leftChild; }     // added <= so key lessthen or equal to gets put in left subtree
            else { x = x.rightChild; }
        }
        z.parent = y;
        if (y == null) {this.root = z; }
        else if (z.i.low < y.i.low) {y.leftChild = z; }
        else {y.rightChild = z; }                           // end of insertion
        while (z.parent.priority > z.priority) {
            y = z.parent; x = y.parent;
            if (y.leftChild == z){                          //rotate right
                if ( x.rightChild == y ){
                    y.leftChild = z.rightChild; z.rightChild.parent = y;
                    z.rightChild = y; y.parent = z;
                    x.rightChild = z; z.parent = x;
                }
                if (x.leftChild == y) {
                    y.leftChild = z.rightChild; z.rightChild.parent = y;
                    z.rightChild = y; y.parent = z;
                    x.leftChild = z; z.parent = x;
                }
            }
            if (y.rightChild == z){                      //rotate left
                if ( x.rightChild == y ){
                    y.rightChild = z.leftChild; z.leftChild.parent = y;
                    z.leftChild = y; y.parent = z;
                    x.rightChild = z; z.parent = x;
                }
                if (x.leftChild == y) {
                    y.rightChild = z.leftChild; z.leftChild.parent = y;
                    z.leftChild = y; y.parent = z;
                    x.leftChild = z; z.parent = x;
                }
            }
        }// end right left rotate
    }

    /**
     * removes node z from the interval treap.
     * @param z node to be deleted from treap
     */
    public void intervalDelete(Node z){
        Node y;
        if (z.leftChild == null) { transplant(this, z, z.rightChild); }
        else if (z.rightChild == null) {transplant(this, z, z.leftChild); }
        else {
            y = z.rightChild;
            while (y.leftChild != null) {
                y = y.leftChild;
            }
            if (y.parent != z) {
                transplant(this, y, y.rightChild);
                y.rightChild = z.rightChild;
                y.rightChild.parent = y;
            }
            transplant(this, z, y);
            y.leftChild = z.leftChild;
            y.leftChild.parent = y;
        }
    }

    /**
     * transplant replaces the subtree rooted at node u with the subtree rooted at node v.
     * @param T Tree that the transplant is taking place
     * @param u Node to be replaced
     * @param v Node to place in u's place
     */
    public void transplant(IntervalTreap T, Node u, Node v){
        if (u.parent == null) {
            T.root = v;
        }
        else if (u == u.parent.leftChild){
            u.parent.leftChild = v;
        }
        else{ u.parent.rightChild = v; }
        if (v != null) { v.parent = u.parent; }
    }

    /**
     * returns a reference to a node x in the interval treap such that x.interv overlaps interval i, or null if no such element is on the treap.
     * @param i interval looking for overlap
     * @return node containing interval that overlaps i
     */
    public Node intervalSearch(Interval i){
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
    public Node intervalSearchExactly(Interval i){  //extra credit             needs work still not done
        Node x = null;
        return x;
    }

    /**
     * reutrns a list all intervals in the treap that overlap i.
     * @param i interval looking for overlap
     * @return list of nodes who's intervals overlap i
     */
    public List<Interval> overlappingIntervals(Interval i){   //extra credit           needs work still not done
        List<Interval> x = new ArrayList<Interval>();
        return x;
    }
}
