package com.jiuyi.qujiuyi.service.numsource;

import com.jiuyi.qujiuyi.dto.ResponseDto;
import com.jiuyi.qujiuyi.dto.numsource.NumSourceDto;

/**
 * @description 号源业务层接口
 * @author zhb
 * @createTime 2015年12月28日
 */
public interface NumSourceService {
    /**
     * @description 获取号源
     * @param numSourceDto
     * @return
     * @throws Exception
     */
    public ResponseDto getNumSource(NumSourceDto numSourceDto) throws Exception;

    /**
     * @description 同步医生可约号源
     * @param numSourceDto
     * @return
     * @throws Exception
     */
    public ResponseDto syncDoctorNumSource(final NumSourceDto numSourceDto) throws Exception;
}