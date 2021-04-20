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
    <title>회원</title>

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
	 		<h2>회원</h2>
	 	</div>
	    <!--// 제목 -->
	    
	    <!-- 버튼 -->
	    <div class="row text-right">
	    	<label class="col-xs-4 col-sm-3 col-md-2 col-lg-2"></label>
	        <div   class="col-xs-8 col-sm-9 col-md-10 col-lg-10">
	        	<input type="button" class="btn btn-primary btn-sm"  value="목록" id="doRetrieve"/>
	        </div>
	    </div>
	    <!--// 버튼 -->
	    
	    <!-- 검색영역 -->
	    <div class="row">
	    	<form action="" method="get" name="user_frm"  class="form-inline  col-lg-12 col-md-12 text-right">
	    		<div class="form-group">
	    		
	    		  <select class="form-control input-sm" name="pageSize" id="pageSize" >
	    		    <c:forEach var="item" items="${COM_PAGE_SIZE }">
	    		        <option value="${item.detCode }">${item.detNm }</option>
	    		    </c:forEach>
	    		  </select>	    		
	    		  <select class="form-control input-sm" name="searchDiv" id="searchDiv">
	    		    <c:forEach var="item" items="${MEMBER_SEARCH }" varStatus="status">
	    		        <c:if test="${status.count==1 }"> 
	    		        	<option value="">전체</option>
	    		        </c:if>
	    		        <option value="${item.detCode }">${item.detNm }</option>
	    		    </c:forEach>
	    		  </select>  
	    		  <input  type="text" class="form-control  input-sm" name="searchWord" id="searchWord" maxlength="100"  placeholder="검색어"/>
	    		</div>
	    	</form>
	    </div>
	    <!--// 검색영역 -->
	    	    
        <!-- table -->
		<div class="table-responsive">
		    <!-- table -->
			<table id="userTable" class="table table-striped table-bordered table-hover table-condensed">
				<thead class="bg-primary">  
					<th class="text-center col-lg-1 col-md-1  col-xs-1" >번호</th>
					<th class="text-center col-lg-3 col-md-3  col-xs-3">아이디</th>
					<th class="text-center col-lg-2 col-md-2  col-xs-2">이름</th>  
					<th class="text-center col-lg-2 col-md-2  col-xs-2">레벨</th>
					<th class="text-center col-lg-2 col-md-2  col-xs-2">이메일</th>
					<th class="text-center col-lg-2 col-md-2  col-xs-2">등록일</th>
				</thead>
			    <tbody></tbody>
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
	    
	    <!-- form -->
     <!-- div container -->
	 <div class="container">
	 	<!-- 제목 -->
	    <!--// 제목 -->
	    
	    <!-- 버튼 -->
	    <div class="row text-right">
	        <div   class="col-xs-8 col-sm-9 col-md-12 col-lg-12">
	        	<input type="button" class="btn btn-primary btn-sm"  value="목록" id="moveToList"/>
	        	<input type="button" class="btn btn-primary btn-sm"  value="등록" id="doInsert"/>
	        </div>
	    </div>
	    <!--// 버튼 -->
	    
	    <!-- form -->
	    <form action="" class="form-horizontal">
		  <div class="form-group">
		    <div class="col-xs-8 col-sm-9 col-md-12 col-lg-12">
		      <input type="text" class="form-control" id="uId" name="uId" placeholder="아이디" maxlength="20">
		    </div>
		  </div>
		  <div class="form-group">
		    <div class="col-xs-8 col-sm-9 col-md-12 col-lg-12">
		      <input type="text" class="form-control" id="name" name="name" placeholder="이름" maxlength="50">
		    </div>
		  </div>
		  <div class="form-group">
		    <div class="col-xs-8 col-sm-9 col-md-12 col-lg-12">
		      <input type="password" class="form-control" id="passwd" name="passwd" placeholder="비밀번호" maxlength="50">
		    </div>
		  </div>
		  <div class="form-group">
		    <div class="col-xs-8 col-sm-9 col-md-12 col-lg-12">
		      <input type="text" class="form-control" id="level" name="level" placeholder="등급" maxlength="1">
		    </div>
		  </div>
		  <div class="form-group">
		    <div class="col-xs-8 col-sm-9 col-md-12 col-lg-12">
		      <input type="text" class="form-control" id="login" name="login" placeholder="로그인" maxlength="7">
		    </div>
		  </div>
		  <div class="form-group">
		    <div class="col-xs-8 col-sm-9 col-md-12 col-lg-12">
		      <input type="text" class="form-control" id="recommend" name="recommend" placeholder="추천" maxlength="7">
		    </div>
		  </div>
		  <div class="form-group">
		    <div class="col-xs-8 col-sm-9 col-md-12 col-lg-12">
		      <input type="text" class="form-control" id="email" name="email" placeholder="이메일" maxlength="320">
		    </div>
		  </div>
		  <div class="form-group">
		    <div class="col-xs-8 col-sm-9 col-md-12 col-lg-12">
		      <input type="text" class="form-control" id="regDt" name="regDt" placeholder="등록일" maxlength="10">
		    </div>
		  </div>		  		  		  		  		  		    	    	
	    </form>
	    <!--// form -->
	    
	    
	 </div>
	 <!--// div container -->	    
	    <!--// form -->
	    
	    	    
	    <!-- footer -->		
		<%-- <jsp:include page="/inc/footer.jsp"></jsp:include> --%>
		<!--// footer -->	
     </div>
     <!--// contents -->
    <!-- javascript -->
    <script type="text/javascript">
    
		//jquery 객채생성이 완료
		$(document).ready(function() {
			//console.log("1.document:최초수행!");
			doRetrieve(1);
		});//--document ready
		
		
		//검색어 Enter Event처리
		$("#searchWord").on("keypress", function(e) {
			console.log(e.type+","+e.which);
			if(e.which==13){
				console.log("Enter:"+e.which);
				e.preventDefault();//두번 호출 방지.
				doRetrieve(1);
			}
		});
		
		//목록 조회
		$("#doRetrieve").on("click",function(e){
			console.log("doRetrieve");
			e.preventDefault();//두번 호출 방지.
			doRetrieve(1);
		});
		
		
		function doRetrieve(page){
			
	      	$.ajax({
	    		type: "GET",
	    		url:"${hContext }/user/do_retrieve.do",
	    		asyn:"true",
	    		dataType:"html",
	    		data:{
	    			pageSize: $("#pageSize").val(),
	    			searchDiv: $("#searchDiv").val(),
	    			searchWord: $("#searchWord").val(),
	    			pageNum: page	
	    		},
	    		success:function(data){//통신 성공
	    			//alert(data);
	        		//console.log("success data:"+data);
	        		var parseData = JSON.parse(data);
	        		
	        		//기존 데이터 삭제.
	        		$("#userTable>tbody").empty();
	        		var html = "";
	        		
	        		console.log("parseData.length:"+parseData.length);
	        		
	        		//data가 있는 경우
	        		if(parseData.length>0){
	        			
	        			$.each(parseData, function(i, value) {
	        				//console.log(i+","+value.name);	
	        				html +="<tr>";
	        				html +="	<td class='text-center'>"+value.num+"</td>";
 	        				html +="	<td class='text-left'>"+value.uId+"</td>";
	        				html +="	<td class='text-left'>"+value.name+"</td>";
	        				html +="	<td class='text-left'>"+value.level+"</td>";
	        				html +="	<td class='text-left'>"+value.email+"</td>";
	        				html +="	<td class='text-center'>"+value.regDt+"</td>"; 
	        				html +="</tr>";	        				
	        			});
	        			
	        		}else{//data가 없는 경우
	        			html +="<tr>";
	        			html +="	<td class='text-center' colspan='99'>등록된 게시글이 없습니다.</td>";
	        			html +="</tr>";		        			
	        		}
	        		
	        		//tbody에 데이터 추가
	        		$("#userTable>tbody").append(html);
	        		
	
	        	},
	        	error:function(data){//실패시 처리
	        		console.log("error:"+data);
	        	},
	        	complete:function(data){//성공/실패와 관계없이 수행!
	        		console.log("complete:"+data);
	        	}
	    	});			
			
			
		}
		
		
		
		
		
    </script>
    <!--// javascript -->    
</body>
</html>>