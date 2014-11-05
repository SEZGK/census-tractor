package com.sezgk.tractor.webservice;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
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
        ResourceHandler rHandler = new ResourceHandler();
        rHandler.setDirectoriesListed(false);
        rHandler.setResourceBase("./src/main/webapp");

        ContextHandler resourceContext = new ContextHandler("/app");
        resourceContext.setHandler(rHandler);

        ServletContextHandler servletContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContext.setContextPath("/");
        servletContext.addServlet(new ServletHolder(new DataServlet()), "/data/*");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]
        { resourceContext, servletContext });

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
