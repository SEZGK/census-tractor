package com.sezgk.tractor.webservice;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

// TODO doc
public class PageServlet extends HttpServlet
{
    private static final long serialVersionUID = 1313131313L;
    private static final String pathPrefix = "web%s";

    private enum MimeType
    {
	JAVASCRIPT, HTML, CSS, PNG;
    }

    private static final Map<String, MimeType> mimeTypeMap;
    private static final Map<MimeType, String> contentTypeMap;

    static
    {
	mimeTypeMap = new HashMap<String, MimeType>();
	mimeTypeMap.put("html", MimeType.HTML);
	mimeTypeMap.put("js", MimeType.JAVASCRIPT);
	mimeTypeMap.put("css", MimeType.CSS);
	mimeTypeMap.put("png", MimeType.PNG);

	contentTypeMap = new HashMap<MimeType, String>();
	contentTypeMap.put(MimeType.JAVASCRIPT, "text/javascript;charset=UTF-8");
	contentTypeMap.put(MimeType.HTML, "text/html;charset=UTF-8");
	contentTypeMap.put(MimeType.CSS, "text/css;charset=UTF-8");
	contentTypeMap.put(MimeType.PNG, "image/png");
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
	String uri = req.getRequestURI();
	String type = inferContentType(uri);
	String path = String.format(pathPrefix, uri);
	InputStream iStream = PageServlet.getResourceAsStream(path);

	if (iStream == null || type == null)
	{
	    System.out.println(path);
	    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
	}
	else if (type.equals("image/png"))
	{
	    BufferedImage img = ImageIO.read(iStream);
	    OutputStream out = resp.getOutputStream();
	    ImageIO.write(img, "png", out);
	    out.close();
	}
	else
	{
	    String data = IOUtils.toString(iStream);
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
    
    /**
     * TODO
     * @param resourcePath
     * @return
     */
    public static final InputStream getResourceAsStream(String resourcePath) {
    	InputStream iStream = PageServlet.class.getClassLoader().getResourceAsStream(resourcePath);
    	
    	if (iStream == null) {
    		iStream = PageServlet.class.getClassLoader().getResourceAsStream("resources/" + resourcePath);
    	}

    	if (iStream == null) {
    		// TODO this sucks
    		throw new RuntimeException();
    	}
    	
    	return iStream;
    }
}
