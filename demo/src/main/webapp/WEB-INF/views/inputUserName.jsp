<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	<form action="/follow" method="get">
		<input type="text" name="username">
		<input type="submit"> 
	</form>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <!-- 모든 컴파일된 플러그인을 포함합니다 (아래), 원하지 않는다면 필요한 각각의 파일을 포함하세요 -->
    <script src="bootstrap.js"></script>
</body>
</html>