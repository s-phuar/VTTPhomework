package homework2;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.Random;


public class BankAccount {
    
private final String name;
private final String accountNumber;
private float accountBalance;
private ArrayList<String> transactionList = new ArrayList<>();
private boolean closureStatus;
private final LocalDateTime accountCreation;
private LocalDateTime accountClosure;

Random rand = new Random();

//constructor, creates fresh bank account by name input, randomly generates account number
public BankAccount(String name){
    this.name = name;
    this.accountNumber = Integer.toString(rand.nextInt(1000));
    this.accountBalance = 0;
    this.closureStatus = false;
    this.accountCreation = LocalDateTime.now();

}

public BankAccount(String name, int accountBalance){
    this.name = name;
    this.accountNumber = Integer.toString(rand.nextInt(1000));
    this.accountBalance = accountBalance;
    this.closureStatus = false;
    this.accountCreation = LocalDateTime.now();

}


//getters and setters
public String getName() {return name;}

public String getAccountNumber() {return accountNumber;}

public float getAccountBalance() {return accountBalance;}
public void setAccountBalance(float accountBalance) {this.accountBalance = accountBalance;}

public ArrayList<String> getTransactionList() {return transactionList;}
public void setTransactionList(ArrayList<String> transactionList) {this.transactionList = transactionList;}

public boolean isClosureStatus() {return closureStatus;}
public void setClosureStatus(boolean closureStatus) {this.closureStatus = closureStatus;}


public LocalDateTime getAccountCreation() {return accountCreation;}

public LocalDateTime getAccountClosure() {return accountClosure;}
public void setAccountClosure(LocalDateTime accountClosure) {this.accountClosure = accountClosure;}

//Transaction list gets updated when depositing or withdrawing


//Deposit monet
public void Deposit(int money){
    if(money < 1 | this.closureStatus == true){
        throw new IllegalArgumentException("Input must be a positive number\n"); 
    }else{
    this.accountBalance += money;
    String formattedOutput = String.format("deposit $%d at %tT\n", money, LocalDateTime.now());
    System.out.println(formattedOutput);
    transactionList.add(formattedOutput);
    }    
}

//Withdraw money
public void Withdraw(int money){
    if(money < 1 | this.closureStatus == true | money > this.accountBalance){
        throw new IllegalArgumentException("Input must be a positive number\n"); 
    }else{
    this.accountBalance -= money;
    String formattedOutput = String.format("withdraw $%d at %tT\n", money, LocalDateTime.now());
    System.out.println(formattedOutput);
    transactionList.add(formattedOutput);
    }  

}

}
