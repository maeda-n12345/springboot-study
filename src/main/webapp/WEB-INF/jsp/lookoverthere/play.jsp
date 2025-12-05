<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>


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


	<h3>本日の対戦履歴（最新10件）</h3>
	<c:choose>
		<c:when test="{empty recentHistory}">
			<div>対戦履歴がありません</div>
		</c:when>
		<c:otherwise>
			<table>
				<thead>
					<tr>
						<th>対戦日時</th>
						<th>アタッカー</th>
						<th>ディフェンダー</th>
						<th>結果（アタッカー視点）</th>
					</tr>
				</thead>
				<tbody>
					<fmt:formatDate value="${h.matchDatetime}"
						pattern="yyyy/MM/dd HH:mm:ss" />
					<tr>
						<td><c:out value="${h.matchDatetime}" /></td>
						<td><c:out value="${h.attackerId}" /></td>
						<td><c:out value="${h.defenderId}" /></td>
						<td><c:out value="${h.judge}" /></td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:otherwise>
	</c:choose>

</body>
</html>