package spring.workshop.expenses.aspect;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import spring.workshop.expenses.serviceImpl.ShopServiceImpl;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class PerformanceLoggingAspectTest {

    Logger logger = (Logger) LoggerFactory.getLogger(PerformanceLoggingAspect.class);
    private ListAppender<ILoggingEvent> listAppender = new ListAppender<>();

    @Autowired
    private ShopServiceImpl shopService;

    @BeforeEach
    public void setup() {

        listAppender.start();
        logger.addAppender(listAppender);
    }

    @AfterEach
    public void teardown() {
        listAppender.stop();
        listAppender.list.clear();
    }

    @Test
    public void shouldLogPerformanceGetAllShops() {

        shopService.getAllShops();

        ILoggingEvent event = listAppender.list.get(0);

        assertEquals(true, event.getMessage().contains("Execution time of ShopServiceImpl.getAllShops"));

    }

    @Test
    public void shouldLogPerformanceGetShop() {

        shopService.getShop(100l);

        ILoggingEvent event = listAppender.list.get(0);

        assertEquals(true, event.getMessage().contains("Execution time of ShopServiceImpl.getShop"));

    }
}
