package com.jiuyi.qujiuyi.dao.line;

import java.util.List;

import com.jiuyi.qujiuyi.dto.line.LineInfoDto;

/**
 * @description 排队信息DAO层接口
 * @author zhb
 * @createTime 2015年12月28日
 */
public interface LineInfoDao {
    /**
     * @description 获取排队信息
     * @param lineInfoDto
     * @return
     * @throws Exception
     */
    public List<LineInfoDto> getLineInfo(LineInfoDto lineInfoDto) throws Exception;

    /**
     * @description 获取就诊状态
     * @param lineInfoDto
     * @return
     * @throws Exception
     */
    public LineInfoDto getVisitStatus(LineInfoDto lineInfoDto) throws Exception;
}