package com.kingyu.climbingants.entity;

import com.kingyu.climbingants.util.Constant;
import com.kingyu.climbingants.util.GameUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Pole {

    // 图片资源
    private BufferedImage poleImg;
    public static int length = 300;

    public Pole(int length){
        this.poleImg = GameUtil.loadBufferedImage(Constant.POLE_IMG_PATH);
        Pole.length=length;
    }

    public void draw(Graphics g){
        //在木棍后面绘制背景色
        g.setColor(Constant.BG_COLOR);
        g.fillRect(0, 0, Constant.FRAME_WIDTH, Constant.FRAME_HEIGHT);

        g.drawImage(poleImg,Constant.INIT_POSITION,Constant.INIT_HEIGHT, length,20,null);
    }
}
