import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import config.*;
import config.Package;

/**
 * @author codex
 * @since 2021-01-27
 */
public class CodeGenerator {
    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        mpg.setGlobalConfig(Global.getGlobalConfig());
        // 数据源
        mpg.setDataSource(DataSource.getDataSourceConfig());
        // 包名配置
        PackageConfig pc = Package.getPackageConfig();
        mpg.setPackageInfo(pc);
        // 配置策略
        mpg.setStrategy(Strategy.getStrategyConfig());
        // 自定义配置 将mapper的xml文件输出到 resource目录下
        mpg.setCfg(Injection.getInjectionConfig(pc));

        // TemplateConfig
        mpg.setTemplate(Template.getTemplateConfig());
        mpg.setTemplateEngine(new VelocityTemplateEngine());

        mpg.execute();
    }
}
