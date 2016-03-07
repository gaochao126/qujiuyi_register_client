package com.jiuyi.qujiuyi.dao.register;

import com.jiuyi.qujiuyi.dto.register.RegisterDto;

/**
 * @description 预约信息DAO层接口
 * @author zhb
 * @createTime 2015年12月28日
 */
public interface RegisterDao {
    /**
     * @description 预约提交
     * @param registerDto
     * @return
     * @throws Exception
     */
    public RegisterDto commitOrder(RegisterDto registerDto) throws Exception;

    /**
     * @description 预约取消
     * @param registerDto
     * @return
     * @throws Exception
     */
    public RegisterDto cancelOrder(RegisterDto registerDto) throws Exception;

    /**
     * @description 预约取号
     * @param registerDto
     * @return
     * @throws Exception
     */
    public RegisterDto fetchNumber(RegisterDto registerDto) throws Exception;
}