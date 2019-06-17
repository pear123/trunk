package com.arvato.cc.util;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Justin
 */
public class HsqlUtil {

    public static String addNamedParam(String hsql, Map<String, Object> params) {
        return addNamedParam(hsql, params, "");
    }

    public static String addNamedParam(String hsql, Map<String, Object> params, String conditions) {
        String[] conditionStrs = conditions.split(",");
        Map<String, String> conditionMap = new HashMap<String, String>(conditionStrs.length);
        for (String conditionStr : conditionStrs) {
            String[] relation = conditionStr.trim().split(":");
            if (relation.length == 2) {
                conditionMap.put(relation[0].trim(), relation[1].trim());
            }
        }

        StringBuilder hsqlSb = new StringBuilder(hsql.trim());
        int index = 0;
        for (String key : params.keySet()) {
            if (conditionMap.containsKey(key)) {
                convertLikeValue(key, conditionMap, params);
            }
            hsqlSb.append(index == 0 && hsql.indexOf("where") < 0 ? " where " : " and ");
            hsqlSb.append(key);
            hsqlSb.append(conditionMap.containsKey(key) ? convertLike(conditionMap.get(key)) : "=");
            hsqlSb.append(":");
            hsqlSb.append(key);
            index++;
        }

        return hsqlSb.toString();
    }

    private static String convertLike(String relationStr) {
        if (relationStr.equals("like") || relationStr.equals("%like") || relationStr.equals("like%") || relationStr.equals("%like%")) {
            return " like ";
        }
        return relationStr;
    }

    private static void convertLikeValue(String key, Map<String, String> conditionMap, Map<String, Object> params) {
        String relationStr = conditionMap.get(key);
        StringBuilder sb = new StringBuilder();

        if (relationStr.equals("%like")) {
            sb.append("%");
            sb.append(params.get(key));
        } else if (relationStr.equals("like%")) {
            sb.append(params.get(key));
            sb.append("%");
        } else if (relationStr.equals("%like%")) {
            sb.append("%");
            sb.append(params.get(key));
            sb.append("%");
        } else {
            sb.append(params.get(key));
        }

        params.put(key, sb.toString());
    }

    /**
     * 转义一个字符串中所有的特殊字符
     * @param value: 需要转义的字符串
     * @return
     */
    public static String escape(String value) {
        String[] codes = new String[]{"%", "_", "^", "[", "]"};
        for (String code : codes) {
            if (value.indexOf(code) > -1) {
                value = escape(value, code);
            }
        }
        return value;
    }

    /**
     * 转义一个字符串中特定的特殊字符
     * @param value: 需要转义的字符串
     * @param code: 特殊字符
     * @return
     */
    public static String escape(String value, String code) {
        int index;
        if ((index = value.indexOf(code)) > -1) {
            value = value.substring(0, index) + "$" + code + escape(value.substring(index + 1), code);
        }
        return value;
    }
}
