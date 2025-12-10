<%@ tag pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="game" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext" uri="http://example.com/tags/ext"%>

<%@ attribute name="history" required="true"
	type="jp.co.sfrontier.ss3.game.value.LookOverThereMatchHistory"%>

<tr>
	<td><fmt:formatDate value="${history.matchDatetime}"
			pattern="yyyy/MM/dd HH:mm:ss" /></td>

	<td><c:out value="${history.attackerDisplayName}" /></td>

	<td><ext:directionIcon direction="${history.attackerDirection}" />
	</td>

	<td><c:out value="${history.defenderDisplayName}" /></td>

	<td><ext:directionIcon direction="${history.defenderDirection}" />
	</td>

	<td><c:out value="${history.winner}" /></td>
</tr>
