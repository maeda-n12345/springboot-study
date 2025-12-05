package jp.co.sfrontier.ss3.game.mapper;

import static org.assertj.core.api.Assertions.*;

import java.nio.file.Path;
import java.util.List;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.csv.CsvDataSet;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jp.co.sfrontier.ss3.game.model.MatchResult;

/**
 * {@link MatchResultMapper}用の単体テストクラス<br>
 * <br>
 */
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(locations = { "classpath:applicaionContext-match_result.xml" })
public class MatchResultMapperTest {

	private static final Logger logger = LoggerFactory.getLogger(MatchResultMapperTest.class);
	private static final Path DATA_PATH = Path.of("src/test/resources/data", "MatchResultMapper");
	@Autowired
	private MatchResultMapper mapper;

	@Autowired
	private MatchResult supposed;

	@Autowired
	private DataSource dataSource;

	private static final String SCHEMA_NAME = "GAME";

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	public void setUpBeforeClass() throws Exception {
		// テストデータを読み込む
		// Spring が管理している DataSource から、通常の JDBC 接続を取得する
		IDatabaseConnection connection = new DatabaseConnection(dataSource.getConnection(), SCHEMA_NAME);
		DatabaseConfig config = connection.getConfig();
		config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
				new HsqldbDataTypeFactory());
		IDataSet dataSet = new CsvDataSet(DATA_PATH.toFile());
		DatabaseOperation.INSERT.execute(connection, dataSet);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	public void tearDownAfterClass() throws Exception {
		IDatabaseConnection connection = new DatabaseConnection(dataSource.getConnection(), SCHEMA_NAME);
		DatabaseConfig config = connection.getConfig();
		config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
				new HsqldbDataTypeFactory());
		IDataSet dataSet = new CsvDataSet(DATA_PATH.toFile());
		DatabaseOperation.DELETE.execute(connection, dataSet);
	}

	/**
	 * すべての項目について、期待値と一致していることを確認する<br>
	 * <br>
	 * 該当するデータが存在しない場合、<code>null</code>になっていること
	 * {@link MatchResultMapper#selectByPrimaryKey(Long)}
	 */
	@Test
	@DisplayName("selectByPrimeryKey")
	void testSelectByPrimeryKey() {
		if (mapper == null) {
			fail("初期化エラー");
		}
		logger.debug("取得する対象:matchResultId={}", supposed.getMatchResultId());
		MatchResult entity = mapper.selectByPrimaryKey(supposed.getMatchResultId());
		assertThat(entity).isNotNull();
		assertThat(entity).usingRecursiveComparison()
				.withComparatorForType((d1, d2) -> d1.compareTo(d2), java.util.Date.class)
				.isEqualTo(supposed);

		Long nomatchKey = Long.valueOf(99999L);
		MatchResult empty = mapper.selectByPrimaryKey(nomatchKey);

		// 該当するデータが存在しない場合 null を返す
		assertThat(empty).isNull();
	}

	@Test
	@DisplayName("selectByAttackerId")
	public void testSelectedByAttackerId() {
		Long tartgetId = Long.valueOf(10L);
		Long targetGameId = Long.valueOf(50L);
		List<MatchResult> list = mapper.selectByAttackerId(tartgetId, targetGameId);
		assertThat(list).isNotNull();
		assertThat(list.size()).isEqualTo(3);

		assertThat(list.get(0)).usingRecursiveComparison()
				.withComparatorForType((d1, d2) -> d1.compareTo(d2), java.util.Date.class)
				.isEqualTo(supposed);

		// 該当データが存在しないケース
		Long nomatchKey = Long.valueOf(999999L);
		List<MatchResult> emptyList = mapper.selectByAttackerId(nomatchKey, targetGameId);

		assertThat(emptyList).isNotNull();
		assertThat(emptyList.size()).isEqualTo(0);
	}
}