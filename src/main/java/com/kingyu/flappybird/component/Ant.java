package com.kingyu.flappybird.component;

import com.kingyu.flappybird.util.Constant;
import com.kingyu.flappybird.util.GameUtil;
import com.sun.org.apache.xpath.internal.objects.XBoolean;

import java.awt.*;
import java.awt.image.BufferedImage;



public class Ant {

    private int pos;    //蚂蚁的横向坐标
    private BufferedImage AntImg; // 蚂蚁的图片

    // 蚂蚁的运动状态
    private int direction=RIGHT;
    public static final int LEFT = 0; //向左移动
    public static final int RIGHT = 1; //向右移动
    public static final int STOP = 2; // 停止移动

    // 蚂蚁序号
    private int order;

    //蚂蚁的速度
    private int speed=5;

    public Ant(int pos,int order)
    {

        this.pos=Constant.INIT_POSITION+pos;//初始化蚂蚁的位置
        this.order=order;//初始化蚂蚁的序号

        AntImg = GameUtil.loadBufferedImage(Constant.ANT_IMG_PATH[this.order]);//初始化蚂蚁图片


    }


    //绘制蚂蚁
    public void draw(Graphics g){
        climb();
        g.drawImage(AntImg,pos,Constant.INIT_HEIGHT,null);
    }

    //蚂蚁的爬行,改变位置，检测是否出界
    public void climb(){

        if(direction==LEFT){

            if (pos<=Constant.INIT_POSITION){
                pos=Constant.INIT_POSITION;
                direction=STOP;

            }
            else pos=pos-speed;
        }
        else if(direction==RIGHT){

            if (pos>=Constant.POLE_LENGTH+Constant.INIT_POSITION)
            {
                pos=Constant.POLE_LENGTH+Constant.INIT_POSITION;
                direction=STOP;
            }
            else  pos=pos+speed;
        }
        else if(direction==STOP)
        {
            ;
        }


    }

    public Boolean isStop()
    {
        if (direction==STOP)
            return true;
        else return false;
    }

    public void turnRound(){
        if(direction==LEFT){
            direction=RIGHT;
            System.out.println(order+"右转了");
        }
        else if(direction==RIGHT){
            direction=LEFT;
            System.out.println(order+"左转了");
        }
    }

    public int getPos() {
        return pos;
    }

    public int getOrder() {
        return order;
    }

    public void setDirection(int direction)
    {
        this.direction=direction;
    }

    public void setPos(int position)
    {
        this.pos=Constant.INIT_POSITION+position;
    }

}
