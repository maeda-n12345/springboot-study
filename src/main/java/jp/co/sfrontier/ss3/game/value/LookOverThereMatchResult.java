package jp.co.sfrontier.ss3.game.value;

import lombok.Data;

@Data
public class LookOverThereMatchResult extends MatchResult{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 勝敗フラグ：自分が勝利した場合 true になる
	 */
	private boolean win;
	
	private Long attackerId;
	
	private Long defenderId;
	
}