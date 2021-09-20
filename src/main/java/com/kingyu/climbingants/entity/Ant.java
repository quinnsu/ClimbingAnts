package com.kingyu.climbingants.entity;

import static com.kingyu.climbingants.util.Constant.*;
import com.kingyu.climbingants.util.GameUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Ant {
    private int pos;    //蚂蚁的横向坐标
    private final BufferedImage antImg; // 蚂蚁的图片

    // 蚂蚁的运动状态
    private int direction = RIGHT;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int STOP = 2;

    // 蚂蚁序号
    private int order;

    //蚂蚁的速度
    private int speed=5;

    public Ant(int pos,int order,int speed)
    {
        this.pos = INIT_POSITION + pos;
        this.order = order;
        this.speed = speed;
        this.antImg = GameUtil.loadBufferedImage(ANT_IMG_PATH[this.order]);
    }

    // 蚂蚁的爬行,改变位置，检测是否出界
    public void climb() {
        if (direction == LEFT) {
            pos = pos - speed;
            if (pos <= INIT_POSITION){
                pos = INIT_POSITION;
                direction = STOP;
            }
        }
        else if (direction == RIGHT) {
            pos = pos + speed;
            if (pos >= Pole.length + INIT_POSITION)
            {
                pos = Pole.length + INIT_POSITION;
                direction=STOP;
            }
        }
    }

    public Boolean isStop()
    {
        return direction == STOP;
    }

    public void turnRound(){
        if (direction == LEFT) {
            direction = RIGHT;
        }
        else if (direction == RIGHT) {
            direction = LEFT;
        }
    }

    //绘制蚂蚁
    public void draw(Graphics g){
        g.drawImage(antImg, pos, INIT_HEIGHT,null);
    }

    public int getPos() {
        return pos;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setPos(int position) {
        this.pos = INIT_POSITION + position;
    }

    public int getDirection() {
        return direction;
    }
}
