package jp.co.sfrontier.ss3.game.common;

import java.util.Objects;

/**
 * 「あっち向いてほい」の方角情報を定義する。<br>
 * <br>
 */
public enum Direction {

	UP(1),
	
	DOWN(2),
	
	LEFT(3),
	
	RIGHT(4),
	;
	
	/**
	 * 方向を表す値
	 */
	private Integer value;
	
	private Direction(int val) {
		this.value = Integer.valueOf(val);
	}
	
	/**
	 * 方向を表す値を Integer として取り出す。<br>
	 * <br>
	 * @return
	 */
	public Integer getValue() {
		return this.value;
	}
	
	/**
	 * 方向を表す値を int として取り出す。<br>
	 * <br>
	 * @return
	 */
	public int getVal() {
		return this.value.intValue();
	}
	
	/**
	 * 指定された値に該当する方向を取り出す。<br>
	 * <br>
	 * @param val
	 * @return 該当する値ではない場合、null を返す
	 */
	public static Direction get(int val) {
		return get(Integer.valueOf(val));
	}

	/**
	 * 指定された値に該当する方向を取り出す。<br>
	 * <br>
	 * @param val
	 * @return 該当する値ではない場合、null を返す
	 */
	public static Direction get(Integer val) {
		if(Objects.isNull(val)) {
			return null;
		}
		
		for(Direction derection : Direction.values()) {
			if(derection.eq(val)) {
				return derection;
			}
		}
		
		return null;
	}
	
	public boolean eq(Integer val) {
		return Objects.equals(this.value, val);
	}
	
	public boolean eq(int val) {
		return eq(Integer.valueOf(val));
	}
}
