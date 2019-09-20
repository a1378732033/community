/**
 * 提交回复
 */
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId,1,content);
}
function comment2target(targetId, type, content) {
    if (!content){
        alert("请输入要回复的内容！")
    }
    $.ajax({
        type:"POST",
        url:"/comment",
        contentType:'application/json',
        data:JSON.stringify({
            "parentId":targetId,
            "content":content,
            "type":type
        }),
        success: function(response) {
            if (response.code==200){
                window.location.reload();
            }else {
                if (response.code==300){
                    var isAccepted=confirm(response.message);
                    if (isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=6692d86b3859773f6e1b&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable",true);

                    }
                }else {
                    alert(response.message);
                }
            }
        },
        dataType:"json"
    });
}
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-"+commentId).val();
    comment2target(commentId,2,content);
}
/**
 * 展开二级评论
 */
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-"+id)
    var collapse = e.getAttribute("data-collapse");
    //折叠二级评论
    if (collapse){
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    }else
        {//展开二级评论
            var subCommentContainer=$("#comment-"+id);
            if (subCommentContainer.children().length!=1){
                comments.addClass("in");
                e.setAttribute("data-collapse","in");
                e.classList.add("active");
            }else {
                $.getJSON("/comment/" + id, function (data) {
                    console.log(data);
                    $.each(data.data.reverse(), function (index, comment) {
                        var mediaLeftElement = $("<div/>", {
                            "class": "media-left",
                        }).append($("<img/>",{
                            "class":"media-object1 img-rounded",
                            "src":comment.user.avatarUrl
                        }));

                        var mediaLBodyElement = $("<div/>", {
                            "class": "media-body",
                        }).append($("<h5/>",{
                            "class":"media-heading",
                            "html":comment.user.name
                        })).append($("<div/>",{
                            "html":comment.content
                        })).append($("<div/>",{
                            "class":"menu"
                        }).append($("<span/>",{
                          "class":"pull-right",
                            "html":moment(comment.gmtCreate).format('YYYY-MM-DD')
                        })));

                       var mediaElement=$("<div/>",{
                           "class":"media"
                       }).append(mediaLeftElement)
                           .append(mediaLBodyElement);
                       var commentElement=$("<div/>",{
                           "class":"col-lg-12 col-md-12  col-sm-12 col-xs-12 comments"
                       }).append(mediaElement);
                       subCommentContainer.prepend(commentElement);
                    });
                    comments.addClass("in");
                    e.setAttribute("data-collapse","in");
                    e.classList.add("active");
                });
            }
    }
}
function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var pervious=$("#tag").val();
    if (pervious.indexOf(value)==-1){
        if (pervious){
            $("#tag").val(pervious+","+value);
        }else {
            $("#tag").val(value);
        }
    }
}
function showSelectTag(){
  $("#select-tag").show();
}