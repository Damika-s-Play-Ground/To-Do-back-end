/*
 * Copyright (c) 2020 - Present Ranjith Suranga. All Rights Reserved.
 * Licensed under the MIT License. See LICENSE in the project root for license information.
 */

package lk.ijse.exp.jwt.api;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : Ranjith Suranga <suranga@ijse.lk>
 **/

/**
 * @author : Damika Anupama Nanayakkara <damikaanupama@gmail.com>
 * @since : 09/12/2020
 **/

@WebServlet(name = "UserServlet", urlPatterns = "/users")
public class UserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.getWriter().println("<h1>Customer Servlet</h1>");
    }
}
