package com.kingyu.flappybird.component;

import com.kingyu.flappybird.util.Constant;
import com.kingyu.flappybird.util.GameUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

//import com.sun.org.apache.bcel.internal.Const;

public class Pole {

    // 图片资源
    private  BufferedImage PoleImg;
    public  static int length = Constant.POLE_LENGTH;

    public void draw(Graphics g){

        //顺便在木棍后面绘制背景色
        g.setColor(Constant.BG_COLOR);
        g.fillRect(0, 0, Constant.FRAME_WIDTH, Constant.FRAME_HEIGHT);

        g.drawImage(PoleImg,Constant.INIT_POSITION,Constant.INIT_HEIGHT, length,20,null);
    }

    //初始化木棍资源
    public Pole(int length){
        PoleImg = GameUtil.loadBufferedImage(Constant.POLE_IMG_PATH);
        Pole.length=length;
    }

}
