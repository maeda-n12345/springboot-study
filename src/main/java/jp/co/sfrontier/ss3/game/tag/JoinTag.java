package jp.co.sfrontier.ss3.game.tag;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

/**
 * List<String> や String[] を区切り文字で連結して出力するタグ<br>
 * <br>
 * 仕様:<br>
 *   - var: 連結対象 (必須) List<String> / String[] を想定<br>
 *   - split: 区切り文字 (任意) 省略時は "、"<br>
 *   - emptyMessage: 要素が 0 件のときに代わりに出力するメッセージ<br>
 */
public class JoinTag extends SimpleTagSupport {

    private Object var;
    private String split;
    private String emptyMessage;

    public void setVar(Object var) {
        this.var = var;
    }

    public void setSplit(String split) {
        this.split = split;
    }

    public void setEmptyMessage(String emptyMessage) {
        this.emptyMessage = emptyMessage;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        
        String delimiter = (split != null) ? split : "、";

        List<String> parts = toStringList(var);

        // 要素 0 件のとき
        if (parts.isEmpty()) {
            if (emptyMessage != null && !emptyMessage.isEmpty()) {
                out.print(emptyMessage);
            }
            return;
        }

        String joined = String.join(delimiter, parts);
        out.print(joined);
    }

    /**
     * var を List<String> に変換するヘルパー<br>
     * <br>
     * - List<?> の場合: 各要素を toString() した List<String><br>
     * - 配列の場合: 各要素を toString() した List<String><br>
     * - それ以外: 1 要素のみの List<String> (value.toString())<br>
     * - null: 空リスト<br>
     */
    private List<String> toStringList(Object value) {
        List<String> result = new ArrayList<>();

        if (value == null) {
            return result;
        }

        if (value instanceof Collection<?>) {
            for (Object o : (Collection<?>) value) {
                result.add(toStringSafe(o));
            }
            return result;
        }

        if (value.getClass().isArray()) {
            int len = Array.getLength(value);
            for (int i = 0; i < len; i++) {
                Object o = Array.get(value, i);
                result.add(toStringSafe(o));
            }
            return result;
        }

        // その他の型は 1 要素だけのリストとして扱う
        result.add(toStringSafe(value));
        return result;
    }

    private String toStringSafe(Object o) {
        return (o == null) ? "" : o.toString();
    }
}
