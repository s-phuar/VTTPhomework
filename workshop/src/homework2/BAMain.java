package homework2;

public class BAMain {

    public static void main(String[] args){

        BankAccount daniel = new BankAccount("Daniel", 1000);

        System.out.printf("This bank account belongs to %s, with account number %s, account balance %s closesure status %s, created on %s\n", daniel.getName()
        , daniel.getAccountNumber(), daniel.getAccountBalance(), daniel.isClosureStatus(), daniel.getAccountClosure());

        daniel.Deposit(500);
        daniel.Withdraw(200);
        System.out.printf("This bank account belongs to %s, with account number %s, account balance %s closesure status %s, created on %s\n", daniel.getName()
        , daniel.getAccountNumber(), daniel.getAccountBalance(), daniel.isClosureStatus(), daniel.getAccountClosure());
    }
    
}
