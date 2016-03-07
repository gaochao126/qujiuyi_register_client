package com.jiuyi.qujiuyi.service.doctor.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jiuyi.qujiuyi.dao.doctor.DoctorDao;
import com.jiuyi.qujiuyi.dto.ResponseDto;
import com.jiuyi.qujiuyi.dto.doctor.DoctorDto;
import com.jiuyi.qujiuyi.service.doctor.DoctorService;

/**
 * @description 医生业务层实现
 * @author zhb
 * @createTime 2015年12月28日
 */
@Service
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    private DoctorDao doctorDao;

    /**
     * @description 获取医生
     * @param doctorDto
     * @return
     * @throws Exception
     */
    public ResponseDto getDoctors(DoctorDto doctorDto) throws Exception {
        ResponseDto result = new ResponseDto();
        result.setResultCode(0);
        result.setResultDesc("成功");
        Map<String, Object> detail = new HashMap<String, Object>();
        result.setDetail(detail);
        detail.put("list", doctorDao.getDoctors(doctorDto));
        return result;
    }
}