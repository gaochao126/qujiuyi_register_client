package com.jiuyi.qujiuyi.service.line.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jiuyi.qujiuyi.dao.line.LineInfoDao;
import com.jiuyi.qujiuyi.dto.ResponseDto;
import com.jiuyi.qujiuyi.dto.line.LineInfoDto;
import com.jiuyi.qujiuyi.service.line.LineInfoService;

/**
 * @description 排队业务层实现
 * @author zhb
 * @createTime 2015年12月28日
 */
@Service
public class LineInfoServiceImpl implements LineInfoService {
    @Autowired
    private LineInfoDao lineInfoDao;

    /**
     * @description 获取排队信息
     * @param lineInfoDto
     * @return
     * @throws Exception
     */
    public ResponseDto getLineInfo(LineInfoDto lineInfoDto) throws Exception {
        ResponseDto result = new ResponseDto();
        result.setResultCode(0);
        result.setResultDesc("成功");
        Map<String, Object> detail = new HashMap<String, Object>();
        result.setDetail(detail);
        detail.put("list", lineInfoDao.getLineInfo(lineInfoDto));
        return result;
    }

    /**
     * @description 获取就诊状态
     * @param lineInfoDto
     * @return
     * @throws Exception
     */
    public ResponseDto getVisitStatus(LineInfoDto lineInfoDto) throws Exception {
        ResponseDto result = new ResponseDto();
        result.setResultCode(0);
        result.setResultDesc("成功");
        result.setDetail(lineInfoDao.getVisitStatus(lineInfoDto));
        return result;
    }

    /**
     * @description 获取排队信息列表
     * @param lineInfoDto
     * @return
     * @throws Exception
     */
    public List<LineInfoDto> getLineInfoList(LineInfoDto lineInfoDto) throws Exception {
        return lineInfoDao.getLineInfo(lineInfoDto);
    }
}