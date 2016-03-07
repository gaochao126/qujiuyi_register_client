package com.jiuyi.qujiuyi.service.doctor;

import com.jiuyi.qujiuyi.dto.ResponseDto;
import com.jiuyi.qujiuyi.dto.doctor.DoctorDto;

/**
 * @description 医生业务层接口
 * @author zhb
 * @createTime 2015年12月28日
 */
public interface DoctorService {
    /**
     * @description 获取医生
     * @param doctorDto
     * @return
     * @throws Exception
     */
    public ResponseDto getDoctors(DoctorDto doctorDto) throws Exception;
}