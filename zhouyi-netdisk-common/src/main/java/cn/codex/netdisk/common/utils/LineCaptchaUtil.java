package cn.codex.netdisk.common.utils;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.img.GraphicsUtil;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 验证码
 *
 * @author CodeX
 * @since 2021-03-18 14:18:41
 */
public class LineCaptchaUtil extends AbstractCaptcha {

    private static final long serialVersionUID = -7055497156436127L;

    private static final String BASE_STR = RandomUtil.BASE_CHAR_NUMBER + RandomUtil.BASE_CHAR.toUpperCase();

    /**
     * 构造，使用随机验证码生成器生成验证码
     *
     * @param width          图片宽
     * @param height         图片高
     * @param codeCount      字符个数
     * @param interfereCount 验证码干扰元素个数
     */
    public LineCaptchaUtil(int width, int height, int codeCount, int interfereCount) {
        super(width, height, new RandomGenerator(BASE_STR, codeCount), interfereCount);
    }

    @Override
    protected Image createImage(String code) {
        // 图像buffer
        final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics2D g = GraphicsUtil.createGraphics(image, ObjectUtil.defaultIfNull(this.background, Color.WHITE));

        // 干扰线
        drawInterfere(g);

        // 字符串
        drawString(g, code);

        return image;
    }

    /**
     * 绘制字符串
     *
     * @param g    {@link Graphics}画笔
     * @param code 验证码
     */
    private void drawString(Graphics2D g, String code) {
        // 指定透明度
        if (null != this.textAlpha) {
            g.setComposite(this.textAlpha);
        }
        GraphicsUtil.drawStringColourful(g, code, this.font, this.width, this.height);
    }

    /**
     * 绘制干扰线
     *
     * @param g {@link Graphics2D}画笔
     */
    private void drawInterfere(Graphics2D g) {
        final ThreadLocalRandom random = RandomUtil.getRandom();
        // 干扰线
        for (int i = 0; i < this.interfereCount; i++) {
            int xs = random.nextInt(width);
            int ys = random.nextInt(height);
            int xe = xs + random.nextInt(width / 8);
            int ye = ys + random.nextInt(height / 8);
            g.setColor(ImgUtil.randomColor(random));
            g.drawLine(xs, ys, xe, ye);
        }
    }
}
