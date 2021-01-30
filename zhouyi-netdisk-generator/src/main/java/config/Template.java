package config;

import com.baomidou.mybatisplus.generator.config.TemplateConfig;

/**
 * @author codex
 * @since 2021-01-27
 */
public class Template {
    public static TemplateConfig getTemplateConfig(){
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);

        return templateConfig;
    }
}
