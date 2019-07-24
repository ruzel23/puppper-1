var userId = 1; //ToDo реализовать получение id из сессии
var userName = "MrSalatik";
var postId = 1; //ToDo совместить с модулем постов
var $rootContainer = $("#getComments");
var commentsGraph;
var replyParent = 0;
var editing = false;
var editContent = "";
var editFooter = "";

$(function() {
    ajaxWrapper("/comments/getComments", "GET", {post_id : postId}, function(data) {
        commentsGraph = data;
        showComments(data[0], "", $rootContainer);
        $('.auto-size').each(function() {
            autoSize(this);
        }).on('input', function() {
            autoSize(this);
        });
    });

    $("#comment_form").on("submit", function (event) {
        event.preventDefault();
        var content = $("#comment_content").val();
        ajaxWrapper("/comments/addComment", "POST", {
                user_id : userId,
                user_name : userName,
                post_id : postId,
                content : content,
                parent_id : 0},
            function(data) {
                if (data.success) {
                    var commentId = data.message;
                    var mainBlock = '<div class = \"panel panel-default\" id = \"comment_'+ commentId +'\">' +
                        '<div class = \"panel-heading\" id = \"user_'+ commentId +'\" name = \"'+userName+'\">' + userName + '<button type=\"button\" class=\"btn btn-link edit\" id = \"'+ commentId +'\">Редактировать</button><button type=\"button\" class=\"btn btn-link delete\" id = \"'+ commentId +'\">Удалить</button></div>' +
                        '<div class = \"panel-body\" id = \"comment_content_' + commentId + '\">' + content + '</div>' +
                        '<div class = \"panel-footer\" align=\"right\" id = \"comment_footer_'+ commentId +'\">' +
                        '<button type=\"button\" class = \"btn btn-default reply\" name = \"'+ commentId +'\" id = \"'+ commentId +'\">Ответить</button>' +
                        '</div></div>';
                    $rootContainer.append(mainBlock);
                } else {
                    alert(data.message);
                }
            });
    });

    $(document).on("click", ".reply", function () {
        var parentId = $(this).attr("id");
        var commentId = $(this).attr("name");
        var userName = $("#user_"+commentId).attr("name");
        $("#reply_text_area_"+parentId).val(userName+", ");
        if (parentId == 0) {
            parentId = commentId;
        }
        $("#reply_"+parentId).val(commentId);
    });

    $(document).on("click", ".delete", function () {
        event.preventDefault();
        var commentId = $(this).attr("id");
        ajaxWrapper("/comments/deleteComment", "DELETE", {comment_id : commentId},
            function(data) {
                $("#comment_"+commentId).html("Комментарий удален :3");
            });
    });

    $(document).on("click", ".edit", function () {
        var commentId = $(this).attr("id");
        if (!editing) {
            editing = true;
            editContent = $("#comment_content_"+commentId+"").clone();
            editFooter = $("#comment_footer_"+commentId+"").clone();
            $("#comment_content_"+commentId+"").html("<textarea class=\"form-control\" id = \"editText\" rows=\"1\"></textarea>");
            var footEdit = '<button type=\"button\" class = \"btn btn-default saveEdit\" id = \"'+ commentId +'\">Сохранить</button>' +
                '<button type=\"button\" class = \"btn btn-default cancel\" id = \"'+ commentId +'\">Отменить</button>';
            $("#comment_footer_"+commentId+"").html(footEdit);
        }
    });

    $(document).on("click", ".saveEdit", function () {
        var commentId = $(this).attr("id");
        var newContent = $("#editText").val();
        if (editContent.text() == newContent || newContent == "") {
            $("#comment_content_"+commentId+"").replaceWith(editContent);
            $("#comment_footer_"+commentId+"").replaceWith(editFooter);
        } else {
            ajaxWrapper("/comments/editComment", "POST", {id : commentId, content : newContent}, function(data) {
                if (data.success) {
                    $("#comment_content_"+commentId+"").html(data.message);
                    $("#comment_footer_"+commentId+"").replaceWith(editFooter);
                } else {
                    alert(data.message);
                }
            });
        }
        editing = false;
    });

    $(document).on("click", ".cancel", function () {
        var commentId = $(this).attr("id");
        $("#comment_content_"+commentId+"").replaceWith(editContent);
        $("#comment_footer_"+commentId+"").replaceWith(editFooter);
        editing = false;
    });

    $(document).on("click", ".submit2", function () {
        event.preventDefault();
        var commentId = $(this).attr("id");
        var content = $("#reply_text_area_"+commentId).val();
        var parentId = $("#reply_"+commentId+"").val();
        ajaxWrapper("/comments/addComment", "POST", {user_id : userId,
                user_name : userName,
                post_id : postId,
                content : content,
                parent_id : parentId},
            function(data) {
                if (data.success) {
                    var comment = {
                        id: data.message,
                        userName: userName,
                        parentId: parentId,
                        content: content,
                        deleted: 0
                    };
                    displayComment(comment, "", $("#replies_list_" + parentId));
                } else {
                    alert(data.message);
                }
            });
    });
});

function displayComment(comment, parentName, $container) {
    var marginLeft = 0;
    var headingText = comment.userName;
    if (comment.parent != 0) {
        marginLeft = 48;
        headingText += " ответил " + parentName;
    }
    var mainBlock = '';
    if (comment.deleted == 0) {
        var headRight = '';
        if (comment.userId == userId) {
            headRight = '<button type=\"button\" class=\"btn btn-link edit\" id = \"'+ comment.id +'\">Редактировать</button>' +
                '<button type=\"button\" class=\"btn btn-link delete\" id = \"'+ comment.id +'\">Удалить</button>';
        }
        mainBlock = '<div class = \"panel panel-default\" id = \"comment_' + comment.id + '\" style=\"margin-left: ' + marginLeft + 'px\">' +
            '<div class = \"panel-heading\" id = \"user_' + comment.id + '\" name = \"'+ comment.userName +'\">' + headingText + '' + headRight +
            '</div>' +
            '<div class = \"panel-body\" id = \"comment_content_'+ comment.id+'\">' + comment.content + '</div>' +
            '<div class = \"panel-footer\" align=\"right\" id = \"comment_footer_' + comment.id + '\">' +
            '<button type=\"button\" class = \"btn btn-default reply\" name = \"' + comment.id + '\" id = \"'+ replyParent +'\">Ответить</button>' +
            '</div></div>';
        $container.append(mainBlock);
    } else {
        if (commentsGraph[comment.id] != null) {
            mainBlock = '<div class = \"panel panel-default\" style=\"margin-left: ' + marginLeft + 'px\">' +
                '<div class = \"panel-heading\" id = \"user_' + comment.id + '\" name = \"' + comment.userName+'\">' + headingText + '</div>' +
                '<div class = \"panel-body\" id = \"comment_content_' + comment.id + '\">Комментарий удален</div>' +
                '<div class = \"panel-footer\" align=\"right\" id = \"comment_footer_'+comment.id+'\">' +
                '<button type=\"button\" class = \"btn btn-default reply\" name = \"'+ comment.id +'\" id = \"'+ replyParent +'\">Ответить</button>' +
                '</div></div>';
            $container.append(mainBlock);
        }
    }
}

function displayDeletedComment(comment, $container) {
    var marginLeft = 0;
    if (comment.parent != 0) {
        marginLeft = 48;
    }
    var mainBlock = '<div class = \"panel panel-default\" style=\"margin-left: ' + marginLeft + 'px\">' +
        '<div class = \"panel-body\" id = \"comment_content_' + comment.id + '\">Комментарий удален</div>' +
        '</div>';
    $container.append(mainBlock);
}

function autoSize(element) {
    $element = $(element);
    element.style.overflowY = 'hidden';
    var paddingTopBottom = $element.innerHeight() - $element.height();
    element.style.height = 'auto';

    $element.height(element.scrollHeight - paddingTopBottom);
}

function showComments(data, parentName, $container) {
    for (var i in data) {
        var comment = data[i];
        if (comment.deleted == 0) {
            displayComment(comment, parentName, $container);
        } else {
            if (commentsGraph[comment.id] != null) {
                displayDeletedComment(comment, $container);
            }
        }
        if (commentsGraph[comment.id] != null) {
            $container.append('<button type=\"button\" class=\"btn btn-info\" data-toggle=\"collapse\" data-target=\"#replies_list_' + comment.id + '\">Ответы</button>');
            var isRootComment = comment.parent == 0;
            var replies = '<div id=\"replies_list_' + comment.id + '\" class=\"collapse\"></div><br/>';
            $container.append(replies);
            if (isRootComment) {
                replyParent = comment.id;
                var replyForm = '<br/><form class=\"form-horizontal\"><div class=\"form-group\">' +
                    '<input type=\"hidden\" name=\"reply_' + comment.id + '\" id=\"reply_' + comment.id + '\" value=\"' + comment.id + '\"/>' +
                    '<textarea class=\"col-xs-10 auto-size\" name =\"comment_content\" id = \"reply_text_area_' + comment.id + '\" rows=\"2\" style=\"margin-left: 48px\"></textarea>' +
                    '<button type=\"button\" class=\"btn btn-default submit2\" id = \"' + comment.id + '\">Отправить</button>' +
                    '</div></form><br/>';
                $container.append(replyForm);
            }
            showComments(commentsGraph[comment.id], comment.userName,$("#replies_list_" + comment.id));
        }
    }
}