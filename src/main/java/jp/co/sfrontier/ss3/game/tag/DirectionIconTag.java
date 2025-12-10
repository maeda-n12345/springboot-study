package jp.co.sfrontier.ss3.game.tag;

import java.io.IOException;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

/**
 * 方向コード(１～４)を矢印(↑↓←→)に変換して出力するカスタムタグ
 */
public class DirectionIconTag extends SimpleTagSupport {
	private Integer direction;

	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();

		String icon;

		if (direction == null) {
			icon = "-";
		} else {
			switch (direction) {
			case 1:
				icon = "↑";
				break;
			case 2:
				icon = "↓";
				break;
			case 3:
				icon = "←";
				break;
			case 4:
				icon = "→";
				break;
			default:
				icon = "-";
			}
		}
		out.print(icon);
	}
}
