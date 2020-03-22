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
        if(y != null){h = y.getheight() + 1;} //reorder height at and under z
        h = reorder_height(z, h);
        if(h > this.height){ this.height = h;}
    }
    private void rightUpRotate(Node y, Node z){
        if (z.rightChild != null) {
            y.leftChild = z.rightChild;
            y.leftChild.parent = y;
        }
        else{y.leftChild = null;}
        z.parent = y.parent;
        z.rightChild = y;                 // "collecting data" i dont know whats wrong here
        y.parent = z;
        y.setimax(); z.setimax();
        if(z.parent == null){this.root = z;}
        else{
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
        else{y.rightChild = null;}
        z.parent = y.parent;
        z.leftChild = y;                // "collecting data" i dont know whats wrong here
        y.parent = z;
        y.setimax(); z.setimax();
        if(z.parent == null){this.root = z;}
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
    public void intervalDelete(Node z){  // not done needs work
        this.size -= 1;
        Node y;
        //PHASE 1
        if (z.leftChild == null) { transplant(z, z.rightChild); } //P1 Case 1
        else if (z.rightChild == null) {transplant(z, z.leftChild); } //P1 Case 2
        else { //P1 Case 3
            y = z.rightChild;
            while (y.leftChild != null) {
                y = y.leftChild;
            }
            transplant(z, y);
        }
        
        //PHASE 2
        
        
    }

    /**
     * transplant replaces the subtree rooted at node u with the subtree rooted at node v.
     * @param u Node to be replaced
     * @param v Node to place in u's place
     */
    public void transplant(Node u, Node v)
    {
    	if(v == null) //NULL CASE
    	{
    		if(u.parent.leftChild.equals(u))
    			u.parent.leftChild = null;
    		if(u.parent.rightChild.equals(u))
    			u.parent.rightChild = null;
    	}
    	else
    	{
    		//Set u's parent's children = v
    		if(u != root)
    		{
    			if(u.parent.leftChild.equals(u))
    				u.parent.leftChild = v;
    			if(u.parent.rightChild.equals(u))
    				u.parent.rightChild = v;
    		}
    		
    		//Set v's parent's children = v's right child or null
    		//NOTE: It is impossible for V to have a left child
    		if(v.rightChild == null)
    		{
    			if(v.parent.leftChild.equals(v))
    				u.parent.leftChild = null;
    			if(v.parent.rightChild.equals(v))
    				u.parent.rightChild = null;
    		}
    		else
    		{
    			if(v.parent.leftChild.equals(v))
    				u.parent.leftChild = v.rightChild;
    			if(v.parent.rightChild.equals(v))
    				u.parent.rightChild = v.rightChild;
    		}
    		
    		//Set v's children to u's children
    		v.leftChild = u.leftChild;
    		v.rightChild = u.rightChild;
    		
    		
    		//Remove u and return
    		u = null;
    		return;
    		
    	}
    }

    /**
     * returns a reference to a node x in the interval treap such that x.interv overlaps interval i, or null if no such element is on the treap.
     * @param i interval looking for overlap
     * @return node containing interval that overlaps i
     */
    public Node intervalSearch(Interval i){  //needs tested
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
