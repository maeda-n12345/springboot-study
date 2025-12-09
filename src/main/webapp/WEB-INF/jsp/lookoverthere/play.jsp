<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="game" tagdir="/WEB-INF/tags"%>

<html lang="ja">
<head>
<meta charset="UTF-8">
<title>あっちむいてほい!</title>
</head>
<body>
	<h2>あっちむいてほい!</h2>

	<div>
		<span><c:out value="${name}" /></span>
	</div>

	<form id="playForm" name="playForm" action="play" method="post">
		<div>1:上 2:下 3:左 4:右</div>
		<input type="text" name="d"> <input type="submit" value="送信">
	</form>

	<c:if test="${not empty historyList}">
		<c:set var="last" value="${historyList[0]}" />
		<game:matchResult attackerName="${last.attackerDisplayName}"
			attackerDirection="${last.attackerDirection}"
			defenderName="${last.defenderDisplayName}"
			defenderDirection="${last.defenderDirection}" judge="${last.winner}" />
	</c:if>

	<h3>本日の対戦履歴（最新10件）</h3>
	<c:choose>
		<c:when test="${empty historyList}">
			<div>対戦履歴がありません</div>
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
					<c:forEach var="h" items="${historyList}">
						<tr>
							<td><fmt:formatDate value="${h.matchDatetime}"
									pattern="yyyy/MM/dd HH:mm:ss" /></td>
							<td><c:out value="${h.attackerDisplayName}" /></td>
							<td><c:choose>
									<c:when test="${h.attackerDirection == 1}">↑</c:when>
									<c:when test="${h.attackerDirection == 2}">↓</c:when>
									<c:when test="${h.attackerDirection == 3}">←</c:when>
									<c:when test="${h.attackerDirection == 4}">→</c:when>
									<c:otherwise>-</c:otherwise>
								</c:choose></td>
							<td><c:out value="${h.defenderDisplayName}" /></td>
							<td><c:choose>
									<c:when test="${h.defenderDirection == 1}">↑</c:when>
									<c:when test="${h.defenderDirection == 2}">↓</c:when>
									<c:when test="${h.defenderDirection == 3}">←</c:when>
									<c:when test="${h.defenderDirection == 4}">→</c:when>
									<c:otherwise>-</c:otherwise>
								</c:choose></td>
							<td><c:out value="${h.winner}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:otherwise>
	</c:choose>
</body>
</html>