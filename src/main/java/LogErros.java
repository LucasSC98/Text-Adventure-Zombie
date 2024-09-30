import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogErros {
    private static final Logger logger = LoggerFactory.getLogger(LogErros.class);

    public void LogInformacoes() {
        logger.info("Info Mensagem: ");
        logger.debug("Debug Mensagem: ");
        logger.error("Erro Mensagem: ");
    }
}
