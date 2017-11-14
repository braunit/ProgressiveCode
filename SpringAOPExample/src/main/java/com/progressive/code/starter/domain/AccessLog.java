package com.progressive.code.starter.domain;

public class AccessLog {

	private String username;
	private String clazz;
	private String method;
	private String paramters;

	public AccessLog(String username, String clazz, String method, String paramters) {
		super();
		this.clazz = clazz;
		this.username = username;
		this.method = method;
		this.paramters = paramters;
	}

	public AccessLog() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParamters() {
		return paramters;
	}

	public void setParamters(String paramters) {
		this.paramters = paramters;
	}

}