package com.jiuyi.qujiuyi.common.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.gson.JsonObject;
import com.jiuyi.qujiuyi.common.dit.CacheContainer;
import com.jiuyi.qujiuyi.common.dit.Constants;
import com.jiuyi.qujiuyi.common.util.StringUtil;
import com.jiuyi.qujiuyi.common.util.SysCfg;
import com.jiuyi.qujiuyi.common.util.URLInvoke;
import com.jiuyi.qujiuyi.dto.RequestDto;
import com.jiuyi.qujiuyi.dto.line.LineInfoDto;
import com.jiuyi.qujiuyi.service.common.CommonService;
import com.jiuyi.qujiuyi.service.line.LineInfoService;

/**
 * @description 排队信息线程
 * @author zhb
 * @createTime 2016年1月7日
 */
public class LineInfoThread implements Runnable {
    private LineInfoDto lineInfoDto;
    protected final static Logger logger = Logger.getLogger(LineInfoThread.class);

    public LineInfoThread(LineInfoDto lineInfoDto) {
        this.lineInfoDto = lineInfoDto;
    }

    @Override
    public void run() {
        try {
            List<LineInfoDto> list = Constants.applicationContext.getBean(LineInfoService.class).getLineInfoList(lineInfoDto);
            Map<String, LineInfoDto> lineInfoMap = new HashMap<String, LineInfoDto>();
            for (String key : CacheContainer.getLineInfoMap().keySet()) {
                LineInfoDto dto = CacheContainer.getLineInfoMap().get(key);
                if (dto != null && lineInfoDto.getNumSourceId() != null && lineInfoDto.getNumSourceId().equals(dto.getNumSourceId())) {
                    lineInfoMap.put(key, dto);
                }
            }
            Map<String, LineInfoDto> _lineInfoMap = new HashMap<String, LineInfoDto>();
            if (list != null && !list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    LineInfoDto dto = list.get(i);
                    dto.setHospitalId(SysCfg.getInt("hospitalId"));
                    dto.setWaitCount(i);
                    _lineInfoMap.put(dto.getVisitNo(), dto);
                }
            } else {
                CacheContainer.getNumSourceMap().remove(lineInfoDto.getNumSourceId());
            }
            for (String key : lineInfoMap.keySet()) {
                if (!_lineInfoMap.containsKey(key)) {
                    LineInfoDto dto = lineInfoMap.get(key);
                    dto.setVisitNo(key);
                    dto.setHospitalId(SysCfg.getInt("hospitalId"));
                    if (StringUtil.isEmpty(dto.getLineNum())) {
                        dto.setLineNum("-1");
                    }
                    dto.setWaitCount(-1);
                    final LineInfoDto dto1 = dto;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // 获取请求token
                            String token = Constants.applicationContext.getBean(CommonService.class).getRequestToken();

                            // 组装请求数据
                            RequestDto requestDto = new RequestDto();
                            requestDto.setCmd("syncLineInfo");
                            requestDto.setToken(token);
                            requestDto.setParams(dto1);

                            // 请求服务器
                            String result = URLInvoke.post(SysCfg.getString("serverUrl"), Constants.gson.toJson(requestDto));

                            // 解析处理
                            if (StringUtil.isEmpty(result)) {
                                return;
                            }
                            JsonObject json = Constants.jsonParser.parse(result).getAsJsonObject();
                            if (json.has("resultCode") && "0".equals(json.get("resultCode").getAsString())) {
                                CacheContainer.getLineInfoMap().remove(dto1.getVisitNo());
                            }
                        }
                    }).start();
                }
            }
            if (list != null && !list.isEmpty()) {
                for (final LineInfoDto dto1 : list) {
                    final LineInfoDto dto2 = CacheContainer.getLineInfoMap().get(dto1.getVisitNo());
                    if (dto2 != null && !Constants.gson.toJson(dto1).equals(Constants.gson.toJson(dto2))) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 获取请求token
                                String token = Constants.applicationContext.getBean(CommonService.class).getRequestToken();

                                // 组装请求数据
                                RequestDto requestDto = new RequestDto();
                                requestDto.setCmd("syncLineInfo");
                                requestDto.setToken(token);
                                requestDto.setParams(dto1);

                                // 请求服务器
                                String result = URLInvoke.post(SysCfg.getString("serverUrl"), Constants.gson.toJson(requestDto));

                                // 解析处理
                                if (StringUtil.isEmpty(result)) {
                                    return;
                                }
                                JsonObject json = Constants.jsonParser.parse(result).getAsJsonObject();
                                if (json.has("resultCode") && "0".equals(json.get("resultCode").getAsString())) {
                                    CacheContainer.getLineInfoMap().put(dto1.getVisitNo(), dto1);
                                }
                            }
                        }).start();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}