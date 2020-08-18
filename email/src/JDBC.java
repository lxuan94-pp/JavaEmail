/**
 * Created by Shinelon on 2016/12/10.
 */


import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class JDBC {
    static class Mail {
        private String id;
        private String address;

        Mail(String address) {
            this.id = null; //default
            this.address = address;
        }

        public String getid() {
            return id;
        }

        public void setid(String id) {
            this.id = id;
        }
        public String getaddress() {
            return address;
        }

        public void setaddress(String address) {
            this.address = address;
        }
    }
    private static Connection getConn() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/mail_address";
        String username = "root";
        String password = "yrlx1314";
        Connection conn = null;
        try {
            Class.forName(driver); //classLoader,加载对应驱动
            conn = (Connection) DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    static int insert(Mail mail) {
        Connection conn = getConn();
        int i = 0;
        String sql = "insert into sendmail (address) values(?)";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, mail.getaddress());
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }
    public static String getEmailAddress(int id) {
        try{
            Connection conn = getConn();
            Statement statement = (Statement) conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from sendmail where id = "+String.valueOf(id));
            String address = null;
            while(rs.next()){
                address = rs.getString("address");
            }
            rs.close();
            return address;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }

}
