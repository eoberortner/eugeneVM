package org.cidarlab.eugene.app.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cidarlab.eugene.vm.EugeneVM;
import org.json.JSONObject;

/**
 *
 * @author Ernst Oberortner
 */
public class FunctionalEugeneServlet 
	extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public void init()
            throws ServletException {
        super.init();
        
        /*
         * do your initializations here ...
         */
    }

    @Override
    public void destroy() {
    	/*
    	 *  do your clean-up here ...
    	 */
    }

    /**
     * Handles HTTP <code>GET</code> requests.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    	
    	/*
    	 *  handle a GET request here ...
    	 */

    }

    /**
     * Handles HTTP <code>POST</code> requests.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        /*
         * handle a POST request here ... 
         */

    	JSONObject jsonResponse = new JSONObject();

    	/*
    	 * process the request
    	 */
        try {
        	
            String command = request.getParameter("command");
            if ("run".equalsIgnoreCase(command)) {
            	jsonResponse.put("response", 
            			runScript(request.getParameter("script"), 
            					Integer.parseInt(request.getParameter("n"))));
            }
        } catch(Exception e) {
        	try {
        		jsonResponse.put("response", e.toString());
        	} catch(Exception ee) {}
        }
        
        /*
         * respond to the request
         */
        PrintWriter out = null;        
        try {
	        response.setContentType("application/json");
	    	out = response.getWriter();
	    	out.write(jsonResponse.toString());
	    	out.flush();
        } catch(Exception e) {
        	// don't know yet what to do here...
        } finally {
        	if(null != out) {
        		out.close();
        	}
        }
        

    }
    
    private String runScript(String script, int n) 
    		throws Exception {
    	return String.valueOf(
    			EugeneVM.execute(script, n));
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing the servlet's description
     */
    @Override
    public String getServletInfo() {
        return "MiniEugeneServlet serving HTTP POST requests";
    }

}
