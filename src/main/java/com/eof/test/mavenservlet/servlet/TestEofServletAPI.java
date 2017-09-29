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

/**
 * 测试Servlet API
 * @author guodanyang
 */
public class TestEofServletAPI extends HttpServlet {
    private static final long serialVersionUID = -8296325742194492097L;
    private static final Logger logger = Logger.getLogger(TestEofServletAPI.class);

    private static String func = null;
    private static String param1 = null;

    public TestEofServletAPI() {
        super();
    }

    public void init() throws ServletException {
        String prefix = getServletContext().getRealPath("/");
        // Log4J
        String log4jFile = getServletConfig().getInitParameter("log4j");
        String log4jConfigPath = prefix + log4jFile;
        System.out.println("GlobalTool log4jConfigPath: " + log4jConfigPath);
        String propertiesfile = "global_cfg.conf";
        int iRet = GlobalTool.init(log4jConfigPath, propertiesfile);
        if (iRet != 0) {
            System.out.println("GlobalTool init error");
            throw new ServletException("GlobalTool init err");
        } else {
            System.out.println("GlobalTool init done");
        }
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
            logger.info("done:" + oResult.toString());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkParam(HttpServletResponse resp) {
        if (param1 == null) {
            pushResultJson(ReturnCodeDefine.PARAM_ERR, "user is null", null, resp);
            return false;
        }
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

        logger.info("enter func=" + func + ",user=" + param1);
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
            logger.error("check params error");
            return;
        }
        JSONObject oResult = new JSONObject();
        long endtime = System.currentTimeMillis();
        pushResultJson(iRet, null, oResult, resp);
        logger.info("TestEofServletAPI done:func=" + func + ",param1=" + param1 + ",spent time:" + (endtime - begintime) + "ms");
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
    }
}
