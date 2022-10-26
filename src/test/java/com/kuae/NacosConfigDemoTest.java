package com.kuae;

import com.alibaba.nacos.common.utils.StringUtils;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class NacosConfigDemoTest extends TestCase {
    
    public String getServerId(String serverIdWithNs) {
        return serverIdWithNs.split("_")[0];
    }
    
    private String getNamespace(String serverIdWithNs) {
        if (!serverIdWithNs.contains("_")) {
            return StringUtils.EMPTY;
        } else {
            return serverIdWithNs.split("_")[1];
        }
    }
    
    @Test
    public void testGetServerId() {
        String serverId = getServerId("cn-hangzhou_ns");
        Assert.assertEquals("cn-hangzhou", serverId);
    }
    
    @Test
    public void testGetNamespace() {
        String namespace = getNamespace("cn-hangzhou_ns");
        Assert.assertEquals("ns", namespace);
    }
}