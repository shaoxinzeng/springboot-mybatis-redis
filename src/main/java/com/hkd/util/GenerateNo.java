package com.hkd.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 产生各种单号
 *
 */
public class GenerateNo {
	
	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
        "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
        "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
        "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
        "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
        "W", "X", "Y", "Z" };
	private static int indx = 10;
	public static synchronized int nextIndx(){
		if(indx > 999)
			indx = 10;
		return indx ++;
	}
	  /**
	   * 生成充值流水号，是E开始+ 用户ID+当前的年月日时分秒+6位不重复的随机数
	   * @return
	   */
      public static String payRecordNo(Integer userId){
    	 try{
          String pre="E";
    	  SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
    	  String dateString=sdf.format(new Date());
    	  String randomString= String.valueOf(Math.random()).substring(2).substring(0, 6);
    	  
    	  return pre+userId+dateString+randomString;
    	 }catch (Exception e) {
			System.out.println("生成充值流水号出错===="+e.toString());
		 }
    	 return "";
      }
	  /**
	   * 生成充值流水号，是pre开始+ 用户ID+当前的年月日时分秒+6位不重复的随机数
	   * @return
	   */
      public static String payRecordNo(String pre){
    	 try{
    		 if(pre==null){
    			  pre="E"; 
    		 }
    	  SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
    	  String dateString=sdf.format(new Date());
    	  String randomString= String.valueOf(Math.random()).substring(2).substring(0, 6);
    	  
    	  return pre+dateString+randomString;
    	 }catch (Exception e) {
			System.out.println("生成充值流水号出错===="+e.toString());
		 }
    	 return "";
      }
      /**
       * 获取纯数字唯一订单号
       * 
       * @return
       */      
      public static String nextOrdId(){
    	  long time = new Date().getTime();
    	  int end = (int) (Math.random() * 10);
    	  return String.valueOf(nextIndx())
    			  .concat(String.valueOf(time))
    			  .concat(String.valueOf(end));
    			  
      }


	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		str =str.replaceAll("-","").replaceAll("[a-zA-Z]","");
		int end = (int) ((Math.random() + 1) * 10000);
		// 去掉"-"符号
		return str.substring(0,10) + end;

	}


	/**
       * 获取纯数字唯一订单号10位数，有问题，需要完善。不一定唯一
       * 
       * @return
       */      
      public static String nextOrdIdTen(){
    	  long time = new Date().getTime();
    	  System.out.println(time);
    	  return    String.valueOf(time).substring(3, 13);
//    	  int end = (int) (Math.random() * 10);
//    	  return String.valueOf(nextIndx())
//    			  .concat(String.valueOf(time))
//    			  .concat(String.valueOf(end));
//    			  
      }
      public static void main(String[] args) throws Exception {
    	 System.out.println(GenerateNo.payRecordNo("A")); 
    	 System.out.println(payRecordNo("B")); 
    	 System.out.println(payRecordNo("C")); 
//    	 System.out.println(nextOrdIdTen()); 
    	  
      }
      /**
	   * 生成充值流水号16位，是当前的年月日时分秒+2位不重复的随机数  
	   * @return
	   */
      public static String payRecordNo(){
    	 try{
          
    	  SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
    	  String dateString=sdf.format(new Date());
    	  String randomString= String.valueOf(Math.random()).substring(2).substring(0, 2);
    	  
    	  return dateString+randomString;
    	 }catch (Exception e) {
			System.out.println("生成充值流水号出错===="+e.toString());
		 }
    	 return "";
      }
      /**
	   * 生成VipCard，是VIP开始+ 用户ID+当前的年月日时分秒+6位不重复的随机数
	   * @return
	   */
      public static String vipCard(Integer userId){
    	 try{
          String pre="VIP";
    	  SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
    	  String dateString=sdf.format(new Date());
    	  String randomString= String.valueOf(Math.random()).substring(2).substring(0, 6);
    	  return pre+userId+dateString+randomString;
    	 }catch (Exception e) {
			System.out.println("生成VIPCard出错===="+e.toString());
		 }
    	 return "";
      }
      
      /**
       * 生成标编号 生成规则，标种编号+发标人ID+年月日时分少+6位随机数
       * 2014-1-9
       * cjx
       */
      public static String getBorrowNo(String borrowTypeNo,Integer userId){
     	 try{
     	   SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
     	   String dateString=sdf.format(new Date());
     	   String randomString= String.valueOf(Math.random()).substring(2).substring(0, 6);
     	  
     	   return borrowTypeNo+userId+dateString+randomString;
     	 }catch (Exception e) {
 			System.out.println("生成标编号出错===="+e.toString());
 		 }
     	 return "";
       }
      
      
     /**
      * 获得num位订单号 
      * @param num
      * @return
      */
  	public static String generateShortUuid(int num) {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < num; i++) {
		    String str = uuid.substring(i, i + 4);
		    int x = Integer.parseInt(str, 16);
		    shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString();

	}
     /**
      * 获得6位数随机验证码
      * @return
      */
  	public static String getSmsRandom() {

		return String.valueOf(Math.random()).substring(2).substring(0, 6);// 6位固定长度;

	}

}
