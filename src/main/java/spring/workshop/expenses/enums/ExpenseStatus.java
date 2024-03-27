package spring.workshop.expenses.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ExpenseStatus {
    INITIAL("INITIAL"),
    PENDING("PENDING"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED");

    private final String value;

    ExpenseStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static ExpenseStatus fromValue(String value) {
        for (ExpenseStatus expenseStatus : ExpenseStatus.values()) {
            if (expenseStatus.value.equalsIgnoreCase(value)) {
                return expenseStatus;
            }
        }
        throw new IllegalArgumentException("Invalid expense status value: " + value);
    }
}
