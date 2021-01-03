package lk.ijse.exp.jwt.listner;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

/**
 * @author : Damika Anupama Nanayakkara <damikaanupama@gmail.com>
 * @since : 11/12/2020
 **/
@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        BasicDataSource bds = new BasicDataSource();
        bds.setUsername("root");
        bds.setPassword("root");
        bds.setUrl("jdbc:mysql://localhost:3306/To_Do_List");
        bds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        bds.setConnectionProperties("SSL=falsex");
        bds.setInitialSize(5);//total connections in c.pool
        bds.setMaxTotal(5);//Max connections for db(solution for data traffic)
        ServletContext stx = sce.getServletContext();//get the servlet context
        stx.setAttribute("cp",bds);//input data to s.context as key value pairs

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        BasicDataSource cp = (BasicDataSource) sce.getServletContext().getAttribute("cp");
        try {
            cp.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
