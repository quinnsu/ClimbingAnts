package com.kingyu.climbingants.app;

import com.kingyu.climbingants.entity.*;
import static com.kingyu.climbingants.util.Constant.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * 游戏主体
 */

public class CreepingGame extends Frame                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      {
    private static int gameState; // 游戏状态
    private static int gameRound; // 游戏回合

    public static final int GAME_READY = 0; // 游戏未开始,选择可演示的情况
    public static final int GAME_START = 1; // 游戏开始，演示选中的情况
    public static final int GAME_OVER = 2; // 游戏结束，显示当前情况下的蚂蚁最短时间、最长时间

    private List<Ant> antList; // 当前蚂蚁列表

    private int poleLength;
    private int antNum;
    private int speed;
    private int[] positions; //蚂蚁初始位置数组

    private Pole pole;//木棍对象
    private int time; //计时器

    private int shortest_time=99999999;
    private int longgest_time=-99999999;

    // 项目中存在两个线程：系统线程，自定义的线程：调用repaint()。
    // 系统线程：屏幕内容的绘制，窗口事件的监听与处理
    // 两个线程会抢夺系统资源，可能会出现一次刷新周期所绘制的内容，并没有在一次刷新周期内完成
    // （双缓冲）单独定义一张图片，将需要绘制的内容绘制到这张图片，再一次性地将图片绘制到窗口
    private final BufferedImage bufImg = new BufferedImage(FRAME_WIDTH, FRAME_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);

    // 在构造器中初始化
    public CreepingGame(int poleLength, int antNum, int speed, int[] positions) {
        this.poleLength = poleLength;
        this.antNum = antNum;
        this.speed = speed;
        this.positions = positions;

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
                dispose();
            }
        });

        setVisible(true); // 窗口默认为不可见，设置为可见
    }

    // 初始化游戏中的各个对象
    private void initGame() {
        time = 0;
        gameRound = 0; //初始化游戏回合
        pole = new Pole(poleLength);//初始化木棍
        antList = new ArrayList<>();

        for (int j = 0; j < antNum; j++) //初始化蚂蚁List，传入Ant的初始位置和序号
        {
            Ant ant = new Ant(positions[j], j, speed);
            antList.add(ant);
        }

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

        g.setColor(Color.white);
        g.setFont(SCORE_FONT);

        pole.draw(bufG); //绘制木棍

        if (gameState == GAME_READY) {
            time = 0;

            if (gameRound < 1<<antNum) { //如果超出32局则游戏无法再次启动
                // 重置蚂蚁的初始position
                for (int j = 0; j < antNum; j++) {
                    antList.get(j).setPos(positions[j]);
                }

                //给蚂蚁赋初始方向 根据当前的gameRound，计算出五位二进制数用于给蚂蚁赋方向
                for (int i = antList.size()-1; i >= 0; i--) {
                    int direction = gameRound >>> i & 1;
                    antList.get(i).setDirection(direction);
                }
                setGameState(GAME_START);
            }
        } else if(gameState == GAME_OVER) {
            //更新最长时间和最短时间
            if (time<shortest_time) {
                shortest_time=time;
            }
            if (time>longgest_time) {
                longgest_time=time;
            }

            System.out.println("第" + gameRound + "局结束，用时：" + time);

            gameRound++;
            setGameState(GAME_READY);
        } else if(gameState == GAME_START){
            for (Ant ant : antList) {
                ant.climb();
                ant.draw(bufG);
            }

            testCollision();
            time++;

            //全部蚂蚁出界则本回合游戏结束
            if (testAllOutOfBounds()) {
                setGameState(GAME_OVER);
            }
        }
        g.drawImage(bufImg, 0, 0, null);

        g.drawString("最短耗时为：" + shortest_time,50,100);
        g.drawString( "最长耗时为： " + longgest_time,50,130);
        g.drawString( "本局当前耗时为： " + time,50,160);

        if (gameRound >= 1<<antNum) {
            g.drawString("关闭当前窗口开启新游戏",50,250);
        }
    }

    //遍历antList，返回坐标相同的ant序号，若没有返回-1；
    private void testCollision()
    {
        //对每只蚂蚁，检测是否和列表里其他的蚂蚁碰撞
        for (int m = 0; m < antList.size(); m++)
        {
            if (!antList.get(m).isStop()) {
                for (int n = m + 1; n < antList.size(); n++) {
                    if (twoAntsCollison(m, n)) {
                        antList.get(m).turnRound();
                        antList.get(n).turnRound();
                        break;
                    }
                }
            }
        }
    }

    //检测两只蚂蚁距离是否小于等于一个speed单位，即此刻刚好撞上或在下一时刻刷新前即将撞上
    private boolean twoAntsCollison(int m, int n)
    {
        //若小的向右 大的向左
        int left, right, distance;
        int pos_m = antList.get(m).getPos();
        int pos_n = antList.get(n).getPos();
        if (pos_m <= pos_n) {
            left = m;
            right = n;
            distance = pos_n - pos_m;
        }
        else {
            left = n;
            right = m;
            distance = pos_m - pos_n;
        }
        if (distance == 0 && antList.get(left).getDirection() + antList.get(right).getDirection() == 1) {
            return true;
        } else if (distance <= speed && (antList.get(left).getDirection()==1 && antList.get(right).getDirection() == 0))
        {
            return true;
        }

        return false;
    }

    //检测蚂蚁是否全部出界
    private boolean testAllOutOfBounds()
    {
        for (int m = 0; m < antList.size(); m++)
        {
            if(antList.get(m).getPos() > INIT_POSITION && antList.get(m).getPos() < INIT_POSITION + poleLength)
            {
                return false;
            }
        }
        return true;
    }

    //改变游戏状态
    private static void setGameState(int gameState) {
        CreepingGame.gameState = gameState;
    }
}
