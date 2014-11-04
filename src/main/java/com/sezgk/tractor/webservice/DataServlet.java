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
import com.sezgk.tractor.census.StateCode;
import com.sezgk.tractor.census.TractGroupingService;
import com.sezgk.tractor.census.parser.ParsingService;
import com.sezgk.tractor.census.parser.PoliticalParser;

/**
 * Implementation of the data servlet. This servlet is responsible for
 * responding to requests hitting the /data/* pattern, where the wildcard is
 * substituted by a valid state code. The response will consist of a serialized
 * JSON object representing the newly drawn congressional districts for the
 * state with the given state code.
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
	stateCodes.put("/md", StateCode.MD);
	stateCodes.put("/de", StateCode.DE);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
	int sCode;

	String state = req.getPathInfo();
	sCode = stateCodes.get(state);

	List<CensusTract> tracts = ParsingService.parseTracts(sCode);

	if (sCode == StateCode.MD)
	{
	    tracts = PoliticalParser.parsePrecincts(sCode, tracts);
	}

	List<CongressionalDistrict> districts = TractGroupingService.createDistricts(tracts);

	Gson gson = new GsonBuilder().create();
	String json = gson.toJson(districts);

	resp.setContentType("text/json");
	resp.getWriter().write(json);
    }
}
