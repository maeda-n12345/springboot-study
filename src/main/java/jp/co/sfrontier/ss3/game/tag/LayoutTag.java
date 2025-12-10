package jp.co.sfrontier.ss3.game.tag;

import java.io.IOException;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.JspFragment;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

/**
 * 画面レイアウト用のカスタムタグ<br>
 * <br>
 */
public class LayoutTag extends SimpleTagSupport {
	private String title;

	public void setTitle(String title) {
		this.title = title;
	}

	private static final String GLOBAL_STYLE = """
			<style>
			/* 全体 */
			body {
			    font-family: system-ui, -apple-system, BlinkMacSystemFont, "Segoe UI",
			        sans-serif;
			    margin: 16px;
			    line-height: 1.6;
			}

			header h2 {
			    margin-top: 0;
			    margin-bottom: 16px;
			}

			main {
			    margin-top: 8px;
			}

			/* 対戦結果パネル */
			.match-result {
			    border: 1px solid #ccc;
			    border-radius: 8px;
			    padding: 12px 16px;
			    margin: 16px 0;
			    background-color: #f9f9f9;
			}

			.match-result__title {
			    font-weight: bold;
			    margin-bottom: 8px;
			    font-size: 1.1rem;
			}

			.match-result__row {
			    margin: 4px 0;
			    display: flex;
			    align-items: center;
			    gap: 8px;
			}

			.match-result__label {
			    display: inline-block;
			    min-width: 90px;
			    font-weight: bold;
			}

			.match-result__name {
			    margin-right: 8px;
			}

			.match-result__direction {
			    font-size: 1.3rem;
			}

			.match-result__row--judge {
			    margin-top: 8px;
			}

			.match-result__judge {
			    font-weight: bold;
			}

			/* 勝者の色分け（必要に応じてクラスを付け替えてもOK） */
			.match-result__judge--win {
			    color: #0a7f1f;
			}

			.match-result__judge--lose {
			    color: #c02424;
			}

			.match-result__judge--draw {
			    color: #555;
			}

			/* 履歴テーブル */
			table {
			    border-collapse: collapse;
			    margin-top: 8px;
			    width: 100%;
			    max-width: 900px;
			}

			th, td {
			    border: 1px solid #ccc;
			    padding: 4px 8px;
			    text-align: center;
			    font-size: 0.9rem;
			}

			th {
			    background-color: #f0f0f0;
			}

			tbody tr:nth-child(odd) {
			    background-color: #fafafa;
			}

			tbody tr:nth-child(even) {
			    background-color: #ffffff;
			}

			/* フォーム周り */
			#playForm {
			    margin-bottom: 16px;
			}

			#playForm input[type="text"] {
			    width: 40px;
			    text-align: center;
			}

			#playForm input[type="submit"] {
			    margin-left: 4px;
			}
			</style>
			""";

	 @Override
	    public void doTag() throws JspException, IOException {
	        JspWriter out = getJspContext().getOut();
	        String pageTitle = (title != null) ? title : "";

	        out.write("<!DOCTYPE html>");
	        out.write("<html lang=\"ja\">");
	        out.write("<head>");
	        out.write("<meta charset=\"UTF-8\" />");
	        out.write("<title>");
	        out.write(escapeHtml(pageTitle));
	        out.write("</title>");

	        // 共通CSSを head に埋め込む
	        out.write(GLOBAL_STYLE);

	        out.write("</head>");
	        out.write("<body>");
	        out.write("<header><h2>");
	        out.write(escapeHtml(pageTitle));
	        out.write("</h2></header>");

	        out.write("<main>");

	        JspFragment body = getJspBody();
	        if (body != null) {
	            body.invoke(null);
	        }

	        out.write("</main>");
	        out.write("</body>");
	        out.write("</html>");
	    }

	    private String escapeHtml(String s) {
	        if (s == null) return "";
	        return s.replace("&", "&amp;")
	                .replace("<", "&lt;")
	                .replace(">", "&gt;");
	    }
	}
