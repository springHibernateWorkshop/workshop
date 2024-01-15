package spring.workshop.expenses.aspect;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import spring.workshop.expenses.services.ShopServiceImpl;

@SpringBootTest()
public class PerformanceLoggingAspectTest {

    private ListAppender<ILoggingEvent> listAppender;

    @Autowired
    private ShopServiceImpl shopService;

    @Autowired
    private PerformanceLoggingAspect performanceLoggingAspect;

    @BeforeEach
    public void setup() {
        Logger logger = (Logger) LoggerFactory.getLogger(PerformanceLoggingAspect.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
    }

    @AfterEach
    public void teardown() {
        listAppender.stop();
        listAppender.list.clear();
    }

    @Test
    public void shouldLogPerformance() {

        shopService.getAllShops();

        ILoggingEvent event = listAppender.list.get(0);

        assertEquals(true, event.getMessage().contains("Execution time of ShopServiceImpl.getAllShops"));

    }

}
