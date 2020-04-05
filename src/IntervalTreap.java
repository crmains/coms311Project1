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

    /**
     * An interval treap that contains reference to root node, reference to size being number of nodes in this tree,
     * reference to height being number of levels in this tree where root node is level 0.
     */
    public IntervalTreap(){
        this.root = null;
        this.size = 0;
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
        return this.root.getheight();
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
        else if (z.i.low < y.i.low) {
            y.leftChild = z;
            y.setimax(); y.setHeight();
        }
        else {
            y.rightChild = z;
            y.setimax(); y.setHeight();
        }
                                                               // end of insertion
        while (y != null && y.priority > z.priority) {         //Start Rotation
            if (y.leftChild == z) {rightUpRotate(y, z);}
            if (y.rightChild == z) {leftUpRotate(y, z);}
            y = z.parent;
        }
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
        y.setHeight();
        reorder_Height(z);
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
        y.setHeight();
        reorder_Height(z);
    }

    /**
     * Reorders the height of each node form h to the root and sets tree height to new root height.
     * @param h Node
     */
    public void reorder_Height(Node h){
        while(h != null) {
            h.setHeight();
            h = h.parent;
        }
    }

    /**
     * removes node z from the interval treap.
     * @param z node to be deleted from treap
     */
    public void intervalDelete(Node z){  // Done but needs testing
        this.size -= 1;
        Node y;
        
        //PHASE 1
        if (z.leftChild == null) 
        {   //P1 Case 1
        	y = z.rightChild;
        	if(y == null)
        	{
        		transplant(z,y);
        	}
        	else
        	{
        		while (y.leftChild != null)
            	{
                	y = y.leftChild;
            	}
        		transplant(z, y); 
        	}
        	y = z.rightChild;
        } 
        else if (z.rightChild == null) 
        {  //P1 Case 2
        	y = z.leftChild;
        	if(y == null)
        	{
        		transplant(z,y);
        	}
        	else
        	{
        		while (y.rightChild != null)
            	{
                	y = y.rightChild;
            	}
        		transplant(z, y); 
        	}
        	y = z.leftChild;
        } 
        else 
        { //P1 Case 3
            y = z.rightChild;
            while (y.leftChild != null)
            {
                y = y.leftChild;
            }
            transplant(z, y);
        }
        //PHASE 2
        if(y != null)
        {
        	Node reorder = y;
        	while(!checkPriority(reorder))
        	{
        		//System.out.println(this.tostring());
        		if(reorder.leftChild == null)
        		{
        			rightUpRotate(reorder, reorder.rightChild);
        		}
        		else if(reorder.rightChild == null)
        		{
        			leftUpRotate(reorder, reorder.leftChild);
        		}
        		else
        		{
        			if(reorder.leftChild.getPriority() < reorder.rightChild.getPriority())
        			{
        				rightUpRotate(reorder, reorder.leftChild);
        			}
        			else 
        			{
        				leftUpRotate(reorder, reorder.rightChild);
        			}
        		}
        	}
        	root.setHeight();
        }
        
    }
    
    /**
     * Helper method for delete. Checks that the nodes beneath z have nondecreasing priority
     * @param z
     * @return boolean
     */
    private boolean checkPriority(Node z)
    {
    	if(z.leftChild == null && z.rightChild == null)
    		return true;
    	if(z.leftChild == null)
    		return (z.getPriority() <= z.rightChild.getPriority());
    	if(z.rightChild == null)
    		return (z.getPriority() <= z.leftChild.getPriority());
    	return (z.getPriority() <= z.leftChild.getPriority() && z.getPriority() <= z.rightChild.getPriority());
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
    		if(u.parent.leftChild == u)
    			u.parent.leftChild = null;
    		if(u.parent.rightChild == u)
    			u.parent.rightChild = null;
    	}
    	else
    	{
    		//Set u's parent's children = v
    		if(u != root)
    		{
    			if(u.parent.leftChild == u)
    				u.parent.leftChild = v;
    			if(u.parent.rightChild == u)
    				u.parent.rightChild = v;
    		}
    		else
    		{
    			this.root = v;
    		}
    		
    		//Set v's parent's children = v's right child or null
    		//NOTE: It is impossible for V to have a left child
    			if(v.rightChild == null)
    			{
    				if(v.parent.leftChild == v)
    					v.parent.leftChild = null;
    				if(v.parent.rightChild == v)
    					v.parent.rightChild = null;
    			}
    			else
    			{
    				if(v.parent.leftChild == v)
    					v.parent.leftChild = v.rightChild;
    				if(v.parent.rightChild == v)
    					v.parent.rightChild = v.rightChild;
    			}
    		//Set v's children to u's children
    		if(u.leftChild != v)
    			v.leftChild = u.leftChild;
    		if(u.rightChild != v)
    			v.rightChild = u.rightChild;
    		v.parent = u.parent;
    		v.setHeight();
    		
    		
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
     * @param x list of overlapping intervals
     * @return
     */
    private List<Interval> overlappingRecycle(Node n, Interval i, List<Interval> x){
        if(n.i.intervalOverlap(i)) {x.add(n.i); }
        if(n.leftChild != null) { overlappingRecycle(n.leftChild, i, x); }
        if(n.rightChild != null) { overlappingRecycle(n.rightChild, i, x); }
        return x;
    }
}
