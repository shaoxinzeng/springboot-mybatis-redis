package com.hkd.util.encrypt;

public class ByteUtil {
	
	/*
	 * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
	 * 互为可逆的转换过程
	 */
    public static byte[] hexStr2ByteArr(String strIn) throws Exception {  
        byte[] arrB = strIn.getBytes();  
        int iLen = arrB.length;  
        byte[] arrOut = new byte[iLen / 2];  
        for (int i = 0; i < iLen; i = i + 2) {  
            String strTmp = new String(arrB, i, 2);  
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);  
        }  
        return arrOut;  
    } 
    
    /*
     * 将byte数组转换为表示16进制值的字符串
     * hexStr2ByteArr(String strIn)
     */

    public static String byteArr2HexStr(byte[] arrB) throws Exception {   
        int iLen = arrB.length;  
        StringBuffer sb = new StringBuffer(iLen * 2);  
        for (int i = 0; i < iLen; i++) {  
            int intTmp = arrB[i];  
            while (intTmp < 0) {  
                intTmp = intTmp + 256;  
            }  
            if (intTmp < 16) {  
                sb.append("0");  
            }  
            sb.append(Integer.toString(intTmp, 16));  
        }  
        String result = sb.toString();  
        return result;  
    } 
  
    
    public static void main(String[] args){
    	String str= "qwr123";
    	try {
			System.out.println(hexStr2ByteArr(str));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

}
