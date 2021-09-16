package com.kingyu.flappybird.component;

import com.kingyu.flappybird.util.Constant;
import com.kingyu.flappybird.util.GameUtil;
import com.sun.org.apache.bcel.internal.Const;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Pole {

    // 图片资源

    private  BufferedImage PoleImg;

    public void draw(Graphics g){

        //顺便在木棍后面绘制背景色
        g.setColor(Constant.BG_COLOR);
        g.fillRect(0, 0, Constant.FRAME_WIDTH, Constant.FRAME_HEIGHT);

        g.drawImage(PoleImg,Constant.INIT_POSITION,200, Constant.POLE_LENGTH,20,null);
    }

    //初始化木棍资源
    public Pole(){


        PoleImg = GameUtil.loadBufferedImage(Constant.POLE_IMG_PATH);
    }

}
