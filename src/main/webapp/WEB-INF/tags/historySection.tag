<%@ tag pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="game" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext" uri="http://example.com/tags/ext"%>
<%@ attribute name="title" required="false"%>
<%@ attribute name="items" required="true" type="java.lang.Object"%>
<%@ attribute name="emptyMessage" required="false"%>

<h3>
	<c:choose>
		<c:when test="${not empty title}">
      ${title}
    </c:when>
		<c:otherwise>
      本日の対戦履歴（最新10件）
    </c:otherwise>
	</c:choose>
</h3>

<c:choose>
	<c:when test="${empty items}">
		<div>
			<c:choose>
				<c:when test="${not empty emptyMessage}">
          ${emptyMessage}
        </c:when>
				<c:otherwise>
          対戦履歴がありません
        </c:otherwise>
			</c:choose>
		</div>
	</c:when>

	<c:otherwise>
		<table>
			<thead>
				<tr>
					<th>対戦日時</th>
					<th>アタッカー</th>
					<th>アタッカーの方向</th>
					<th>ディフェンダー</th>
					<th>ディフェンダーの方向</th>
					<th>勝者</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="h" items="${items}">
					<ext:historyRow history="${h}" />
				</c:forEach>
			</tbody>
		</table>
	</c:otherwise>
</c:choose>
