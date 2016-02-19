package cn.jiuling.vehicleinfosys2.vo;

import cn.jiuling.vehicleinfosys2.util.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件辅助类
 */
public class MultipartFileWrapper implements MultipartFile {


        private File file;

        public MultipartFileWrapper(File file) {
           this.file=file;
        }

        public String getName() {
            return this.file.getName();
        }

        public String getOriginalFilename() {
            return this.file.getName();
        }

        public String getContentType() {
            return null;
        }

        public boolean isEmpty() {
            return false;
        }

        public long getSize() {
            return 1;
        }

        public byte[] getBytes() throws IOException {
            return null;
        }

        public InputStream getInputStream() throws IOException {
            return null;
        }

        public void transferTo(File dest) throws IOException, IllegalStateException {
            FileUtils.copyFile(this.file,dest);
        }
    }
