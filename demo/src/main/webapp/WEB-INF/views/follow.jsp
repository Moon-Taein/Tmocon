<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <!-- 위 3개의 메타 태그는 *반드시* head 태그의 처음에 와야합니다; 어떤 다른 콘텐츠들은 반드시 이 태그들 *다음에* 와야 합니다 -->
    <link rel="icon" href="img/favicon-32x32.png">
    <link rel="shortcut icon" href="img/apple-touch-icon.png" />
    <title>Tmocon</title>
    
    <!-- 부트스트랩 -->
    <link href="css/bootstrap.css" rel="stylesheet" />
</head>
<body>
	<%
		// 자동 새로고침 120
	 	response.setHeader("Refresh", "120");
	%>
<header>
<h1 id="home">${ user_id }의 팔로우</h1>
<nav class="navbar navbar-expand-lg bg-primary" data-bs-theme="dark">
        <div class="container-fluid">
          <a class="navbar-brand" href="#">Tmocon</a>
          <button
            class="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarColor01"
            aria-controls="navbarColor01"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarColor01">
            <ul class="navbar-nav me-auto">
              <li class="nav-item">
                <a class="nav-link active" href="#home"
                 >최상단
                  <span class="visually-hidden">(current)</span>
                </a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#online">온라인스트리머</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#offline">오프라인스트리머</a>
              </li>              
            </ul>
          </div>
        </div>
      </nav>
</header>
<main>
	<%-- ${ target.broadcaster_login }
	${ target.display_name }
	${ target.id } --%>
	<h2 class="h2"id="online">Now Live Streamers</h2>
	<c:if test="${ not empty online }" >
	<div class="container px-4 px-lg-5 mt-5">
        <div
          class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center"
        >
		<c:forEach var="item" items="${ online }">
		<div class="col mb-5">
		<div class="card h-100 w-110" >
			<a href="https://www.twitch.tv/${ item.user_login }"><img alt="" src="${ item.thumbnail_url }"></a>
			<button type="button" class="btn btn-success">${ item.user_name }(${ item.user_login })</button>
			<button type="button" class="btn btn-info">${ item.title }</button>
			<button type="button" class="btn btn-dark">${ item.game_name }</button>
			<button type="button" class="btn btn-primary">viewer : ${ item.viewer_count }</button>
			<button type="button" class="btn btn-danger">${ item.started_at }</button>
			
			<c:if test="${ not empty item.tags }" >
			<c:forEach var="tag" items="${ item.tags }">
			<button type="button" class="btn btn-primary">${ tag }</button>
			</c:forEach>
			</c:if>
			<%-- <p>${ item.viewer_count }</p>
			<p>${ item.user_name }(${ item.user_login })</p>
			<p>${ item.title }</p>
			<p>${ item.thumbnail_url }</p>
			<p>${ item.game_name }</p>
			<p>${ item.started_at }</p> --%>
		</div>
		</div>
		</c:forEach>
		</div>
		</div>
	</c:if>
	<h2 class="h2"id="offline">Now Offline Streamers</h2>
	<c:if test="${ not empty offline }" >
	<div class="container px-4 px-lg-5 mt-5">
        <div
          class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center"
        >
		<c:forEach var="item" items="${ offline }">
		<div class="col mb-5">
		<div class="card h-100 w-110" >
			<img alt="" src="${ item.profile_image_url }">
			<button type="button" class="btn btn-primary">${ item.display_name }</button>
			<%-- <button type="button" class="btn btn-danger">${ item.profile_image_url }</button> --%>
			<%-- <p>${ item.display_name }</p>
			<p>${ item.profile_image_url }</p> --%>
			</div>
			</div>
		</c:forEach>
		</div>
		</div>
	</c:if>
</main>
	<footer>
	<p>라라라라라라라라라라</p>
	</footer>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <!-- 모든 컴파일된 플러그인을 포함합니다 (아래), 원하지 않는다면 필요한 각각의 파일을 포함하세요 -->
    <script src="bootstrap.js"></script>
</body>
</html>