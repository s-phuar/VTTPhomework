package homework2;



public class FixedDepositAccount {

    private float interest = 3;
    private int duration = 6;
    
    int setDuration = 0;
    int setInterest = 0;
    
    
    public float getInterest() {return interest;}
    public void setInterest(float interest) {
        setInterest ++;
        this.interest = interest;
    }
    
    public int getDuration() {return duration;}
    public void setInterest(int duration) {
        setDuration ++;
        this.duration = duration;
    }

    
}
