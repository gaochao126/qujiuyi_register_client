package com.jiuyi.qujiuyi.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.jiuyi.qujiuyi.common.dit.CacheContainer;
import com.jiuyi.qujiuyi.common.dit.Constants;
import com.jiuyi.qujiuyi.common.util.StringUtil;
import com.jiuyi.qujiuyi.common.util.SysCfg;
import com.jiuyi.qujiuyi.dto.ResponseDto;
import com.jiuyi.qujiuyi.service.BusinessException;

/**
 * @description ApiServlet
 * @author zhb
 * @createTime 2015年12月26日
 */
public class ApiServlet extends BaseServlet {
    /** serialVersionUID. */
    private static final long serialVersionUID = 4442010333664446648L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long time = System.currentTimeMillis();

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String reqData = getRequestContent(request);
        if (StringUtil.isEmpty(reqData)) {
            print(response, "");
            return;
        }
        logger.info("requestData--------" + reqData);

        /** 鉴权. */
        ResponseDto responseDto = new ResponseDto();
        JsonObject jsonObject = Constants.jsonParser.parse(reqData).getAsJsonObject();
        String cmd = jsonObject != null && jsonObject.has("cmd") ? jsonObject.get("cmd").getAsString() : null;
        if (cmd == null || !CacheContainer.getServiceMap().containsKey(cmd)) {
            responseDto.setResultCode(Constants.FAILED);
            responseDto.setResultDesc(Constants.CMD_IS_NOT_EXIST);
            String result = Constants.gson.toJson(responseDto);
            logger.info("responseData--------" + result);
            print(response, result);
            return;
        }
        if (!"1".equals(SysCfg.getString("test.switch"))) {
            if (!CacheContainer.getNotAuthMap().containsKey(cmd)) {
                String token = jsonObject.has("token") ? jsonObject.get("token").getAsString() : null;
                if (StringUtil.isEmpty(token) || !CacheContainer.getTokenMap().containsKey(token)) {
                    responseDto.setResultCode(Constants.OAUTH_FAILED);
                    responseDto.setResultDesc(Constants.OAUTH_FAILED_DESC);
                    String result = Constants.gson.toJson(responseDto);
                    logger.info("responseData--------" + result);
                    print(response, result);
                    return;
                } else {
                    CacheContainer.getTokenMap().remove(token);
                }
            }
        }

        /** 获取业务方法. */
        JsonObject params = jsonObject != null && jsonObject.has("params") ? jsonObject.get("params").getAsJsonObject() : new JsonObject();
        Object obj = Constants.applicationContext.getBean(CacheContainer.getServiceMap().get(cmd));
        Method[] methods = CacheContainer.getServiceMap().get(cmd).getMethods();
        Method serviceMethod = null;
        for (Method m : methods) {
            if (m.getName().equals(cmd) && m.getParameterTypes().length == 1) {
                serviceMethod = m;
                break;
            }
        }
        if (serviceMethod == null) {
            logger.info("ApiServlet.doPost#service method not found");
            responseDto.setResultCode(Constants.FAILED);
            responseDto.setResultDesc(Constants.CMD_IS_NOT_EXIST);
            String result = Constants.gson.toJson(responseDto);
            logger.info("responseData--------" + result);
            print(response, result);
            return;
        }

        /** 调用业务方法，并返回结果. */
        try {
            responseDto = (ResponseDto) serviceMethod.invoke(obj, Constants.gson.fromJson(params, serviceMethod.getParameterTypes()[0]));
            if (responseDto != null) {
                responseDto.setCmd(cmd);
            }
        } catch (Exception e) {
        	responseDto.setCmd(cmd);
            responseDto.setResultCode(Constants.FAILED);
            if (e instanceof InvocationTargetException) {
                InvocationTargetException e1 = (InvocationTargetException) e;
                if (e1.getTargetException() instanceof BusinessException) {
                    responseDto.setResultDesc(e1.getTargetException().getMessage());
                    logger.info("ApiServlet.doPost#business exception,{cmd : " + cmd + ", reusultDesc : " + e1.getTargetException().getMessage() + "}");
                } else {
                    responseDto.setResultDesc(Constants.FAILED_DESC);
                    logger.error("ApiServlet.doPost#service interface error,{cmd : " + cmd + ", message : " + e.getMessage() + "}", e1.getTargetException());
                }
            } else {
                responseDto.setResultDesc(Constants.FAILED_DESC);
                logger.error("ApiServlet.doPost#service interface error,{cmd : " + cmd + ", message : " + e.getMessage() + "}", e);
            }
        } finally {
            String result = Constants.gson.toJson(responseDto);
            logger.info("run time --------------------- " + (System.currentTimeMillis() - time));
            logger.info("responseData--------" + result);
            print(response, result);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}