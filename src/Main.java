
/**
 * This main is for testing and such.
 * Project 1 Team Members
 * Christian Mains
 * Sam Jungman
 */
public class Main {

    public static void main(String[] args) {
        //IntervalTreap A = generate_three_node_tree();
        IntervalTreap A = generate_tree_example();
        System.out.println(testing_intervalsearch(A, 31, 32));
        System.out.println(A.tostring());
    }

    public static IntervalTreap generate_three_node_tree(){
        Interval x = new Interval(1, 2);
        Interval y = new Interval(2, 3);
        Interval z = new Interval(3, 4);
        Node a = new Node(y);
        Node b = new Node(x);
        Node c = new Node(z);
        IntervalTreap A = new IntervalTreap();
        A.intervalInsert(a);
        A.intervalInsert(b);
        A.intervalInsert(c);
        return A;
    }

    public static IntervalTreap generate_tree_example() {
        int l = 0, p = 0;
        IntervalTreap A = new IntervalTreap();
        int[] interv = {16, 21, 8, 9, 25, 30, 5, 8, 15, 23, 17, 19, 26, 26, 0, 3, 6, 10, 19, 20};
        int[] prior = {8, 12, 10, 17, 16, 13, 11, 21, 20, 17};
        for(p = 0; p < 10; p++){
            Interval a = new Interval(interv[l], interv[l+1]);
            Node b = new Node(a);
            b.priority = prior[p];
            A.intervalInsert(b);
            l = l +2;
        }
        return A;
    }
    public static String testing_intervalsearch(IntervalTreap A, int l, int h){
        Interval in = new Interval(l, h);
        Node an = A.intervalSearch(in);
        if(an == null){return "this interval returns Node NULL \n";}
        return "This interval returns Node :" + an.tostring() + "\n";
    }
}