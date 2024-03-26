package spring.workshop.expenses.services.impl;

public class ValidateDateHelper {

    public static Boolean validateDate(Integer year, Integer month) {
        return checkYear(year) && checkMonth(month);
    }

    private static Boolean checkYear(Integer year) {
        if (year == null || (year >= 1000 && year <= 9999)) {
            return true;
        }
        return false;
    }

    private static Boolean checkMonth(Integer month) {
        if (month == null || (month >= 1 && month <= 12)) {
            return true;
        }
        return false;
    }
}
