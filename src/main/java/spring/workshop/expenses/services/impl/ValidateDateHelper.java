package spring.workshop.expenses.services.impl;

public class ValidateDateHelper {

    public static Boolean validateDate(int year, int month) {

        if (year < 1000 || year > 9999 || month < 1 || month > 12) {
            return false;
        }
        return true;
    }
}
