package com.jiuyi.qujiuyi.common.handler;

import com.jiuyi.qujiuyi.common.dit.Constants;
import com.jiuyi.qujiuyi.common.util.SysCfg;
import com.jiuyi.qujiuyi.common.util.URLInvoke;
import com.jiuyi.qujiuyi.dto.RequestDto;
import com.jiuyi.qujiuyi.dto.numsource.NumSourceDto;
import com.jiuyi.qujiuyi.service.common.CommonService;

/**
 * @description 同步
 * @author zhb
 * @createTime 2016年1月29日
 */
public class SyncNumSourceThread implements Runnable {
    private NumSourceDto numSourceDto;
    public SyncNumSourceThread(NumSourceDto numSourceDto) {
        this.numSourceDto = numSourceDto;
    }

    @Override
    public void run() {
        RequestDto request = new RequestDto();
        request.setCmd("syncDoctorNumSource");
        String token = Constants.applicationContext.getBean(CommonService.class).getRequestToken();
        request.setToken(token);
        request.setParams(numSourceDto);
        URLInvoke.post(SysCfg.getString("serverUrl"), Constants.gson.toJson(request));
    }
}