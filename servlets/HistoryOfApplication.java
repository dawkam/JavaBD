package pl.polsl.lab.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import pl.polsl.lab.service.database.CRUD;
import pl.polsl.lab.view.ServletView;

/**
 *
 * History of application
 * 
 * @author Dawid Kampa
 * @version 5.1
 */
public class HistoryOfApplication extends HttpServlet {
    
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        ServletConfig config = getServletConfig();
        Boolean error=false;
        StringBuilder responseFromDataBase= new StringBuilder();
        Connection con=null;
        
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup(config.getInitParameter("address"));
            con= ds.getConnection(config.getInitParameter("user"),config.getInitParameter("password"));
            crud.read(con,responseFromDataBase);
            con.close();
        }catch (NamingException | SQLException ex ) {
            responseFromDataBase.append(ex.getMessage());
            error =true;
        }finally {
            if (con != null) try {
            con.close();
        } catch (SQLException ex) {
            error =true;
            responseFromDataBase.append(ex.getMessage());
        }
        }
        servletView.historyOfApplicationView(response,responseFromDataBase,error);   
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
        processRequest(request, response);
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "History Of application";
    }// </editor-fold>

}
