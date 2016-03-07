package com.jiuyi.qujiuyi.common.dit;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.jiuyi.qujiuyi.dto.line.LineInfoDto;
import com.jiuyi.qujiuyi.service.common.CommonService;
import com.jiuyi.qujiuyi.service.doctor.DoctorService;
import com.jiuyi.qujiuyi.service.line.LineInfoService;
import com.jiuyi.qujiuyi.service.numsource.NumSourceService;
import com.jiuyi.qujiuyi.service.register.RegisterService;

/**
 * @description cache container
 * @author zhb
 * @createTime 2015年12月26日
 */
public class CacheContainer {
    /** 业务map. */
    private static Map<String, Class<?>> serviceMap;

    /** not authentication map. */
    private static Map<String, Object> notAuthMap;

    /** 排队信息map. */
    private static Map<String, LineInfoDto> lineInfoMap;

    /** 号源map(key:号源id). */
    private static Map<String, Long> numSourceMap;

    /** tokenMap(key:ticket, value:创建时间). */
    private static Map<String, Long> tokenMap;

    static {
        serviceMap = new HashMap<String, Class<?>>();
        notAuthMap = new HashMap<String, Object>();
        lineInfoMap = new ConcurrentHashMap<String, LineInfoDto>();
        numSourceMap = new ConcurrentHashMap<String, Long>();
        tokenMap = new ConcurrentHashMap<String, Long>();

        serviceMap.put("getNumSource", NumSourceService.class);
        serviceMap.put("getDoctors", DoctorService.class);
        serviceMap.put("commitOrder", RegisterService.class);
        serviceMap.put("cancelOrder", RegisterService.class);
        serviceMap.put("fetchNumber", RegisterService.class);
        serviceMap.put("syncRegisterInfo", RegisterService.class);
        serviceMap.put("syncDoctorNumSource", NumSourceService.class);
        serviceMap.put("getLineInfo", LineInfoService.class);
        serviceMap.put("getVisitStatus", LineInfoService.class);
        serviceMap.put("getTicket", CommonService.class);

        notAuthMap.put("getTicket", null);
    }

    public static Map<String, Class<?>> getServiceMap() {
        return serviceMap;
    }

    public static Map<String, Object> getNotAuthMap() {
        return notAuthMap;
    }

    public static Map<String, LineInfoDto> getLineInfoMap() {
        return lineInfoMap;
    }

    public static Map<String, Long> getNumSourceMap() {
        return numSourceMap;
    }

    public static Map<String, Long> getTokenMap() {
        return tokenMap;
    }
}