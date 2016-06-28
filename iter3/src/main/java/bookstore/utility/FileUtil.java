package bookstore.utility;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

/**
 * Created by codeworm on 6/10/16.
 *
 * utilities about file
 */
public class FileUtil {
    // get uploaded file's content
    public static byte[] getUploadContent(HttpServletRequest request, String fieldName) {
        byte[] res = null;

        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(8 * 1024 * 1024); // max 8mb

        try {
            // Parse the request
            List<?> items = upload.parseRequest(request);

            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();

                if (!item.isFormField() && item.getFieldName().equals(fieldName)) {
                    // Process a file upload
                    InputStream uploadedStream = item.getInputStream();

                    res = IOUtils.toByteArray(uploadedStream);

                    uploadedStream.close();
                }
            }

            return res;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    // load a file from disk
    public static byte[] loadFile(HttpServletRequest request, String filename) {
        if (filename == null) {
            return null;
        }

        byte[] res = null;
        FileInputStream fis = null;
        try {
            String full_name = request.getSession().getServletContext().getRealPath(filename);
            File file = new File(full_name);
            fis = new FileInputStream(file);
            res = new byte[fis.available()];
            fis.read(res);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return res;
    }
}
