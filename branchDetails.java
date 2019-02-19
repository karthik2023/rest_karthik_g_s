package com.demoBank;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class branchDetails extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        PrintWriter pw=response.getWriter();
        String bankName=request.getParameter("bankName");
        String city=request.getParameter("city");
        pw.print("<html>");
        pw.print("<table>");
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","Gadare@1998");  
            PreparedStatement pst=con.prepareStatement("select ifsc,bank_id,branch,address,district,state from bank_branches where bank_name=? and city=?");
            pst.setString(1, bankName);
            pst.setString(2, city);
            ResultSet rs=pst.executeQuery();
            if(!rs.isBeforeFirst()) {
                pw.println("BANK WITH PROVIDED DETAILS DOES NOT EXIST");
                return;
            }
            pw.print("<tr><th>IFSC</th><th>BankId</th><th>Branch</th><th>Address</th><th>District</th><th>State</th></tr>");
            while(rs.next())
                pw.print("<tr>"+"<td>"+rs.getString(1)+"</td><td>"+rs.getInt(2)+"</td><td>"+rs.getString(3)+"</td><td>"+rs.getString(4)+"</td><td>"+rs.getString(5)+"</td><td>"+rs.getString(6)+"</td></tr>");
            pw.print("</table>");
            pw.print("</html>");
        }
        catch(Exception e){
            pw.print(e);
        }
    }
}
