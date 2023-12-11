package spring.workshop.expenses;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class ExpensesApplicationTests {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	void contextLoads() {
	}

	@Test
	void testMyTableRowCount() {
		int actualRowCount = jdbcTemplate.queryForObject( "SELECT COUNT(*) FROM USER_TAB", Integer.class);
		assertEquals(5, actualRowCount);
	}

}
