package life.gjj.community.community.provider;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Service
public class AliyunProvider {
   private String endpoint = "http://oss-cn-beijing.aliyuncs.com";
   @Value("${alyun-ufile.public}")
   private String accessKeyId;
   @Value("${alyun-ufile.private}")
   private String accessKeySecret;
   @Value("${alyun-ufile.bucketName}")
   private  String bucketName;
    private String objectName ;
      String imgUrl;
    public String upload(InputStream fileStream,String fileName,String mimeType) throws IOException {
        String[] filePaths = fileName.split("\\.");
        if (filePaths.length>1){
            objectName= UUID.randomUUID().toString()+"."+filePaths[filePaths.length-1];
        }else {
            return null;
        }
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
             ObjectMetadata metadata=new ObjectMetadata();
             metadata.setContentLength(fileStream.available());
             metadata.setContentEncoding("utf-8");
             metadata.setContentType(mimeType);
             metadata.setContentDisposition("inline");
            ossClient.putObject(bucketName, objectName,fileStream,metadata);
        Date expiration = new Date(new Date().getTime() + 3600 * 1000);
        URL url=ossClient.generatePresignedUrl(bucketName,objectName,expiration);
        imgUrl = url.toString();
        ossClient.shutdown();
        return imgUrl;
    }
    }
