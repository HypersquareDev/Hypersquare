package hypersquare.hypersquare.plot;

import hypersquare.hypersquare.Hypersquare;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    public static void changePlotName(int plotID, String newName) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "UPDATE Plots SET name = ? WHERE PlotID = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newName);
            pstmt.setInt(2, plotID);

            pstmt.executeUpdate();

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
    public static void changePlotIcon(int plotID, String newIcon) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "UPDATE Plots SET icon = ? WHERE PlotID = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newIcon);
            pstmt.setInt(2, plotID);

            pstmt.executeUpdate();

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
    public static boolean doesPlayerOwnPlot(String ownerUUID) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean ownsPlot = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "SELECT COUNT(*) AS plotCount FROM Plots WHERE Owner = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, ownerUUID);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int plotCount = rs.getInt("plotCount");
                ownsPlot = plotCount > 0; // If plotCount is greater than 0, the player owns a plot
            }

            rs.close();

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

        return ownsPlot;
    }

    public static String getPlotName(int plotID) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String name = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "SELECT name FROM Plots WHERE PlotID = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, plotID);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                name = rs.getString("name");
            }

            rs.close();

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

        return name;
    }


    public static int getPlotNode(int plotID) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int node = -1;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "SELECT node FROM Plots WHERE PlotID = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, plotID);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                node = rs.getInt("node");
            }

            rs.close();

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

        return node;
    }

    public static String getPlotOwner(int plotID) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String owner = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "SELECT Owner FROM Plots WHERE PlotID = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, plotID);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                owner = rs.getString("Owner");
            }

            rs.close();

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

        return Bukkit.getOfflinePlayer(UUID.fromString(owner)).getName();
    }

    public static void updateLocalData(int plotID){
        List data = new ArrayList();
        data.set(0,getPlotName(plotID));
        data.set(1,getPlotOwner(plotID));
        data.set(2,getPlotNode(plotID));
        Hypersquare.localPlotData.put(plotID,data);

    }

    public static List getPlotData(int plotID){
        if (Hypersquare.localPlotData.get(plotID) != null)
        {
            return Hypersquare.localPlotData.get(plotID);
        } else {
            updateLocalData(plotID);
            return Hypersquare.localPlotData.get(plotID);
        }
    }

    public static String[] getPlotDevs(int plotID) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String devsString = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "SELECT devs FROM Plots WHERE PlotID = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, plotID);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                devsString = rs.getString("devs");
            }

            rs.close();

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

        String[] devs = devsString.split(",");

        return devs;
    }
    public static String getRawDevs(int plotID) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String devsString = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "SELECT devs FROM Plots WHERE PlotID = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, plotID);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                devsString = rs.getString("devs");
            }

            rs.close();

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


        return devsString;
    }

    public static void addDev(int plotID, UUID playerID) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "UPDATE Plots SET devs = ? WHERE PlotID = ?";

            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, getRawDevs(plotID) + "," + playerID);
            pstmt.setInt(2, plotID);

            pstmt.executeUpdate();

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




}
