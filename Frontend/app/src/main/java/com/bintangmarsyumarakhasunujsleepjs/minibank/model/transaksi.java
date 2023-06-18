package com.bintangmarsyumarakhasunujsleepjs.minibank.model;

public class transaksi {
    private int transaction_id;
    private String sender_id;
    private String recipient_id;
    private String amount;
    private String transaction_date;
    private String transaction_type;
    private String type;

    public String setType(){
        if (transaction_type.equals("1")){
            type = "Transfer";
        } else if (transaction_type.equals("2")){
            type = "Deposit";
        } else if (transaction_type.equals("3")){
            type = "Withdraw";
        }
        return type;
    }
    // if transaction type = 1 then type = Transfer

    // if transaction type = 2 then type = Deposit
    // if transaction type = 3 then type = Withdraw

    public String toString() {
        String type = "";
        if (transaction_type.equals("1")) {
            type = "Transfer";
        } else if (transaction_type.equals("2")) {
            type = "Deposit";
        } else if (transaction_type.equals("3")) {
            type = "Withdraw";
        }
        return "Transaction ID: " + transaction_id + "\nSender ID: " + sender_id + "\nRecipient ID: " + recipient_id + "\nAmount: " + amount + "\nTransaction Date: " + transaction_date + "\nTransaction Type: " + type;
    }


}
