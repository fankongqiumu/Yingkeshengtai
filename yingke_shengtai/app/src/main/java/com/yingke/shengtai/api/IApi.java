package com.yingke.shengtai.api;


/**
 * Created by yihengyan on 2015/5/21.
 */
public class IApi {
    /*Network method*/
    public static final int NETWORK_METHOD_GET = 0;
    public static final int NETWORK_METHOD_POST = 1;
    public static final String URL_GET_PLACE = "http://virtual.paipai.com/extinfo/GetMobileProductInfo?amount=10000&callname=getPhoneNumInfoExtCallback&mobile=";


    public static  String URL_BASE = "http://218.249.159.112:8080/api10/";//
//    public static  String URL_BASE1 = "http://218.249.159.112:8080/api10/";//
//    private static  final String URL_BASE = "http://localhost:9001/api10/";//


    public static final String URL_ZHUCE_GTECODE = URL_BASE + "smsval";//获取验证码
    public static final String URL_ZHUCE_TWO = URL_BASE + "register"; //登录
    public static final String URL_LOGIN = URL_BASE + "login"; //登录

    public static final String URL_GUIDLIST = URL_BASE + "channels"; //业务指南列表
    public static final String URL_GUIDLIST_TWO = URL_BASE + "channels/" ; //业务指南列表er
    public static final String URL_GUIDLIST_THREE = URL_BASE + "channels/" ; //咨列表
    public static final String URL_CENTERLIST = URL_BASE + "news?page=" ;
    public static final String URL_CENTER_DETAIL = URL_BASE + "news/" ; //咨讯详情
    public static final String URL_SEARCH_LIST = URL_BASE + "mybusiness" ; //业务查询列表
    public static final String URL_CUSTOMER_LIST = URL_BASE + "rootservice" ; //在线咨询列表
    public static final String URL_SEARCH_DETAIL = URL_BASE + "mybusiness/" ; //业务查询详情
    public static final String URL_UPDATE_INFOR = URL_BASE + "profile" ; //更新用户信息
    public static final String URL_UPDATE_PASSWORD = URL_BASE + "password" ; //修改密码
    public static final String URL_SOFT_TERMS = URL_BASE + "terms" ; //软件协议



    public static final String URL_SALE_CUSTOMER_LIST = URL_BASE + "customers" ; //客户列表
    public static final String URL_SALE_BUSSINESS_LIST = URL_BASE + "business" ; //业务列表
    public static final String URL_SALE_BUSSINESS_LIST_LIST = URL_BASE + "bizlist" ; //业务列表
    public static final String URL_SALE_YEJI_LIST = URL_BASE + "gains?page=" ; //业绩
    public static final String URL_SALE_CUSTOMER_COMMENT = URL_BASE + "review?id=" ; //业绩
    public static final String URL_STATUS = URL_BASE + "status" ; //状态接口
    public static final String URL_TRACKS  = URL_BASE + "track/" ; //业务记录
    public static final String URL_SALE_LIST  = URL_BASE + "saleslist" ;
}
