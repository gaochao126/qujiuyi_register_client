package com.jiuyi.qujiuyi.common.handler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.jiuyi.qujiuyi.common.dit.Constants;
import com.jiuyi.qujiuyi.dto.line.LineInfoDto;

/**
 * @description 排队信息管理器
 * @author zhb
 * @createTime 2016年1月7日
 */
public class LineInfoThreadHandler implements Runnable {
    public static final BlockingQueue<LineInfoDto> numSourceQueue = new LinkedBlockingQueue<LineInfoDto>();
    protected final static Logger logger = Logger.getLogger(LineInfoThreadHandler.class);
    
    @Override
    public void run() {
        while (true) {
            try {
                LineInfoDto lineInfoDto = numSourceQueue.take();
                // 处理排队信息
                Constants.executorService.execute(new LineInfoThread(lineInfoDto));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}