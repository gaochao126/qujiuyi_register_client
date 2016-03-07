package com.jiuyi.qujiuyi.dao.numsource;

import java.util.List;

import com.jiuyi.qujiuyi.dto.numsource.NumSourceDto;

/**
 * @description 号源信息DAO层接口
 * @author zhb
 * @createTime 2015年12月28日
 */
public interface NumSourceDao {
    /**
     * @description 获取号源
     * @param numSourceDto
     * @return
     * @throws Exception
     */
    public List<NumSourceDto> getNumSource(NumSourceDto numSourceDto) throws Exception;
}