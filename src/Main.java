/**
 * Project 1 Team Members
 * Christian Mains
 * Sam Jungman
 */
public class Main {

    public static void main(String[] args) {
        Interval x = new Interval(1, 2);
        Interval y = new Interval(2,3);
        Interval z = new Interval(3,4);
        Node a = new Node(y);
        Node b = new Node(x);
        Node c = new Node(z);
        IntervalTreap A = new IntervalTreap();
        A.intervalInsert(a);
        A.intervalInsert(b);
        A.intervalInsert(c);
        System.out.println(A.tostring());
    }
}
