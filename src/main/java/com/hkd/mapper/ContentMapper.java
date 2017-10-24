package com.hkd.mapper;

import java.util.List;
import java.util.Map;

import com.hkd.entity.Content;
import com.hkd.util.MyMapper;

public interface ContentMapper extends MyMapper<Content> {
	/**
	 * 根据条件查询内容列表
	 * @param params
	 * @return
	 */
	List<Content> findList(Map<String, Object> params);
	
	/**
	 * 根据Id查询内容列表
	 * @param id
	 * @return
	 */
	public Content findById(String id);
}
