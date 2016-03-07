package com.jiuyi.qujiuyi.service;


/**
 * @description business exception
 * @author zhb
 * @createTime 2015年12月26日
 */
public class BusinessException extends Exception {
	private static final long serialVersionUID = 7524075320515958506L;

    /**
     * @description 判断字符串不为空
     * @param message
     * @return
     */
	public BusinessException(String message) {
		super(message);
	}
}