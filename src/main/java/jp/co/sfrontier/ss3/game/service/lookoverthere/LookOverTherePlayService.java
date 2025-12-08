package jp.co.sfrontier.ss3.game.service.lookoverthere;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sfrontier.ss3.game.common.Direction;
import jp.co.sfrontier.ss3.game.mapper.MatchResultMapper;
import jp.co.sfrontier.ss3.game.model.MatchResult;
import jp.co.sfrontier.ss3.game.service.lookoverthere.value.Player;
import jp.co.sfrontier.ss3.game.value.LookOverThereMatchHistory;

/**
 * 「あっちむいてほい」についての各種サービスを提供する。<br>
 * <br>
 */
@Service
@Transactional
public class LookOverTherePlayService {

	private static final Logger logger = LoggerFactory.getLogger(LookOverTherePlayService.class);

	public static final Long GAME_ID = Long.valueOf(2L);

	@Autowired
	private MatchResultMapper matchResultMapper;

	/**
	 * 「あっちむいてほい」を実行して結果を返す。<br>
	 * <br>
	 * ユーザ vs CPUの勝負を行う
	 * @return 勝利したほうのIDを返す
	 */

	public long fight(Player player) {
		logger.info("call fight");

		// CPU の向きを決める
		Player cpu = createCpuPlayer();

		// 勝敗を判定し、結果を DB に保存する
		Long winnerId = fight(player, cpu);

		// 結果を返す
		return winnerId.longValue();
	}

	/**
	 * あっちむいてほいの対戦を行い、勝敗を判定する<br>
	 * DB に対戦結果を2レコード（攻撃側視点・防御側視点）を保存する<br>
	 * 
	 * @param attacker
	 * @param defender
	 */
	public Long fight(Player attacker, Player defender) {
		logger.debug("attacker={},defender={}", attacker.getDirection().name(), defender.getDirection().name());

		Long winnerId = null;

		if (attacker.getDirection() == defender.getDirection()) {
			logger.debug("attackerの勝ちです");
			attacker.setWin(true);
			defender.setWin(false);
			winnerId = attacker.getId();
		} else {
			logger.debug("attackerの負けです");
			attacker.setWin(false);
			defender.setWin(true);
			winnerId = defender.getId();
		}

		saveMatchResult(attacker, defender);

		return winnerId;
	}

	/**
	 * 対戦結果を DB に保存する<br>
	 * 1回の対戦について、攻撃側の視点と防御側の視点の2レコードを登録する。<br>
	 * 
	 * @param attacker 指をさすプレイヤー
	 * @param defender 顔を動かすプレイヤー
	 */
	private void saveMatchResult(Player attacker, Player defender) {
		Date matchDatetime = new Date();

		registMatchResult(attacker.getId(), defender.getId(), attacker.isWin(), matchDatetime);

		registMatchResult(defender.getId(), attacker.getId(), defender.isWin(), matchDatetime);
	}

	/**
	 * 1レコード分の対戦結果を DB に登録する。<br>
	 * 
	 * @param attackerId
	 * @param defenderId
	 * @param isAttackerWin
	 * @param matchDatetime
	 * @return 登録した MatchResult エンティティ
	 */
	private MatchResult registMatchResult(Long attackerId, Long defenderId, boolean isAttackerWin, Date matchDatetime) {

		MatchResult matchResult = new MatchResult();

		matchResult.setAttackerId(attackerId);
		matchResult.setDefenderId(defenderId);
		matchResult.setGameId(GAME_ID);
		matchResult.setMatchDatetime(matchDatetime);
		matchResult.setJudge((isAttackerWin) ? Integer.valueOf(1) : Integer.valueOf(0));
		matchResult.setCreatedAt(matchDatetime);
		matchResult.setUpdatedAt(matchDatetime);
		matchResult.setVersion(Integer.valueOf(1));

		matchResultMapper.insert(matchResult);

		logger.debug("登録完了:result_match_id={}", matchResult.getMatchResultId());

		return matchResult;
	}

	/**
	 * CPU プレイヤーを生成し、方向を指定する
	 */
	private Player createCpuPlayer() {
		Player cpu = new Player();
		cpu.setId(Long.valueOf(0L));
		cpu.setPlayerName("CPU");
		cpu.setDirection(getRandomDirection());
		return cpu;
	}

	private Direction getRandomDirection() {
		return Direction.get((new Random()).nextInt(4) + 1);
	}

	/**
	 * 本日の対戦履歴 最新10件を取得する。
	 */
	public List<LookOverThereMatchHistory> getHistory() {
	    ZoneId zone = ZoneId.of("Asia/Tokyo");
	    LocalDate today = LocalDate.now(zone);

	    LocalDateTime fromDate = today.atStartOfDay();              
	    LocalDateTime toDate   = today.plusDays(1).atStartOfDay(); 

	    Long playerId = 10L; 

	    return matchResultMapper.selectRecentHistory(fromDate, toDate, playerId);
	}

}

