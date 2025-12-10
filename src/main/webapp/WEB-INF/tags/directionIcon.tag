 <%@ tag language="java" pageEncoding="UTF-8" isELIgnored="false" body-content="empty" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%@ attribute name="direction" required="true" %>

<c:choose>
  <c:when test="${direction == 1}">↑</c:when>
  <c:when test="${direction == 2}">↓</c:when>
  <c:when test="${direction == 3}">←</c:when>
  <c:when test="${direction == 4}">→</c:when>
  <c:otherwise>-</c:otherwise>
</c:choose>
