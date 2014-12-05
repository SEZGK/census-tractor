package com.sezgk.tractor.webservice;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Provides service to incoming HTTP connections requesting census tract data.
 * 
 * @author Ennis Golaszewski
 */
public class Main
{
    public static void main(String[] args)
    {
        ServletContextHandler servletContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContext.setContextPath("/");
        servletContext.addServlet(new ServletHolder(new DataServlet()), "/data/*");
        servletContext.addServlet(new ServletHolder(new PageServlet()), "/");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]
        { servletContext });

        Server server = new Server(8080);
        server.setHandler(handlers);

        try
        {
            server.start();
            server.join();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
