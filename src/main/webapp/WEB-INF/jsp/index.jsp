<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <script src="http://code.jquery.com/jquery-2.2.4.js"
            type="text/javascript"></script>
    <script src="/js/common.js"></script>
    <title>Homepage</title>
    <style>
        .clear {
            clear: both;
        }
    </style>
</head>
<body>
<h1>Comments</h1>
<div id="getComments"></div>


<script>
    var postId = 1;

    $(function() {
        ajaxWrapper("/getComments", "GET", {post_id : postId}, function(data) {
            var $container = $("#getComments");
            for (var i in data) {
                $container.append($("<p></p>").text(JSON.stringify(data[i].content)));
            }
        });
    });
</script>
</body>
</html>
