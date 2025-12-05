package jp.co.sfontier.ss3.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ログの出力テスト
 */
public class LoggingTest {


		private static final Logger logger = LoggerFactory.getLogger(LoggingTest.class);
		
		public static void main(String[] args) {
			logger.trace("TRACE");
			logger.debug("DEBUG");
			logger.info("INFO");
			logger.warn("WARN");
			logger.error("ERROR");
		}
}
