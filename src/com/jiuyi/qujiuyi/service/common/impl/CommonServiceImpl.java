package com.jiuyi.qujiuyi.service.common.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.jiuyi.qujiuyi.common.dit.CacheContainer;
import com.jiuyi.qujiuyi.common.dit.Constants;
import com.jiuyi.qujiuyi.common.util.MD5;
import com.jiuyi.qujiuyi.common.util.StringUtil;
import com.jiuyi.qujiuyi.common.util.SysCfg;
import com.jiuyi.qujiuyi.common.util.URLInvoke;
import com.jiuyi.qujiuyi.dto.RequestDto;
import com.jiuyi.qujiuyi.dto.ResponseDto;
import com.jiuyi.qujiuyi.service.common.CommonService;

/**
 * @description CommonServiceImpl
 * @author zhb
 * @createTime 2016年1月8日
 */
@Service
public class CommonServiceImpl implements CommonService {
    /**
     * @description 获取ticket
     * @param obj
     * @return
     */
    public ResponseDto getTicket(Object obj) {
        String ticket = MD5.getMD5Code(StringUtil.getUniqueSn());
        CacheContainer.getTokenMap().put(MD5.getMD5Code("!@#$%^&*()" + ticket + ")(*&^%$#@!"), System.currentTimeMillis());

        ResponseDto result = new ResponseDto();
        result.setResultCode(0);
        result.setResultDesc("成功");
        Map<String, Object> detail = new HashMap<String, Object>();
        result.setDetail(detail);
        detail.put("ticket", ticket);
        return result;
    }

    /**
     * @description 获取token
     * @return
     */
    public String getRequestToken() {
        // 组装请求数据
        RequestDto requestDto = new RequestDto();
        requestDto.setCmd("getTicket");
        Map<String, String> params = new HashMap<String, String>();
        params.put("hospitalId", SysCfg.getString("hospitalId"));
        requestDto.setParams(params);

        // 请求服务器
        String result = URLInvoke.post(SysCfg.getString("serverUrl"), Constants.gson.toJson(requestDto));

        // 解析处理
        if (StringUtil.isEmpty(result)) {
            return null;
        }
        JsonObject json = Constants.jsonParser.parse(result).getAsJsonObject();
        boolean b = json.has("detail") && json.get("detail").getAsJsonObject().has("ticket");
        String ticket = b ? json.get("detail").getAsJsonObject().get("ticket").getAsString() : null;
        return ticket != null ? MD5.getMD5Code("!@#$%^&*()" + ticket + ")(*&^%$#@!") : null;
    }
}