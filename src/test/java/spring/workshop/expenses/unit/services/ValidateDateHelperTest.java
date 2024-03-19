package spring.workshop.expenses.unit.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import spring.workshop.expenses.services.impl.ValidateDateHelper;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ValidateDateHelperTest {
    @Test
    public void testValidateDatePositive() {
        // Given
        int year = 1999;
        int month = 12;
        // When
        Boolean result = ValidateDateHelper.validateDate(year, month);
        // Then
        assertTrue(result);
    }

    @Test
    public void testValidateDateNegativeInvalidYear() {
        // Given
        int year = 999;
        int month = 12;
        // When
        Boolean result = ValidateDateHelper.validateDate(year, month);
        // Then
        assertFalse(result);
    }

    @Test
    public void testValidateDateNegativeInvalidMonth() {
        // Given
        int year = 1999;
        int month = 122;
        // When
        Boolean result = ValidateDateHelper.validateDate(year, month);
        // Then
        assertFalse(result);
    }
}
