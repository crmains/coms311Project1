import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * This main is for getto testing and such.
 * Project 1 Team Members
 * Christian Mains
 * Sam Jungman
 */

public class Main {


    public static void main(String[] args) {
        //IntervalTreap A = generate_random_tree(100);
        IntervalTreap A = generate_tree_example();
        //System.out.println(testing_intervalsearch(A, 9000, 9001));
        //System.out.println(testing_intervalSearchExactly(A, 7, 25));
        //System.out.println(testing_overlappingIntervals(A, 16, 21));
        //System.out.println("This treap priority is "+ check_treap_priority(A.getRoot(), true));
        //System.out.println("This treap imax is "+ (check_treap_imax(A.root, null)).tostring());
        System.out.print(A.tostring());
        //System.out.println(testing_intervalDelete(A,25,30));
        //System.out.println(testing_intervalDelete(A,26,26));
        //System.out.println("The size of this TREE is: "+A.size);
        //System.out.println("The height of this TREE is: "+A.getHeight());
    }
                               // generates treap with N nodes
    public static IntervalTreap generate_random_tree(int N){
        Random dice = new Random(); int n = 0;
        IntervalTreap A = new IntervalTreap();
        for(n = 0; n < N; n++){
            Interval a = new Interval(dice.nextInt(10000), dice.nextInt(10000));
            Node b = new Node(a);
            A.intervalInsert(b);
        }
        return A;
    }
                  // generates example treap from the project 1 description document
    public static IntervalTreap generate_tree_example() {
        int l = 0, p = 0;
        IntervalTreap A = new IntervalTreap();
        int[] interv = {16, 21, 8, 9, 25, 30, 5, 8, 15, 23, 17, 19, 26, 26, 0, 3, 6, 10, 19, 20, 7, 25};
        int[] prior = {8, 12, 10, 17, 16, 13, 11, 21, 20, 17, 9};
        for(p = 0; p < 11; p++){
            Interval a = new Interval(interv[l], interv[l+1]);
            Node b = new Node(a);
            b.priority = prior[p];
            A.intervalInsert(b);
            l = l +2;
        }
        return A;
    }
                                       // tests intervalsearch  [l, h] makes the interval to be searched
    public static String testing_intervalsearch(IntervalTreap A, int l, int h){
        Interval in = new Interval(l, h);
        Node an = A.intervalSearch(in);
        if(an == null){return "This interval returns Node NULL \n";}
        return "This interval returns Node :" + an.tostring();
    }
                                          // tests intervalSearchExactly  [l, h] makes the interval to be searched
    public static String testing_intervalSearchExactly(IntervalTreap A, int l, int h){
        Interval in = new Interval(l, h);
        Node an = A.intervalSearchExactly(in);
        if(an == null){return "This interval returns Node NULL \n";}
        return "This interval returns Node :" + an.tostring();
    }
                                        // tests overlappingIntervals  [l, h] makes the interval to be searched
    public static String testing_overlappingIntervals(IntervalTreap A, int l, int h){
        List<Interval> an = new ArrayList<>(); int i;
        Interval in = new Interval(l, h);
        an = A.overlappingIntervals(in);
        if(an == null){return "This interval returns List NULL \n";}
        if(an.isEmpty()){return "This interval returns List EMPTY \n";}
        String answer = "This interval return List: ";
        for(i = 0; i < an.size(); i++){
            Interval th = an.get(i);
            answer += th.tostring() + ", ";
        }
        return answer + "\n";
    }
                                      // tests intervalDelete, Node containing interval [l, h] is the node to be deleted
    public static String testing_intervalDelete(IntervalTreap A, int l, int h){
        Interval in = new Interval(l, h);
        Node an = A.intervalSearchExactly(in);
        if(an == null){return"THIS INTERVAL IS NOT A VALID NODE, YOU MUST HAVE A VALID INTERVAL\n"; }
        String answer = "This is the tree with with the node:" + an.tostring() +" deleted:\n";
        A.intervalDelete(an);
        return answer + A.tostring();
    }
    public static boolean check_treap_priority(Node current, boolean answer){
        if(current.leftChild != null){
            if(current.priority > current.leftChild.priority){ return false; }
            answer = check_treap_priority(current.leftChild, answer);
        }
        if(current.rightChild != null){
            if(current.priority > current.rightChild.priority){ return false; }
            answer = check_treap_priority(current.rightChild, answer);
        }
        return answer;
    }
    public static Node check_treap_imax(Node current, Node answer){
        if(current == null){ return answer; }
        int imax = current.i.high;
        if(current.leftChild != null){
            if(current.leftChild.iMax > imax){ imax = current.leftChild.iMax; }
        }
        if(current.rightChild != null){
            if(current.rightChild.iMax > imax){ imax = current.rightChild.iMax; }
        }
        if(current.iMax != imax){ return current; }
        answer = check_treap_imax(current.leftChild, answer);
        answer = check_treap_imax(current.rightChild, answer);
        return answer;
    }
}