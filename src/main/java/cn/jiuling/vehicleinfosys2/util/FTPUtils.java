package cn.jiuling.vehicleinfosys2.util;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.SocketException;

/**
 * Created by Administrator on 2015/8/6.
 */
public class FTPUtils {

    private static final Logger logger = Logger.getLogger(FTPUtils.class);
    public static String encoding = System.getProperty("file.encoding");

    /*ftp用户名*/
    private String userName;
    /*ftp密码*/
    private String password;
    /*ftp服务器ip*/
    private String ip;
    /*ftp端口*/
    private Integer port;

    private FTPClient ftpClient;

    public FTPUtils(String userName, String password, String ip, Integer port) {
        this.userName = userName;
        this.password = password;
        this.ip = ip;
        this.port = port;
    }

    /**
     * 获取ftp链接
     *
     * @return ftpClient
     */
    public FTPClient getFTPClient() {
        int reply;
        try {
            ftpClient = new FTPClient();

            ftpClient.connect(ip, port);
            ftpClient.login(userName, password);
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                logger.info("FTP服务器拒绝连接！.");
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ftpClient;
    }

    /**
     * 上传文件
     *
     * @param localFile 要上传的文件
     * @param uploadDir 上传文件的路径
     * @return boolean b 上传结果 {true:成功 false:失败}
     */
    public boolean uploadFile(File localFile, String uploadDir) {
        InputStream instream = null;
        boolean result = false;
        try {
            //中文文件名转换
            uploadDir=new String(uploadDir.getBytes(encoding),"iso-8859-1");
            try {
                ftpClient.changeWorkingDirectory(uploadDir);
                //FTP协议规定了两种传输方式分别是ASCII与binary方式：
                //1.ASCII方式，这种机制指，在针对传输内容是ASCII码文本时，文件内容会被调整，有时会造成传输的文件被损坏，ASCII方式会把文件中的某些字符串位丢弃，所以不能保证文件数据的每一位都是重要的。
                //2.二进制方式，首先二进制方式保证了文件内容所有数据位都是重要的。
                //3.经过验证在传输文件类型为非文本内容的文件，使用ASCII传输方式造成copy的文件已经损坏。
                //默认传输方式为ASCII,所以必须显式的设置为bianry
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                instream = new BufferedInputStream(new FileInputStream(localFile));
                result = ftpClient.storeFile(new String(localFile.getName().getBytes(encoding),"iso-8859-1"), instream);
            } finally {
                if (instream != null) {
                    instream.close();
                    ftpClient.logout();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        return result;
    }
}
