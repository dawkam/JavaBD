package pl.polsl.lab.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import pl.polsl.lab.service.database.CRUD;
import pl.polsl.lab.service.LengthException;
import pl.polsl.lab.service.PalindromeService;
import pl.polsl.lab.service.RangeException;
import pl.polsl.lab.view.ServletView;



/**
 *  Conection between application and html file
 * 
 * @author Dawid Kampa
 * @version 5.1
 */

public class NumericPalindromeServlet extends HttpServlet {

    private PalindromeService palindromeService = new PalindromeService();
    private ServletView servletView= new ServletView();
    private CRUD crud=  new CRUD();   
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void NumericPalindromeServletController (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        
        ServletConfig config = getServletConfig();
        Boolean error=false;
        String responseFromService = null;
        StringBuilder nameOfCookie = new StringBuilder();
        Connection con =null;

        try {
                Context ctx = new InitialContext();
                DataSource ds = (DataSource) ctx.lookup(config.getInitParameter("address"));
                con= ds.getConnection(config.getInitParameter("user"),config.getInitParameter("password"));


            String action = request.getParameter("palindrome");         
            nameOfCookie.append("Palindrome_nr_");
            if(request.getCookies() == null){
            nameOfCookie.append("0");
            }else{
            nameOfCookie.append(request.getCookies().length);
            }      
            switch (action) {
                case "check":
                   try{
                    String palindromeToCheck = request.getParameter("palindromeToCheck");
                    Long.parseLong(request.getParameter("palindromeToCheck"));
                    }catch (NumberFormatException e) {
                    error = true;
                    responseFromService = "No numbers of type int!";
                    }
                    if(!error){
                        if( palindromeService.checkPalindrome(request.getParameter("palindromeToCheck"))){
                            responseFromService = "Palindrome is correct";
                            try {
                                crud.create(con,request.getParameter("palindromeToCheck"), false, true);
                            } catch (SQLException ex) {
                                error=true;
                                responseFromService=ex.getMessage();
                            }
                            nameOfCookie.append("_correct") ;
                            Cookie cookie = new Cookie(nameOfCookie.toString() ,request.getParameter("palindromeToCheck"));
                            cookie.setMaxAge( 63072000 ); 
                            response.addCookie(cookie);
                        }
                        else {
                            responseFromService = "It is not a palindrome";
                            try {
                                crud.create(con,request.getParameter("palindromeToCheck"), false, true);
                            } catch (SQLException ex) {
                                error=true;
                                responseFromService=ex.getMessage();
                            }
                            nameOfCookie.append("_incorrect" );
                            Cookie cookie = new Cookie(nameOfCookie.toString() ,request.getParameter("palindromeToCheck"));
                            cookie.setMaxAge( 63072000 ); 
                            response.addCookie(cookie);
                        }
                    }
                    break;
                case "generate":
                    try{
                    int firstOfRange = Integer.parseInt(request.getParameter("firstOfRange"));
                    int lastOfRange = Integer.parseInt(request.getParameter("lastOfRange"));

                    if(request.getParameter("lengthOfPalindrome").length() != 0){

                        int lengthOfPalindrome = Integer.parseInt(request.getParameter("lengthOfPalindrome"));
                        responseFromService = palindromeService.generatePalindrome(firstOfRange,lastOfRange,lengthOfPalindrome);
                        crud.create(con,responseFromService, true, true);
                        nameOfCookie.append("_correct") ;
                        Cookie cookie = new Cookie(nameOfCookie.toString() ,responseFromService);
                        cookie.setMaxAge( 63072000 ); 
                        response.addCookie(cookie);

                    }else{
                        responseFromService =palindromeService.generatePalindrome(firstOfRange,lastOfRange);
                        crud.create(con,responseFromService, true, true);
                        nameOfCookie. append("_correct") ;
                        Cookie cookie = new Cookie(nameOfCookie.toString() ,responseFromService);
                        cookie.setMaxAge( 63072000 ); 
                        response.addCookie(cookie);
                    }
                    }catch (NumberFormatException e) {
                        error = true;
                        responseFromService = "No numbers of type int!";
                    }catch (RangeException | LengthException | SQLException e){
                        error = true;
                        responseFromService = e.getMessage();
                    }
                    break;
                default:
                    error=true;
                    responseFromService="Unknown error";
                    break;
            }

            try {
                con.close();
            } catch (SQLException ex) {
                error=true;
                responseFromService = ex.getMessage();
            }
        }catch (NamingException | SQLException ex ) {
                responseFromService=ex.getMessage();
                error =true;
        }finally {
            if (con != null) try {
            con.close();
        } catch (SQLException ex) {
            error =true;
            responseFromService=ex.getMessage();
        }
        }
        servletView.numericPalindromeServletView(response, responseFromService, error);

    }


    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NumericPalindromeServletController(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NumericPalindromeServletController(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Application has capability of checking or generating palindrome";
    }

    
}
