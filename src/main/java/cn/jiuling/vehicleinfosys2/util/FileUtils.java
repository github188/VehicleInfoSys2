package cn.jiuling.vehicleinfosys2.util;

import java.io.*;
import java.nio.channels.FileChannel;

public class FileUtils {
    /**
     * 替换文件中内容
     *
     * @param filePath  文件path
     * @param str       用于定位要替换的行,如果行中包含str,则替换
     * @param toReplace 把找到的行整行替换成toReplace
     */
    public static void replaceFileContent(String filePath, String str, String toReplace) {
        try {
            FileReader f = new FileReader(filePath);
            BufferedReader r = new BufferedReader(f);
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = r.readLine()) != null) {
                if (line.contains(str)) {
                    sb.append(toReplace + "\r\n");
                } else {
                    sb.append(line + "\r\n");
                }
            }
            FileWriter w = new FileWriter(filePath);
            w.write(sb.toString());
            w.flush();
            w.close();
            f.close();
            r.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 复制文件
    public static void copyFile(File sourceFile, File targetFile)
            throws IOException {
        // 新建文件输入流并对它进行缓冲
        FileInputStream input = new FileInputStream(sourceFile);
        BufferedInputStream inBuff = new BufferedInputStream(input);

        // 新建文件输出流并对它进行缓冲
        FileOutputStream output = new FileOutputStream(targetFile);
        BufferedOutputStream outBuff = new BufferedOutputStream(output);

        // 缓冲数组
        byte[] b = new byte[1024 * 5];
        int len;
        while ((len = inBuff.read(b)) != -1) {
            outBuff.write(b, 0, len);
        }
        // 刷新此缓冲的输出流
        outBuff.flush();

        //关闭流
        inBuff.close();
        outBuff.close();
        output.close();
        input.close();
    }

    // 复制文件夹
    public static void copyDirectiory(String sourceDir, String targetDir)
            throws IOException {
        // 新建目标目录
        (new File(targetDir)).mkdirs();
        // 获取源文件夹当前下的文件或目录
        File[] file = (new File(sourceDir)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                // 源文件
                File sourceFile = file[i];
                // 目标文件
                File targetFile = new
                        File(new File(targetDir).getAbsolutePath()
                        + File.separator + file[i].getName());
                copyFile(sourceFile, targetFile);
            }
            if (file[i].isDirectory()) {
                // 准备复制的源文件夹
                String dir1 = sourceDir + "/" + file[i].getName();
                // 准备复制的目标文件夹
                String dir2 = targetDir + "/" + file[i].getName();
                copyDirectiory(dir1, dir2);
            }
        }
    }

    /**
     * 拷贝文件到另一个目录
     *
     * @param srcFile File 要拷贝的文件
     * @param desDir  String 目的目录
     */
    public static void copyFileToDirectory(File srcFile, String desDir) {

        FileInputStream fis = null;
        FileOutputStream fos = null;

        FileChannel in = null;
        FileChannel out = null;

        //只对文件进行处理
        if (!srcFile.isFile()) {
            return;
        }

        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(desDir + File.separator + srcFile.getName());

            in = fis.getChannel();
            out = fos.getChannel();

            //copy文件
            out.transferFrom(in, 0, in.size());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (out != null) {
                    out.close();
                }
                if (fos != null) {
                    fos.close();
                }
                if (in != null) {
                    in.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 删除文件以及文件夹
     */
    public static void deleteFileOrDirector(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    // 把每个文件用这个方法进行迭代
                    deleteFileOrDirector(files[i]);
                }
                file.delete();// 删除文件夹
            }
        }
    }

    public static File transFileNameToGBK(String filePath) {

        String transFileNameToGBK = "";
        try {
            transFileNameToGBK = new String(filePath.getBytes("ISO8859-1"), "gb2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return new File(transFileNameToGBK);
    }

    /**
     * 重命名文件
     * @param oldFilePath
     * @param newFilePath
     */
    public static void renameFile(String oldFilePath,String newFilePath){
        if(!oldFilePath.equals(newFilePath)){//新的文件名和以前文件名不同时,才有必要进行重命名
            File oldfile=new File(oldFilePath);
            File newfile=new File(newFilePath);
            if(!oldfile.exists()){
                return;//重命名文件不存在
            }
            if(newfile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名
                System.out.println(newFilePath+"已经存在！");
            else{
                oldfile.renameTo(newfile);
            }
        }else{
            System.out.println("新文件名和旧文件名相同...");
        }
    }

    public static void main(String[] args) throws Exception {
        /*String filePath = "C:\\Users\\Administrator\\Desktop\\GMap2-Config.js";
		replaceFileContent(filePath, "centerLng:", "centerLng:20,");
		replaceFileContent(filePath, "centerLat:", "centerLat:30,");*/

        File file =   transFileNameToGBK("D:/midUpload\\×Ô¶¯¼ì²â.avi");
        System.out.println(file.getPath());
    }
}
