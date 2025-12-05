package jp.co.sfrontier.ss3.game.service.lookoverthere.value;

import java.io.Serializable;

import jp.co.sfrontier.ss3.game.common.Direction;
import lombok.Data;

@Data
public class Player implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String playerName;
	
	private Direction direction;
	
	private boolean win;
	
}