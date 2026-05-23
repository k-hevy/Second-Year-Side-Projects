package banking.model;


import java.sql.Timestamp;

public class Transaction {

    private int transactionID;
    private int senderAccount;
    private int receiverAccount;
    private String type;
    private double amount;
    private Timestamp transactionDate;

    public Transaction(int transactionID, int senderAccount, int receiverAccount, String type, double amount, Timestamp transactionDate) {
        this.transactionID = transactionID;
        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;
        this.type = type;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public int getTransactionID () {
        return transactionID;
    }

    public int getSenderAccount () {
        return senderAccount;
    }

    public int getReceiverAccount () {
        return receiverAccount;
    }

    public String getType () {
        return type;
    }

    public double getAmount () {
        return amount;
    }

    public Timestamp getTransactionDate () {
        return transactionDate;
    }
}
