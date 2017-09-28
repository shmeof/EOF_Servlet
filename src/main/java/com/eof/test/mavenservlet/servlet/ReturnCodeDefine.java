package com.eof.test.mavenservlet.servlet;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回值的定义
 * 
 * @author zhuliyun
 */
public class ReturnCodeDefine {
    public static final int     RET_TYPE_SUCCESS                        = 0;
    public static final int     SYS_ERROR                               = 500;
    public static final int     MD5_CHECK_INVALID                       = 10000;
    public static final int     DB_ERR                                  = 10001;
    public static final int     CMD_ERR                                 = 10002;
    public static final int     PARAM_ERR                               = 10003;
    public static final int     QUERY_DB_EXCEPTION                      = 10004;
    public static final int     QUERY_RESULT_EMPTY                      = 10005;
    public static final int     NO_TABLE_AUTHORITY_RECORD               = 10006;
    public static final int     NO_MATCH_AUTHORITY_RECORD               = 10007;
    public static final int     ACCESS_TYPE_ERR                         = 10008;
    public static final int     ADD_USER_TABLE_AUTHORITY_ERROR          = 10009;
    public static final int     GIANO_RETURN_JSON_ERROR                 = 10010;
    public static final int     INSERT_DB_RECORD_ERROR                  = 10011;
    public static final int     QE_CHECKAUTHORITY_INVALID_USER_OR_GROUP = 201;
    public static final int     QE_CHECKAUTHORITY_INVALID_COLUMNS       = 202;
    public static final int     NO_EXIST_DBNAME                         = 10020;
    public static final int     NOT_ADMIN_USER_NO_DROP_AUTHORITY        = 10021;
    public static final int     TABLE_IS_PUBLISHED_BEFORE               = 10022;
    public static final int     NO_RIGHT_TO_DROP_TABLE                  = 10023;
    public static final int     TABLE_IS_NO_PUBED_BEFORED               = 10024;
    public static final int     GP_TASK_NO_DONE                         = 10025;
    public static final int     GET_RESULT_MSG_NULL                     = 10026;
    public static final int     NO_RIGHT_TO_DELETE                      = 10027;
    public static final int     PUBLIC_GROUP_NOT_CREATER                = 10028;
    public static final int     TABLE_IS_CREATED_BEFORE                 = 10029;
    public static final int     HAS_NO_RIGHT                            = 10030;
    public static final int     PART_TABLE_NO_RIGHT                     = 10032;
    public static final int     INPUT_TABLELIST_INVALID                 = 10031;
    public static final int     TABLE_NO_EXISTS                         = 10034;
    public static final int     COLUMN_NO_PUBED                         = 10035;
    public static final int     COLUMN_NOT_EXIST                        = 10036;
    public static final int     RECORD_NO_MATCH                         = 10037;
    public static final int     NO_TABLE_WARITE_AUTHORITY               = 10038;
    public static final int     TABLE_EXPIRE_AUTHORITY                  = 10039;
    public static final int     GIANO_CRED_VERIFY_ERROR                 = 10040;
    public static final int     CHECK_CRED_GROUP_INVALID_ERROR          = 10041;
    public static final int     CHECK_NAMESPACE_INVALID_ERROR           = 10042;
    static Map<Integer, String> m_errorMsg;
    
    /*
     * 返回值对应的返回信息初始化
     */
    public static void initMsg() {
        m_errorMsg = new HashMap<Integer, String>();
        addErrorMsg(RET_TYPE_SUCCESS, "success");
        addErrorMsg(SYS_ERROR, "sys error");
        addErrorMsg(MD5_CHECK_INVALID, "MD5 verify error");
        addErrorMsg(DB_ERR, "DB error ");
        addErrorMsg(PARAM_ERR, "parameter invalid");
        addErrorMsg(CMD_ERR, "command error");
        addErrorMsg(QUERY_DB_EXCEPTION, "DB query error");
        addErrorMsg(NO_TABLE_AUTHORITY_RECORD, "no table authority record");
        addErrorMsg(NO_MATCH_AUTHORITY_RECORD, "no match authority record");
        addErrorMsg(ACCESS_TYPE_ERR, "access type invalid");
        addErrorMsg(ADD_USER_TABLE_AUTHORITY_ERROR,
                "insert user table authority to db error");
        addErrorMsg(GIANO_RETURN_JSON_ERROR,
                "giano api return is invalid json format");
        addErrorMsg(INSERT_DB_RECORD_ERROR, "insert record to db error");
        addErrorMsg(QE_CHECKAUTHORITY_INVALID_USER_OR_GROUP,
                "failed because of invalid user or group");
        addErrorMsg(QE_CHECKAUTHORITY_INVALID_COLUMNS,
                "failed because of invalid columns");
        addErrorMsg(NO_EXIST_DBNAME, "not exist dbname");
        addErrorMsg(NOT_ADMIN_USER_NO_DROP_AUTHORITY,
                "user is not admin user,cannot drop table");
        addErrorMsg(TABLE_IS_PUBLISHED_BEFORE, "table is published before");
        addErrorMsg(NO_RIGHT_TO_DROP_TABLE, "No right to drop table");
        addErrorMsg(TABLE_IS_NO_PUBED_BEFORED,
                "table is not published before,please publish table");
        addErrorMsg(GP_TASK_NO_DONE, "gp task process is not done");
        addErrorMsg(GET_RESULT_MSG_NULL, "get result msg null");
        addErrorMsg(NO_RIGHT_TO_DELETE, "no right to drop table");
        addErrorMsg(PUBLIC_GROUP_NOT_CREATER,
                "not right to public, only creater group can public table");
        addErrorMsg(TABLE_IS_CREATED_BEFORE, "table is created before");
        addErrorMsg(HAS_NO_RIGHT, "authorized deny");
        addErrorMsg(INPUT_TABLELIST_INVALID,
                "tablelist input is invalid  jsonarray");
        addErrorMsg(PART_TABLE_NO_RIGHT, "part of table authorized deny");
        addErrorMsg(TABLE_NO_EXISTS, "table is not exist");
        addErrorMsg(COLUMN_NO_PUBED, "column is not publish");
        addErrorMsg(COLUMN_NOT_EXIST, "column is not exist");
        addErrorMsg(RECORD_NO_MATCH, "no match record in authority db.");
        addErrorMsg(NO_TABLE_WARITE_AUTHORITY, "no table write authority");
        addErrorMsg(TABLE_EXPIRE_AUTHORITY, "table authority expire");
        addErrorMsg(GIANO_CRED_VERIFY_ERROR, "giano cred verify error");
        addErrorMsg(CHECK_CRED_GROUP_INVALID_ERROR, "check cred group invalid error");
        addErrorMsg(CHECK_NAMESPACE_INVALID_ERROR, "check namespace invalid error");
    }
    
    private static void addErrorMsg(int iCode, String strMsg) {
        m_errorMsg.put(Integer.valueOf(iCode), strMsg);
    }
    
    /**
     * 获取返回信息
     * 
     * @param iReturnCode 错误码
     * @return 错误码描述
     */
    public static String getReturnMsg(int iReturnCode) {
        if (m_errorMsg == null) {
            return "";
        }
        String strMsg = m_errorMsg.get(Integer.valueOf(iReturnCode));
        if (strMsg == null) {
            return "";
        }
        return strMsg;
    }
}
