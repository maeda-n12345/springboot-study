package jp.co.sfrontier.ss3.game.mapper;

import static org.assertj.core.api.Assertions.*;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.csv.CsvDataSet;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
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

		// 1) Spring管理の DataSource から JDBC コネクションを取得
		try (Connection conn = dataSource.getConnection()) {

			// ★ 1-1) まず既存データを削除（子テーブル → 親テーブルの順）
			try (Statement stmt = conn.createStatement()) {
				// MATCH_RESULT から先に消す
				stmt.executeUpdate("DELETE FROM GAME.MATCH_RESULT");
				// 次に GAME_USER
				stmt.executeUpdate("DELETE FROM GAME.GAME_USER");
			}

			// 2) DBUnit の接続ラッパーを作成（スキーマは GAME）
			IDatabaseConnection connection = new DatabaseConnection(conn, SCHEMA_NAME);

			DatabaseConfig config = connection.getConfig();
			config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
					new HsqldbDataTypeFactory());
			config.setProperty(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, false);

			// 3) フォルダ内の CSV から DataSet を構築（GAME_USER.csv, match_result.csv）
			IDataSet allDataSet = new CsvDataSet(DATA_PATH.toFile());

			// 親テーブル GAME_USER のみを取り出す
			ITable gameUserTable = allDataSet.getTable("GAME_USER");
			IDataSet gameUserDataSet = new DefaultDataSet(gameUserTable);

			// 子テーブル MATCH_RESULT のみを取り出す
			ITable matchResultTable = allDataSet.getTable("MATCH_RESULT");
			IDataSet matchResultDataSet = new DefaultDataSet(matchResultTable);

			// 4) 親 → 子 の順で INSERT
			DatabaseOperation.INSERT.execute(connection, gameUserDataSet);
			DatabaseOperation.INSERT.execute(connection, matchResultDataSet);
		}
	}

//	@AfterAll
//	public void tearDownAfterClass() throws Exception {
//
//		// テストで入れたデータだけ削除する
//		try (Connection conn = dataSource.getConnection()) {
//
//			IDatabaseConnection connection = new DatabaseConnection(conn, SCHEMA_NAME);
//
//			DatabaseConfig config = connection.getConfig();
//			config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
//					new HsqldbDataTypeFactory());
//			config.setProperty(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, false);
//
//			// CSV に載っている GAME_USER / MATCH_RESULT の行だけ削除
//			IDataSet dataSet = new CsvDataSet(DATA_PATH.toFile());
//			DatabaseOperation.DELETE.execute(connection, dataSet);
//		}
//	}

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