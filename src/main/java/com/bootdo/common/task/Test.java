package com.bootdo.common.task;

import java.io.*;
import java.util.*;

/**
 * @author rory.chen
 * @date 2021/5/24
 */
public class Test {

    public static void main(String[] args) throws Exception {
        Map<String, String> map = getHttpNameMap();
        for (String name : map.keySet()) {
            String upperName = toUpperCase4Index(name);
            System.out.println("    @ApiOperation(value = \"" + map.get(name) + "\")");
            System.out.println("    @RequestMapping(path = \"/userinfocenter/v1/" + name + "\", method = RequestMethod.POST)");
            System.out.println("    public " + upperName + "Response " + name + "(@RequestBody "+upperName+"Request request) {");
            System.out.println("        return null;");
            System.out.println("    }");
            System.out.println("");
            //生成request和response文件
            createFile("/Users/mac/Desktop/gen/request/" + upperName + "Request.java", "request", name);
            createFile("/Users/mac/Desktop/gen/response/" + upperName + "Response.java", "response", name);
        }
    }

    private static void createFile(String fileUrl, String type, String name) throws Exception {
        String upperName = toUpperCase4Index(name);
        String upperType = toUpperCase4Index(type);
        File file = new File(fileUrl);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        FileWriter writer = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        bufferedWriter.write("package com.jinfuzi.userinfocenter.pojo."+type+";\r\n");
        bufferedWriter.write("\r\n");
        bufferedWriter.write("import lombok.Data;\r\n");
        bufferedWriter.write("\r\n");
        bufferedWriter.write("/**\r\n");
        bufferedWriter.write(" * @author rory.chen\r\n");
        bufferedWriter.write(" * @date 2021/5/24\r\n");
        bufferedWriter.write(" */\r\n");
        bufferedWriter.write("@Data\r\n");
        bufferedWriter.write("public class " + upperName + upperType + " {\r\n");
        bufferedWriter.write("}\r\n");
        bufferedWriter.close();
        writer.close();
    }

    private static Map<String, String> getHttpNameMap(){
        Map<String, String> map = new LinkedHashMap<>();
        map.put("checkRealBankWithoutUid", "验证用户银行卡信息，返回true成功(专供erp)");
        map.put("checkRealBank",  "添加用户银行卡信息，返回true成功");
        map.put("addUserInfo",  "添加用户所有信息，返回true成功");
        map.put("addPlatformUserBankCardInfo",  "添加用户指定平台的银行卡信息，返回true成功");
        map.put("delPlatformUserBankCardInfo",  "删除用户指定平台的银行卡信息，返回true成功");
        map.put("addPlatformUserAliPayInfo",  "添加用户指定平台的支付宝信息，返回true成功");
        map.put("delPlatformUserAliPayInfo",  "删除用户指定平台的支付宝信息，返回true成功");
        map.put("isIdentityCardNumberExist",  "身份证号码是否被绑定过，true被绑定了，false没有被绑定");
        map.put("addNotCheckUserInfo",  "添加未检验的用户所有信息，返回true成功，notcheckFrom为信息来源如\"gunxueqiu\"");
        map.put("updateBankCardAuthentication",  "更新用户的银行卡被认证过");
        map.put("updateBankCardAuthenticationByPlatform",  "按平台更新用户的银行卡被认证过");
        map.put("delUserAllData",  "在UIC清除此用户信息，请慎重使用");
        map.put("updateUserVIP",  "将普通用户更新为VIP用户");
        map.put("getUserAllData",  "获取用户的所有信息，包含已删除的银行卡信息");
        map.put("getUserAccountModifyInfo",  "获取用户的账户信息更改记录");
        map.put("addUserPFRiskEvaluationInfo", "新增用户私募风险评测");
        map.put("getUserPFRiskEvaluationInfo", "获取用户私募风险评测(erp)");
        map.put("getUserPFRiskEvaluationInfoByPhone", "获取用户私募风险评测(erp)");
        map.put("addIdentityInfoInHand", "手工添加信息，只对证件长度校验");
        map.put("unbindIdCard", "用户身份证信息解绑操作");
        map.put("unbindIdentityAndOcrInfo", "应产品经理要求，不解绑风测信息");
        map.put("listRiskEvaluationPapers", "获取风险测评试题内容");
        map.put("submitRiskEvaluationPapers", "提交测评题目答案，获取对应分数及风险等级");
        map.put("getRiskEvaScore2TypeInfo", "获取对应分数及风险等级对应数据");
        map.put("getRiskEvaPapersResultByUid", "根据uid获取对应分数及风险等级");
        map.put("userIdentityOcrRequest", "ocr请求数据");
        map.put("userBandCardOcrRequest", "ocr请求数据");
        map.put("userBandCardOcrRequestWithOutUid", "没有UID的银行卡ocr请求数据");
        map.put("updateUserIdentityOcrInfo", "更新OCR信息（因为前端允许更改OCR识别后的数据），所以，需要同步UIC的OCR数据");
        map.put("userHasIdentity", "用户是否已实名");
        map.put("getUserAddressJobEmailInfo", "用户的额外信息（居住地址、email地址，职业编码信息、年收入信息）信息");
        map.put("updateUserAddressJobEmailInfo", "更新用户的额外信息（居住地址、email地址，职业编码信息、年收入信息）信息");
        return map;
    }

    /**
     * 首字母大写
     *
     * @param string
     * @return
     */
    public static String toUpperCase4Index(String string) {
        char[] methodName = string.toCharArray();
        methodName[0] = toUpperCase(methodName[0]);
        return String.valueOf(methodName);
    }

    /**
     * 字符转成大写
     *
     * @param chars
     * @return
     */
    public static char toUpperCase(char chars) {
        if (97 <= chars && chars <= 122) {
            chars ^= 32;
        }
        return chars;
    }
}
