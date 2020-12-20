package cn.codex.netdisk.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author CodeX
 * @since 2020-11-18 15:17:39
 */
@SpringBootApplication(scanBasePackages = "cn.codex.netdisk")
@MapperScan("cn.codex.netdisk.dao")
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  舟意网盘启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                ",---.   .--.    .-''-. ,---------.  ______     .-./`)    .-'''-. .--.   .--.   \n" +
                "|    \\  |  |  .'_ _   \\\\          \\|    _ `''. \\ .-.')  / _     \\|  | _/  /    \n" +
                "|  ,  \\ |  | / ( ` )   '`--.  ,---'| _ | ) _  \\/ `-' \\ (`' )/`--'| (`' ) /     \n" +
                "|  |\\_ \\|  |. (_ o _)  |   |   \\   |( ''_'  ) | `-'`\"`(_ o _).   |(_ ()_)      \n" +
                "|  _( )_\\  ||  (_,_)___|   :_ _:   | . (_) `. | .---.  (_,_). '. | (_,_)   __  \n" +
                "| (_ o _)  |'  \\   .---.   (_I_)   |(_    ._) ' |   | .---.  \\  :|  |\\ \\  |  | \n" +
                "|  (_,_)\\  | \\  `-'    /  (_(=)_)  |  (_.\\.' /  |   | \\    `-'  ||  | \\ `'   / \n" +
                "|  |    |  |  \\       /    (_I_)   |       .'   |   |  \\       / |  |  \\    /  \n" +
                "'--'    '--'   `'-..-'     '---'   '-----'`     '---'   `-...-'  `--'   `'-'   \n");
    }
}
