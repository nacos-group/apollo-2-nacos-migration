package com.kuae;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

// 该类为Apollo 迁移Nacks的脚本
public class Transfer {
    
    static final Properties propertiesForMse;
    static final String keyId = "alias/acs/mse";
    static final String regionId = "cn-hangzhou";
    
    
    // 阿里云账号的AK/SK
    static final String ak = "{ak}";
    static final String sk = "{sk}";
    static {
        propertiesForMse = new Properties();
        // 给访问MSE的授予基础信息
        propertiesForMse.put("keyId", keyId);
        propertiesForMse.put("regionId", regionId);
        // 给访问MSE的Properties授予AK/SK
        propertiesForMse.put("accessKey", ak);
        propertiesForMse.put("secretKey", sk);
    }
    
    public static void main(String[] args) throws IOException, NacosException {
        // 期望发布配置的目标MSE地址
        String mseServerAddr = "{mse-clusterId}-p.nacos-ans.mse.aliyuncs.com";
        // 传入NamespaceId，如果希望发布到public则将其namespaceId置为""
        String namespaceId = "test-to";
        // 导出配置之后的根目录
        String rootPath =  "/Users/sunli/lab/configs/ns1";
        
        propertiesForMse.put("serverAddr", mseServerAddr);
        propertiesForMse.put("namespace", namespaceId);
        String[] groups = getFileNames(rootPath);
        if (groups == null) {
            return;
        }
        for (String group : groups) {
            String groupPath = rootPath + "/" + group;
            String[] dataIds = getFileNames(groupPath);
            if (dataIds == null) {
                continue;
            }
            for (String dataId : dataIds) {
                String dataIdPath = groupPath + "/" + dataId;
                System.out.println("Reading " + dataIdPath);
                byte[] bytes = Files.readAllBytes(Paths.get(dataIdPath));
                String content = new String(bytes, StandardCharsets.UTF_8);
                publishMSE(dataId, group, content);
            }
        }
    }
    
    public static String[] getFileNames(String path) {
        File dir = new File(path);
        String[] children = dir.list();
        if (children == null) {
            return null;
        }
        else {
            for (int i=0; i< children.length; i++) {
                String filename = children[i];
                System.out.println(filename);
            }
        }
        return children;
    }
    
    public static boolean publishMSE(String dataId, String group, String content) throws NacosException {
        ConfigService configService = NacosFactory.createConfigService(propertiesForMse);
        boolean result = configService.publishConfig(dataId, group, content);
        System.out.println("dataId: " + dataId + " group: " + group + "transfered");
        return result;
    }
    
}