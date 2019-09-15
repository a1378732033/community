package life.gjj.community.community.dto;

import life.gjj.community.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
    private User user;

//    public QuestionDTO() {
//    }
//
//    public QuestionDTO(long id, String title, String description, Long gmtCreate, Long gmtModified, Long creator, Integer commentCount, Integer viewCount, Integer likeCount, String tag, User user) {
//        this.id = id;
//        this.title = title;
//        this.description = description;
//        this.gmtCreate = gmtCreate;
//        this.gmtModified = gmtModified;
//        this.creator = creator;
//        this.commentCount = commentCount;
//        this.viewCount = viewCount;
//        this.likeCount = likeCount;
//        this.tag = tag;
//        this.user = user;
//    }
//
//    @Override
//    public String toString() {
//        return "QuestionDTO{" +
//                "id=" + id +
//                ", title='" + title + '\'' +
//                ", description='" + description + '\'' +
//                ", gmtCreate=" + gmtCreate +
//                ", gmtModified=" + gmtModified +
//                ", creator=" + creator +
//                ", commentCount=" + commentCount +
//                ", viewCount=" + viewCount +
//                ", likeCount=" + likeCount +
//                ", tag='" + tag + '\'' +
//                ", user=" + user +
//                '}';
//    }
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public Long getGmtCreate() {
//        return gmtCreate;
//    }
//
//    public void setGmtCreate(Long gmtCreate) {
//        this.gmtCreate = gmtCreate;
//    }
//
//    public Long getGmtModified() {
//        return gmtModified;
//    }
//
//    public void setGmtModified(Long gmtModified) {
//        this.gmtModified = gmtModified;
//    }
//
//    public Integer getCreator() {
//        return creator;
//    }
//
//    public void setCreator(Integer creator) {
//        this.creator = creator;
//    }
//
//    public Integer getCommentCount() {
//        return commentCount;
//    }
//
//    public void setCommentCount(Integer commentCount) {
//        this.commentCount = commentCount;
//    }
//
//    public Integer getViewCount() {
//        return viewCount;
//    }
//
//    public void setViewCount(Integer viewCount) {
//        this.viewCount = viewCount;
//    }
//
//    public Integer getLikeCount() {
//        return likeCount;
//    }
//
//    public void setLikeCount(Integer likeCount) {
//        this.likeCount = likeCount;
//    }
//
//    public String getTag() {
//        return tag;
//    }
//
//    public void setTag(String tag) {
//        this.tag = tag;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
}
