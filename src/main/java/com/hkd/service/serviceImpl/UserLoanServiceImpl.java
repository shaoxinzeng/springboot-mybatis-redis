package com.hkd.service.serviceImpl;

import com.hkd.entity.*;
import com.hkd.entity.vo.UserRequestVo;
import com.hkd.exception.HkdException;
import com.hkd.mapper.*;
import com.hkd.service.BorrowOrderService;
import com.hkd.service.IndexService;
import com.hkd.service.RedisService;
import com.hkd.service.UserLoanService;
import com.hkd.util.CollectionUtil;
import com.hkd.util.GenerateNo;
import com.hkd.util.RedisUtil;
import com.hkd.util.ResultUtils;
import com.hkd.util.encrypt.AESUtil;
import com.hkd.util.encrypt.MD5coding;
import com.qiyuesuo.sdk.SDKClient;
import com.qiyuesuo.sdk.api.SealService;
import com.qiyuesuo.sdk.impl.SealServiceImpl;
import com.qiyuesuo.sdk.signer.PaperType;
import com.qiyuesuo.sdk.signer.Person;
import com.xiaoleilu.hutool.json.JSONUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * User: xuedaiyao
 * Date: 2017/8/22
 * Time: 15:32
 * Description:
 */
@Service
public class UserLoanServiceImpl implements UserLoanService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisService redisService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BorrowOrderService borrowOrderService;
    @Autowired
    private UserCardInfoMapper userCardInfoMapper;
    @Autowired
    private BackConfigParamsMapper backConfigParamsMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private IndexService indexService;
    @Autowired
    private JedisCluster jedisCluster;
    @Autowired
    private BorrowOrderMapper borrowOrderMapper;
    @Autowired
    private AssetOrderPoolMapper assetOrderPoolMapper;
    @Autowired
    private AssetBorrowOrderMapper assetBorrowOrderMapper;
    @Autowired
    private AssetOrderConnectMapper assetOrderConnectMapper;

    @Value("${serverUrl}")
    private String serverUrl;

    @Value("${accessKey}")
    private String accessKey;

    @Value("${accessSecret}")
    private String accessSecret;


    /**
     *  去借款
     * @param userRequestVo 用户登录参数vo
     * @param money 借款金额
     * @param periods 借款期数
     * @return
     */
    @Override
    public Result getconfirmData(UserRequestVo userRequestVo, String money, Integer periods,HttpServletRequest request) {
        Map<String, Object> data = new HashMap<String, Object>();
        Map<String, Object> json = new HashMap<String, Object>();
        String key = "xjx_borrow_apply_loan"; //
        boolean isDelKey = true;
        Result response = new Result();
        String userId = redisService.getLoginUser(userRequestVo.getDeviceId(),userRequestVo.getMobilePhone());
        key = key + "_" + userId; // 确定key唯一
        if (null != redisUtil.get(key)) {
            isDelKey = false;
            response.setMessage("您的借款申请正在处理中，请稍后再试");
            response.setCode("600");
           return  response;
        } else {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("userId", userId);
            map.put("money", money);
            map.put("period", periods.toString());
            Result serviceResult = allowBorrow(map);
            if ("0".equals(serviceResult.getCode())) {
                // 从数据库获取最新的user信息，因需要判断用户的可借额度
                User user = userMapper.selectByPrimaryKey(userId);
                // 修改用户设备号
                if (StringUtils.isBlank(user.getEquipmentNumber()) || "H5".equals(user.getEquipmentNumber())
                        || user.getUserName().equals(user.getEquipmentNumber())) {
                    User u = new User();
                    String equipmentNumber = userRequestVo.getDeviceId();// 设备号
                    if (StringUtils.isBlank(equipmentNumber)) {
                        if (!StringUtils.isBlank(user.getUserName())) {
                            u.setId(user.getId());
                            u.setEquipmentNumber(user.getUserName());
                            userMapper.updateByPrimaryKeySelective(u);
                        }
                    } else {
                        u.setId(user.getId());
                        u.setEquipmentNumber(equipmentNumber);
                        userMapper.updateByPrimaryKeySelective(u);
                    }
                }
                HashMap<String, Object> param = new HashMap<String, Object>();
                // 查询用户的借款入账账户信息
                param.put("userId", user.getId()); //
                param.put("type", "2"); // 借记卡
                param.put("status", "1"); // 状态成功
                List<UserCardInfo> cardInfo = userCardInfoMapper.findUserCardByUserId(user.getId(),2,1);//查询有效的用户银行卡信息
                UserCardInfo info = new UserCardInfo();
                if (CollectionUtil.notEmpty(cardInfo)) {
                    info = cardInfo.get(0);
                } /*else {
                    response.setMessage("请先绑定银行卡");
                    response.setCode("601");
                    return response;
                }*/
                // 借款金额
                Integer loanMoney = Integer.parseInt(money);
                // 计算服务费
                int serviceFee = 0;
                List<Map<String, Object>> fee = indexService.getServiceFeeDetail(periods,loanMoney);
                for (Map<String,Object> map1:fee){
                    if ("合计".equals(map1.get("name"))){
                        serviceFee = new BigDecimal(map1.get("value").toString()).intValue();
                    }
                }
                json.put("service_fee", String.valueOf(serviceFee));
                json.put("true_money", String.valueOf(loanMoney - serviceFee));
                json.put("period", String.valueOf(periods));
                List<Map<String,Object>> interests = this.getHDKInterests();
                for (Map<String,Object> map1:interests){
                    if (periods.equals(Integer.parseInt(map1.get("periods").toString()))){
                        DecimalFormat df = new DecimalFormat("#.00");
                        json.put("tips", "您将还款" + periods + "期，累计" +
                                df.format(Integer.parseInt(money)+ ((Integer.parseInt(money)) * (Double.valueOf(map1.get("interest").toString())))*periods) + "元");
                    }
                }
                json.put("money", money);
                json.put("bank_name", info.getBankName() == null ? "" : info.getBankName());
                json.put("card_no", info.getCard_no() == null ? "" : info.getCard_no());
                if (info.getCard_no() != null) {
                    //截取银行卡号后4位
                    json.put("card_no_lastFour", info.getCard_no().substring(info.getCard_no().length()-4, info.getCard_no().length()));
                }
                json.put("protocol_msg", "");
                //借款咨询服务协议
                json.put("protocol_url",
                        /*request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + */"/credit-loan/agreement");
                //扣款协议
					json.put("withholding_url",
						/*	request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()
									+ */"/agreement/withholdAuthorization");
                //平台服务协议
                json.put("platformservice_url",
                        /*request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()
                                + */"/agreement/platformServiceNew");
                //绑定银行卡H5
                json.put("firstUserBank_url", /*request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()
                        +*/"/lianlianBindCard/credit-card/firstUserBank");
                json.put("real_pay_pwd_status", StringUtils.isBlank(user.getPayPassword()) ? "0" : "1"); // 支付密码状态；1：已设置支付密码；0：未设置支付密码
                json.put("verify_loan_pass", "1"); // 是否通过认证：1：认证全部通过；0：认证不通过
            } else {
                response.setMessage(serviceResult.getMessage());
                response.setCode("602");
                return response;
            }
        }
        if (isDelKey) {
            redisUtil.delete(key);
        }
        data.put("item", json);
        return ResultUtils.success(data);

    }

    /**
     *  提交借款申请
     * @param userRequestVo 用户请求VO
     * @param money 借款金额
     * @param periods 借款期数
     * @param pay_password 付款密码
     * @param request
     * @return
     */
    @Override
    @Transactional
    public Result commitLoan(UserRequestVo userRequestVo,String money,Integer periods,String pay_password,HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> item = new HashMap<String, Object>();
        String key = "xjx_borrow_apply_loan"; // redis key
        Result r = new Result();
        String msg = null;
        String code = null;
        boolean isDelKey = true;
        try {
            boolean isTg = false;
            String userId = redisService.getLoginUser(userRequestVo.getDeviceId(),userRequestVo.getMobilePhone());
            User user = new User();
            if ( StringUtils.isBlank(userId)) {
                String ip = request.getRemoteAddr();
                String value = backConfigParamsMapper.getParamsByKey("TG_SERVER_IP").getSysValue();
                if (StringUtils.isNotBlank(value)) {
                    String[] ips = value.split(",");
                    List<String> list = Arrays.asList(ips);
                    if (list.contains(ip)) {
                        isTg = true;
                        user = userMapper.searchByUserid(Integer.valueOf(AESUtil.decrypt(request.getParameter("userId"), backConfigParamsMapper.getParamsByKey("TG_SERVER_KEY").getSysValue())));
                    }
                }
            }
            if (user == null) {
                if (isTg) {
                    msg = "用户不存在";
                } else {
                    msg = "请先登录";
                }
                r.setCode("-1");
                r.setMessage(msg);
                return r;
            } else if (!isTg) {
                // 从数据库获取最新的user信息，因需要判断用户的可借额度
                user = userMapper.searchByUserid(Integer.parseInt(userId));
            }
            key = key + "_" + user.getId(); // 确定key唯一
            if (StringUtils.isNotBlank(jedisCluster.get(key))) {
                isDelKey = false;
                msg = "您的借款申请正在处理中，请稍后再试";
            } else {
                HashMap<String, String> map2 = new HashMap<String, String>();
                map2.put("userId", user.getId());
                map2.put("money", money);
                map2.put("period", periods.toString());
                map2.put("pay_password", pay_password);
                Result serviceResult = allowBorrow(map2);
                if ("0".equals(serviceResult.getCode())) {
                    AESUtil aesEncrypt = new AESUtil();
                    String checkPassWord = MD5coding.MD5(aesEncrypt.encrypt(pay_password, ""));// 加密
                    if (!isTg && !user.getPayPassword().equals(checkPassWord)) {
                        msg = "支付密码错误";
                        r.setCode("-103");
                        r.setMessage(msg);
                        return r;
                    }
                    Map<String, Object> map = this.saveLoan(money,periods, user);
                    r.setCode(map.get("code").toString());;
                    r.setMessage(map.get("msg").toString()); ;
                    item.put("order_id", map.get("orderId"));
                    // 申请借款成功-更新info_user_info borrow_status 状态为可见
                    HashMap<String, Object> borrowMap = new HashMap<String, Object>();
                    borrowMap.put("USER_ID", user.getId());
                    borrowMap.put("BORROW_STATUS", "1");
                    // System.out.println("borrowMap="+borrowMap+"  申请借款，需要显示进度");
                    borrowOrderMapper.updateInfoUserInfoBorrowStatus(borrowMap);

                    if(StringUtils.isBlank(user.getQysId())){
                        try{
                            // 生成个人印章数据
                            SDKClient client = new SDKClient(serverUrl,accessKey,accessSecret);
                            SealService sealService = new SealServiceImpl(client);
                            Person person = new Person(user.getRealname());
                            person.setIdcard(user.getIdNumber());
                            person.setPaperType(PaperType.IDCARD);
                            person.setMobile(user.getUserName());
                            String sealData = sealService.generateSeal(person);// 生成个人印章数据
                            if(StringUtils.isNotBlank(sealData)){
                                user.setQysId(sealData);
                                userMapper.updateByPrimaryKeySelective(user);
                            }
                        }catch (Exception e) {
                            logger.error("生成个人签名错误======", e);
                        }
                    }
                    jedisCluster.del(key);
                } else {
                    r.setCode(serviceResult.getCode());
                    r.setMessage(serviceResult.getMessage());
                }
            }

        } catch (HkdException e) {
            logger.error("credit-loan_get-apply-loan======", e);
            e.printStackTrace();
            r.setCode(String.valueOf(e.getErrorCode()));
            r.setMessage(e.getErrorMsg());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//如果异常手动回滚事务
        } finally {
            if (isDelKey) {
                try {
                    jedisCluster.del(key);
                } catch (Exception e) {
                    try {
                        jedisCluster.expire(key, 0);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
            result.put("item", item);
            r.setData(JSONUtil.parseFromMap(result));
            return r;
        }
    }

    /**
     *  检查是否允许借款
     * @param params
     * @return
     */
    public Result allowBorrow(HashMap<String, String> params) {
        Result serviceResult = ResultUtils.error("500", "未知异常");
        String key = "xjx_borrow_confirm_loan"; // redis key
        String userId = params.get("userId");
        try {
            User user = userMapper.selectByPrimaryKey(userId);
            redisUtil.setWithExpireTime(key,userId ,60);
            if (!"2".equals(user.getNewFlag())) {
                serviceResult.setMessage("您当前为新用户，信用正在审核中，请您耐心等待");
            } else {
                if (StringUtils.isBlank(params.get("money")) || StringUtils.isBlank(params.get("period"))) {
                    serviceResult.setMessage("参数错误");
                } else {
                    String currperiodIsopen = "0";// 默认关
                    String currperiod = params.get("period");// 当前借款期限
                    BackConfigParams keys = backConfigParamsMapper.getParamsByKey("hkd_is_open");
                    String switchString  = keys.getSysValue();
                    try {
                        String periodTypesArr[] = switchString.split(";");
                        for (String p : periodTypesArr) {
                            String period[] = p.split(":");
                            if (currperiod.equals(period[0])) {
                                currperiodIsopen = period[1];
                                break;
                            }
                        }
                    } catch (Exception e) {
                        logger.error("judge periodsTypeIsOpen error", e);
                    }

                    if (!currperiodIsopen.equals("1")) {
                        serviceResult.setMessage("暂不受理" + currperiod + "期借款服务");
                    } else {
                        if (!"1".equals(user.getRealnameStatus())) {
                            serviceResult.setMessage("请先实名认证");
                        } else {
                            if ("2".equals(user.getStatus())) {
                                serviceResult.setMessage("您为黑名单用户，暂不能借款");
                            } else {
                                if (StringUtils.isBlank(user.getFirstContactPhone()) || StringUtils.isBlank(user.getSecondContactPhone())) {
                                    serviceResult.setMessage("请完善紧急联系人信息");
                                } else {
                                    if ("1".equals(user.getZmStatus())) {
                                        serviceResult.setMessage("芝麻信用未认证");
                                    } else {
                                        if ("1".equals(user.getJxlStatus())) {
                                            serviceResult.setMessage("手机运营商未认证");
                                        } else {
                                            Integer checkResult = borrowOrderService.checkBorrow(Integer.parseInt(user.getId()));
                                            if (1 == checkResult) {
                                                serviceResult.setMessage("您有借款申请正在审核或未还款完成，暂不能借款。");
                                            } else {
                                                Integer money = Integer.parseInt(params.get("money"));
                                                if (money < 10) {
                                                    serviceResult.setMessage("您的借款金额不能低于10元。");
                                                } else {
                                                    int minAmount = Integer.parseInt(user.getAmountMin()) / 100;
                                                    if (minAmount <= 0) {
                                                        serviceResult.setMessage("您的最低借款额度为0元，暂不能借款。");
                                                    } else if (money < minAmount) {
                                                        serviceResult.setMessage("您的最低借款额度为" + minAmount + "元。");
                                                    } else {
                                                        int maxAmount = Integer.parseInt(user.getAmountMax()) / 100;
                                                        if (maxAmount <= 0) {
                                                            serviceResult.setMessage("您的最高借款额度为0元，暂不能借款。");
                                                        } else if (money > maxAmount) {
                                                            serviceResult.setMessage("您的最高借款额度为" + maxAmount + "元。");
                                                        } else {
                                                            int amountAvailable = Integer.parseInt(user.getAmountAvailable()) / 100;
                                                            if (amountAvailable <= 0) {
                                                                serviceResult.setMessage("您的剩余可借额度为0元，暂不能借款。");
                                                            } else if (money > amountAvailable) {
                                                                serviceResult.setMessage("您的剩余可借额度为" + amountAvailable + "元");
                                                            } else {
                                                                // 系统最大额度
                                                                Integer sysAmountMax = Integer.parseInt(backConfigParamsMapper.getParamsByKey("max_amount_sys").getSysValue()) / 100;
                                                                if (money > sysAmountMax) {
                                                                    serviceResult.setMessage("您目前最高可借款额度不能超过" + sysAmountMax + "元。");
                                                                } else {
                                                                    Map<String, String> interval = borrowOrderService.findAuditFailureOrderByUserId(user.getId());
                                                                    if ("-1".equals(interval.get("code"))) {
                                                                        serviceResult.setData(interval.get("canLoan"));
                                                                        serviceResult.setMessage(interval.get("msg"));
                                                                    } else {
                                                                        serviceResult = ResultUtils.success("可以借款");
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("allowBorrow error ", e);
        } finally {
            return serviceResult;
        }
    }

    /**
     *  保存借款申请
     * @param money 借款金额
     * @param periods 借款期数
     * @param user 用户
     * @return
     */
    @Transactional
    public Map<String, Object> saveLoan(String money,Integer periods, User user) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<UserCardInfo> cardInfo = userCardInfoMapper.findUserCardByUserId(user.getId(),2,1);
        UserCardInfo info = new UserCardInfo();//用户的银行卡
        if (com.xiaoleilu.hutool.util.CollectionUtil.isNotEmpty(cardInfo)) {
            info = cardInfo.get(0);
        } else {
            result.put("code", -1);
            result.put("msg", "请先绑定银行卡");
            return result;
        }

        List<AssetBorrowOrder> boList = borrowOrderMapper.findByUserId(Integer.valueOf(user.getId()));
        List<Integer> paying = new ArrayList<Integer>();
        paying.add(22);
        paying.add(25);
        for (AssetBorrowOrder bo:boList){
            if(paying.contains(bo.getStatus())){
                result.put("code", -1);
                result.put("msg", "您当前有处理中的借款, 请不要重复提交");
                return result;
            }
        }
        // 获取滞纳金/借款服务费率走向，用户拆分服务费
        Integer lateApr = Integer.valueOf(backConfigParamsMapper.getParamsByKey("fee_lateapr").getSysValue());
        Integer fee_company = Integer.valueOf(backConfigParamsMapper.getParamsByKey("fee_company").getSysValue()); // 公司
        Integer fee_person = Integer.valueOf(backConfigParamsMapper.getParamsByKey("fee_person").getSysValue()); // 个人
        // 查询银行信息
        Map<String, String> bankInfo = userCardInfoMapper.selectBankInfoByBankId(info.getBank_id());
        Integer bank_iscmb = 2; // 是否是招商银行 ，默认：1：是，2：否
        if (bankInfo != null && "0308".equals(bankInfo.get("bankCode"))) {
            bank_iscmb = 1;
        }
        // 计算服务费率
        Integer borrowAmount = Integer.parseInt(money) * 100; // 借款金额// ，单位：分
        List<Map<String, Object>> fee = indexService.getServiceFeeDetail(periods,borrowAmount);
        Integer serviceFee = 0;
        for (Map<String,Object> map:fee){
            if ("合计".equals(map.get("name"))){
                serviceFee = new BigDecimal(map.get("value").toString()).intValue() ;
            }
        }
        //利率
        List<Map<String,Object>> rates = this.getHDKInterests();
        Integer loanRate = 0;
        for (Map<String,Object> map:rates){
            if (Integer.parseInt(map.get("periods").toString()) == periods){
                loanRate = (int)(Double.valueOf(map.get("interest").toString())*100*100);
            }
        }
        Integer intoMoney = borrowAmount - serviceFee;
        Date date = new Date();

        //生成总订单
        AssetOrderPool assetOrderPool = new AssetOrderPool();
        assetOrderPool.setUserId(Integer.parseInt(user.getId()));
        assetOrderPool.setOutTradeNo(GenerateNo.nextOrdId());
        assetOrderPool.setOrderType(1);//1-现金侠
        assetOrderPool.setMoneyAmount(borrowAmount);//借款金额
        assetOrderPool.setIntoMoney(intoMoney);//实际到账金额
        assetOrderPool.setLoanInterests(serviceFee);//服务费
        assetOrderPool.setLoanMethod(1);//按月
        assetOrderPool.setLoanTerm(periods);
        assetOrderPool.setCreatedAt(date);
        assetOrderPool.setUpdatedAt(date);
        assetOrderPool.setOrderTime(date);
        assetOrderPool.setReceiveCardId(info.getBank_id());
        assetOrderPool.setUserPhone(user.getUserPhone());
        assetOrderPool.setRealname(user.getRealname());
        assetOrderPool.setBankNumber(bankInfo.get("bankNumber"));
        assetOrderPool.setCardNo(info.getCard_no());
        assetOrderPool.setBankIscmb(bank_iscmb);
        assetOrderPool.setYurref(GenerateNo.payRecordNo("A"));
        assetOrderPool.setCustomerType(Integer.parseInt(user.getCustomerType()));
        assetOrderPool.setIdNumber(user.getIdNumber());
        assetOrderPool.setSerialNo(GenerateNo.generateShortUuid(10));
        assetOrderPool.setLoanRate(loanRate);
        assetOrderPool.setStatus(AssetOrderPool.STATUS_DCS);
        assetOrderPoolMapper.insertSelective(assetOrderPool);
        Integer totalOrderId = 0;
        if (null != assetOrderPool.getId()){
            totalOrderId = assetOrderPool.getId();
            //生成分期订单
            savePeriodOrder(totalOrderId,user,periods,money,info,bank_iscmb,loanRate,bankInfo,intoMoney,serviceFee);
            //修改用户剩余额度
            Integer userAmountAvalable = Integer.parseInt(user.getAmountAvailable()) - Integer.parseInt(money) * 100;
            user.setAmountAvailable(userAmountAvalable.toString());
            userMapper.updateByPrimaryKeySelective(user);
            Integer addAmount = 0;
            Map<String, BigDecimal> userLimit = borrowOrderMapper.countAddAmount(Integer.parseInt(user.getId()));//查询用户提额记录
            if (null != userLimit && userLimit.get("add_amount") != null) {
                addAmount = userLimit.get("add_amount").intValue();
            }
            RiskCreditUser risk = new RiskCreditUser();
            risk.setUserId(Integer.parseInt(user.getId()));
            risk.setAssetId(totalOrderId);
            risk.setCardNum(user.getIdNumber());
            risk.setUserName(user.getRealname());
            risk.setAge(user.getUserAge());
            risk.setEducation(Integer.parseInt(StringUtils.isNotBlank(user.getEducation()) ? user.getEducation() : "7"));
            if ("男".equals(user.getUserSex())) {
                risk.setSex(1);
            } else if ("女".equals(user.getUserSex())) {
                risk.setSex(2);
            }
            risk.setUserPhone(user.getUserPhone());
            risk.setAmountAddsum(new BigDecimal(addAmount / 100.00));
            risk.setMoneyAmount(new BigDecimal(assetOrderPool.getMoneyAmount() / 100.00));
            borrowOrderMapper.insertRiskUser(risk);
            result.put("code", 0);
            result.put("msg", "借款申请提交成功");
            result.put("orderId",totalOrderId);
        }
        return result;
    }

    /**
     *  插入分期借款订单
     * @param totalOrderId  总订单ID
     * @param user  用户
     * @param periods 借款期数
     * @param money 借款金额
     */
    private void savePeriodOrder(Integer totalOrderId, User user, Integer periods, String money,UserCardInfo info,Integer bank_iscmb,Integer loanRate,Map<String,String> bankInfo,Integer intoMoney,Integer serviceFee) {
        if (periods < 1){
            return;
        }
        Integer userId = Integer.parseInt(user.getId());
        int tmpSum = 0;
        int intoSum = 0;
        int serviceSum = 0;
        for (int i = 1;i<= periods;i++){
            AssetBorrowOrder assetBorrowOrder = new AssetBorrowOrder();
            assetBorrowOrder.setUserId(userId);
            assetBorrowOrder.setOutTradeNo(GenerateNo.nextOrdId());
            //保存期数
            assetBorrowOrder.setPeriod(i);
            int amount = Math.round(Integer.parseInt(money)/periods) ;
            if (i != periods){
                tmpSum+=amount;
            }
            assetBorrowOrder.setMoneyAmountShow(amount * 100);
            DecimalFormat df = new DecimalFormat("#.00");
            Double showAmount = Double.valueOf((amount *100)+ Double.valueOf(df.format((float)loanRate/10000 ))*(Integer.parseInt(money)*100));
            assetBorrowOrder.setMoneyAmount(showAmount.intValue());
            if (i == periods){
                assetBorrowOrder.setMoneyAmountShow((Integer.parseInt(money) - tmpSum)*100);
                showAmount = Double.valueOf(((Integer.parseInt(money) - tmpSum)*100)+ Double.valueOf(df.format((float)loanRate/10000 ))*(Integer.parseInt(money)*100));
                assetBorrowOrder.setMoneyAmount(showAmount.intValue());
            }
            int intoAmount = Math.round(intoMoney/periods) ;
            int serviceAmount = Math.round(serviceFee /periods) ;
            if (i != periods){
                intoSum+=intoAmount;
                serviceSum+=serviceAmount;
            }
            assetBorrowOrder.setIntoMoney(intoAmount);
            assetBorrowOrder.setLoanInterests(serviceAmount);
            if (i == periods){
                assetBorrowOrder.setIntoMoney(intoMoney - intoSum);
                assetBorrowOrder.setLoanInterests(serviceFee - serviceSum );
            }
            assetBorrowOrder.setLoanTerm(periods);
            assetBorrowOrder.setLoanRate(loanRate);
            assetBorrowOrder.setLoanMethod(1);
            assetBorrowOrder.setOrderTime(new Date());
            assetBorrowOrder.setCreatedAt(new Date());
            assetBorrowOrder.setUpdatedAt(new Date());
            assetBorrowOrder.setUserPhone(user.getUserPhone());
            assetBorrowOrder.setRealname(user.getRealname());
            assetBorrowOrder.setIdNumber(user.getIdNumber());
            assetBorrowOrder.setOrderType(1);
            assetBorrowOrder.setStatus(AssetBorrowOrder.STATUS_DCS);
            assetBorrowOrder.setCardNo(info.getCard_no());
            assetBorrowOrder.setBankIscmb(bank_iscmb);
            assetBorrowOrder.setBankNumber(bankInfo.get("bankNumber"));
            assetBorrowOrder.setCustomerType(Integer.parseInt(user.getCustomerType()));
            assetBorrowOrderMapper.insertSelective(assetBorrowOrder);
            if (null != assetBorrowOrder.getId()){
                //插入关联表
                AssetOrderConnect assetOrderConnect = new AssetOrderConnect();
                assetOrderConnect.setId(totalOrderId);//pool的ID
                assetOrderConnect.setUserId(userId);
                assetOrderConnect.setAssetOrderId(assetBorrowOrder.getId());
                assetOrderConnect.setSort(i);
                assetOrderConnectMapper.insertSelective(assetOrderConnect);
            }
        }
    }

    /**
     *  获取分期利息
     * @return
     */
    public List<Map<String,Object>> getHDKInterests() {
        BackConfigParams backConfigParams = backConfigParamsMapper.getParamsByKey("hkd_rate");
        List<Map<String,Object>> list = new ArrayList<>();
        if (null != backConfigParams){
            String[] interestString = backConfigParams.getSysValue().split(";");
            for (String a : interestString) {
                String interests[] = a.split(":");
                Map<String,Object> map = new HashMap<>();
                map.put("periods",interests[0]);
                map.put("interest",interests[1]);
                list.add(map);
            }
        }
        return list;
    }

}

