/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.root;

import database.Connex;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.user.Utilisateur;


public class SignupServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Object object = request.getParameter("save");
            if(object == null){
                RequestDispatcher dispatcher = request.getRequestDispatcher("auth/signup.jsp");
                dispatcher.forward(request, response);
            }else{
                out.println("1");
                String nom = request.getParameter("nom");
                String prenom = request.getParameter("prenom");
                String dateString = request.getParameter("dateNaissance");
                out.println("2");
                LocalDate localDate = LocalDate.parse(dateString);
                out.println("3");
                Date dateNaissance = Date.valueOf(localDate);
                out.println("4");
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                out.println("5");
                
                try{
                    
                    Utilisateur utilisateur = new Utilisateur();
                    utilisateur.setNom(nom);
                    utilisateur.setPrenom(prenom);
                    utilisateur.setDateNaissance(dateNaissance);
                    utilisateur.setEmail(email);
                    utilisateur.setPassword(password);
                    Connection connection = Connex.getConnection();
                    HttpSession session = request.getSession();
                    String idUser = utilisateur.insert(connection);
                    session.setAttribute("idUser", idUser);
                    out.println(idUser);
                    
                    
                    response.sendRedirect("AccueilServlet");
                    
                    connection.close();
                }
                catch(Exception e){
                    request.setAttribute("errorMessage", e.getMessage());
                    RequestDispatcher dispatcher = request.getRequestDispatcher("auth/signup.jsp");
                    dispatcher.forward(request, response);
                }
            }
                
            
        }
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
        return "Short description";
    }// </editor-fold>

}
