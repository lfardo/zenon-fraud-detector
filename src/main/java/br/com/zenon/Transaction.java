package br.com.zenon.model;

import java.math.BigDecimal;

public class Transaction {

    int step;
    enum type {CASH_IN, CASH_OUT, DEBIT, PAYMENT, TRANSFER};
    BigDecimal amount;
    String nameOrig;
    BigDecimal oldbaanceOrg;
    BigDecimal newbalanceOrig;
    String nameDest;
    BigDecimal oldbalanceDest;
    BigDecimal newbalanceDest;
    boolean isFraud;
    boolean isFlaggedFraud;

    public Transaction(int step, type type, BigDecimal amount, String nameOrig, BigDecimal oldbaanceOrg, BigDecimal newbalanceOrig, String nameDest, BigDecimal oldbalanceDest, BigDecimal newbalanceDest, boolean isFraud, boolean isFlaggedFraud) {
        this.step = step;
        //Transaction.Type = type;
        this.amount = amount;
        this.nameOrig = nameOrig;
        this.oldbaanceOrg = oldbaanceOrg;
        this.newbalanceOrig = newbalanceOrig;
        this.nameDest = nameDest;
        this.oldbalanceDest = oldbalanceDest;
        this.newbalanceDest = newbalanceDest;
        this.isFraud = isFraud;
        this.isFlaggedFraud = isFlaggedFraud;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getNameOrig() {
        return nameOrig;
    }

    public void setNameOrig(String nameOrig) {
        this.nameOrig = nameOrig;
    }

    public BigDecimal getOldbaanceOrg() {
        return oldbaanceOrg;
    }

    public void setOldbaanceOrg(BigDecimal oldbaanceOrg) {
        this.oldbaanceOrg = oldbaanceOrg;
    }

    public BigDecimal getNewbalanceOrig() {
        return newbalanceOrig;
    }

    public void setNewbalanceOrig(BigDecimal newbalanceOrig) {
        this.newbalanceOrig = newbalanceOrig;
    }

    public String getNameDest() {
        return nameDest;
    }

    public void setNameDest(String nameDest) {
        this.nameDest = nameDest;
    }

    public BigDecimal getOldbalanceDest() {
        return oldbalanceDest;
    }

    public void setOldbalanceDest(BigDecimal oldbalanceDest) {
        this.oldbalanceDest = oldbalanceDest;
    }

    public BigDecimal getNewbalanceDest() {
        return newbalanceDest;
    }

    public void setNewbalanceDest(BigDecimal newbalanceDest) {
        this.newbalanceDest = newbalanceDest;
    }

    public boolean isFraud() {
        return isFraud;
    }

    public void setFraud(boolean fraud) {
        isFraud = fraud;
    }

    public boolean isFlaggedFraud() {
        return isFlaggedFraud;
    }

    public void setFlaggedFraud(boolean flaggedFraud) {
        isFlaggedFraud = flaggedFraud;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "step=" + step +
                ", amount=" + amount +
                ", nameOrig='" + nameOrig + '\'' +
                ", oldbaanceOrg=" + oldbaanceOrg +
                ", newbalanceOrig=" + newbalanceOrig +
                ", nameDest='" + nameDest + '\'' +
                ", oldbalanceDest=" + oldbalanceDest +
                ", newbalanceDest=" + newbalanceDest +
                ", isFraud=" + isFraud +
                ", isFlaggedFraud=" + isFlaggedFraud +
                '}';
    }
}
