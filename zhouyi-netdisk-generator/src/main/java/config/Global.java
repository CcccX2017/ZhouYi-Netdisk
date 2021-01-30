package config;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;

/**
 * GlobalConfig
 *
 * @author codex
 * @since 2021-01-27
 */
public class Global {
    public static GlobalConfig getGlobalConfig() {
        String property = System.getProperty("user.dir");

        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(property + "/zhouyi-netdisk-generator/src/main/java");
        gc.setAuthor("codex");
        // 生成的文件是否覆盖原来的文件
        gc.setFileOverride(false);
        // 生成完成是否自动打开目录
        gc.setOpen(false);
        gc.setSwagger2(true);
        gc.setIdType(IdType.ASSIGN_ID);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        gc.setDateType(DateType.ONLY_DATE);


        return gc;
    }
}
