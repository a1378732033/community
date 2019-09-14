package life.gjj.community.community.dto;

import life.gjj.community.community.exception.CustomizeErrorCode;
import life.gjj.community.community.exception.CustomizeException;
import lombok.Data;

@Data
public class ResultDTO {
    private Integer code;
    private  String message;
    public  static  ResultDTO error(Integer code, String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static ResultDTO error(CustomizeErrorCode noLogin) {
        return error(noLogin.getCode(),noLogin.getMessage());
    }
    public  static  ResultDTO okOf(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }

    public static ResultDTO error(CustomizeException e) {
        return error(e.getCode(),e.getMessage()) ;
    }
}
