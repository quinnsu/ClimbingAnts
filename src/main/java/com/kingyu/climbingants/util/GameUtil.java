package com.kingyu.climbingants.util;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 工具类，游戏中用到的工具都在此类
 */
public class GameUtil {

    private GameUtil() {
    } // 私有化，防止其他类实例化此类

    /**
     * 装载图片的方法
     *
     * @param imgPath 图片路径
     * @return 图片资源
     */
    public static BufferedImage loadBufferedImage(String imgPath) {
        try {
            return ImageIO.read(new FileInputStream(imgPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
