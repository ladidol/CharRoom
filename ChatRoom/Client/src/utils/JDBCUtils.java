package utils;


import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class JDBCUtils {


    private static String url;
    private static String user;
    private static String password;
    private static String driver;

    private static DataSource ds;


    static{
        //读取资源文件,获取值

        try {


            Properties pro = new Properties();
            InputStream ins =utils.JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
            pro.load(ins);
            //获取连接池对象
            ds = DruidDataSourceFactory.createDataSource(pro);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void close(Statement stmt, Connection conn){
        close(null,stmt,conn);

    }
    public static void close(ResultSet rs , Statement stmt, Connection conn){
        if (stmt   != null){
            try {
                stmt.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }if (rs != null){
            try {
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    public static DataSource getDataSource(){
        return ds;
    }


}