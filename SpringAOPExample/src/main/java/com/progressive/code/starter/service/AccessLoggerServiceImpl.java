package com.progressive.code.starter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.progressive.code.starter.domain.AccessLog;

@Service
public class AccessLoggerServiceImpl implements AccessLoggerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccessLoggerServiceImpl.class);

	@Override
	public void logAccess(AccessLog accessLog) {
		LOGGER.info("User {}, invoked method {} of class {}, with parameters {}", accessLog.getUsername(),
				accessLog.getMethod(), accessLog.getClazz(), accessLog.getParamters());
	}

}