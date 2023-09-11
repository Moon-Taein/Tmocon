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
<header>
<h1 id="home">Tmocon</h1>
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
                <a class="nav-link" href="#inputForm">검색</a>
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
	<form action="/follow" method="get" id="inputForm">
		<div class="form-group">
			<input class="form-control form-control-lg text-center" type="text"
				placeholder="검색할 ID를 입력하세요" name="username" id="inputLarge">
		</div>
		<div class="card">
		<button class="btn btn-primary" type="submit" id="button-addon2">제출</button>
		</div>
	</form>
</main>
<footer class="py-5 bg-dark">
      <div class="container">
        <p class="m-0 text-center text-white">
          Copyright &copy; Your Website 2023
        </p>
      </div>
    </footer>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<!-- 모든 컴파일된 플러그인을 포함합니다 (아래), 원하지 않는다면 필요한 각각의 파일을 포함하세요 -->
	<script src="js/bootstrap.js"></script>
</body>
</html>