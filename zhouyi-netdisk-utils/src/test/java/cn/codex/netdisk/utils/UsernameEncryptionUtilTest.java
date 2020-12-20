package cn.codex.netdisk.utils;

import org.junit.Test;

/**
 * @author CodeX
 * @since 2020-11-03 16:27:05
 */
public class UsernameEncryptionUtilTest{

    @Test
    public void encryptionUsername(){
        String s = UsernameEncryptionUtil.encryptionUsername("聂检华njh聂检华");
        System.out.println(s);
    }

}