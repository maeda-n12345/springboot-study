package jp.co.sfrontier.ss3.game.value;

import java.io.Serializable;

import jp.co.sfrontier.ss3.game.common.ResultCode;
import lombok.Data;

/**
 * 
 */
@Data
public class MatchResult implements Serializable {
	private static final long serealVersionUID = 1L;

	private ResultCode resultCode = ResultCode.INIT;
}