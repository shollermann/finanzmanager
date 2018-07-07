package finanzmanager.model;

import java.util.Date;

public class Transaction {

    private boolean additive;
    private Long value;
    private String description;
    private Date date;
    private enum TransactionType {SINGLE, DAILY, WEEKLY, MONTHLY, YEARLY};


    private String getTransactionText(TransactionType transactionType){
        switch (transactionType){
            case SINGLE:
                return "Einzelbuchung";

            case DAILY:
                return "Tägliche Buchung";

            case WEEKLY:
                return "Wöchentliche Buchung";

            case MONTHLY:
                return "Monatliche Buchung";

            case YEARLY:
                return "Jährliche Buchung";

                default:
                    return "Einzelbuchung";
        }

    }

    public void setAdditive(boolean additive) {
        this.additive = additive;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isAdditive() {
        return additive;
    }

    public Long getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }
}
