package life.gjj.community.community.provider;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import cn.ucloud.ufile.http.OnProgressListener;
import life.gjj.community.community.exception.CustomizeErrorCode;
import life.gjj.community.community.exception.CustomizeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

@Service
public class UcloudProvider {
    @Value("${ucloud.ufile.public}")
 private String publickey;
    @Value("${ucloud.ufile.private}")
 private  String privatekey;
    private String bucketName="gjluntan";

    // Bucket相关API的授权器
    public String upload(InputStream fileStream, String mimeType,String fileName){
        String generateFileName="";
        String[] filePaths = fileName.split("\\.");
        if (filePaths.length>1){
            generateFileName= UUID.randomUUID().toString()+"."+filePaths[filePaths.length-1];
        }else {
            return null;
        }
        try {
            ObjectAuthorization objectAuthorization = new UfileObjectLocalAuthorization(publickey, privatekey);
            ObjectConfig config = new ObjectConfig("cn-bj", "ufileos.com");
            PutObjectResultBean response = UfileClient.object(objectAuthorization, config)
                    .putObject(fileStream, mimeType)
                    .nameAs(generateFileName)
                    .toBucket(bucketName)
                    .setOnProgressListener((bytesWritten, contentLength) -> {

                    })
                    .execute();
            if (response!=null && response.getRetCode()==0){
             String url=UfileClient.object(objectAuthorization,config).
                     getDownloadUrlFromPrivateBucket(generateFileName,bucketName,24*60*60)
                     .createUrl();
             return url;
            }else {
            throw  new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
            }
        } catch (UfileClientException e) {
            e.printStackTrace();
            throw  new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        } catch (UfileServerException e) {
            e.printStackTrace();
            throw  new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }
    }
}
