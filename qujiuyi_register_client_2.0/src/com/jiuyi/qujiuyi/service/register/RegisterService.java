package com.jiuyi.qujiuyi.service.register;

import com.jiuyi.qujiuyi.dto.ResponseDto;
import com.jiuyi.qujiuyi.dto.register.RegisterDto;

/**
 * @description 预约业务层接口
 * @author zhb
 * @createTime 2015年12月28日
 */
public interface RegisterService {
    /**
     * @description 预约提交
     * @param registerDto
     * @return
     * @throws Exception
     */
    public ResponseDto commitOrder(RegisterDto registerDto) throws Exception;

    /**
     * @description 预约取消
     * @param registerDto
     * @return
     * @throws Exception
     */
    public ResponseDto cancelOrder(RegisterDto registerDto) throws Exception;

    /**
     * @description 预约取号
     * @param registerDto
     * @return
     * @throws Exception
     */
    public ResponseDto fetchNumber(RegisterDto registerDto) throws Exception;

    /**
     * @description 同步挂号信息
     * @param params
     * @return
     * @throws Exception
     */
    public ResponseDto syncRegisterInfo(RegisterDto registerDto) throws Exception;
}