package com.daily.eshop.cache.ha.util;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *文件上传工具类
 * @@author daiba
 *
 */
public class FileUtils {


    /**
     * 文件数据流获取
     * @param map
     * @param request
     * @return
     *
     */
    public boolean uploadFileRendInfoToMap(Map<String, Object> map, HttpServletRequest request) {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (!isMultipart) {
            return true;
        }
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            File repository = (File) request.getServletContext().getAttribute("javax.servlet.context.tempdir");
            factory.setRepository(repository);
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> items = upload.parseRequest((RequestContext) request);
            Iterator<FileItem> iter = items.iterator();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while (iter.hasNext()) {
                FileItem item = iter.next();
                if (item.isFormField()) {
                    continue;
                }
                //文件名称
                map.put("fileName", item.getName());
                InputStream is = item.getInputStream();
                int len = 1024;
                byte[] tmp = new byte[len];
                int i;
                while ((i = is.read(tmp, 0, len)) > 0) {
                    baos.write(tmp, 0, i);
                }
                map.put("fileBytes", baos.toByteArray().toString());
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
        return false;
    }
}
