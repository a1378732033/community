package life.gjj.community.community.model;

import lombok.Data;

@Data
public class User {

  private Integer id;
  private String accountId;
  private String name;
  private String token;
  private Long gmtCreate;
  private Long gmtModified;
  private String avatarUrl;

  public User() {
  }

  public User(Integer id, String accountId, String name, String token, Long gmtCreate, Long gmtModified, String avatarUrl) {
    this.id = id;
    this.accountId = accountId;
    this.name = name;
    this.token = token;
    this.gmtCreate = gmtCreate;
    this.gmtModified = gmtModified;
    this.avatarUrl = avatarUrl;
  }

  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", accountId='" + accountId + '\'' +
            ", name='" + name + '\'' +
            ", token='" + token + '\'' +
            ", gmtCreate=" + gmtCreate +
            ", gmtModified=" + gmtModified +
            ", avatarUrl='" + avatarUrl + '\'' +
            '}';
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Long getGmtCreate() {
    return gmtCreate;
  }

  public void setGmtCreate(Long gmtCreate) {
    this.gmtCreate = gmtCreate;
  }

  public Long getGmtModified() {
    return gmtModified;
  }

  public void setGmtModified(Long gmtModified) {
    this.gmtModified = gmtModified;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }
}
