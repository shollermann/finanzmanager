package finanzmanager.model;

import java.util.Date;

public class MoneyTransfer {

    private int id;
    private boolean additive;
    private Double value;
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

    public void setValue(Double value) {
        this.value = value;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean isAdditive() {
        return additive;
    }

    public Double getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "MoneyTransfer{" +
                "id=" + id +
                ", additive=" + additive +
                ", value=" + value +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}
