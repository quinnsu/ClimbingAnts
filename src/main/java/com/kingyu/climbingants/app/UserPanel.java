package com.kingyu.climbingants.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.kingyu.climbingants.util.Constant.*;

/**
 * 游戏控制台，负责输入数据
 */
public class UserPanel extends JFrame {

    private static int poleLength;
    private static int antNum;
    private static int speed;
    private static int[] positions;

    public UserPanel()
    {
        initFrame();
    }

    private void initFrame() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle("showing");
        setLocation(FRAME_X, FRAME_Y);
        setResizable(false);

        setLayout(new FlowLayout(FlowLayout.CENTER,150,20));

        // 界面组件
        JLabel label1 = new JLabel("木杆长度");
        label1.setFont(SCORE_FONT);
        JTextField poleLengthText = new JTextField("300",15);

        JLabel label2 = new JLabel("蚂蚁数量");
        JTextField antNumText = new JTextField("5",15);
        label2.setFont(SCORE_FONT);

        JLabel label3 = new JLabel("蚂蚁速度");
        JTextField speedText = new JTextField("5",15);
        label3.setFont(SCORE_FONT);

        JLabel label4 = new JLabel("初始位置");
        JTextField initialPosText = new JTextField("30 80 110 160 250",15);
        label4.setFont(SCORE_FONT);

        JButton button = new JButton("开始游戏");
        button.setFont(SCORE_FONT);

        add(label1);
        add(poleLengthText);
        add(label2);
        add(antNumText);
        add(label3);
        add(speedText);
        add(label4);
        add(initialPosText);
        add(button);

        // 添加关闭窗口事件（监听窗口发生的事件，派发给参数对象，参数对象调用对应的方法）
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0); // 结束程序
            }
        });
        setVisible(true); // 窗口默认为不可见，设置为可见

        // 按键逻辑
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 读数据
                try {
                    poleLength = Integer.parseInt(poleLengthText.getText());
                    antNum = Integer.parseInt(antNumText.getText());
                    speed = Integer.parseInt(speedText.getText());
                    positions = stringToIntArr(initialPosText.getText());
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null,
                            "请输入合法整数作为输入！",
                            "警告",
                            JOptionPane.WARNING_MESSAGE);
                }

                // 验证是否合法
                if (!checkPoleLength(poleLength)) {
                    JOptionPane.showMessageDialog(null,
                            "您输入的木杆长度不合法，超出可表示界限",
                            "警告",
                            JOptionPane.WARNING_MESSAGE);
                }
                else if (!checkAntNum(antNum)) {
                    JOptionPane.showMessageDialog(null,
                            "您输入的蚂蚁数量不合法",
                            "警告",
                            JOptionPane.WARNING_MESSAGE);
                }
                else if (!checkSpeed(speed)) {
                    JOptionPane.showMessageDialog(null,
                            "您输入的速度不合法，请确保速度小于木杆长度且能整除木杆长度",
                            "警告",
                            JOptionPane.WARNING_MESSAGE);
                }
                else if (!checkPositions(positions)) //检测输入初始位置是否是小于木棒长度的正数
                {
                    JOptionPane.showMessageDialog(null,
                            "您输入的初始位置不合法，请确保初始位置能被蚂蚁速度整除且与蚂蚁数量匹配",
                            "警告",
                            JOptionPane.WARNING_MESSAGE);
                }
                else {
                    setVisible(false);
                    CreepingGame game = new CreepingGame(poleLength, antNum, speed, positions);//开始游戏
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
    }

    private static int[] stringToIntArr(String str) {
        String[] arr = str.split("\\s+");
        int[] intArr = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            intArr[i] =Integer.parseInt(arr[i]);
        }

        return intArr;
    }

    private boolean checkPoleLength(int poleLength) {
        // 不能超过窗口显示范围
        return poleLength < FRAME_WIDTH - INIT_POSITION && poleLength > 0;
    }

    private boolean checkAntNum(int antNum) {
        return antNum > 0 && antNum <= 5;
    }

    private boolean checkSpeed(int speed) {
       return speed > 0 && speed <= poleLength && poleLength % speed == 0;
    }

    private boolean checkPositions(int[] positions) {
        if (positions == null || positions.length == 0 || positions.length != antNum) {
            return false;
        }

        for (int i = 0; i < positions.length; i++) {
            if (positions[i] < 0 || positions[i] > poleLength || positions[i] % speed != 0) {
                return false;
            }
        }

        return true;
    }
}

