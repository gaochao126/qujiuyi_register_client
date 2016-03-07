package com.jiuyi.qujiuyi.service.register.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jiuyi.qujiuyi.common.dit.CacheContainer;
import com.jiuyi.qujiuyi.dao.register.RegisterDao;
import com.jiuyi.qujiuyi.dto.ResponseDto;
import com.jiuyi.qujiuyi.dto.line.LineInfoDto;
import com.jiuyi.qujiuyi.dto.register.RegisterDto;
import com.jiuyi.qujiuyi.service.register.RegisterService;

/**
 * @description 预约业务层实现
 * @author zhb
 * @createTime 2015年12月28日
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    protected final static Logger logger = Logger.getLogger(RegisterServiceImpl.class);
    @Autowired
    private RegisterDao registerDao;

    /**
     * @description 预约提交
     * @param registerDto
     * @return
     * @throws Exception
     */
    public ResponseDto commitOrder(RegisterDto registerDto) throws Exception {
        registerDao.commitOrder(registerDto);

        ResponseDto result = new ResponseDto();
        result.setResultCode(registerDto.getResultCode());
        result.setResultDesc(registerDto.getResultDesc());
        Map<String, Object> detail = new HashMap<String, Object>();
        result.setDetail(detail);
        detail.put("visitNo", registerDto.getVisitNo());
        detail.put("billNo", registerDto.getBillNo());
        return result;
    }

    /**
     * @description 预约取消
     * @param registerDto
     * @return
     * @throws Exception
     */
    public ResponseDto cancelOrder(RegisterDto registerDto) throws Exception {
        registerDao.cancelOrder(registerDto);

        ResponseDto result = new ResponseDto();
        result.setResultCode(registerDto.getResultCode());
        result.setResultDesc(registerDto.getResultDesc());
        return result;
    }

    /**
     * @description 预约取号
     * @param registerDto
     * @return
     * @throws Exception
     */
    public ResponseDto fetchNumber(RegisterDto registerDto) throws Exception {
        registerDao.fetchNumber(registerDto);

        ResponseDto result = new ResponseDto();
        result.setResultCode(registerDto.getResultCode());
        result.setResultDesc(registerDto.getResultDesc());
        return result;
    }

    /**
     * @description 同步挂号信息
     * @param params
     * @return
     * @throws Exception
     */
    public ResponseDto syncRegisterInfo(RegisterDto registerDto) throws Exception {
        // 存放号源ID
        CacheContainer.getNumSourceMap().put(registerDto.getNumSourceId(), System.currentTimeMillis());

        // 缓存排队信息
        LineInfoDto lineInfoDto = new LineInfoDto();
        lineInfoDto.setNumSourceId(registerDto.getNumSourceId());
        lineInfoDto.setVisitNo(lineInfoDto.getVisitNo());
        CacheContainer.getLineInfoMap().put(registerDto.getVisitNo(), lineInfoDto);

        // 返回结果
        ResponseDto result = new ResponseDto();
        result.setResultCode(0);
        result.setResultDesc("成功");
        return result;
    }
}