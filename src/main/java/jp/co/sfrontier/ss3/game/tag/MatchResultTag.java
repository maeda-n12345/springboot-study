package jp.co.sfrontier.ss3.game.tag;

import java.io.IOException;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

/**
 * 対戦結果を表示するカスタムタグ
 */
public class MatchResultTag extends SimpleTagSupport {
	private String attackerName;
	private Integer attackerDirection;
	private String defenderName;
	private Integer defenderDirection;
	private String judge;

	public void setAttackerName(String attackerName) {
		this.attackerName = attackerName;
	}

	public void setAttackerDirection(Integer attackerDirection) {
		this.attackerDirection = attackerDirection;
	}

	public void setDefenderName(String defenderName) {
		this.defenderName = defenderName;
	}

	public void setDefenderDirection(Integer defenderDirection) {
		this.defenderDirection = defenderDirection;
	}

	public void setJudge(String judge) {
		this.judge = judge;
	}

	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();

		String attackerArrow = toArrow(attackerDirection);
		String defenderArrow = toArrow(defenderDirection);

		String judgeText;
		String judgeCssClass;
		String j = safe(judge);
		String attacker = safe(attackerName);
		String defender = safe(defenderName);
		
		if ("ATTACKER_WIN".equals(j) || attacker.equals(j)) {
		    judgeText = "勝者：" + attacker;
		    judgeCssClass = "match-result__judge match-result__judge--win";

		} else if ("DEFENDER_WIN".equals(j) || defender.equals(j)) {
		    judgeText = "勝者：" + defender;
		    judgeCssClass = "match-result__judge match-result__judge--lose";

		} else {
		    // どれでもない場合（本来は来てほしくないが保険）
		    judgeText = "結果不明";
		    judgeCssClass = "match-result__judge";
		}

		out.write("<div class=\"match-result\">");
		out.write("<div class=\"match-result__title\">対戦結果</div>");

		// アタッカー行
		out.write("<div class=\"match-result__row\">");
		out.write("<span class=\"match-result__label\">アタッカー</span>");
		out.write("<span class=\"match-result__name\">");
		out.write(safe(attackerName));
		out.write("</span>");
		out.write("<span class=\"match-result__direction\">");
		out.write(attackerArrow);
		out.write("</span>");
		out.write("</div>");

		// ディフェンダー行
		out.write("<div class=\"match-result__row\">");
		out.write("<span class=\"match-result__label\">ディフェンダー</span>");
		out.write("<span class=\"match-result__name\">");
		out.write(safe(defenderName));
		out.write("</span>");
		out.write("<span class=\"match-result__direction\">");
		out.write(defenderArrow);
		out.write("</span>");
		out.write("</div>");

		// 結果行
		out.write("<div class=\"match-result__row match-result__row--judge\">");
		out.write("<span class=\"match-result__label\">結果</span>");
		out.write("<span class=\"");
		out.write(judgeCssClass);
		out.write("\">");
		out.write(judgeText);
		out.write("</span>");
		out.write("</div>");

		out.write("</div>");
	}

	/**
	 *  方向(1〜4)を矢印に変換<br>
	 */
	private String toArrow(Integer direction) {
		if (direction == null) {
			return "-";
		}
		switch (direction) {
		case 1:
			return "↑";
		case 2:
			return "↓";
		case 3:
			return "←";
		case 4:
			return "→";
		default:
			return "-";
		}
	}

	/** 
	 * null 安全な String 化<br>
	 */
	private String safe(String s) {
		return (s == null) ? "" : s;
	}

}
