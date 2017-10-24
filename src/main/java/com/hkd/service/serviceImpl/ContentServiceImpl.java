package com.hkd.service.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hkd.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hkd.entity.Content;
import com.hkd.mapper.ContentMapper;
import com.hkd.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private ContentMapper contentMapper;

	/**
	 *
	 * @param params
	 *      	  channelType所属栏目<br>
	 *            contentTitle标题<br>
	 *            contentTxt内容
	 * @return
	 */
	@Override
	public PageInfo<Map<String, Object>> findHelps(Map<String, Object> params,String pageN,String pageS) {
		Integer pageNum = Integer.parseInt(pageN);
		Integer pageSize = Integer.parseInt(pageS);
		if(null != pageNum && null != pageSize){
			PageHelper.startPage(pageNum,pageSize);
		}

		List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();

		List<Content> list = contentMapper.findList(params);
		for (Content content : list ) {
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("id",content.getId());	//公告编号
			dataMap.put("channelTitle",content.getContentTitle());	//标题
			dataMap.put("", DateUtil.getDateFormat(content.getAddTime(),"yy-MM-dd hh:mm:ss"));
			dataMap.put("contentTxt", content.getContentTxt());	//内容

			dataList.add(dataMap);
		}

		return new PageInfo<>(dataList);
	}

	/**
	 *
	 * @param params
	 *      	  channelType所属栏目<br>
	 *            contentTitle标题<br>
	 *            contentTxt内容
	 * @return
	 */
	@Override
	public PageInfo<Map<String, Object>> findActivities(Map<String, Object> params,String pageN,String pageS) {
		Integer pageNum = Integer.parseInt(pageN);
		Integer pageSize = Integer.parseInt(pageS);
		if(null != pageNum && null != pageSize){
			PageHelper.startPage(pageNum,pageSize);
		}

		List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();

		List<Content> list = contentMapper.findList(params);
		for (Content content : list ) {
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("id",content.getId());	//公告编号
			dataMap.put("channelTitle",content.getContentTitle());	//标题
			dataMap.put("contentSummary",content.getContentSummary());
			dataMap.put("createTime",DateUtil.getDateFormat(content.getAddTime(), "yyyy-MM-dd HH:mm:ss"));
			
			dataList.add(dataMap);
		}

		return new PageInfo<>(dataList);
	}

	@Override
	public Content findById(String id) {
		return contentMapper.findById(id);
	}

}
