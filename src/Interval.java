/**
 * Project 1 Team Members
 *  Christian Mains
 *   Sam Jungman
 */
public class Interval {

    int high;
    int low;

    /**
     * Interval is [low,high]  low and high is Real numbers,  low is less then or equal to high.
     * @param low low endpoint of this interval
     * @param high high endpoint of this interval
     */
    Interval(int low, int high){
        if(low <= high){
            this.low = low;
            this.high = high;
        }
        else{
            this.low = high;
            this.high = low;
        }
    }

    /**
     * Returns the low endpoint of the interval.
     * @return low endpoint
     */
    public int getLow(){ return this.low; }

    /**
     * Returns the high endpoint of the interval.
     * @return high endpoint
     */
    public int getHigh(){ return this.high; }

    /**
     * String representaion of this interval.
     * @return String
     */
    public String tostring(){
        return "[" + this.low + "," + this.high + "]";
    }

    /**
     * Returns true of intervals a and b overlap in any way, will return false if they do not overlap.
     * @param b interval to be compared
     * @return true boolean value of intervals overlap and false otherwise
     */
    public boolean intervalOverlap(Interval b){
        int al = this.low; int ah = this.high;
        int bl = b.low; int bh = b.high;
        if ( (al <= bl && ah >= bl) || (bl <= al && bh >= al) ) { return true; }
        if ( (al <= bh && ah >= bh) || (bl <= ah && bh >= ah) ) { return true; }
        if ( (al >= bl && ah <= bh) || (al <= bl && ah >= bh) ) { return true; }
        return false;
    }

    /**
     * Returns true of this interval equals i interval or false otherwise.
     * return true if this.low == i.low and this.high == i.high.
     * @param i Interval to be compared to this interval
     * @return boolean true if this interval is equal to i interval
     */
    public boolean intervalEquals(Interval i){
        if(this.low == i.low && this.high == i.high){
            return true;
        }
        return false;
    }
}
