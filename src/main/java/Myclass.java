import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Myclass {
    private static final Logger logger = LoggerFactory.getLogger(Myclass.class);

    public void doSomething() {
        logger.info("This is an info message");
        logger.debug("This is a debug message");
        logger.error("This is an error message");
    }
}
