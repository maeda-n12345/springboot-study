package jp.co.sfrontier.ss3.game.tag;

import java.io.IOException;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

/**
 * 方向コード(１～４)を矢印(↑↓←→)に変換して出力するカスタムタグ
 */
public class DirectionIcon extends SimpleTagSupport {
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
			case 2:
				icon = "↓";
			case 3:
				icon = "←";
			case 4:
				icon = "→";
			default:
				icon = "-";
			}
		}

		out.print(icon);
	}
}
