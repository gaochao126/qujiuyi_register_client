package com.jiuyi.qujiuyi.service.common;

import com.jiuyi.qujiuyi.dto.ResponseDto;

/**
 * @description CommonService
 * @author zhb
 * @createTime 2016年1月8日
 */
public interface CommonService {
    /**
     * @description 获取ticket
     * @param obj
     * @return
     */
    public ResponseDto getTicket(Object obj);

    /**
     * @description 获取token
     * @return
     */
    public String getRequestToken();
}