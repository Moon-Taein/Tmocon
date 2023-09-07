<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>my</title>
</head>
<body>
	<%-- ${ target.broadcaster_login }
	${ target.display_name }
	${ target.id } --%>
	<c:if test="${ not empty online }" >
		<c:forEach var="item" items="${ online }">
			${ item.display_name }
			${ item.viewer_count }
			${ item.title }
			${ item.thumbnail_url }
			${ item.game_name }
			${ item.started_at }
		</c:forEach>
	</c:if>
	<c:if test="${ not empty offline }" >
		<c:forEach var="item" items="${ offline }">
			${ item.display_name }
			${ item.profile_image_url }
		</c:forEach>
	</c:if>	
</body>
</html>