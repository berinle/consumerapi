package com.consumerapi.servlet;

import com.berinle.ArrayOfString;
import com.berinle.Employee;
import cxfclient.Util;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ApiServlet extends HttpServlet
{
    private static Logger log = Logger.getLogger(ApiServlet.class);
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug(" in service");
        String server = req.getParameter("server");

        String wsdlLocation = null;

        if(server.equals("jetty")){
            wsdlLocation = "http://localhost:8080/sample-xfire/services/employeeService?wsdl";
        } else if(server.equals("weblogic")){
            wsdlLocation = "http://localhost:7070/sample-xfire/services/employeeService?wsdl";
        } else {
            log.debug("using the default...");
            wsdlLocation = "http://localhost:8080/sample-xfire/services/employeeService?wsdl";
        }

        Util client = new Util();
        client.invoke(wsdlLocation, createEmployee());

        log.info("Done with servlet invocation");

    }

    public Employee createEmployee(){
        Employee e = new Employee();
        e.setFirstName("Gradle");
        e.setLastName("0.9");
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
        e.setDob(dt);
        e.setEmployeeNo(System.currentTimeMillis()+"");
        e.setDobAsString(sdf.format(dt));
        ArrayOfString aos = new ArrayOfString();
        log.debug("is aos null? " + (aos == null));
        List<String> ls = aos.getString();
        ls.add("hello");
        ls.add("world");

        log.info(" Successfully created employee: " + e);
        e.setMyList(aos);
        return e;
    }
}
