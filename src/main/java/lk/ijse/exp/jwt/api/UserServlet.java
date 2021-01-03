/*
 * Copyright (c) 2020 - Present Ranjith Suranga. All Rights Reserved.
 * Licensed under the MIT License. See LICENSE in the project root for license information.
 */

package lk.ijse.exp.jwt.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import lk.ijse.exp.jwt.model.User;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Ranjith Suranga <suranga@ijse.lk>
 **/

/**
 * @author : Damika Anupama Nanayakkara <damikaanupama@gmail.com>
 * @since : 09/12/2020
 **/

@WebServlet(name = "UserServlet", urlPatterns = "/users")
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        /* CORS Policy */
//        resp.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");

        String id = req.getParameter("id");
        BasicDataSource cp = (BasicDataSource) getServletContext().getAttribute("cp");
        resp.setContentType("application/json");

        try (Connection connection = cp.getConnection()) {
            PrintWriter out = resp.getWriter();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM user" + ((id != null) ? " WHERE id=?" : ""));
            if (id != null) {
                pstm.setObject(1, id);
            }
            ResultSet rst = pstm.executeQuery();
            List<User> usersList = new ArrayList<>();
            while (rst.next()) {
                id = rst.getString(1);
                String name = rst.getString(2);
                String password = rst.getString(3);
                String email = rst.getString(4);
                usersList.add(new User(id, name, password,email));
            }

            if (id != null && usersList.isEmpty()) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            } else {
                Jsonb jsonb = JsonbBuilder.create();
                out.println(jsonb.toJson(usersList));
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /* *//* CORS Policy *//*
        resp.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
*/
        BasicDataSource cp = (BasicDataSource) getServletContext().getAttribute("cp");
        try (Connection connection = cp.getConnection()) {
            Jsonb jsonb = JsonbBuilder.create();
            User user = jsonb.fromJson(req.getReader(), User.class);

            /* Validation Logic */
            if (user.getId() == null || user.getName() == null || user.getPassword() == null || user.getEmail() == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            if (!user.getId().matches("U\\d{3}") || user.getName().trim().isEmpty() || user.getEmail().trim().isEmpty()||user.getPassword().length()<10) {

                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO user VALUES (?,?,?,?)");
            pstm.setString(1, user.getId());
            pstm.setString(2, user.getName());
            pstm.setString(3, user.getPassword());
            pstm.setString(4, user.getEmail());
            if (pstm.executeUpdate() > 0) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (SQLIntegrityConstraintViolationException ex) {
            System.out.println("------------------------------------");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch ( JsonbException ex) {
            // System.out.println("------------------------------------");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (SQLException throwables) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throwables.printStackTrace();
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /*   *//* CORS Policy *//*
        resp.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
*/
        String id = req.getParameter("id");
        if (id == null || !id.matches("U\\d{3}")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        BasicDataSource cp = (BasicDataSource) getServletContext().getAttribute("cp");
        try (Connection connection = cp.getConnection()) {
            Jsonb jsonb = JsonbBuilder.create();
            User user = jsonb.fromJson(req.getReader(), User.class);

            /* Validation Logic */
            if (user.getId() == null || user.getName() == null || user.getPassword() == null || user.getEmail() == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            if (user.getName().trim().isEmpty() || user.getEmail().trim().isEmpty()||user.getPassword().length()<10) {

                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM user WHERE id=?");
            pstm.setObject(1, id);
            if (pstm.executeQuery().next()) {
                pstm = connection.prepareStatement("UPDATE user SET name=?, password=?, email=? WHERE id=?");
                pstm.setObject(1, user.getName());
                pstm.setObject(2, user.getPassword());
                pstm.setObject(3, user.getEmail());
                pstm.setObject(4, id);
                if (pstm.executeUpdate() > 0) {
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } else {
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (JsonbException exp) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /* CORS Policy *//*
        resp.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
*/
        String id = req.getParameter("id");
        if (id == null || !id.matches("U\\d{3}")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        BasicDataSource cp = (BasicDataSource) getServletContext().getAttribute("cp");
        try (Connection connection = cp.getConnection()) {
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM user WHERE id=?");
            pstm.setObject(1, id);
            if (pstm.executeQuery().next()) {
                pstm = connection.prepareStatement("DELETE FROM user WHERE id=?");
                pstm.setObject(1, id);
                boolean success = pstm.executeUpdate() > 0;
                if (success) {
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } else {
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLIntegrityConstraintViolationException ex) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (SQLException throwables) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
    }

}
