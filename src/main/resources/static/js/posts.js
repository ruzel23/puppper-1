$(function () {

    var data = [];
    var user_id = 1;

    axios.get('/posts')
        .then(function (response) {
            showPosts(response.data);
            console.log(response);
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
                <p class="lead">${post.content}</p>
                <div class="input-group mb-3">
                    <input type="text" class="form-control" placeholder="Write a comment">
                    <div class="input-group-append">
                      <button class="btn btn-outline-secondary" type="button" id="button-addon2">Comment</button>
                    </div>
                </div>`;

        return `<div class="postItem media mb-3 pr-3 pt-1 border border-primary rounded" id=${post.id}>
	<img src="images/userpic.svg" width="50" height="50" class="m-2">
	<div class="media-body">
		${header}
		${body}
	</div>
</div>`
    }

    function postHeader(post_id, author_id, author_name, date) {

        let dropdown = author_id === user_id ?
            '<div class="bd-highlight">\n' +
            '<div class="dropdown">\n' +
            '<button class="btn btn-link dropdown-toggle" type="button" data-toggle="dropdown">\n' +
            '</button>\n' +
            '<div class="dropdown-menu dropdown-menu-right">\n' +
            '<button class="dropdown-item" >Edit</button>\n' +
            '<button class="dropdown-item deletePost" >Delete</button>\n' +
            '</div>\n' +
            '</div>\n' +
            '</div>\n'
            : '';

        return `<div class="d-flex bd-highlight">
	<div class="flex-grow-1 bd-highlight">
		<a class="h3 m-0" href="#">${author_name}</a>
		<a class="h7 ml-2 text-muted" href="#">${date.getUTCDate().toString()}</a>
	</div>
	${dropdown}
</div>`;
    }

    $('.postItem').on('click', '.deletePost', function () {
        let postId = $(this).attr("id");
        axios.delete('/posts/' + postId)
            .then(function (response) {
                console.log(response);
                $(this).remove();
            });
    })
});