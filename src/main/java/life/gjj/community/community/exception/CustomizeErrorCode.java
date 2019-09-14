package life.gjj.community.community.exception;

public enum CustomizeErrorCode implements  ICustomizeErrorCode {
    QUESTION_NOT_FOUND(100,"你找的问题不在要不换个试试"),
    TARGET_PARAM_NOT_FOUND(200,"请选择要评论的内容"),
    NO_LOGIN(300,"未登录请重新登录"),
    SYS_ERROR(400,"服务器异常"),
    TYPE_PARAM_WRONG(500,"评论类型错误或者不存在"),
    COMMENT_NOT_FOUND (501,"评论不存在");

    private  String message;
    CustomizeErrorCode(String message) {
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
    private  Integer code;
    CustomizeErrorCode( Integer code,String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }
}
