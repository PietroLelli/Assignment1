package lab01.example.model;

public class SimpleBankAccountWithAtm implements BankAccount{
    private AccountHolder holder;
    private int balance;
    public SimpleBankAccountWithAtm(AccountHolder accountHolder, int balance) {
        this.holder = accountHolder;
        this.balance = balance;
    }

    @Override
    public AccountHolder getHolder() {
        return null;
    }

    @Override
    public double getBalance() {
        return this.balance;
    }

    @Override
    public void deposit(int userID, double amount) {
        if (checkUser(userID)) {
            this.balance += amount;
            this.balance--;
        }
    }

    @Override
    public void withdraw(int userID, double amount) {

    }
    private boolean checkUser(final int id) {
        return this.holder.getId() == id;
    }
}
