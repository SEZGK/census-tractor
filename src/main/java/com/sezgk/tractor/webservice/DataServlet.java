package com.sezgk.tractor.webservice;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sezgk.tractor.census.CensusTract;
import com.sezgk.tractor.census.CongressionalDistrict;
import com.sezgk.tractor.census.MapCoordinate;
import com.sezgk.tractor.census.StateService;
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

    private static final Map<String, Integer> stateCodes;

    static
    {
        stateCodes = new HashMap<String, Integer>();
        stateCodes.put("md", StateService.MD);
        stateCodes.put("de", StateService.DE);
        stateCodes.put("wv", StateService.WV);
    }

    private class DataRequest
    {
        public String stateCode;
        public int nDistricts;
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        DataRequest r = parseRequestURI(req.getRequestURI());

        if (r.nDistricts == 0 || !stateCodes.containsKey(r.stateCode))
        {
            // ERROR CONDITION. TODO
            r.nDistricts = 1;
            r.stateCode = "md";
        }
        
        int sCode = stateCodes.get(r.stateCode);

        List<CensusTract> tracts = ParsingService.parseTracts(sCode);

        if (sCode == StateService.MD)
        {
            tracts = ParsingService.parsePrecincts(sCode, tracts);
        }

        List<CongressionalDistrict> districts = TractGroupingService.createDistricts(tracts, r.nDistricts);

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(districts);

        resp.setContentType("text/json");
        resp.getWriter().write(json);
    }

    /**
     * Parses the request URI in order to determine the state code and the number of districts to create.
     * 
     * @return the request data wrapped in a <code>DataRequest</code>.
     */
    private DataRequest parseRequestURI(String uri)
    {
        String[] elements = uri.split("/");
        DataRequest r = new DataRequest();
        r.stateCode = elements[elements.length - 2];
        r.nDistricts = Integer.parseInt(elements[elements.length - 1]);
        return r;
    }
}
