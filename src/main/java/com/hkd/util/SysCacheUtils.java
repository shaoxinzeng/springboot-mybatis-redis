package com.hkd.util;

import com.hkd.entity.BackConfigParams;
import com.hkd.entity.Constant;

import javax.servlet.ServletContext;

import org.springframework.web.context.ContextLoader;

import java.util.*;

/**
 * 提供读取缓存的方法
 * 
 * @author Liutq
 * 
 */
public class SysCacheUtils {

	private static ServletContext servletContext = null;

	/**
	 * 获取系统参数
	 * 
	 * @return 2014-7-16 fx
	 */
	public static LinkedHashMap<String, String> getConfigParams(String type) {
		return (LinkedHashMap<String, String>) getServletContext()
				.getAttribute(type);
	}

	/**
	 * 获取系统参数
	 * 
	 * @return 2014-7-16 fx
	 */
	public static List<BackConfigParams> getListConfigParams(String type) {
		type = type + Constant.SYS_CONFIG_LIST;
		return (List<BackConfigParams>) getServletContext().getAttribute(type);
	}

	/**
	 * 获得Map集合
	 * 
	 * @return
	 */
	public static Map<String, String> getConfigMap(String type) {
		return (Map<String, String>) getServletContext().getAttribute(type);
	}




	public static ServletContext getServletContext() {
		if (servletContext == null) {
			servletContext = ContextLoader.getCurrentWebApplicationContext()
					.getServletContext();
		}
		return servletContext;
	}

}
