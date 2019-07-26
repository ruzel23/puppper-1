var data = [];
var user_id = null;
var tempBody = null;

$(function () {

    init();

    axios.get('/api/posts')
        .then(function (response) {
            showPosts(response.data);
            console.log(response);
        });

    function getCookie(name) {
        let match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'));
        if (match) {
            console.log(match[2]);
            return match[2];
        } else {
            console.log('--something went wrong---');
        }
    }

    function init() {
        user_id = getCookie("user_id");
        userName = getCookie("user_name");
    }

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
    <button class="btn btn-outline-success saveEdit" id="${postId}" type="button">Save</button>
    <button class="btn btn-outline-danger cancelEdit" id="${postId}" type="button">Cancel</button>
  </div>
</div>`;
        body.replaceWith(newBody);

    });

    $(document).on('click', '.saveEdit', function () {
        let postId = $(this).attr('id');
        let updated = $('.contentEditor').val();
        console.log(updated);
        if (updated !== '' && updated !== tempBody.text()) {
            axios({
                method: 'put',
                url: '/api/posts/' + postId,
                data: {content : updated}
            }).then(function (response) {
                console.log(response);
                tempBody.text(updated);
                $('#editor').replaceWith(tempBody);
                tempBody = null;
            });
        }
    });

    $(document).on('click', '.cancelEdit', function () {
        $('#editor').replaceWith(tempBody);
        tempBody = null;
    })
});

function showPosts(data) {

    var container = $("#postList");

    for (var i in data) {
        container.append(renderPost(data[i]));
    }
}

function renderPost(post) {
    let author_id = post.author.id;
    let author_name = post.author.name;
    let date = new Date(post.creationDate + "Z");

    let header = postHeader(post.id, author_id, author_name, date);
    let body = `<div class="dropdown-divider m-0 mb-2"></div>
<p class="lead" id="content_${post.id}">${post.content}</p>
<input type="button" name="showComments" id="${post.id}" class="btn btn-info mb-1 showComments" value="Show comments"/>
<br/>
<div id="root_comments_${post.id}"></div>
`;

    return `<div class="postItem media mb-3 pr-3 pt-1 border border-primary rounded" id=${post.id}>
	<img src="/images/userpic.svg" width="50" height="50" class="m-2">
	<div class="media-body">
		${header}
		${body}
	</div>
</div>`
}

function postHeader(post_id, author_id, author_name, date) {
    let dropdown = author_id.toString() === user_id ?
        `<div class="bd-highlight">
<div class="dropdown">
<button class="btn btn-link dropdown-toggle" type="button" data-toggle="dropdown">
</button>
<div class="dropdown-menu dropdown-menu-right">
<button class="dropdown-item editPost" id="${post_id}">Edit</button>
<button class="dropdown-item deletePost" id="${post_id}"=>Delete</button>
</div>
</div>
</div>`
        : '';

    return `<div class="d-flex bd-highlight">
	<div class="flex-grow-1 bd-highlight">
		<a class="h3 m-0" href="#">${author_name}</a>
		<a class="h7 ml-2 text-muted" href="#">${date.getDate() + '.' + date.getMonth()
    + ' ' + date.getHours() + ':' + date.getMinutes()}</a>
	</div>
	${dropdown}
</div>`;
}
