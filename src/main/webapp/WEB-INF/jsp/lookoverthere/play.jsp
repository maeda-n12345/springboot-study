<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="game" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext" uri="http://example.com/tags/ext"%>




<ext:layout title="あっちむいてほい!">
	<div>
		<span><c:out value="${name}" /></span>
	</div>

	<form id="playForm" name="playForm" action="play" method="post">
		<div>向きを選んでください：</div>
		<div>
			<label> <input type="radio" name="d" value="1"
				checked="checked"> 上
			</label> <label> <input type="radio" name="d" value="2"> 下
			</label> <label> <input type="radio" name="d" value="3"> 左
			</label> <label> <input type="radio" name="d" value="4"> 右
			</label>
		</div>
		<div style="margin-top: 8px;">
			<input type="submit" value="送信">
		</div>
	</form>


	<c:if test="${not empty historyList}">
		<c:set var="last" value="${historyList[0]}" />
		<ext:matchResult attackerName="${last.attackerDisplayName}"
			attackerDirection="${last.attackerDirection}"
			defenderName="${last.defenderDisplayName}"
			defenderDirection="${last.defenderDirection}" judge="${last.winner}" />
		<p>
			CPUが出した直近5戦の方向：
			<ext:join var="${cpuLast5Hands}" split=" ,"
				emptyMessage="まだ対戦履歴がありません" />
		</p>

	</c:if>

	<game:historySection items="${historyList}" />

	</ext:layout>
</html>