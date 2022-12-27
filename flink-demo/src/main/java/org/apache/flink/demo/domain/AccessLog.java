package org.apache.flink.demo.domain;

import org.apache.flink.demo.util.IpUtils;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Random;

/**
 * @author jie zhao
 * @date 2022/12/10 12:19
 */
//@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccessLog {
    
    private long apiId;
    private String sip;
    private String dip;

    private static Random random = new Random();

    public AccessLog(long apiId, String sip, String dip) {
        this.apiId = apiId;
        this.sip = sip;
        this.dip = dip;
    }

    public static AccessLog newInstance() {
        return new AccessLog(random.nextLong(), IpUtils.getRandomIp(), IpUtils.getRandomIp());
    }
}
