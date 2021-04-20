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
    <!--     <script src="${hContext }/resources/js/jquery.min.js"></script> -->
    <script src="${hContext }/resources/js/bootstrap.min.js"></script>
     
  </head>
<body>
    <!--
      작성일:2021. 3. 8
      작성자:sist
      설명:
     -->
     <!-- div container -->
	 <div class="container">
	 	<!-- 제목 -->
	 	<div class="page-header">
	 		<h2>등록</h2>
	 	</div>
	    <!--// 제목 -->
	    
	    <!-- 버튼 -->
	    <div class="row text-right">
	    	<label class="col-xs-4 col-sm-3 col-md-2 col-lg-2"></label>
	        <div   class="col-xs-8 col-sm-9 col-md-10 col-lg-10">
	        	<input type="button" class="btn btn-primary btn-sm"  value="목록" id="moveToList"/>
	        	<input type="button" class="btn btn-primary btn-sm"  value="등록" id="doInsert"/>
	        </div>
	    </div>
	    <!--// 버튼 -->
	    
	    <!-- form -->
	    <form action="" class="form-horizontal">
		  <div class="form-group">
		    <label for="title" class="col-xs-4 col-sm-3 col-md-2 col-lg-2 control-label">제목</label>
		    <div class="col-xs-8 col-sm-9 col-md-10 col-lg-10">
		      <input type="text" class="form-control" id="title" name="title" placeholder="제목">
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="reg_id" class="col-xs-4 col-sm-3 col-md-2 col-lg-2 control-label">작성자</label>
		    <div class="col-xs-8 col-sm-9 col-md-10 col-lg-10">
		      <input type="text" class="form-control" id="reg_id" name="reg_id" placeholder="작성자">
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="reg_id" class="col-xs-4 col-sm-3 col-md-2 col-lg-2 control-label">내용</label>
		    <div class="col-xs-8 col-sm-9 col-md-10 col-lg-10">
		      <textarea rows="5" cols="40" class="form-control" id="contents" name="contents" placeholder="내용"></textarea>
		    </div>
		  </div>		    	    	
	    </form>
	    <!--// form -->
	    
	    
	 </div>
	 <!--// div container -->
</body>
</html>
















