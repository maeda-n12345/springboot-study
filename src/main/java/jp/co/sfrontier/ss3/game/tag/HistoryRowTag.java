package jp.co.sfrontier.ss3.game.tag;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import jp.co.sfrontier.ss3.game.value.LookOverThereMatchHistory;

/**
 * 対戦履歴1行分を出力するカスタムタグ
 */
public class HistoryRowTag extends SimpleTagSupport {
	private LookOverThereMatchHistory history;

	public void setHistory(LookOverThereMatchHistory history) {
		this.history = history;
	}

	@Override
	public void doTag() throws JspException, IOException {
		if (history == null) {
			return;
		}

		JspWriter out = getJspContext().getOut();

		// 日付フォーマット
		String datetime = formatDate(history.getMatchDatetime());

		String attackerName = safe(history.getAttackerDisplayName());
		String defenderName = safe(history.getDefenderDisplayName());
		String winnerName = safe(history.getWinner());

		String attackerArrow = toArrow(history.getAttackerDirection());
		String defenderArrow = toArrow(history.getDefenderDirection());

		out.write("<tr>");

		// 対戦日時
		out.write("<td>");
		out.write(datetime);
		out.write("</td>");

		// アタッカー名
		out.write("<td>");
		out.write(attackerName);
		out.write("</td>");

		// アタッカーの方向
		out.write("<td>");
		out.write(attackerArrow);
		out.write("</td>");

		// ディフェンダー名
		out.write("<td>");
		out.write(defenderName);
		out.write("</td>");

		// ディフェンダーの方向
		out.write("<td>");
		out.write(defenderArrow);
		out.write("</td>");

		// 勝者
		out.write("<td>");
		out.write(winnerName);
		out.write("</td>");

		out.write("</tr>");
	}

	/** 方向(1〜4)を矢印に変換 */
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

	/** 日付を yyyy/MM/dd HH:mm:ss 形式にフォーマット */
	private String formatDate(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return fmt.format(date);
	}

	/** null 安全な文字列化 */
	private String safe(String s) {
		return (s == null) ? "" : s;
	}
}
