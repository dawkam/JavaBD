/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.lab.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * Class which is responsible for View of Servlets
 * 
 * @author Dawid Kampa
 * @version 5.1
 */
public class ServletView {
    
     /**
     * Method show view for numericPalindromeServlet
     * 
     * @param response 
     * @param responseFromService response from service
     * @param error if an error occurs
     */
    public void numericPalindromeServletView(HttpServletResponse response,String responseFromService, Boolean error){
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Result</title>");            
            out.println("</head>");
            out.println("<body>");
            if(error){   
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, responseFromService);
            }else{
            out.println("<h1>" + responseFromService + "</h1>");
            }
            out.println("</body>");
            out.println("</html>");
        }catch (IOException ex) {
            Logger.getLogger(ServletView.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }       

         /**
         * Method show view for historyOfApplication
         * 
         * @param response 
         * @param responseFromService response from service
         * @param error if an error occurs
         */
        public void historyOfApplicationView(HttpServletResponse response,StringBuilder responseFromService, Boolean error){
        StringBuilder responseFromDataBase= new StringBuilder(); 
        response.setContentType("text/html;charset=UTF-8");
   
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.print("<style>table, th, td {border: 1px solid black;border-collapse: collapse;}th, td {padding: 5px;text-align: center;}");
            out.print("h1 {padding: 5px;text-align: center;}</style>");
            out.println("<title>History of application</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>History of application</h1>");
            out.print("<table style=\"width:100%\">");
            out.print("<tr>");
            out.print("<th>ID</th>");
            out.print("<th>Date</th>");
            out.print("<th>Palindrome</th>");
            out.print("<th>isCorrect</th>");
            out.print("<th>isCreated</th>");
            out.println("</tr>");
            if(error){   
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, responseFromDataBase.toString());
            }else{
            out.print(responseFromService.toString());    
            }   
            out.println("<hr>");
            out.println("</body>");
            out.println("</html>");
        } catch (IOException ex) {
            Logger.getLogger(ServletView.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    } 


}
