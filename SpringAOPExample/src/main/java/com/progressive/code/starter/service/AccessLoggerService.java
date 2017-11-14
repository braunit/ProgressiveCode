package com.progressive.code.starter.service;

import com.progressive.code.starter.domain.AccessLog;

public interface AccessLoggerService {

	void logAccess(AccessLog accessLog);

}