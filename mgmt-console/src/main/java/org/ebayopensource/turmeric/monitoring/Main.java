/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

package org.ebayopensource.turmeric.monitoring;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

/**
 * Main class for standalone running purposes.
 * @author manuelchinea
 *
 */
public class Main {
    
    /** The Constant GET_METRICS_DATA_JSON. */
    public static final String GET_METRICS_DATA_JSON = "{"+
    "\"getMetricsDataResponse\":{"+
    "\"returnData\":[{"+
    "\"criteriaInfo\":{"+
    "\"serviceConsumerType\":\"Operation\","+
    "\"metricName\":\"CallCount\","+
    "\"serviceName\":\"SOAMetricsQueryService\","+
    "\"operationName\":\"getMetricsData\","+
    "\"roleType\":\"server\""+
    "},"+
    "\"diff\":\"0.0\","+
    "\"count2\":\"1.0\","+
    "\"count1\":\"1.0\""+
    "}]"+
    "}"+
    "}";
    
    /**
     * The Class SimpleHandler.
     */
    public static class SimpleHandler extends AbstractHandler {

        /**
         * Handle.
         *
         * @param target the target
         * @param baseRequest the base request
         * @param request the request
         * @param response the response
         * @throws IOException Signals that an I/O exception has occurred.
         * @throws ServletException the servlet exception
         * @see org.eclipse.jetty.server.Handler#handle(java.lang.String, org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
         */
        public void handle(String target,
                Request baseRequest,
                HttpServletRequest request,
                HttpServletResponse response) throws IOException,
                ServletException {
            
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            baseRequest.setHandled(true);
            
            String tmp = request.getParameter("X-TURMERIC-SERVICE-NAME");
            if ("SOAMetricsQueryService".equals(tmp)) {
                tmp = request.getParameter("X-TURMERIC-OPERATION-NAME");
                
                if ("getMetricsData".equals(tmp)) {
                    response.getOutputStream().write(GET_METRICS_DATA_JSON.getBytes());
                }
            }
        }
    }
    
    /**
     * The main method.
     *
     * @param args the arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception
    {
        Server server = new Server(9090);
        server.setHandler(new SimpleHandler());

        server.start();
        server.join();
    }

    

}
