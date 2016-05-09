package com.jiuyi.qujiuyi.servlet;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jiuyi.qujiuyi.common.dit.CacheContainer;
import com.jiuyi.qujiuyi.common.dit.Constants;
import com.jiuyi.qujiuyi.common.handler.LineInfoThreadHandler;
import com.jiuyi.qujiuyi.dto.line.LineInfoDto;
import com.jiuyi.qujiuyi.service.numsource.NumSourceService;

/**
 * @description InitServlet
 * @author zhb
 * @createTime 2015年12月26日
 */
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = -5305744701888018846L;
    protected final static Logger logger = Logger.getLogger(InitServlet.class);

	@Override
	public void init(ServletConfig config) throws ServletException {
        Constants.applicationContext = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        lineInfoTimerTask();
        clearExpireToken();
        syncStopNumSource();
	}

    /**
     * @description 排队信息定时处理任务
     */
    private void lineInfoTimerTask() {
        new Thread(new LineInfoThreadHandler()).start();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // 处理排队信息
                Set<String> keySet = CacheContainer.getNumSourceMap().keySet();
                if (!keySet.isEmpty()) {
                    for (String key : keySet) {
                        LineInfoDto lineInfoDto = new LineInfoDto();
                        lineInfoDto.setNumSourceId(key);
                        LineInfoThreadHandler.numSourceQueue.add(lineInfoDto);
                    }
                }
            }
        }, 0, 5000);
    }

    /**
     * @description 清理过期的token
     */
    private void clearExpireToken() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Set<String> keySet = CacheContainer.getTokenMap().keySet();
                if (!keySet.isEmpty()) {
                    for (String key : keySet) {
                        Long time = CacheContainer.getTokenMap().get(key);
                        if (time != null) {
                            if (System.currentTimeMillis() - time >= 600 * 1000) {
                                CacheContainer.getTokenMap().remove(key);
                            }
                        }
                    }
                }
            }
        }, 0, 300 * 1000);
    }

    private void syncStopNumSource() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    Constants.applicationContext.getBean(NumSourceService.class).syncStopNumSource();
                } catch (Exception e) {
                    logger.error("", e);
                }
            }
        }, 0, 600 * 1000);
    }
}