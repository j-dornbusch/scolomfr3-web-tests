package scolomfr.web.tests.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import scolomfr.web.tests.model.vocabulary.Formats;
import scolomfr.web.tests.model.vocabulary.Versions;

public class TargetInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String version = request.getParameter("version");
		String format = request.getParameter("format");
		try {
			if (!StringUtils.isEmpty(version)) {
				Versions.setCurrent(Versions.valueOf(version));
			}
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		try {
			if (!StringUtils.isEmpty(format)) {
				Formats.setCurrentFormat(Formats.valueOf(format));

			}
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		return true;
	}
}
