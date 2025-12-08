package jp.co.sfrontier.ss3.game.value;

import java.util.Date;

import lombok.Data;

/**
 * 対戦履歴1行分の情報を定義するエンティティクラス
 */
@Data
public class LookOverThereMatchHistory {

	private Date matchDatetime;
	private String attackerDisplayName;
	private String defenderDisplayName;
	private Integer judge;
}
