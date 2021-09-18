package com.kingyu.flappybird.app;

import com.kingyu.flappybird.component.*;
import com.kingyu.flappybird.util.Constant;

import javax.swing.*;

import static com.kingyu.flappybird.util.Constant.FRAME_HEIGHT;
import static com.kingyu.flappybird.util.Constant.FRAME_WIDTH;
import static com.kingyu.flappybird.util.Constant.FRAME_X;
import static com.kingyu.flappybird.util.Constant.FRAME_Y;
import static com.kingyu.flappybird.util.Constant.FPS;
import static com.kingyu.flappybird.util.Constant.GAME_TITLE;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * 游戏主体，管理游戏的组件和窗口绘制
 */

public class Game extends Frame                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      {
    private static int gameState; // 游戏状态
    private static int gameRound; //游戏回合

    public static final int GAME_READY = 0; // 游戏未开始,选择可演示的情况
    public static final int GAME_START = 1; // 游戏开始，演示选中的情况
    public static final int STATE_OVER = 2; // 游戏结束，显示当前情况下的蚂蚁最短时间、最长时间

    public static final int ANT_AMOUNT = 5; // 蚂蚁总数量

    List<Ant> antList;//当前蚂蚁列表
    private int[] positions={30,80,110,160,250}; //蚂蚁初始位置数组
    private int speed = 5;
    private int poloLength = 300;


    private Pole pole;//木棍对象

    private int time; //计时器

    // 项目中存在两个线程：系统线程，自定义的线程：调用repaint()。
    // 系统线程：屏幕内容的绘制，窗口事件的监听与处理
    // 两个线程会抢夺系统资源，可能会出现一次刷新周期所绘制的内容，并没有在一次刷新周期内完成
    // （双缓冲）单独定义一张图片，将需要绘制的内容绘制到这张图片，再一次性地将图片绘制到窗口
    private final BufferedImage bufImg = new BufferedImage(FRAME_WIDTH, FRAME_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);

    private TimeDisplay timeDisplay; //游戏结束时显示耗时

    int shortest_time=99999,longgest_time=0;

    // 在构造器中初始化
    public Game(int poloLength,int[] positions,int speed) {
        this.poloLength = poloLength;
        this.positions=positions;
        this.speed=speed;
        initFrame(); // 初始化游戏窗口
        initGame(); // 初始化游戏对象

    }

    // 初始化游戏窗口
    private void initFrame() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT); // 设置窗口大小
        setTitle(GAME_TITLE); // 设置窗口标题
        setLocation(FRAME_X, FRAME_Y); // 窗口初始位置
        setResizable(false); // 设置窗口大小不可变

        // 添加关闭窗口事件（监听窗口发生的事件，派发给参数对象，参数对象调用对应的方法）
      addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0); // 结束程序
            }
        });


        setVisible(true); // 窗口默认为不可见，设置为可见

    }

    // 初始化游戏中的各个对象
    private void initGame() {
        pole = new Pole(poloLength);//初始化木棍
        antList= new ArrayList<>();


        for (int j = 0; j< ANT_AMOUNT;j++) //初始化蚂蚁List，传入Ant的初始位置和序号
        {
            Ant ant = new Ant(positions[j],j,speed);
            antList.add(ant);
        }
        gameRound=0; //初始化游戏回合
        setGameState(GAME_READY);

        // 启动用于刷新窗口的线程
        new Thread(() ->{
            while (true) {
                repaint(); // 通过调用repaint(),让JVM调用update()
                try {
                    Thread.sleep(FPS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 绘制游戏内容 当repaint()方法被调用时，JVM会调用update()，参数g是系统提供的画笔，由系统进行实例化
     * 单独启动一个线程，不断地快速调用repaint()，让系统对整个窗口进行重绘
     */
    @Override
    public void update(Graphics g) {
        Graphics bufG = bufImg.getGraphics(); // 获得图片画笔

        if (gameState == GAME_READY) { // 游戏未开始
            time = 0;
            // 选定当前演示的情况
            pole.draw(bufG); //绘制木棍
            g.drawImage(bufImg, 0, 0, null);

            //绘制提醒文字
            g.setColor(Color.white);
            g.setFont(Constant.SCORE_FONT);

            g.drawString("最短耗时为："+shortest_time,50,100);
            g.drawString( "最长耗时为： "+longgest_time,50,130);
            g.drawString( "本局当前耗时为： "+time,50,160);

            if (gameRound < 1<<ANT_AMOUNT) { //如果超出32局则游戏无法再次启动
                for (int j = 0; j < ANT_AMOUNT; j++) // 重置蚂蚁的初始position
                {
                    antList.get(j).setPos(positions[j]);
                }

                //给蚂蚁赋初始方向 根据当前的gameRound，计算出五位二进制数，分别给蚂蚁赋方向
                for (int i = antList.size()-1; i >= 0; i--) {
                    int direction = gameRound >>> i & 1;
                    antList.get(i).setDirection(direction);
                    //System.out.println( antList.get(i).getOrder()+"号蚂蚁的方向是"+direction+"\n");
                }
                setGameState(GAME_START); // 游戏状态改变
            }
        } else if(gameState == STATE_OVER) { // 游戏结束
            //timeDisplay.draw(g,gameRound,time);//绘制时间结果

            //更新最长时间和最短时间
            if (time<shortest_time) {
                shortest_time=time;
            }
            if (time>longgest_time) {
                longgest_time=time;
            }

            System.out.println("第"+gameRound+"局结束，用时："+time);
            gameRound++;
            setGameState(GAME_READY);
        }
        else { //游戏开始
            pole.draw(bufG); //绘制木棍
            for (int i = 0; i < antList.size(); i++) //绘制蚂蚁List
            {
                antList.get(i).climb();
                antList.get(i).draw(bufG);
            }
            g.drawImage(bufImg, 0, 0, null); // 一次性将图片绘制到屏幕上
            testCollision(); //检测碰撞

            time++;//计时器增加

            g.setColor(Color.white);
            g.setFont(Constant.SCORE_FONT);

            g.drawString("最短耗时为："+shortest_time,50,100);
            g.drawString( "最长耗时为： "+longgest_time,50,130);
            g.drawString( "本局当前耗时为： "+time,50,160);

            if (testAllOutOfBounds()==true) //全部蚂蚁出界则本回合游戏结束
            {
                setGameState(STATE_OVER);
            }
        }

    }

    //改变游戏状态
    public static void setGameState(int gameState) {
        Game.gameState = gameState;
    }

    //遍历antList，返回坐标相同的ant序号，若没有返回-1；
    public void testCollision()
    {
        //对每只蚂蚁，检测是否和列表里其他的蚂蚁碰撞
        for (int m = 0; m < antList.size(); m++)
        {
            if (antList.get(m).isStop()==false) {
                for (int n = m + 1; n < antList.size(); n++) {
                    if (antList.get(m).getPos() == antList.get(n).getPos()) {
                        if (antList.get(n).isStop() == false) {
                            //System.out.println("撞击时，两只蚂蚁的坐标分别是" + (antList.get(m).getPos() - Constant.INIT_POSITION) +
                             //       " " + (antList.get(n).getPos() - Constant.INIT_POSITION));
                            antList.get(m).turnRound();
                            antList.get(n).turnRound();
                            break;
                        }
                    }
                }
            }
        }
    }

    //检测蚂蚁是否全部出界
    public boolean testAllOutOfBounds()
    {
        for (int m = 0; m < antList.size(); m++)
        {
            if(antList.get(m).getPos() > Constant.INIT_POSITION && antList.get(m).getPos() < Constant.INIT_POSITION+poloLength)
            {

                return false;
            }
        }
        return true;
    }

}
