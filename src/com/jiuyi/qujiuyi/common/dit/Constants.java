package com.jiuyi.qujiuyi.common.dit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.ApplicationContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

/**
 * @description 常量类
 * @author zhb
 * @createTime 2015年12月18日
 */
public class Constants {
    /** 常用常量. */
	public static ApplicationContext applicationContext;
    public static final Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    public static final JsonParser jsonParser = new JsonParser();
    public static final ExecutorService executorService = Executors.newFixedThreadPool(20);
    public static final int FAILED = 1;
    public static final int OAUTH_FAILED = 2;
    public static final String FAILED_DESC = "失败";
    public static final String CMD_IS_NOT_EXIST = "接口不存在";
    public static final String OAUTH_FAILED_DESC = "鉴权失败";
    public static final String DATA_ERROR = "请求数据异常";
}