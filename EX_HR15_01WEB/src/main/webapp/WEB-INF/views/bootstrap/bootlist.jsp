<%--
/**
	Class Name:
	Description:
	Modification information
	
	수정일     수정자      수정내용
    -----   -----  -------------------------------------------
    2021. 4. 19.        최초작성 
    
    author eclass 개발팀
    since 2020.11.23
    Copyright (C) by KandJang All right reserved.
*/
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="hContext"  value="${pageContext.request.contextPath }"></c:set>    
<!DOCTYPE html>     
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 위 3개의 메타 태그는 *반드시* head 태그의 처음에 와야합니다; 어떤 다른 콘텐츠들은 반드시 이 태그들 *다음에* 와야 합니다 -->
    <title>bootstrap form</title>

    <!-- 부트스트랩 -->
    <link href="${hContext }/resources/css/bootstrap.min.css" rel="stylesheet">

    <!-- IE8 에서 HTML5 요소와 미디어 쿼리를 위한 HTML5 shim 와 Respond.js -->
    <!-- WARNING: Respond.js 는 당신이 file:// 을 통해 페이지를 볼 때는 동작하지 않습니다. -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js">
    <!--     <script src="hContext/resources/js/jquery.min.js"></script> -->
    <script src="${hContext }/resources/js/bootstrap.min.js"></script>
     
  </head>
<body >

	<!-- div container -->
	<div class="wrap container">
	   <!-- header -->
		<%-- <jsp:include page="/inc/header.jsp"></jsp:include> --%>
	   <!--//header -->
	 	<!-- 제목 -->
	 	<div class="page-header">
	 		<h2>게시 목록</h2>
	 	</div>
	    <!--// 제목 -->
	    
	    <!-- 버튼 -->
	    <div class="row text-right">
	    	<label class="col-xs-4 col-sm-3 col-md-2 col-lg-2"></label>
	        <div   class="col-xs-8 col-sm-9 col-md-10 col-lg-10">
	        	<input type="button" class="btn btn-primary btn-sm"  value="목록" id="move_to_list"/>
	        	<input type="button" class="btn btn-primary btn-sm"  value="등록" onclick="doInsert();"/>
	        </div>
	    </div>
	    <!--// 버튼 -->
	    
	    <!-- 검색영역 -->
	    <div class="row">
	    	<form action=""  class="form-inline  col-lg-12 col-md-12 text-right">
	    		<div class="form-group">
	    		  <select class="form-control input-sm">
	    		  	<option value="10">10</option>
	    		  	<option value="20">20</option>
	    		  	<option value="30">30</option>
	    		  	<option value="50">50</option>
	    		  	<option value="100">100</option>
	    		  </select>	    		
	    		  <select class="form-control input-sm">
	    		  	<option value="10">제목</option>
	    		  	<option value="20">내용</option>
	    		  	<option value="30">작성자</option>
	    		  </select>  
	    		  <input  type="text" class="form-control  input-sm"  placeholder="검색어"/>
	    		</div>
	    	</form>
	    </div>
	    <!--// 검색영역 -->
	    	    
        <!-- table -->
		<div class="table-responsive">
		    <!-- table -->
			<table class="table table-striped table-bordered table-hover table-condensed">
				<thead class="bg-primary">  
					<th class="text-center col-lg-1 col-md-1  col-xs-1" >번호</th>
					<th class="text-center col-lg-6 col-md-6  col-xs-9">제목</th>
					<th class="text-center col-lg-2 col-md-2  col-xs-1">작성자</th>
					<th class="text-center col-lg-2 col-md-2  col-xs-1">작성일</th>
					<th class="text-center col-lg-1 col-md-1  col-xs-1">조회수</th>
				</thead>
			    <tbody>
			        <!-- 문자: 왼쪽, 숫자: 오른쪽, 같은면: 가운데 -->
			    	<tr>
			    		<td class="text-center">1</td>
			    		<td class="text-left">코로나 온라인 수업</td>
			    		<td class="text-left">Admin</td>
			    		<td class="text-center">2020/09/16</td>
			    		<td class="text-right">0</td>
			    	</tr>
			    	<tr>
			    		<td class="text-center">2</td>
			    		<td class="text-left">코로나 온라인 수업</td>
			    		<td class="text-left">Admin</td>
			    		<td class="text-center">2020/09/16</td>
			    		<td class="text-right">0</td>
			    	</tr>    		    	
			    </tbody>
			</table>
		</div>
        <!--// table -->
	    
	    <!-- pagenation -->
		<div class="text-center">
			<nav>
			  <ul class="pagination">
			    <li>
			      <a href="#" aria-label="Previous">
			        <span aria-hidden="true">&laquo;</span>
			      </a>
                </li>
                <li>
			      <a href="#" aria-label="Previous">
			        <span aria-hidden="true">&lt;</span>
			      </a>
			    </li>
			    <li><a href="#">1</a></li>
			    <li><a href="#">2</a></li>
			    <li><a href="#">3</a></li>
			    <li><a href="#">4</a></li>
			    <li><a href="#">5</a></li>
                <li>
			      <a href="#" aria-label="Previous">
			        <span aria-hidden="true">&gt;</span>
			      </a>
			    </li>
			    <li>
			      <a href="#" aria-label="Next">
			        <span aria-hidden="true">&raquo;</span>
			      </a>
			    </li>
			  </ul>
			</nav>
		</div>
	    <!--// pagenation -->	    
	    <!-- footer -->		
		<%-- <jsp:include page="/inc/footer.jsp"></jsp:include> --%>
		<!--// footer -->	
     </div>
     <!--// contents -->
    <!-- javascript -->
    <script type="text/javascript">
    
		//jquery 객채생성이 완료
		$(document).ready(function() {
			console.log("1.document:최초수행!");
		});//--document ready
    </script>
    <!--// javascript -->    
</body>
</html>