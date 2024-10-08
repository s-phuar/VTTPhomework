package homework2;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.Random;


public class FixedDepositAccount {

    private final String name;
    private final String accountNumber;
    private float accountBalance;
    private ArrayList<String> transactionList = new ArrayList<>();
    private boolean closureStatus;
    private final LocalDateTime accountCreation;
    private LocalDateTime accountClosure;
    private float interest = 3;
    private int duration = 6;
    
    //counters for number of times interest/duration was set
    int setDuration = 0;
    int setInterest = 0;
    
    Random rand = new Random();

    //3 different constructors
    public FixedDepositAccount(String name, float accountBalance){
        this.name = name;
        this.accountNumber = Integer.toString(rand.nextInt(1000));
        this.accountBalance = accountBalance;
        this.closureStatus = false;
        this.accountCreation = LocalDateTime.now();
    }
    public FixedDepositAccount(String name, float accountBalance, float interest){
        this.name = name;
        this.accountNumber = Integer.toString(rand.nextInt(1000));
        this.accountBalance = accountBalance;
        this.closureStatus = false;
        this.accountCreation = LocalDateTime.now();
        this.interest = interest;
    }
    public FixedDepositAccount(String name, float accountBalance, float interest, int duration){
        this.name = name;
        this.accountNumber = Integer.toString(rand.nextInt(1000));
        this.accountBalance = accountBalance;
        this.closureStatus = false;
        this.accountCreation = LocalDateTime.now();
        this.interest = interest;
        this.duration = duration;
    }

    //getter and setter
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

    //methods
    public void Despoit(int money){
        //Deposit monet that does NOTHING

    }

    public void Withdraw(int money){
        //Withdraw money that does NOTHING
    }



}
