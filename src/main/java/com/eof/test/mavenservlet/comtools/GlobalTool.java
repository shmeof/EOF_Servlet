package com.eof.test.mavenservlet.comtools;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 默认配置加载
 */
public class GlobalTool {
    private static Logger mLogger = Logger.getLogger(GlobalTool.class);
    private static Map<String, String> mConf = null;

    private static int loadPropertyFile(String strFile) {
        mConf = new HashMap<String, String>();
        Properties property = new Properties();
        try {
            property.load(new FileInputStream(strFile));
        } catch (FileNotFoundException e) {
            System.out.println("GlobalTool e: " + e.toString());
            mLogger.error("exception:" + e);
            return -1;
        } catch (IOException e) {
            System.out.println("GlobalTool e: " + e.toString());
            mLogger.error("exception:" + e);
            return -2;
        }
        for (String key : property.stringPropertyNames()) {
            mConf.put(key, property.getProperty(key));
        }
        return 0;
    }

    public static String getKey(String key) {
        Object o = mConf.get(key);
        if (o != null) {
            return o.toString();
        } else {
            return "";
        }
    }

    public static String getKey(String key, String defaultValue) {
        if (mConf == null) {
            return defaultValue;
        }
        Object o = mConf.get(key);
        if (o != null) {
            return o.toString();
        } else {
            return defaultValue;
        }
    }

    public static int loadFile(String strFileName) {
        int iRet = loadPropertyFile(strFileName);
        if (iRet != 0) {
            return iRet;
        }
        return 0;
    }

    public static int loadDBConfig(String strFilename) {
        int iRet = loadPropertyFile(strFilename);
        if (iRet != 0) {
            return iRet;
        }

        return 0;
    }

    /**
     * 初始化数据库连接
     * @return
     */
    public static Connection initConn() {
        Connection conn = null;
        String strUrl = getDBUrl();
        mLogger.info("dbUrl:" + strUrl);
        try {
            String dbuser = getKey("dbuser", "root");
            String dbpasswd = getKey("dbpasswd", "123456");
            mLogger.info("dbUrl:" + strUrl + " dbuser:" + dbuser + " dbpasswd:" + dbpasswd);
            conn = DriverManager.getConnection(strUrl, getKey("dbuser", "root"), getKey("dbpasswd", "123"));
        } catch (SQLException e) {
            e.printStackTrace();
            mLogger.error("initConn getConnection:" + strUrl, e);
        }
        return conn;
    }

    /**
     * 关闭数据库连接
     * @param conn
     * @param stPs
     */
    public static void closeConn(Connection conn, Statement stPs) {
        try {
            if (stPs != null) {
                stPs.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mLogger.error("closeConn:", e);
        }
    }

    private static String getDBUrl() {
        String dbname = getKey("dbname", "eofs_db");
        String dbIP = getKey("dbip", "localhost");
        String dbPort = getKey("dbport", "3306");
        StringBuffer sb = new StringBuffer("jdbc:mysql://");
        String urlparam = "";
        sb.append(dbIP).append(":").append(dbPort).append("/").append(dbname);
        if (!urlparam.isEmpty()) {
            sb.append("?").append(urlparam);
        }
        return sb.toString();
    }

    public static String getLocalIP() {
        String localIP = "";
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            mLogger.error("getLocalIP()" + e.toString());
            e.printStackTrace();
            return "";
        }
        localIP = addr.getHostAddress();// 获得本机IP
        return localIP;
    }

    public static String getBasePath() {
        return getKey("projecthome", "D:/workplace/table_authority/table_authority");
    }

    public static String getCurrentHour() {
        Date date = new Date();
        SimpleDateFormat currentTime = new SimpleDateFormat("yyyyMMdd_HH");
        String strCurrent = currentTime.format(date);
        return strCurrent;
    }

    /**
     *
     * @param log4jProperties
     * @param propertiesfile
     * @return
     */
    public static int init(final String log4jProperties, final String propertiesfile) {
        // 全局配置
        int iRet = GlobalTool.loadDBConfig(propertiesfile);
        if (iRet < 0) {
            System.out.println("GlobalTool init error");
            return iRet;
        }

        // 初始化log4j：方法二
        PropertyConfigurator.configure(log4jProperties);
        mLogger.info("init done");

        // 数据库Driver测试
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("init:" + e.toString());
            mLogger.error("init:" + e.toString());
        }
        return 0;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        //
    }
}
