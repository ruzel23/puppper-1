var data = [];
var loading = true;
var user_id = null;
var tempBody = null;

$(function () {

    init();

    $(document).on('click', '#createPost', function () {
        let content = $('#postContent').val();

        if (content !== '' && content.length <= 140) {
            axios({
                method: 'post',
                url: '/api/posts/',
                data: {
                    content: content,
                    author: {
                        id: user_id
                    }
                }
            }).then(function (response) {
                console.log(response);
                $("#postList").append(renderPost(response.data));
            }).catch(function (error) {
                console.log(error);
            });
        }
    });

    $(document).on('click', '.deletePost', function () {
        let postId = $(this).attr("id");
        axios.delete('/api/posts/' + postId)
            .then(function (response) {
                console.log(response);
                $('.postItem').html('post deleted');
            });
    });

    $(document).on('click', '.editPost', function () {
        let postId = $(this).attr('id');
        let body = $('#content_' + postId.toString());
        tempBody = body.clone();
        let newBody = `<div class="input-group" id="editor">
  <input type="text" class="form-control contentEditor" placeholder="Edit post" value="${tempBody.text()}">
  <div class="input-group-append" id="button-addon4">
    <button class="btn btn-outline-success savePost" id="${postId}" type="button">Save</button>
    <button class="btn btn-outline-danger restorePost" id="${postId}" type="button">Cancel</button>
  </div>
</div>`;
        body.replaceWith(newBody);

    });

    $(document).on('click', '.savePost', function () {
        let postId = $(this).attr('id');
        let updated = $('.contentEditor').val();
        console.log(updated);
        if (updated !== '' && updated !== tempBody.text()) {
            axios({
                method: 'put',
                url: '/api/posts/' + postId,
                data: {content: updated}
            }).then(function (response) {
                console.log(response);
                tempBody.text(updated);
                $('#editor').replaceWith(tempBody);
                tempBody = null;
            });
        }
    });

    $(document).on('click', '.restorePost', function () {
        $('#editor').replaceWith(tempBody);
        tempBody = null;
    });
});

function init() {
    user_id = getCookie("user_id");
    userName = getCookie("user_name");

    axios.get('/api/posts')
        .then(function (response) {
            showPosts(response.data);
            console.log(response);
        }).catch(function (error) {
        console.log(error);
    });
}

function postTemplate(authorInfo, dropMenu, postBody) {
    return `<div>
<div class="postItem media mb-3 pr-3 pt-1 border border-primary rounded">
	<img src="/images/userpic.svg" width="50" height="50" class="m-2">
	<div class="media-body">
		<div class="d-flex bd-highlight">
			<div class="flex-grow-1 bd-highlight">
				${authorInfo}
			</div>
			<div class="bd-highlight">
				<div class="dropdown">
					<button class="btn btn-link dropdown-toggle" type="button" data-toggle="dropdown">
					</button>
					<div class="dropdown-menu dropdown-menu-right" id="dropMenu">
						${dropMenu}
					</div>
				</div>
			</div>
		</div>
		<div class="dropdown-divider m-0 mb-2" id="postBody"></div>
		${postBody}
	</div>
</div>
</div>`;
}

function renderPost(post) {
    let post_id = post.id;

    let authorInfo = `<a class="h3 m-0" href="/users/${post.author.id}">${post.author.name}</a>
<a class="h7 ml-2 text-muted" href="/posts/${post_id}$">${dateTimeParser(new Date(post.creationDate))}</a>`;

    //only show post options if it was published by current user
    let dropMenu = user_id === post.author.id.toString() ?
        `<button class="dropdown-item editPost" id="${post_id}">Edit</button>
<button class="dropdown-item deletePost" id="${post_id}">Delete</button>` : ``;


    let postBody = `<p class="lead" id="content_${post_id}">${post.content}</p>
<input type="button" name="showComments" id=${post_id} class="btn btn-info mb-1 showComments"
       value="Show comments"/>
<br/>
<div id="root_comments_${post_id}"></div>`;

    return postTemplate(authorInfo, dropMenu, postBody);
}

function showPosts(data) {
    try {
        for (var i in data) {
            $('#postList').append(renderPost(data[i]));
        }
    } catch (e) {
        console.log(e);
    }
}

function getCookie(name) {
    let match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'));
    if (match) {
        console.log(match[2]);
        return match[2];
    } else {
        console.log('--something went wrong---');
    }
}

function dateTimeParser(date) {
    var months_arr = ['January', 'February', 'March', 'April', 'May', 'June',
        'July', 'August', 'September', 'October', 'November', 'December'];

    var year = date.getFullYear();
    var month = months_arr[date.getMonth() + 1];
    var day = date.getDate();
    var hours = date.getHours();
    var minutes = "0" + date.getMinutes();
    var seconds = "0" + date.getSeconds();

    return `${day}-${month}-${year} ${hours}:${minutes.substr(-2)}:${seconds.substr(-2)}`;
}
