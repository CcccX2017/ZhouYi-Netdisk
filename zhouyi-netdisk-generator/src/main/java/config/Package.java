package config;

import com.baomidou.mybatisplus.generator.config.PackageConfig;

/**
 * @author codex
 * @since 2021-01-27
 */
public class Package {
    public static PackageConfig getPackageConfig(){
        PackageConfig pc = new PackageConfig();
        pc.setParent("cn.codex.netdisk");
        pc.setEntity("model.entity");
        pc.setMapper("dao");
        pc.setController("portal.controller");
        pc.setService("service");
        pc.setServiceImpl("service.impl");

        return pc;
    }
}
