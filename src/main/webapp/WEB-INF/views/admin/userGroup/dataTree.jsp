<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<c:choose>
		<c:when test="${not empty node.parentId}">
			<tr data-tt-id='${node.id}'  data-tt-parent-id='${node.parentId}'>
		</c:when>
		<c:otherwise>
			<tr data-tt-id='${node.id}' >
		</c:otherwise>
	</c:choose>
	<td><span class='folder'>${node.name}</span></td>
	<td>
		<c:choose>
		   <c:when test="fn:contains(readableIds, node.id)">
		   		<form:checkbox path="readableIds" value="${node.id}"  checked="checked" />
		   </c:when>
		   <c:otherwise>
		   		<form:checkbox path="readableIds" value="${node.id}"  />
		   </c:otherwise>
	    </c:choose>
	</td>
	<td>
		<c:choose>
		   <c:when test="fn:contains(writableIds, node.id)">
		   		<form:checkbox path="writableIds" value="${node.id}"  checked="checked" />
		   </c:when>
		   <c:otherwise>
		   		<form:checkbox path="writableIds" value="${node.id}"  />
		   </c:otherwise>
	    </c:choose>
	</td>
	<td>
		<c:choose>
		   <c:when test="fn:contains(viewableIds, node.id)">
		   		<form:checkbox path="viewableIds" value="${node.id}"  checked="checked" />
		   </c:when>
		   <c:otherwise>
		   		<form:checkbox path="viewableIds" value="${node.id}"  />
		   </c:otherwise>
	    </c:choose>
	</td>
	<td>
		<c:choose>
		   <c:when test="fn:contains(downloadableIds, node.id)">
		   		<form:checkbox path="downloadableIds" value="${node.id}"  checked="checked" />
		   </c:when>
		   <c:otherwise>
		   		<form:checkbox path="downloadableIds" value="${node.id}"  />
		   </c:otherwise>
	    </c:choose>
	</td>
	<td>
		<c:choose>
		   <c:when test="fn:contains(printableIds, node.id)">
		   		<form:checkbox path="printableIds" value="${node.id}"  checked="checked" />
		   </c:when>
		   <c:otherwise>
		   		<form:checkbox path="printableIds" value="${node.id}"  />
		   </c:otherwise>
	    </c:choose>
	</td>
</tr>
<c:if test="${not empty node.children}">
   <c:forEach var="node" items="${node.children}">
       <c:set var="node" value="${node}" scope="request"/>
    	<jsp:include page="dataTree.jsp"/>
    </c:forEach>            
</c:if>
