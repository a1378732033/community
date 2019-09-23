package life.gjj.community.community.controller;

import life.gjj.community.community.dto.FileDTO;
import life.gjj.community.community.exception.CustomizeErrorCode;
import life.gjj.community.community.exception.CustomizeException;
import life.gjj.community.community.provider.AliyunProvider;
import life.gjj.community.community.provider.UcloudProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class FileController {
    @Autowired
    UcloudProvider ucloudProvider;
    @Autowired
    AliyunProvider aliyunProvider;
    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request) throws IOException {
        MultipartHttpServletRequest multipartHttpServletRequest= (MultipartHttpServletRequest)request;
        MultipartFile file = multipartHttpServletRequest.getFile("editormd-image-file");
        //ud文件存储
        try {
            String fileName = ucloudProvider.upload(file.getInputStream(), file.getContentType(), file.getOriginalFilename());
            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(1);
            fileDTO.setUrl(fileName);
            return fileDTO;
        } catch (IOException e) {
            throw  new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }
        //阿里云文件存储
//        String fileName = aliyunProvider.upload(file.getInputStream(), file.getOriginalFilename(), file.getContentType());
//        FileDTO fileDTO = new FileDTO();
//        fileDTO.setSuccess(1);
//        fileDTO.setUrl(fileName);
//        return fileDTO;
    }
}
