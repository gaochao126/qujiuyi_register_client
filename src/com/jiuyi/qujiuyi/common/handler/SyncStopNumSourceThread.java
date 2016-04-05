package com.jiuyi.qujiuyi.common.handler;

import com.jiuyi.qujiuyi.common.dit.Constants;
import com.jiuyi.qujiuyi.common.util.SysCfg;
import com.jiuyi.qujiuyi.common.util.URLInvoke;
import com.jiuyi.qujiuyi.dto.RequestDto;
import com.jiuyi.qujiuyi.dto.numsource.NumSourceDto;
import com.jiuyi.qujiuyi.service.common.CommonService;

/**
 * @description SyncStopNumSourceThread
 * @author zhb
 * @createTime 2016年4月5日
 */
public class SyncStopNumSourceThread implements Runnable {
    private NumSourceDto numSourceDto;

    public SyncStopNumSourceThread(NumSourceDto numSourceDto) {
        this.numSourceDto = numSourceDto;
    }

    @Override
    public void run() {
        RequestDto request = new RequestDto();
        request.setCmd("syncStopNumSource");
        String token = Constants.applicationContext.getBean(CommonService.class).getRequestToken();
        request.setToken(token);
        request.setParams(numSourceDto);
        URLInvoke.post(SysCfg.getString("serverUrl"), Constants.gson.toJson(request));
    }
}