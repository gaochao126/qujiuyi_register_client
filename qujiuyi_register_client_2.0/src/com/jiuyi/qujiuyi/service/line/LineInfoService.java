package com.jiuyi.qujiuyi.service.line;

import java.util.List;

import com.jiuyi.qujiuyi.dto.ResponseDto;
import com.jiuyi.qujiuyi.dto.line.LineInfoDto;

/**
 * @description 排队业务层接口
 * @author zhb
 * @createTime 2015年12月28日
 */
public interface LineInfoService {
    /**
     * @description 获取排队信息
     * @param lineInfoDto
     * @return
     * @throws Exception
     */
    public ResponseDto getLineInfo(LineInfoDto lineInfoDto) throws Exception;

    /**
     * @description 获取就诊状态
     * @param lineInfoDto
     * @return
     * @throws Exception
     */
    public ResponseDto getVisitStatus(LineInfoDto lineInfoDto) throws Exception;

    /**
     * @description 获取排队信息列表
     * @param lineInfoDto
     * @return
     * @throws Exception
     */
    public List<LineInfoDto> getLineInfoList(LineInfoDto lineInfoDto) throws Exception;
}