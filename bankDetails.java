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
public class bankDetails extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        PrintWriter pw=response.getWriter();
        String ifsc=request.getParameter("ifsc");
        pw.print("<html>");
        pw.print("<table>");
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","Gadare@1998");  
            PreparedStatement pst=con.prepareStatement("select br.bank_id,br.branch,br.address,br.city,br.district,br.state,b.name from branches br,banks b where br.bank_id=b.id and br.ifsc=?");
            pst.setString(1, ifsc);
            ResultSet rs=pst.executeQuery();
            if(!rs.isBeforeFirst()){ 
                pw.println("BANK WITH ABOVE IFSC CODE DOES NOT EXIST");
                return;
            }
            pw.print("<tr><th>BankID</th><th>Branch</th><th>Address</th><th>City</th><th>District</th><th>State</th><th>BankName</th><th>City</th></tr>");
            while(rs.next())
                pw.print("<tr>"+"<td>"+rs.getInt(1)+"</td><td>"+rs.getString(2)+"</td><td>"+rs.getString(3)+"</td><td>"+rs.getString(4)+"</td><td>"+rs.getString(5)+"</td><td>"+rs.getString(6)+"</td><td>"+rs.getString(7)+"</td></tr>");
            pw.print("</table>");
            pw.print("</html>");
        }
        catch(Exception e){
            pw.print(e);
        }
    }
}
