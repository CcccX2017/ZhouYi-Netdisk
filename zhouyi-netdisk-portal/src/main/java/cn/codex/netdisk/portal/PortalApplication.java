package cn.codex.netdisk.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 舟意网盘门户网站启动类
 *
 * @author CodeX
 * @since 2020-11-18 15:17:39
 */
@SpringBootApplication(scanBasePackages = "cn.codex.netdisk")
public class PortalApplication {
    public static void main(String[] args) {
        SpringApplication.run(PortalApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  舟意网盘门户网站启动成功   ლ(´ڡ`ლ)ﾞ \n" +
                ".-------.     ,-----.    .-------. ,---------.    ____      .---.      \n" +
                "\\  _(`)_ \\  .'  .-,  '.  |  _ _   \\\\          \\ .'  __ `.   | ,_|      \n" +
                "| (_ o._)| / ,-.|  \\ _ \\ | ( ' )  | `--.  ,---'/   '  \\  \\,-./  )      \n" +
                "|  (_,_) /;  \\  '_ /  | :|(_ o _) /    |   \\   |___|  /  |\\  '_ '`)    \n" +
                "|   '-.-' |  _`,/ \\ _/  || (_,_).' __  :_ _:      _.-`   | > (_)  )    \n" +
                "|   |     : (  '\\_/ \\   ;|  |\\ \\  |  | (_I_)   .'   _    |(  .  .-'    \n" +
                "|   |      \\ `\"/  \\  ) / |  | \\ `'   /(_(=)_)  |  _( )_  | `-'`-'|___  \n" +
                "/   )       '. \\_/``\".'  |  |  \\    /  (_I_)   \\ (_ o _) /  |        \\ \n" +
                "`---'         '-----'    ''-'   `'-'   '---'    '.(_,_).'   `--------` \n");
    }
}
