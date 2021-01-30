package config;

import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * @author codex
 * @since 2021-01-27
 */
public class Strategy {
    public static StrategyConfig getStrategyConfig(){
        StrategyConfig sc = new StrategyConfig();
        sc.setNaming(NamingStrategy.underline_to_camel);
        sc.setColumnNaming(NamingStrategy.underline_to_camel);
        sc.setEntityLombokModel(true);
        sc.setTablePrefix("tb_");
        sc.setFieldPrefix("is_");
        sc.setInclude("tb_files", "tb_folders", "tb_friends", "tb_friends_application", "tb_friends_session",
                "tb_friends_share", "tb_order", "tb_pay_info", "tb_payment_method", "tb_url_share", "tb_user",
                "tb_user_groups");
        sc.setLogicDeleteFieldName("is_deleted");
        sc.setRestControllerStyle(true);

        return sc;
    }
}
