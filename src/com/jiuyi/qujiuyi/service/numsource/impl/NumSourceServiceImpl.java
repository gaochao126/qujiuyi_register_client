package com.jiuyi.qujiuyi.service.numsource.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jiuyi.qujiuyi.common.dit.Constants;
import com.jiuyi.qujiuyi.common.handler.SyncNumSourceThread;
import com.jiuyi.qujiuyi.common.util.SysCfg;
import com.jiuyi.qujiuyi.dao.numsource.NumSourceDao;
import com.jiuyi.qujiuyi.dto.ResponseDto;
import com.jiuyi.qujiuyi.dto.numsource.NumSourceDto;
import com.jiuyi.qujiuyi.service.BusinessException;
import com.jiuyi.qujiuyi.service.numsource.NumSourceService;
import com.jiuyi.qujiuyi.servlet.BaseServlet;

/**
 * @description 号源业务层实现
 * @author zhb
 * @createTime 2015年12月28日
 */
@Service
public class NumSourceServiceImpl implements NumSourceService {
    @Autowired
    private NumSourceDao numSourceDao;

    private final static Logger logger = Logger.getLogger(BaseServlet.class);

    /**
     * @description 获取号源
     * @param numSourceDto
     * @return
     * @throws Exception
     */
    public ResponseDto getNumSource(NumSourceDto numSourceDto) throws Exception {
        ResponseDto result = new ResponseDto();
        result.setResultCode(0);
        result.setResultDesc("成功");
        Map<String, Object> detail = new HashMap<String, Object>();
        result.setDetail(detail);
        detail.put("list", numSourceDao.getNumSource(numSourceDto));
        return result;
    }

    /**
     * @description 同步医生可约号源
     * @param numSourceDto
     * @return
     * @throws Exception
     */
    public ResponseDto syncDoctorNumSource(final NumSourceDto numSourceDto) throws Exception {
        if (numSourceDto == null) {
            throw new BusinessException(Constants.DATA_ERROR);
        }
        if (numSourceDto.getStartTime() == null) {
            throw new BusinessException("开始时间不能为空");
        }

        // 医生源号同步
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> list = numSourceDto != null ? numSourceDto.getDoctorIds() : null;
                if (list != null && !list.isEmpty()) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date startTime = sdf.parse(sdf.format(numSourceDto.getStartTime()).split(" ")[0] + " 00:00:00");
                        for (String doctorId : list) {
                            NumSourceDto _numSourceDto = new NumSourceDto();
                            _numSourceDto.setDoctorId(doctorId);
                            List<NumSourceDto> l = numSourceDao.getNumSource(_numSourceDto);
                            _numSourceDto.setReserveOrderNum(0);
                            _numSourceDto.setHospitalId(SysCfg.getInt("hospitalId"));
                            _numSourceDto.setStartTime(startTime);
                            if (l != null && !l.isEmpty()) {
                                for (NumSourceDto dto : l) {
                                    if (dto.getEndTime().getTime() - numSourceDto.getStartTime().getTime() < 60 * 60 * 1000L) {
                                        continue;
                                    }
                                    if (dto.getReserveOrderNum() != null && dto.getReserveOrderNum() > 0) {
                                        _numSourceDto.setReserveOrderNum(dto.getReserveOrderNum());
                                        break;
                                    }
                                }
                            }
                            Constants.executorService.execute(new SyncNumSourceThread(_numSourceDto));
                        }
                    } catch (Exception e) {
                        logger.error("", e);
                    }
                }
            }
        }).start();

        ResponseDto result = new ResponseDto();
        result.setResultCode(0);
        result.setResultDesc("同步成功");
        return result;
    }

    /**
     * @description 挂号费用查询
     * @param numSourceDto
     * @return
     * @throws Exception
     */
    public ResponseDto getVisitCost(NumSourceDto numSourceDto) throws Exception {
        numSourceDao.getVisitCost(numSourceDto);

        ResponseDto result = new ResponseDto();
        result.setResultCode(numSourceDto.getResultCode());
        result.setResultDesc(numSourceDto.getResultDesc());
        Map<String, Object> detail = new HashMap<String, Object>();
        result.setDetail(detail);
        detail.put("visitCost", numSourceDto.getVisitCost());
        return result;
    }

    /**
     * @description 同步停诊号源
     * @throws Exception
     */
    public void syncIsStopNumSource() throws Exception {
        Date date = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr = sdf.format(date);
        Date date2 = sdf.parse(timeStr.split(" ")[0] + " 12:00:00");

        NumSourceDto numSourceDto = new NumSourceDto();
        numSourceDto.setStartTime(date.before(date2) ? sdf.parse(timeStr.split(" ")[0] + " 00:00:00") : date2);
        numSourceDto.setStopVisitStatus(1);
    }
}