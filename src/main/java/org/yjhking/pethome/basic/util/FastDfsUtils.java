package org.yjhking.pethome.basic.util;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;

/**
 * fastDfs文件上传下载工具类
 *
 * @author YJH
 */
public class FastDfsUtils {
    
    //从classpath：resources中的内容最终会编译到classpath下
    public static String CONF_FILENAME = FastDfsUtils.class.getClassLoader()
            .getResource("fdfs_client.conf").getFile();
    
    
    /**
     * 上传文件
     *
     * @param file
     * @param extName
     * @return
     */
    public static String upload(byte[] file, String extName) {
        
        try {
            ClientGlobal.init(CONF_FILENAME);
            
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;
            
            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
            NameValuePair nvp[] = new NameValuePair[]{
                    new NameValuePair("age", "18"),
                    new NameValuePair("sex", "male")
            };
            String fileIds[] = storageClient.upload_file(file, extName, nvp);
            
            System.out.println("组名：" + fileIds[0]);
            System.out.println("路径: " + fileIds[1]);
            return "/" + fileIds[0] + "/" + fileIds[1];
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 上传文件
     *
     * @param extName
     * @return
     */
    public static String upload(String path, String extName) {
        
        try {
            ClientGlobal.init(CONF_FILENAME);
            
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;
            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
            String fileIds[] = storageClient.upload_file(path, extName, null);
            
            System.out.println("组名：" + fileIds[0]);
            System.out.println("路径: " + fileIds[1]);
            return "/" + fileIds[0] + "/" + fileIds[1];
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 下载文件
     *
     * @param groupName
     * @param fileName
     * @return
     */
    public static byte[] download(String groupName, String fileName) {
        try {
            
            ClientGlobal.init(CONF_FILENAME);
            
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;
            
            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
            byte[] b = storageClient.download_file(groupName, fileName);
            return b;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    /**
     * 删除文件
     *
     * @param groupName
     * @param fileName
     */
    public static void delete(String groupName, String fileName) {
        try {
            ClientGlobal.init(CONF_FILENAME);
            
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;
            
            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
            int i = storageClient.delete_file(groupName, fileName);
            System.out.println(i == 0 ? "删除成功" : "删除失败:" + i);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("删除异常," + e.getMessage());
        }
    }
}