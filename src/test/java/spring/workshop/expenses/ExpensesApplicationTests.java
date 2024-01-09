package spring.workshop.expenses;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class ExpensesApplicationTests {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	void contextLoads() {
	}

	@Value("${example.propertyCheck}")
	private String propertyCheck;

	@Value("${example.property.value}")
	private String propertyValue;

	@Test
	void testMyTableRowCount() {
		Integer actualRowCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_tab", Integer.class);
		assertEquals(5, actualRowCount);
	}

	@Test
	public void shouldTestPropertyOverrideProdProperty() {
		assertEquals("test", propertyCheck);
	}

	@Test
	public void testPropertyValue() {
		assertEquals("qwerty12345", propertyValue);
	}
}
