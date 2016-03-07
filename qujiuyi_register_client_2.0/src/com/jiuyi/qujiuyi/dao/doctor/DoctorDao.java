package com.jiuyi.qujiuyi.dao.doctor;

import java.util.List;

import com.jiuyi.qujiuyi.dto.doctor.DoctorDto;

/**
 * @description 医生信息DAO层接口
 * @author zhb
 * @createTime 2015年12月28日
 */
public interface DoctorDao {
    /**
     * @description 获取医生
     * @param doctorDto
     * @return
     * @throws Exception
     */
    public List<DoctorDto> getDoctors(DoctorDto doctorDto) throws Exception;
}