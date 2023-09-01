package hypersquare.hypersquare;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://156.155.107.104/chicken_plots";

    static final String USER = "chicken";
    static final String PASS = System.getenv("DB_PASSWORD");

    public static void addPlot(int plotID, String ownerUUID, String icon, String name, int node, String tags, int votes, String size) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "INSERT INTO Plots (PlotID, Owner, devs, builders, icon, name, node, tags, votes, size) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, plotID);
            pstmt.setString(2, ownerUUID);
            pstmt.setString(3, ownerUUID); // devs column
            pstmt.setString(4, ownerUUID); // builders column
            pstmt.setString(5, icon);
            pstmt.setString(6, name);
            pstmt.setInt(7, node);
            pstmt.setString(8, tags);
            pstmt.setInt(9, votes);
            pstmt.setString(10, size);

            pstmt.executeUpdate();

            System.out.println("New plot added successfully.");

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }


    public static List<List<Object>> getPlot(String ownerUUID) {
        Connection conn = null;
        Statement stmt = null;
        List<List<Object>> info = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            String sql = "SELECT * FROM Plots WHERE Owner='" + ownerUUID + "'";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int plotID = rs.getInt("PlotID");
                String owner = rs.getString("Owner");
                String devs = rs.getString("devs");
                String builders = rs.getString("builders");
                String icon = rs.getString("icon");
                String name = rs.getString("name");
                int node = rs.getInt("node");
                String tags = rs.getString("tags");
                int votes = rs.getInt("votes");
                String size = rs.getString("size");

                List<Object> list = new ArrayList<>();
                list.add(plotID);
                list.add(owner);
                list.add(devs);
                list.add(builders);
                list.add(icon);
                list.add(name);
                list.add(node);
                list.add(tags);
                list.add(votes);
                list.add(size);

                info.add(list);
            }

            rs.close();

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return info;
    }

}
