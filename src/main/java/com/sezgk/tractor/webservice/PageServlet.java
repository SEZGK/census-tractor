package com.sezgk.tractor.webservice;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

// TODO doc
public class PageServlet extends HttpServlet
{
    private static final long serialVersionUID = 1313131313L;
    private static final String pathFormat = "web/%s";

    private enum MimeType
    {
        JAVASCRIPT, HTML, CSS;
    }

    private static final Map<String, MimeType> mimeTypeMap;
    private static final Map<MimeType, String> contentTypeMap;

    static
    {
        mimeTypeMap = new HashMap<String, MimeType>();
        mimeTypeMap.put("html", MimeType.HTML);
        mimeTypeMap.put("js", MimeType.JAVASCRIPT);
        mimeTypeMap.put("css", MimeType.CSS);

        contentTypeMap = new HashMap<MimeType, String>();
        contentTypeMap.put(MimeType.JAVASCRIPT, "text/javascript;charset=UTF-8");
        contentTypeMap.put(MimeType.HTML, "text/html;charset=UTF-8");
        contentTypeMap.put(MimeType.CSS, "text/css;charset=UTF-8");
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String uri = req.getRequestURI();
        String type = inferContentType(uri);
        File respFile = new File(String.format(pathFormat, req.getRequestURI()));

        if (!respFile.exists() || type == null)
        {
            System.out.println(uri);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        else
        {
            String data = FileUtils.readFileToString(respFile, "UTF-8");
            resp.setContentType(type);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(data);
        }
    }

    /**
     * Infers the HTTP content/MIME type for the given URI.
     * 
     * @param uri - the URI to infer the content/MIME type for.
     * @return the content/MIME type, or null if none was found.
     */
    private String inferContentType(String uri)
    {
        String extension = FilenameUtils.getExtension(uri);
        MimeType type = mimeTypeMap.get(extension);
        return contentTypeMap.get(type);
    }
}
