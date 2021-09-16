package com.kingyu.flappybird.component;

import com.kingyu.flappybird.util.Constant;

import java.awt.*;

/*
 显示当前耗时和最短时间
 */
public class TimeDisplay {

    int shortest_time=99999,longgest_time=0;

    public void draw(Graphics g,int cur_time)
    {
        //更新最长时间和最短时间
        if (cur_time<shortest_time)
            shortest_time=cur_time;
        if (cur_time>longgest_time)
            longgest_time=cur_time;

       //绘制当前时间、最长时间和最短时间
        g.setColor(Color.white);
        g.setFont(Constant.SCORE_FONT);
        g.drawString( "本局耗时为   ： "+cur_time,100,200);
        g.drawString("最短耗时为   ： "+shortest_time,100,150);
        g.drawString( "最长耗时为   ： "+longgest_time,100,100);






    }
}
