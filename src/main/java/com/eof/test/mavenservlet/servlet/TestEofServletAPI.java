package com.eof.test.mavenservlet.servlet;

import com.eof.test.mavenservlet.comtools.GlobalTool;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;

/**
 * 测试Servlet API
 * @author guodanyang
 */
public class TestEofServletAPI extends HttpServlet {
    private static final long serialVersionUID = -8296325742194492097L;
    private static final Logger mLogger = Logger.getLogger(TestEofServletAPI.class);

    private static String func = null;
    private static String param1 = null;

    public TestEofServletAPI() {
        super();
    }

    public void init() throws ServletException {
        // Log4J
        // 相对路径
        String prefix = getServletContext().getRealPath("/");
        String log4jFile = getServletConfig().getInitParameter("log4j");
        String log4jConfigPathRel = prefix + log4jFile;
        // 绝对路径
//        String log4jConfigPathAbs = getServletConfig().getInitParameter("log4j");
//        System.out.println("GlobalTool log4jConfigPathAbs: " + log4jConfigPathAbs);
        String propertiesfile = "global_cfg.conf";
        int iRet = GlobalTool.init(log4jConfigPathRel, propertiesfile);
        if (iRet != 0) {
            System.out.println("GlobalTool init error");
            throw new ServletException("GlobalTool init err");
        } else {
            System.out.println("GlobalTool init done");
        }

        // 初始化数据库连接
        Connection conn = GlobalTool.initConn();
        if (null != conn) {
            mLogger.info("conn:" + conn.hashCode());
        } else {
            mLogger.error("conn is null");
        }

        // 测试mysql
        MySQLDemo mysqldemo = new MySQLDemo();
        mysqldemo.test();

        // 初始化错误码描述
        ReturnCodeDefine.initMsg();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8"); // UTF-8
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        doGet(request, response);
    }

    private void pushResultJson(int resultcode, String strMsg, JSONObject oResult, HttpServletResponse resp) {
        String strRetMsg = strMsg;
        if (strMsg == null) {
            strRetMsg = ReturnCodeDefine.getReturnMsg(resultcode);
        }
        if (oResult == null) {
            oResult = new JSONObject();
        }
        try {
            if (oResult.isNull("code")) {
                oResult.put("code", resultcode);
            }
            if (oResult.isNull("msg")) {
                oResult.put("msg", strRetMsg);
            }
            resp.setContentType("text/html;charset=utf-8"); // utf-8
            resp.setHeader("Cache-Control", "no-cache");
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        try {
            PrintWriter out = resp.getWriter();
            out.write(oResult.toString());
            mLogger.info("done:" + oResult.toString());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkParam(HttpServletResponse resp) {
//        if (param1 == null) {
//            pushResultJson(ReturnCodeDefine.PARAM_ERR, "user is null", null, resp);
//            return false;
//        }
        return true;
    }


    /**
     * 初始化参数
     * @param request
     */
    public void initParams(HttpServletRequest request) {
        try {
            request.setCharacterEncoding("UTF-8"); // UTF-8
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        func = request.getParameter("func");
        param1 = request.getParameter("param1");

        mLogger.info("enter func=" + func + ",user=" + param1);
    }

    /**
     * @param request
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        long begintime = System.currentTimeMillis();
        initParams(request);
        int iRet = -1;
        //检查参数
        if (!checkParam(resp)) {
            mLogger.error("check params error");
            return;
        }

        JSONObject oResult = new JSONObject();
        long endtime = System.currentTimeMillis();
        pushResultJson(iRet, null, oResult, resp);
        mLogger.info("TestEofServletAPI done:func=" + func + ",param1=" + param1 + ",spent time:" + (endtime - begintime) + "ms");
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
    }
}
