package life.gjj.community.community.dto;

import life.gjj.community.community.exception.CustomizeErrorCode;
import life.gjj.community.community.exception.CustomizeException;
import lombok.Data;

import java.util.List;

@Data
public class ResultDTO<T> {
    private Integer code;
    private  String message;
    private  T data;
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
    public  static <T>  ResultDTO okOf(T t){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        resultDTO.setData(t);
        return resultDTO;
    }
}
