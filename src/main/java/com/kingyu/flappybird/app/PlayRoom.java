package com.kingyu.flappybird.app;

import com.kingyu.flappybird.util.Constant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.kingyu.flappybird.util.Constant.*;

public class PlayRoom extends JFrame {

    public PlayRoom()
    {
        initFrame();
    }

    private void initFrame() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT); // 设置窗口大小
        setTitle(GAME_TITLE); // 设置窗口标题
        setLocation(FRAME_X, FRAME_Y); // 窗口初始位置
        setResizable(false); // 设置窗口大小不可变

        setLayout(new FlowLayout(FlowLayout.LEFT,150,10));//设置布局

        JLabel label1 = new JLabel("木杆长度");
        JTextField poleLengthText = new JTextField("300",10);
        JLabel label2 = new JLabel("初始位置");
        JTextField initialPosText = new JTextField("30 80 110 160 250",15);
        JLabel label3 = new JLabel("蚂蚁速度");
        JTextField speedText = new JTextField("5",10);
        JButton button = new JButton("开始游戏");

        add(label1);
        add(poleLengthText);
        add(label2);
        add(initialPosText);
        add(label3);
        add(speedText);
        add(button);

        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //处理输入数据
                int poloLength = Integer.parseInt(poleLengthText.getText());
                int[] positions = stringToIntArr(initialPosText.getText()); //转换为整型数组
                int speed = Integer.parseInt(speedText.getText());

                    if (positionsNotLegal(positions, poloLength)) //检测输入初始位置是否是小于木棒长度的正数
                    {
                        JOptionPane.showMessageDialog(null,
                                "您输入的初始位置和木棍长度不合法，请确保初始位置是小于木棒长度的正数",
                                "警告",
                                JOptionPane.WARNING_MESSAGE);

                    }
                    else if(speedNotLegal(positions,speed)) //检测输入初始位置是否是速度的整数倍
                    {
                        JOptionPane.showMessageDialog(null,
                                "您输入的初始位置和速度不合法，请确保初始位置是速度的整数倍",
                                "警告",
                                JOptionPane.WARNING_MESSAGE);
                    }
                    else {
                        setVisible(false);
                        Game game = new Game(poloLength, positions, speed);//开始游戏
                        game.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                super.windowClosed(e);
                                setVisible(true);
                            }
                        });
                    }
                }

        });

        // 添加关闭窗口事件（监听窗口发生的事件，派发给参数对象，参数对象调用对应的方法）
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0); // 结束程序
            }
        });
        setVisible(true); // 窗口默认为不可见，设置为可见

    }


    public static int[] stringToIntArr(String str) {
        int[] intArr = new int[Game.ANT_AMOUNT];
        String [] arr = str.split("\\s+");
        for (int i = 0; i < arr.length; i++) {
            intArr[i] =Integer.parseInt(arr[i]);
        }

        return intArr;
    }

    private boolean positionsNotLegal(int[] positions,int poloLength)
    {
        if(poloLength> Constant.FRAME_WIDTH- INIT_POSITION)
            return true;
        for (int i = 0; i <positions.length;i++)
            if (positions[i]<0 || positions[i]>poloLength)
                return true;

        return false;
    }

    private boolean speedNotLegal(int[] positions,int speed)
    {
        for (int i = 0; i <positions.length;i++)
            if (positions[i] % speed != 0)
                return true;

        return false;
    }

}

