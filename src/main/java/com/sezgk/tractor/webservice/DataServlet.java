package com.sezgk.tractor.webservice;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sezgk.tractor.census.CensusTract;
import com.sezgk.tractor.census.CongressionalDistrict;
import com.sezgk.tractor.census.StateCode;
import com.sezgk.tractor.census.TractGroupingService;
import com.sezgk.tractor.census.parser.ParsingService;

/**
 * Implementation of the data servlet. This servlet is responsible for responding to requests hitting the /data/*
 * pattern, where the wildcard is substituted by a valid state code. The response will consist of a serialized JSON
 * object representing the newly drawn congressional districts for the state with the given state code.
 * 
 * @author Ennis Golaszewski
 * 
 */
public class DataServlet extends HttpServlet
{
    private static final long serialVersionUID = 1313131313L;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        List<CensusTract> tracts = ParsingService.parseTracts(StateCode.MD);
        List<CongressionalDistrict> districts = TractGroupingService.createDistricts(tracts);

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(districts);

        resp.setContentType("text/json");
        resp.getWriter().write(json);
    }
}
