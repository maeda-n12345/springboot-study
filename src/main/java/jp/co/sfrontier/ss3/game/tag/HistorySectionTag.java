package jp.co.sfrontier.ss3.game.tag;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import jp.co.sfrontier.ss3.game.value.LookOverThereMatchHistory;

/**
 * 対戦履歴セクション全体を出力するカスタムタグ<br>
 * <br>
 */
public class HistorySectionTag extends SimpleTagSupport {
	private List<LookOverThereMatchHistory> items;

	public void setItems(List<LookOverThereMatchHistory> items) {
		this.items = items;
	}

	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();

		// 見出し
		out.write("<h3>本日の対戦履歴（最新10件）</h3>");

		// 履歴が無い場合
		if (items == null || items.isEmpty()) {
			out.write("<div>対戦履歴がありません</div>");
			return;
		}

		// テーブル開始
		out.write("<table>");
		out.write("<thead>");
		out.write("<tr>");
		out.write("<th>対戦日時</th>");
		out.write("<th>アタッカー</th>");
		out.write("<th>アタッカーの方向</th>");
		out.write("<th>ディフェンダー</th>");
		out.write("<th>ディフェンダーの方向</th>");
		out.write("<th>勝者</th>");
		out.write("</tr>");
		out.write("</thead>");
		out.write("<tbody>");

		// 1 行ずつ出力（既存の HistoryRowTag を使ってもいいし、ここで直接書いてもよい）
		for (LookOverThereMatchHistory h : items) {
			HistoryRowTag rowTag = new HistoryRowTag();
			rowTag.setJspContext(getJspContext());
			rowTag.setParent(this);
			rowTag.setHistory(h);
			rowTag.doTag();
		}

		out.write("</tbody>");
		out.write("</table>");
	}

}
