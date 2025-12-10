<%@ tag body-content="empty" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<%@ attribute name="attackerName" required="true"%>
<%@ attribute name="attackerDirection" required="true"%>
<%@ attribute name="defenderName" required="true"%>
<%@ attribute name="defenderDirection" required="true"%>
<%@ attribute name="judge" required="true"%>

<div class="match-result_title"><h3>対戦結果</h3></div>

<div class="match-result_row">
	<span class="match-result__label">アタッカー </span> <span
		class="match-result__name">${attackerName} </span> <span
		class="match-result__direction"> <c:choose>
			<c:when test="${attackerDirection == 1}">↑</c:when>
			<c:when test="${attackerDirection == 2}">↓</c:when>
			<c:when test="${attackerDirection == 3}">←</c:when>
			<c:when test="${attackerDirection == 4}">→</c:when>
			<c:otherwise>-</c:otherwise>
		</c:choose>
</div>

<div class="match-result__row">
	<span class="match-result__label">ディフェンダー</span> <span
		class="match-result__name">${defenderName}</span> <span
		class="match-result__direction"> <c:choose>
			<c:when test="${defenderDirection == 1}">↑</c:when>
			<c:when test="${defenderDirection == 2}">↓</c:when>
			<c:when test="${defenderDirection == 3}">←</c:when>
			<c:when test="${defenderDirection == 4}">→</c:when>
			<c:otherwise>-</c:otherwise>
		</c:choose>
	</span>
</div>

<div class="match-result__row match-result__row--judge">
	<span class="match-result__judge"> 勝者：${judge} </span>
</div>
</div>