package com.hkd.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.hkd.entity.Content;

public interface ContentService {

	/**
	 *
	 * @param params
	 *      	  channelType所属栏目<br>
	 *            contentTitle标题<br>
	 *            contentTxt内容
	 * @param pageNum 当前页数
	 * @param pageSize	每页显示条数
	 * @return
	 */
	PageInfo<Map<String, Object>> findActivities(Map<String, Object> params,String pageNum,String pageSize);

	/**
	 *
	 * @param params
	 *      	  channelType所属栏目<br>
	 *            contentTitle标题<br>
	 *            contentTxt内容
	 * @param pageNum 当前页数
	 * @param pageSize	每页显示条数
	 * @return
	 */
	PageInfo<Map<String, Object>> findHelps(Map<String, Object> params,String pageNum,String pageSize);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Content findById (String id);

}
