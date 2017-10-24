package com.hkd.service.serviceImpl;

import com.hkd.entity.*;
import com.hkd.entity.vo.UserRequestVo;
import com.hkd.mapper.*;
import com.hkd.service.IndexService;
import com.hkd.util.CollectionUtil;
import com.hkd.util.RedisUtil;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.json.JSON;
import com.xiaoleilu.hutool.json.JSONObject;
import com.xiaoleilu.hutool.json.JSONUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.hkd.entity.BorrowOrder.borrowStatusMap_shenheFail;
import static com.hkd.entity.BorrowOrder.borrowStatusMap_shenhezhong;

/**
 * User: xuedaiyao
 * Date: 2017/8/18
 * Time: 12:33
 * Description:
 */
@Service
public class IndexServiceImpl implements IndexService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IndexMapper indexMapper;
    @Autowired
    private BackConfigParamsMapper backConfigParamsMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AssetOrderPoolMapper assetOrderPoolMapper;
    @Autowired
    private AssetOrderConnectMapper assetOrderConnectMapper;
    @Autowired
    private AssetBorrowOrderMapper assetBorrowOrderMapper;


    /**
     *   首页展示
     * @param userRequestVo
     * @return
     */
    @Override
    public Object show(UserRequestVo userRequestVo) {
        String deviceId = userRequestVo.getDeviceId();
        String telephone = userRequestVo.getMobilePhone();
        if(StringUtils.isBlank(deviceId) || StringUtils.isBlank(telephone)){
            logger.info("设备号或者手机号不存在,默认展示");
            return sendDefault();
        }else {
            String  userId = redisUtil.get("user_" + deviceId + telephone);
            try{
                if (null == userId){
                    Example example = new Example(User.class);
                    Example.Criteria criteria = example.createCriteria();
                    criteria.andEqualTo("userPhone",telephone);
                    List<User> users = userMapper.selectByExample(example);
                    if (CollectionUtil.notEmpty(users)){
                        userId = users.get(0).getId();
                        return sendDynamic(userId);
                    }
                    return sendDefault();
                }
                return sendDynamic(userId);
            }catch(Exception e){
                logger.error("首页 异常 "+ e.toString());
                return  sendDefault();
            }
        }
    }

    /**
     *  用户不登录时返回的首页数据
     * @return
     */
    private Object sendDefault() {
        JSON indexJson =  initIndex();
        if (null == indexJson){
            return JSONUtil.parseObj(getDefaultJson());
        }else {
            return JSONUtil.parseObj(indexJson);
        }
    }

    /**
     *  初始化首页
     * @return
     */
    private JSON initIndex() {
        logger.info("首页初始化开始 ...");
        HashMap<String,Object> dataMap = new HashMap<String,Object>();//存放数据

        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("NOT_SELECT", Constant.STATUS_VALID);
        InfoIndex index = indexMapper.searchInfoIndex(map);//查询首页内容

        List<InfoNotice> noticeList = null;//滚动公告
        if(null!=index) {
            HashMap<String, Object> itemMap = new HashMap<String, Object>();
            itemMap.put("card_title", index.getCard_title());
            itemMap.put("card_amount", index.getCard_amount());
            itemMap.put("card_verify_step", Constant.CARD_VERIFY_STEP + index.getAuth_min() + "/" + index.getAuth_max());
            itemMap.put("verify_loan_pass", this.getVerifyLoanPass(index.getAuth_min(), index.getAuth_max()));
            itemMap.put("verify_loan_nums", index.getAuth_min());
            dataMap.put("item", itemMap);

            HashMap<String, Object> amountPeriodsListMap = new HashMap<String, Object>();
            amountPeriodsListMap.put("days", this.getPeriods(index));//借款期数
            amountPeriodsListMap.put("interests", this.getInterests(null,index));//服务费费率
            amountPeriodsListMap.put("amounts", this.getAmounts(index));//金额列表
            dataMap.put("amount_days_list", amountPeriodsListMap);

            Integer noticeSize = null;
            List<InfoImage> imageList = null;
            noticeSize = Integer.parseInt(index.getNotice_size());

            if (noticeSize == null) {
                noticeSize = Constant.INDEX_LIMIT;
            }
            map.put("COUNT_HKD", noticeSize);

            noticeList = indexMapper.searchInfoNoticeByIndex(map);//查询公告
            dataMap.put("user_loan_log_list", CollectionUtil.buildList(noticeList, String.class, "notContent"));
            map.put("STATUS", Constant.STATUS_VALID);
            imageList = indexMapper.searchInfoImage(map);//查询图片
            dataMap.put("index_images", this.getInfoImageList(imageList));
            dataMap.put("today_last_amount", index.getToday_last_amount());
        }
        return JSONUtil.parse(dataMap);
    }

    /**
     * 获取首页图片
     *
     * @param infoImageList
     * @return
     */
    public static Object getInfoImageList(List<InfoImage> infoImageList) {
        List<HashMap<String, Object>> infoImageLists = new ArrayList<HashMap<String, Object>>();
        for (InfoImage infoImage : infoImageList) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("url", infoImage.getUrl());
            map.put("title", infoImage.getTitle());
            map.put("sort", infoImage.getSort());
            map.put("reurl", infoImage.getReurl());
            infoImageLists.add(map);
        }
        return infoImageLists;
    }


    /**
     * 获取产品的额度获取金额列表
     *
     * @param index 产品信息
     * @return
     */
    public static Object getAmounts(InfoIndex index) {
        List<String> amountList = new ArrayList<String>();
        Integer max = null;
        Integer min = null;
        try {
            max = Integer.parseInt(index.getAmount_max());
            min = Integer.parseInt(index.getAmount_min());
        } catch (Exception e) {
            max = Constant.AMOUNT_MAX;
            min = Constant.AMOUNT_MIN;
        }
        if (max == null) {
            max = Constant.AMOUNT_MAX;
        }
        if (min == null) {
            min = Constant.AMOUNT_MIN;
        }
        for (int i = min; i <= max; i = i + Constant.FOR_BASE) {
            amountList.add(String.valueOf(i));
        }
        return amountList;
    }

    /**
     * 根据用户额度获取金额列表
     *
     * @param min 最小额度(单位分)
     * @param max 最大额度(单位分)
     * @return
     */
    public static List<Object> getAmounts(Integer min, Integer max) {
        List<Object> amountList = new ArrayList<>();
        if (1 == max) {
            amountList.add("0");
            amountList.add("1");
            return amountList;
        }
        boolean bool = true;
        if ((max / Constant.FOR_BASE) != 0) {
            bool = false;
        }
        Integer maxs = max / Constant.FOR_BASE * Constant.FOR_BASE;

        for (int i = min; i <= maxs; i = i + Constant.FOR_BASE) {
            amountList.add(String.valueOf(i));
        }
        if (!bool) {
            amountList.add(String.valueOf(max));
        }
        return amountList;
    }

    /**
     * 获取认证
     *
     * @param pass1
     * @param pass2
     * @return
     */
    public  String getVerifyLoanPass(String pass1, String pass2) {
        logger.info("获取认证getVerifyLoanPass-pass1=" + pass1 + " pass2=" + pass2);
        if (StringUtils.isNotBlank(pass1) && StringUtils.isNotBlank(pass2)) {
            if (pass1.equals(pass2)) {// 认证完成(需要完成必填项的5项)
                return "1";
            }
        }
        return "0";
    }

    /**
     * 获取借款期数集合
     *
     * @param index 产品信息
     * @return
     */
    public  Object getPeriods(InfoIndex index) {
        String[] strArr = index.getPeriods().split(",");
        logger.info("获取借款期数:" + strArr.toString());
        List<String> periodsList = new ArrayList<String>();
        for (String string : strArr) {
            periodsList.add(string);
        }
        return periodsList;
    }

    /**
     * 获取默认信息
     * @return
     */
    public String getDefaultJson(){
        String defaultString  =  "{\n" +
                "  \"code\": \"0\",\n" +
                "  \"message\": \"成功\",\n" +
                "  \"data\": {\n" +
                "    \"amount_days_list\": {\n" +
                "      \"amounts\": [\n" +
                "        \"500000\",\n" +
                "        \"510000\",\n" +
                "        \"520000\",\n" +
                "        \"530000\",\n" +
                "        \"540000\",\n" +
                "        \"550000\",\n" +
                "        \"560000\",\n" +
                "        \"570000\",\n" +
                "        \"580000\",\n" +
                "        \"590000\",\n" +
                "        \"600000\",\n" +
                "        \"610000\",\n" +
                "        \"620000\",\n" +
                "        \"630000\",\n" +
                "        \"640000\",\n" +
                "        \"650000\",\n" +
                "        \"660000\",\n" +
                "        \"670000\",\n" +
                "        \"680000\",\n" +
                "        \"690000\",\n" +
                "        \"700000\",\n" +
                "        \"710000\",\n" +
                "        \"720000\",\n" +
                "        \"730000\",\n" +
                "        \"740000\",\n" +
                "        \"750000\",\n" +
                "        \"760000\",\n" +
                "        \"770000\",\n" +
                "        \"780000\",\n" +
                "        \"790000\",\n" +
                "        \"800000\",\n" +
                "        \"810000\",\n" +
                "        \"820000\",\n" +
                "        \"830000\",\n" +
                "        \"840000\",\n" +
                "        \"850000\",\n" +
                "        \"860000\",\n" +
                "        \"870000\",\n" +
                "        \"880000\",\n" +
                "        \"890000\",\n" +
                "        \"900000\",\n" +
                "        \"910000\",\n" +
                "        \"920000\",\n" +
                "        \"930000\",\n" +
                "        \"940000\",\n" +
                "        \"950000\",\n" +
                "        \"960000\",\n" +
                "        \"970000\",\n" +
                "        \"980000\",\n" +
                "        \"990000\",\n" +
                "        \"1000000\",\n" +
                "        \"1010000\",\n" +
                "        \"1020000\",\n" +
                "        \"1030000\",\n" +
                "        \"1040000\",\n" +
                "        \"1050000\",\n" +
                "        \"1060000\",\n" +
                "        \"1070000\",\n" +
                "        \"1080000\",\n" +
                "        \"1090000\",\n" +
                "        \"1100000\",\n" +
                "        \"1110000\",\n" +
                "        \"1120000\",\n" +
                "        \"1130000\",\n" +
                "        \"1140000\",\n" +
                "        \"1150000\",\n" +
                "        \"1160000\",\n" +
                "        \"1170000\",\n" +
                "        \"1180000\",\n" +
                "        \"1190000\",\n" +
                "        \"1200000\",\n" +
                "        \"1210000\",\n" +
                "        \"1220000\",\n" +
                "        \"1230000\",\n" +
                "        \"1240000\",\n" +
                "        \"1250000\",\n" +
                "        \"1260000\",\n" +
                "        \"1270000\",\n" +
                "        \"1280000\",\n" +
                "        \"1290000\",\n" +
                "        \"1300000\",\n" +
                "        \"1310000\",\n" +
                "        \"1320000\",\n" +
                "        \"1330000\",\n" +
                "        \"1340000\",\n" +
                "        \"1350000\",\n" +
                "        \"1360000\",\n" +
                "        \"1370000\",\n" +
                "        \"1380000\",\n" +
                "        \"1390000\",\n" +
                "        \"1400000\",\n" +
                "        \"1410000\",\n" +
                "        \"1420000\",\n" +
                "        \"1430000\",\n" +
                "        \"1440000\",\n" +
                "        \"1450000\",\n" +
                "        \"1460000\",\n" +
                "        \"1470000\",\n" +
                "        \"1480000\",\n" +
                "        \"1490000\",\n" +
                "        \"1500000\",\n" +
                "        \"1510000\",\n" +
                "        \"1520000\",\n" +
                "        \"1530000\",\n" +
                "        \"1540000\",\n" +
                "        \"1550000\",\n" +
                "        \"1560000\",\n" +
                "        \"1570000\",\n" +
                "        \"1580000\",\n" +
                "        \"1590000\",\n" +
                "        \"1600000\",\n" +
                "        \"1610000\",\n" +
                "        \"1620000\",\n" +
                "        \"1630000\",\n" +
                "        \"1640000\",\n" +
                "        \"1650000\",\n" +
                "        \"1660000\",\n" +
                "        \"1670000\",\n" +
                "        \"1680000\",\n" +
                "        \"1690000\",\n" +
                "        \"1700000\",\n" +
                "        \"1710000\",\n" +
                "        \"1720000\",\n" +
                "        \"1730000\",\n" +
                "        \"1740000\",\n" +
                "        \"1750000\",\n" +
                "        \"1760000\",\n" +
                "        \"1770000\",\n" +
                "        \"1780000\",\n" +
                "        \"1790000\",\n" +
                "        \"1800000\",\n" +
                "        \"1810000\",\n" +
                "        \"1820000\",\n" +
                "        \"1830000\",\n" +
                "        \"1840000\",\n" +
                "        \"1850000\",\n" +
                "        \"1860000\",\n" +
                "        \"1870000\",\n" +
                "        \"1880000\",\n" +
                "        \"1890000\",\n" +
                "        \"1900000\",\n" +
                "        \"1910000\",\n" +
                "        \"1920000\",\n" +
                "        \"1930000\",\n" +
                "        \"1940000\",\n" +
                "        \"1950000\",\n" +
                "        \"1960000\",\n" +
                "        \"1970000\",\n" +
                "        \"1980000\",\n" +
                "        \"1990000\",\n" +
                "        \"2000000\"\n" +
                "      ],\n" +
                "      \"days\": [\n" +
                "        \"6\"\n" +
                "      ],\n" +
                "      \"interests\": [\n" +
                "        \"0.125\"\n" +
                "      ]\n" +
                "    },\n" +
                "    \"item\": {\n" +
                "      \"card_title\": \"恒快贷\",\n" +
                "      \"verify_loan_nums\": \"0\",\n" +
                "      \"card_amount\": \"2000000\",\n" +
                "      \"card_verify_step\": \"认证0/5\",\n" +
                "      \"verify_loan_pass\": \"0\"\n" +
                "    },\n" +
                "    \"user_loan_log_list\": [\n" +
                "      \"尾号2269，正常还款，成功提额至1050元\",\n" +
                "      \"尾号6547，成功借款1000元，申请至放款耗时3分钟\",\n" +
                "      \"尾号2265，成功借款1000元，申请至放款耗时4分钟\"\n" +
                "    ],\n" +
                "    \"today_last_amount\": \"123400\",\n" +
                "    \"index_images\": [\n" +
                "      {\n" +
                "        \"reurl\": \"http://101.132.78.154/hkd_api/gotoAboutIndex\",\n" +
                "        \"sort\": \"1\",\n" +
                "        \"title\": \"首页活动三\",\n" +
                "        \"url\": \"http://101.132.78.154/hkd_api/common/web/images/index_banner2.png\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"reurl\": \"http://101.132.78.154/hkd_api/gotoDrawAwardsIndex\",\n" +
                "        \"sort\": \"2\",\n" +
                "        \"title\": \"首页活动二\",\n" +
                "        \"url\": \"http://101.132.78.154/hkd_api/common/web/images/index_banner3.png\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";
        return defaultString;
    }

    /**
     * 用户登录时首页数据
     * @param userId 用户ID
     * @return
     */
    private Object sendDynamic( String userId) {
        logger.info("sendDynamic:userid="+userId);
        String indexJson = null;
        if(StringUtils.isBlank(indexJson)){
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("USER_ID", userId);
            //用户获取表中的数据
            InfoIndexInfo indexInfo = indexMapper.searchInfoIndexInfo(map);
            User user = indexMapper.searchUserByIndex(map);

            if(null==indexInfo){
                logger.info("info_user_info表中无"+userId+"用户记录");
                return sendDefault();
            }else{
                //infoIndexInfo 存在开始组装用户数据
                user = setMaxAmount(user);
                indexJson = initIndex().toString();
                if(StringUtils.isBlank(indexJson)){
                    logger.info("静态展示-2-");
                    indexJson = getDefaultJson();//发送静态字符串
                }
                //添加用户部分数据
                String indexJsonRe = getJsonStr2(indexJson, indexInfo,user);
                if(null == indexJsonRe){
                    indexJson = this.getDefaultJson();
                }else{
                    indexJson= indexJsonRe;
                }
                redisUtil.set(Constant.CACHE_INDEX_INFO_KEY+userId,indexJson);
                logger.info("动态返回");
                return JSONUtil.parseObj(indexJson);
            }
        }else{
            logger.info("用户"+userId+"存在于缓存");
            return indexJson;
        }
    }

    private User setMaxAmount(User user){
        String min = "1";
        String max = "1";
        if(StringUtils.isNotBlank(user.getAmountMin())){
            if("0".equals(user.getAmountMin())){
                min = "1";
            }else{
                min = user.getAmountMin();
            }
        }else{
            min = "1";
        }
        user.setAmountMin(min);

        if(StringUtils.isNotBlank(user.getAmountMax())){
            if("0".equals(user.getAmountMax())){
                max = "1";
            }else{
                max = user.getAmountMax();
            }
        }else{
            max = "1";
        }
        user.setAmountMax(max);
        return user;
    }

    /**
     *  获取用户部分数据json
     * @param indexJson  默认初始化json数据
     * @param indexInfo 用户认证信息
     * @param user 用户
     * @return
     */
    private String getJsonStr2(String indexJson, InfoIndexInfo indexInfo, User user){
        InfoIndex index = indexMapper.searchInfoIndex(new HashMap<>());//查询首页内容
        try{
            JSONObject data = new JSONObject(indexJson);
            Map<String,Object>  amount_days_list = (Map) data.getJSONObject("amount_days_list");//金额列表、期数、到期金额、服务费
            JSONObject item =  data.getJSONObject("item");//认证数据

            List<Object> amounts = null;
            JSON interests = null;
            String cardAmount = null;

            if(null != user){
                amounts = this.getAmounts(Integer.parseInt(user.getAmountMin()),Integer.parseInt(user.getAmountAvailable()));
                interests = this.getInterests(indexInfo,index);
                cardAmount = user.getAmountMax();
            }else{
                amounts = this.getAmounts(Integer.parseInt(indexInfo.getAmountMin()),Integer.parseInt(indexInfo.getCardAmount()));
                interests = this.getInterests(indexInfo,index);
                cardAmount = indexInfo.getCardAmount();
            }
            //amounts
            amount_days_list.put("amounts", amounts);
            //interests
            amount_days_list.put("interests",interests);
            //item
            item.put("card_amount", cardAmount);
            //TODO  给你用已启用 2.2.0 版本页面
            item.put("card_verify_step", Constant.CARD_VERIFY_STEP+(indexInfo.getAuthInfo()+indexInfo.getAuthContacts()+indexInfo.getAuthMobile()+indexInfo.getAuthSesame())+"/"+4);

            item.put("verify_loan_nums", indexInfo.getAuthSum().toString());
            //TODO  给你用已启用 2.2.0 版本页面
            item.put("verify_loan_pass", this.getVerifyLoanPass2(indexInfo.getAuthSum()+"", indexInfo.getAuthCount()+"",indexInfo.getAuthBank()+"").toString());

            //总认证数
            item.put("all_auth_Count", indexInfo.getAuthCount().toString());
            //获取用户借款信息
            HashMap<String,Object> orderMap = new HashMap<String,Object>();
            orderMap.put("USER_ID", indexInfo.getUserId());
            AssetOrderPool assetOrderPool = indexMapper.searchBorrowOrderByIndex(orderMap);
            if(null != assetOrderPool){//存在借款
                if(!checkBankNo(indexInfo)){
                    HashMap<String,Object> map = new HashMap<String,Object>();
                    map.put("userId", indexInfo.getUserId());
                    this.authBank(map);//设置银行卡号

                    map = new HashMap<String,Object>();
                    map.put("USER_ID", indexInfo.getUserId());
                    indexInfo = indexMapper.searchInfoIndexInfo(map);//重新查询
                    logger.error("indexInfo-getBankNo:"+indexInfo.getBankNo());
                }
                //判断分期账单是否全部还完
                boolean isHkComplete = true;
                Example example = new Example(AssetOrderConnect.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("id",assetOrderPool.getId());
                List<AssetOrderConnect> connectList = assetOrderConnectMapper.selectByExample(example);
                List<Integer> orderIds = CollectionUtil.buildList(connectList,Integer.class,"assetOrderId");

                Example orderExample = new Example(AssetBorrowOrder.class);
                Example.Criteria orderCriteria = orderExample.createCriteria();
                orderCriteria.andIn("id",orderIds);
                List<AssetBorrowOrder> orders = assetBorrowOrderMapper.selectByExample(orderExample);
                for (AssetBorrowOrder abo :orders){
                    if (!AssetBorrowOrder.STATUS_YHK.equals(abo.getStatus())){
                        isHkComplete = false;
                        break;
                    }
                }
                if (!isHkComplete){
                    item.put("loan_infos", this.getLoanInfos(indexInfo));
                }
            }
            if(null != user){
                Map<String,String> interval = findAuditFailureOrderByUserId(user.getId());
                item.put("next_loan_day", interval.get("nextLoanDay"));
            } else{
                item.put("next_loan_day", String.valueOf(0));
            }
            data.put("amount_days_list", amount_days_list);
            //获取用户最近借款状态 代替风控
            // 如果最近借款被拒绝, 那么就显示问号, 跳转去其他的app
            // 0 不显示 1显示
            item.put("risk_status","0");
            AssetOrderPool bo = indexMapper.selectBorrowOrderNowUseId(Integer.valueOf(user.getId()));
            if(bo!=null){
                //借款审核被拒绝 添加全局控制开关 1 打开 0 关闭
                HashMap<String,Object> params = new HashMap<String,Object>();
                params.put("sysType","SYS_NOCACHE");
                params.put("syskey","SYJ_SWITCH");
                List<BackConfigParams> list = indexMapper.findParams(params);
                String offon = "1";
                if(list.size()==1){
                    offon = list.get(0).getSysValue();
                }

                if(borrowStatusMap_shenheFail.containsKey(bo.getStatus())
                        &&"1".equals(offon)){
                    item.put("risk_status","1");
                }
            }
            //获取页面引流的地址
            HashMap<String,Object> params = new HashMap<String,Object>();
            params.put("sysType","SYS_NOCACHE");
            params.put("syskey","drainage_url");
            List<BackConfigParams> list = indexMapper.findParams(params);
            item.put("drainage_url","http://mobile.souyijie.com/recommend");
            if(list.size()==1){
                item.put("drainage_url",list.get(0).getSysValue());
            }
            data.put("item", item);
            return data.toString();
        }catch(Exception e){
            e.printStackTrace();
            logger.info("静态展示-3-");
            return  getDefaultJson();
        }
    }

    /**
     *  2.2.0版本认证
     * @param authSum
     * @param authCount
     * @param authBank
     * @return
     */
    public  Object getVerifyLoanPass2(String authSum, String authCount, String authBank) {
        logger.info("获取认证getVerifyLoanPass2-authSum=" + authSum + " authCount=" + authCount + "authBank=" + authBank);
        if (StringUtils.isNotBlank(authSum) && StringUtils.isNotBlank(authCount)) {
            if (Integer.valueOf(authSum) == 4 && Integer.valueOf(authBank) == 0) {// 认证完成(完成除银行卡的必填项4项外)
                return 1;
            }
            if (authSum.equals(authCount)) {
                return 1;
            }
        }
        return 0;
    }

    /**
     * 校验银行卡
     * @return
     */
    private boolean checkBankNo(InfoIndexInfo indexInfo){
        if(null!=indexInfo){
            if(null!=indexInfo.getAuthBank()){
                if(Constant.STATUS_INT_VALID == indexInfo.getAuthBank()){//银行认证完成
                    if(StringUtils.isNotBlank(indexInfo.getBankNo())){//银行卡号是否存在
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * 获取服务费率
     *
     * @param indexInfo
     * @return
     */
    public  JSON getInterests(InfoIndexInfo indexInfo,InfoIndex index) {
        logger.info("获取服务费利率:Rate:" );
        List<Object> serviceRates = new ArrayList<>();
       if ((null != indexInfo) && StringUtils.isNotEmpty(indexInfo.getRate())){
           serviceRates = getRate(indexInfo.getRate());
       }else {
           if ((null != index) && StringUtils.isNotEmpty(index.getRate())){
               serviceRates = getRate(index.getRate());
           }
       }
        return JSONUtil.parse(serviceRates);
    }

    public List<Object> getRate(String rateString){
        if (StringUtils.isBlank(rateString)){
            return null;
        }
        List<Object> serviceRates = new ArrayList<>();
        String[] rateArr = rateString.split(",");
        for (String s:rateArr){
            serviceRates.add(s);
        }
        return serviceRates;
    }

    /**
     * 用户银行卡认证
     * @param map
     */
    public void authBank(HashMap<String,Object> map){
        logger.info("authBank start...map:"+map);
        InfoIndexInfo indexInfo = new InfoIndexInfo();
        indexInfo.setUserId(Integer.parseInt(String.valueOf(map.get("userId"))));

        HashMap<String, Object> bankMap = new HashMap<String,Object>();
        bankMap.put("USER_ID", Integer.parseInt(String.valueOf(map.get("userId"))));
        UserCardInfo cardInfo = indexMapper.searchUserCardInfo(bankMap);
        if(null!=cardInfo){
            if(StringUtils.isNotBlank(cardInfo.getCard_no())){
                indexInfo.setBankNo(cardInfo.getCard_no());
            }
        }
        indexInfo.setAuthBank(Constant.STATUS_INT_VALID);
        indexMapper.updateIndexInfoByUserId(indexInfo);
    }

    /**
     * 获取用户借款信息
     * @param indexInfo
     * @return
     */
    public JSON getLoanInfos(InfoIndexInfo indexInfo){
        HashMap<String,Object> orderMap = new HashMap<String,Object>();
        orderMap.put("USER_ID", indexInfo.getUserId());
        AssetOrderPool bo = indexMapper.searchBorrowOrderByIndex(orderMap);
        if (null != bo){
             return JSONUtil.parse(this.getLoanInfos(indexInfo, bo));
        }
        return null;
    }
    /**
     * 获取用户借款信息
     *
     * @param bo
     * @return
     */
    public  String getLoanInfos(InfoIndexInfo indexInfo, AssetOrderPool bo) {
        logger.info("getLoanInfos-indexInfo:" + indexInfo.toString());
        logger.info("getLoanInfos-BorrowOrder:" + bo.toString());
        Map<String, Object> reMap = new HashMap<String, Object>();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        Map<String, String> buttonMap = null;// 当审核拒绝时，显示button
        String intoMoney = null;// 借款金额
        String loanEndTime = null;// loanEndTime 还款时间
        String lastRepaymentD = null;// lastRepaymentD 剩余还款天数
        String yqDays = "0";
        String header_tip = "申请提交成功";
        map.put("tag", "0");
        map.put("title", "申请提交成功" + DateUtil.format(bo.getCreatedAt(), "yyyy-MM-dd HH:mm"));
        map.put("body", "申请借款" + (bo.getMoneyAmount() / 100.00) + "元，期限" + bo.getLoanTerm() + AssetBorrowOrder.loanMethed.get(bo.getLoanMethod()) + "，手续费" + (bo.getLoanInterests() / 100.00) + "元");
        list.add(map);
        map = new HashMap<String, String>();
        // 如果是 待初审(待机审)、初审通过(机审通过)，待复审 显示审核中
        // if(bo.getStatus().equals(AssetBorrowOrder.STATUS_DCS) ||
        // bo.getStatus().equals(AssetBorrowOrder.STATUS_CSTG)){
        logger.info("bo.getStatus=" + bo.getStatus());
        if (borrowStatusMap_shenhezhong.containsKey(bo.getStatus())) {

            map.put("tag", "1");
            map.put("title", "审核中");
            map.put("body", "已进入风控审核状态，请您耐心等待");
            list.add(map);
            header_tip = "风控审核中，请您耐心等待";
        }
        // 如果是 机审拒绝、初审驳回、复审驳回 显示审核不通过
        // else if(bo.getStatus().equals(AssetBorrowOrder.STATUS_JSJJ) ||
        // bo.getStatus().equals(AssetBorrowOrder.STATUS_CSBH) ||
        // bo.getStatus().equals(AssetBorrowOrder.STATUS_FSBH)){
        else if (borrowStatusMap_shenheFail.containsKey(bo.getStatus())) {
            map.put("tag", "2");
            String shTimeStr = bo.getVerifyTrialTime() == null ? "" : DateUtil.format(bo.getVerifyTrialTime(), "yyyy-MM-dd HH:mm");
            if (bo.getVerifyReviewTime() != null) {
                shTimeStr = DateUtil.format(bo.getVerifyReviewTime(), "yyyy-MM-dd HH:mm");
            }
            map.put("title", "审核不通过" + shTimeStr);
            // 如果是 机审拒绝显示初审备注
            // if(bo.getStatus().equals(AssetBorrowOrder.STATUS_JSJJ)){
            // map.put("body", bo.getVerifyTrialRemark());
            // }
            // // 如果是 初审驳回 显示初审备注
            // else
            if (bo.getStatus().equals(AssetBorrowOrder.STATUS_CSBH)) {
                if (bo.getVerifyTrialUser().equals("机审")) {
                    map.put("body", "信用评分不足");
                } else {
                    map.put("body", bo.getVerifyTrialRemark());
                }
                // <--
                Date vDate = DateUtil.offsetDay(bo.getVerifyTrialTime(), Constant.DEFAULT_DAYS);// 默认再借时间
                String scdDays = DateUtil.format(DateUtil.offsetDay(new Date(), Constant.DEFAULT_DAYS), "yyyy-MM-dd");
                if (null != vDate) {
                    scdDays = DateUtil.format(vDate, "yyyy-MM-dd");
                }
                map.put("days", scdDays);
                // -->
                buttonMap = new HashMap<String, String>();
                buttonMap.put("msg", Constant.BUTTON_MSG);
                buttonMap.put("id", indexInfo.getUserId() + "");
            }
            // 如果是 复审拒绝 显示复审备注
            else if (bo.getStatus().equals(AssetBorrowOrder.STATUS_FSBH)) {
                map.put("body", bo.getVerifyReviewRemark());
                buttonMap = new HashMap<String, String>();
                buttonMap.put("msg", Constant.BUTTON_MSG);
                buttonMap.put("id", indexInfo.getUserId() + "");
                // <--
                Date vDate = DateUtil.offsetDay(bo.getVerifyReviewTime(), Constant.DEFAULT_DAYS);// 默认再借时间
                String scdDays = DateUtil.format(DateUtil.offsetDay(new Date(), Constant.DEFAULT_DAYS), "yyyy-MM-dd");
                if (null != vDate) {
                    scdDays = DateUtil.format(vDate, "yyyy-MM-dd");
                }
                map.put("days", scdDays);
                // -->
            } else if (bo.getStatus().equals(AssetBorrowOrder.STATUS_FKBH)) {
                map.put("body", bo.getVerifyLoanRemark());
                buttonMap = new HashMap<String, String>();
                buttonMap.put("msg", Constant.BUTTON_MSG);
                buttonMap.put("id", indexInfo.getUserId() + "");
                // <--
                Date vDate = DateUtil.offsetDay(bo.getVerifyLoanTime(),
                        Constant.DEFAULT_DAYS);// 默认再借时间
                String scdDays = DateUtil.format(DateUtil.offsetDay(new Date(), Constant.DEFAULT_DAYS), "yyyy-MM-dd");
                if (null != vDate) {
                    scdDays = DateUtil.format(vDate, "yyyy-MM-dd");
                }
                map.put("days", scdDays);
                // -->
            } else {
                map.put("body", "经审核您不符合借款要求");
            }
            list.add(map);

            header_tip = "审核未通过";

        } else {
            map.put("tag", "0");
            map.put("title", "审核通过" + DateUtil.format(bo.getVerifyReviewTime(), "yyyy-MM-dd HH:mm"));
            map.put("body", "恭喜通过风控审核");
            list.add(map);
            header_tip = "审核通过";
            map = new HashMap<String, String>();
            if (bo.getStatus().equals(AssetBorrowOrder.STATUS_FSTG)) {
                map.put("tag", "1");
                map.put("title", "打款审核中");
                map.put("body", "已进入打款审核状态，请您耐心等待");
                list.add(map);
                header_tip = "打款审核中，请耐心等待";
            } else if (bo.getStatus().equals(AssetBorrowOrder.STATUS_FKZ)
                    || bo.getStatus().equals(AssetBorrowOrder.STATUS_FKSB)) {
                map.put("tag", "1");
                map.put("title", "打款中");
                map.put("body", "已进入打款流程，请您耐心等待");
                list.add(map);
                header_tip = "打款中，请耐心等待";
            } else if (bo.getStatus().equals(AssetBorrowOrder.STATUS_FKBH)) {
                map.put("tag", "2");
                map.put("title", "打款审核不通过");
                map.put("body", bo.getVerifyLoanRemark());
                list.add(map);
                header_tip = "审核未通过";
                buttonMap = new HashMap<String, String>();
                buttonMap.put("msg", Constant.BUTTON_MSG);
                buttonMap.put("id", indexInfo.getUserId() + "");
                // <--
                Date vDate = DateUtil.offsetDay(bo.getVerifyLoanTime(), Constant.DEFAULT_DAYS);// 默认再借时间
                String scdDays = DateUtil.format(DateUtil.offsetDay(new Date(), Constant.DEFAULT_DAYS), "yyyy-MM-dd");
                if (null != vDate) {
                    scdDays = DateUtil.format(vDate, "yyyy-MM-dd");
                }
                map.put("days", scdDays);
                // -->
            } else {
                map.put("tag", "0");
                map.put("title", "打款成功");
                map.put("body", "打款至银行卡尾号为" + indexInfo.getBankNo().substring(indexInfo.getBankNo().length() - 4, indexInfo.getBankNo().length()));
                list.add(map);
                map = new HashMap<String, String>();
                map.put("tag", "3");
                Map<String, Object> mapT = new HashMap<String, Object>();
                mapT.put("assetOrderId", bo.getId());
                //todo xuedaiyao 打款成功 首页状态 还款List 展示当期账单状态
                List<Repayment> repaymentList  = indexMapper.findRepaymentList(mapT);
                Repayment repayment = getCurrentRepayment(repaymentList);
                if (null != repayment){
                    //当期已还款
                    if (AssetOrderPool.STATUS_YHK.equals(repayment.getStatus()) || AssetOrderPool.STATUS_YQYHK.equals(repayment.getStatus()) ){
                        for (Repayment re : repaymentList){
                            if (re.getStatus().equals(AssetBorrowOrder.STATUS_HKZ)
                                    || re.getStatus().equals(AssetBorrowOrder.STATUS_YYQ) ||re.getStatus().equals(AssetBorrowOrder.STATUS_KKSB) || re.getStatus().equals(AssetBorrowOrder.STATUS_KKZ)|| re.getStatus().equals(AssetBorrowOrder.STATUS_YHZ) ){
                                Long moneyAmount = re.getRepaymentAmount() - re.getRepaymentedAmount();
                                moneyAmount = moneyAmount < 0 ? 0 : moneyAmount;
                                intoMoney = String.valueOf(moneyAmount / 100.00);
                                loanEndTime = DateUtil.format(re.getRepaymentTime(), "yyyy-MM-dd");
                                try {
                                    lastRepaymentD = String.valueOf(DateUtil.betweenDay(new Date(), re.getRepaymentTime(),true));
                                } catch (Exception e) {
                                    lastRepaymentD = "0";
                                    logger.error("计算剩余还款天数错误2 repayId=" + re.getId(), e);
                                }
                                if (re.getStatus().equals(AssetBorrowOrder.STATUS_HKZ)) {
                                    Long days = 0L;
                                    try {
                                        days = DateUtil.betweenDay(new Date(), re.getRepaymentTime(),true);
                                        lastRepaymentD = String.valueOf(days);
                                    } catch (Exception e) {
                                        lastRepaymentD = "0";
                                        logger.error("计算剩余还款天数错误 repayId=" + re.getId(), e);
                                    }
                                    map.put("title", days + "天后还款");
                                    map.put("body", "请于" + DateUtil.format(re.getRepaymentTime(), "yyyy-MM-dd") + "前将还款金额存入银行卡中");
                                } else if (re.getStatus().equals(AssetBorrowOrder.STATUS_YYQ)) {
                                    yqDays = String.valueOf(DateUtil.betweenDay(re.getRepaymentTime(),new Date(),true));
                                    map.put("title", "已逾期");
                                    map.put("body", "您的借款已逾期，请尽快完成还款操作");
                                } else if (re.getStatus().equals(AssetBorrowOrder.STATUS_YHZ)) {
                                    map.put("title", "已坏账");
                                    map.put("body", "您的借款已坏账，详情请联系客服");
                                } else {// if(bo.getStatus().equals(AssetBorrowOrder.STATUS_YHK))
                                    map.put("title", "已还款");
                                    map.put("body", "恭喜还款成功，又积攒了一份信用");
                                }
                                list.add(map);
                                header_tip = "还款中，请按时还款";
                                break;
                            }
                        }
                    }else {
                        //当期未还款
                        Long moneyAmount = repayment.getRepaymentAmount() - repayment.getRepaymentedAmount();
                        moneyAmount = moneyAmount < 0 ? 0 : moneyAmount;
                        intoMoney = String.valueOf(moneyAmount / 100.00);
                        loanEndTime = DateUtil.format(repayment.getRepaymentTime(), "yyyy-MM-dd");
                        try {
                            lastRepaymentD = String.valueOf(DateUtil.betweenDay(new Date(), repayment.getRepaymentTime(),true));
                        } catch (Exception e) {
                            lastRepaymentD = "0";
                            logger.error("计算剩余还款天数错误2 repayId=" + repayment.getId(), e);
                        }
                        if (repayment.getStatus().equals(AssetBorrowOrder.STATUS_HKZ) || repayment.getStatus().equals(AssetBorrowOrder.STATUS_BFHK)) {
                            Long days = 0L;
                            try {
                                days = DateUtil.betweenDay(new Date(), repayment.getRepaymentTime(),true);
                                lastRepaymentD = String.valueOf(days);
                            } catch (Exception e) {
                                lastRepaymentD = "0";
                                logger.error("计算剩余还款天数错误 repayId=" + repayment.getId(), e);
                            }
                            map.put("title", days + "天后还款");
                            map.put("body", "请于" + DateUtil.format(repayment.getRepaymentTime(), "yyyy-MM-dd") + "前将还款金额存入银行卡中");
                        } else if (repayment.getStatus().equals(AssetBorrowOrder.STATUS_YYQ)) {
                            yqDays = String.valueOf(DateUtil.betweenDay(repayment.getRepaymentTime(),new Date(),true));
                            map.put("title", "已逾期");
                            map.put("body", "您的借款已逾期，请尽快完成还款操作");
                        } else if (repayment.getStatus().equals(AssetBorrowOrder.STATUS_YHZ)) {
                            map.put("title", "已坏账");
                            map.put("body", "您的借款已坏账，详情请联系客服");
                        } else {// if(bo.getStatus().equals(AssetBorrowOrder.STATUS_YHK))
                            map.put("title", "已还款");
                            map.put("body", "恭喜还款成功，又积攒了一份信用");
                        }
                        list.add(map);
                        header_tip = "还款中，请按时还款";
                    }
                    buttonMap = new HashMap<String, String>();
                    buttonMap.put("msg", Constant.BUTTON_MSG);
                    buttonMap.put("id", indexInfo.getUserId() + "");
                }
            }
        }
        Collections.reverse(list);// 倒序排列
        reMap.put("lists", list);
        reMap.put("header_tip", header_tip);
        if (null != buttonMap) {
            reMap.put("button", buttonMap);
        }
        if (StringUtils.isNotBlank(intoMoney)) {
            reMap.put("intoMoney", intoMoney);
        }
        if (StringUtils.isNotBlank(loanEndTime)) {
            reMap.put("loanEndTime", loanEndTime);
        }
        if (null != lastRepaymentD) {
            reMap.put("lastRepaymentD", lastRepaymentD);
        }
        if (null != yqDays){
            reMap.put("yqDays",yqDays);
        }
        return JSONUtil.parseFromMap(reMap).toString();
    }

    private Map<String,Object> getRepaymentStatus(Repayment repayment,String intoMoney,String loanEndTime,String lastRepaymentD,String header_tip,
                                    AssetOrderPool bo,Map<String,String> map,List<Map<String, String>> list) {
        Map<String,Object> result = new HashMap<>();
        Long moneyAmount = repayment.getRepaymentAmount() - repayment.getRepaymentedAmount();//剩余还款金额
        moneyAmount = moneyAmount < 0 ? 0 : moneyAmount;
        intoMoney = String.valueOf(moneyAmount / 100.00);
        loanEndTime = DateUtil.format(repayment.getRepaymentTime(), "yyyy-MM-dd");//还款时间
        try {
            lastRepaymentD = String.valueOf(DateUtil.betweenDay(new Date(), repayment.getRepaymentTime(),true));
        } catch (Exception e) {
            lastRepaymentD = "0";
            logger.error("计算剩余还款天数错误2 repayId=" + repayment.getId(), e);
        }
        if (bo.getStatus().equals(AssetBorrowOrder.STATUS_HKZ) || bo.getStatus().equals(AssetBorrowOrder.STATUS_BFHK)) {
            Long days = 0L;
            try {
                days = DateUtil.betweenDay(new Date(), repayment.getRepaymentTime(),true);
                lastRepaymentD = String.valueOf(days);
            } catch (Exception e) {
                lastRepaymentD = "0";
                logger.error("计算剩余还款天数错误 repayId=" + repayment.getId(), e);
            }
            map.put("title", days + "天后还款");
            map.put("body", "请于" + DateUtil.format(repayment.getRepaymentTime(), "yyyy-MM-dd") + "前将还款金额存入银行卡中");
        } else if (bo.getStatus().equals(AssetBorrowOrder.STATUS_BFHK)) {
            map.put("title", "部分还款");
            map.put("body", "请于" + DateUtil.format(repayment.getRepaymentTime(), "yyyy-MM-dd") + "前将还款金额存入银行卡中");
        } else if (bo.getStatus().equals(AssetBorrowOrder.STATUS_YYQ)) {
            map.put("title", "已逾期");
            map.put("body", "您的借款已逾期，请尽快完成还款操作");
        } else if (bo.getStatus().equals(AssetBorrowOrder.STATUS_YHZ)) {
            map.put("title", "已坏账");
            map.put("body", "您的借款已坏账，详情请联系客服");
        } else {// if(bo.getStatus().equals(AssetBorrowOrder.STATUS_YHK))
            map.put("title", "已还款");
            map.put("body", "恭喜还款成功，又积攒了一份信用");
        }
        list.add(map);
        header_tip = "还款中，请按时还款";
        result.put("list",list);
        result.put("header_tip",header_tip);
        result.put("lastRepaymentD",lastRepaymentD);
        result.put("intoMoney",intoMoney);
        result.put("loanEndTime",loanEndTime);
        return result;
    }

    /**
     *  根据还款列表获取当期还款单
     * @param repaymentList
     * @return
     */
    private Repayment getCurrentRepayment(List<Repayment> repaymentList) {
        Repayment repayment = new Repayment();
        if(CollectionUtil.isEmpty(repaymentList)){
            return repayment;
        }
        Date now = new Date();
        for (Repayment r: repaymentList){
            if (now.after(r.getCreditRepaymentTime()) && now.before(r.getRepaymentTime()) ){
                repayment = r;
                break;
            }
        }
        return repayment;
    }

    /**
     *  根据用户ID查询审核失败订单
     * @param userId
     * @return
     */
    public Map<String, String> findAuditFailureOrderByUserId(String userId) {
        Map<String, String> result = new HashMap<String, String>();
        Integer code = 0;
        String msg = "";
        int nextLoanDay = 0;// 剩余可借款天数
        Long interval_day = 0L; // 申请失败距当前时间的间隔天数

        BackConfigParams backConfigParams = backConfigParamsMapper.getParamsByKey("INTERVAL_BORROW_AGAIN");
        int interval = Integer.parseInt(backConfigParams.getSysValue()) ;
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("statusList", Arrays.asList(AssetBorrowOrder.STATUS_CSBH, AssetBorrowOrder.STATUS_FSBH, AssetBorrowOrder.STATUS_FKBH,AssetBorrowOrder.STATUS_HKZ,AssetBorrowOrder.STATUS_BFHK));
        AssetBorrowOrder bo = indexMapper.findAuditFailureOrderByUserId(params);
        if (bo != null) {
            Date date = new Date();
            Date smDate = new Date();
            try {
                if (bo.getVerifyLoanTime() != null) {
                    smDate = bo.getVerifyLoanTime();
                } else if (bo.getVerifyReviewTime() != null) {
                    smDate = DateUtil.parse(bo.getVerifyReviewTime());
                } else if (bo.getVerifyTrialTime() != null) {
                    smDate = bo.getVerifyTrialTime();
                }
                interval_day = DateUtil.betweenDay(smDate, date,true);
                code = interval_day < interval ? -1 : 0;

            } catch (Exception e) {
                code = -1;
            }
            if (code == -1) {
                msg = "距离上次审核失败不足" + interval + "天，请" + (interval - interval_day) + "天后重新申请。";
                nextLoanDay = (int) (interval - interval_day);
                result.put("canLoan", new SimpleDateFormat("yyyy-MM-dd").format(DateUtil.offsetDay(smDate, interval - 1)));
            }
        }
        result.put("code", code + "");
        result.put("msg", msg);
        result.put("nextLoanDay", String.valueOf(nextLoanDay));
        return result;
    }


    /**
     * 查询服务费明细
     *
     * @param periods     借款期数
     * @param moneyAmount 借款金额
     * @return
     */
    @Override
    public List<Map<String, Object>> getServiceFeeDetail(Integer periods, Integer moneyAmount) {
        try{
            //用户不登录 也让看到各种费用信息
            //获取费率
            Map<String,Double> map = getAllrateDetail(periods +"");
            DecimalFormat df   = new DecimalFormat("######0.00");
            List<Map<String,Object>> list = new ArrayList<>();
            if(map==null){
                if(null == moneyAmount || moneyAmount == 0){
                    Map<String, Object> fees = new HashMap<String, Object>();
                    fees.put("name", "管理费");
                    fees.put("value", "0.00");
                    list.add(fees);
                    fees = new HashMap<String, Object>();
                    fees.put("name", "咨询费");
                    fees.put("value", "0.00");
                    list.add(fees);
                    fees = new HashMap<String, Object>();
                    fees.put("name", "合计");
                    fees.put("value", "0.00");
                    list.add(fees);
                } else{
                    Map<String, Object> fees = new HashMap<String, Object>();
                    fees.put("name","管理费");
                    fees.put("value", df.format((moneyAmount / 100 ) * 0.100 ));
                    list.add(fees);
                    fees = new HashMap<String, Object>();
                    fees.put("name", "咨询费");
                    fees.put("value", df.format((moneyAmount / 100 ) * 0.025));
                    list.add(fees);
                    fees = new HashMap<String, Object>();
                    fees.put("name", "合计");
                    fees.put("value", df.format((moneyAmount / 100) * 0.125));
                    list.add(fees);
                }
            }else{
                Double zxRate = map.get("zxRate");// 咨询费率
                Double manageRate = map.get("manageRate");// 管理费率
                Map<String, Object> fees = new HashMap<String, Object>();
                fees.put("name", "咨询费");
                fees.put("value", df.format(moneyAmount * zxRate));
                list.add(fees);
                fees = new HashMap<String, Object>();
                fees.put("name", "管理费");
                fees.put("value", df.format(moneyAmount * manageRate));
                list.add(fees);
                fees = new HashMap<String, Object>();
                fees.put("name", "合计");
                fees.put("value", df.format(moneyAmount  * (zxRate+manageRate)));
                list.add(fees);
            }
            return list;
        } catch (Exception e){
            logger.error("获取服务费计算错误 periods=" + periods + " moneyAmount=" + moneyAmount, e);
            return null;
        }
    }

    /**
     *  获取所有服务费费率
     * @param periods
     * @return
     */
    public Map<String,Double> getAllrateDetail(String periods) {
        Map<String,Double> reMap = new HashMap<String,Double>();
        try {
            Double zxRate = null;// 咨询费率
            Double manageRate = null;// 管理费率

            // 咨询费
            BackConfigParams zxfeeParam = backConfigParamsMapper.getParamsByKey("hkd_zxfee");
            String zxfeeParamSysValue = zxfeeParam.getSysValue();
            String[] zxfArr = zxfeeParamSysValue.split(";");
            for (String a : zxfArr) {
                String zxfRates[] = a.split(":");
                if (periods.equals(zxfRates[0])) {// 判断天数是否符合规定的天数例如 7 or 14
                    zxRate = Double.valueOf(zxfRates[1]);
                }
            }
            // 管理费率
            BackConfigParams mgmfeeParam = backConfigParamsMapper.getParamsByKey("hkd_mgmfee");
            String mgmfeeParamSysValue = mgmfeeParam.getSysValue();
            String[] mgmfArr = mgmfeeParamSysValue.split(";");
            for (String s : mgmfArr) {
                String mgmfRates[] = s.split(":");
                if (periods.equals(mgmfRates[0])) {// 判断天数是否符合规定的天数例如 7 or 14
                    manageRate = Double.valueOf(mgmfRates[1]);
                }
            }
            reMap.put("zxRate", zxRate);
            reMap.put("manageRate", manageRate);
        } catch (Exception e) {
            logger.error("获取各种利率失败， 默认利率",e);
            return null;
        }
        return reMap;
    }


    public static void main(String[] args) {
        System.out.println(String.valueOf(DateUtil.betweenDay(new Date(),DateUtil.parseDate("2017-10-20 15:22:50"),true)));
    }


}



