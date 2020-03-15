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
    public void intervalInsert(Node z){                    // needs work still not done
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
        }// end right left shift
    }

    /**
     * removes node z from the interval treap.
     * @param z node to be deleted from treap
     */
    public void intervalDelete(Node z){       //  needs work still not done

    }

    /**
     * returns a reference to a node x in the interval treap such that x.interv overlaps interval i, or null if no such element is on the treap.
     * @param i interval looking for overlap
     * @return node containing interval that overlaps i
     */
    public Node intervalSearch(Interval i){  // should be done
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
