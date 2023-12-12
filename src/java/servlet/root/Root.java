/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.root;

import database.Connex;
import java.io.IOException;
import java.sql.Connection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.user.Utilisateur;

/**
 *
 * @author PC
 */
public class Root {
    public static boolean isToAcceuil(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();
        Object object = session.getAttribute("idUser");
        if(object!=null){
            response.sendRedirect("AccueilServlet");
        }
        
        return false;
    }
    public static boolean root(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();
        Object object = session.getAttribute("idUser");
        if(object!=null){
            response.sendRedirect("AccueilServlet");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("auth/login.jsp");
        dispatcher.forward(request, response);
        return false;
    }
}
