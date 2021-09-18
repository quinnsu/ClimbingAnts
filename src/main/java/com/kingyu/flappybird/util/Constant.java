package com.kingyu.flappybird.util;

import java.awt.Color;
import java.awt.Font;

/**
 * 常量类
 *
 */

public class Constant {
	// 窗口尺寸
	public static final int FRAME_WIDTH = 640;
	public static final int FRAME_HEIGHT = 420;

	// 木杆距离左侧窗口的位置
	public static final int INIT_POSITION=100;
	// 木杆的默认长度
	public static final int POLE_LENGTH=300;
	//木杆和蚂蚁距离下测窗口的高度
	public static  final  int INIT_HEIGHT=200;

	// 游戏标题
	public static final String GAME_TITLE = "ClimbingAnts";

	// 窗口初始化位置
	public static final int FRAME_X = 300;
	public static final int FRAME_Y = 100;

	// 木棍图片路径
	public static final String POLE_IMG_PATH = "resources/img/pole.png";

	//蚂蚁图片
	public static final String[] ANT_IMG_PATH = {"resources/img/Ant_1.png","resources/img/Ant_2.png","resources/img/Ant_3.png","resources/img/Ant_4.png","resources/img/Ant_5.png"};



	// 游戏背景色
	public static final Color BG_COLOR = new Color(0x4bc4cf);

	// 游戏刷新率
	//public static final int FPS = 1000 / 30;
	public static final int FPS = 50;


	public static final Font CURRENT_SCORE_FONT = new Font("华文琥珀", Font.BOLD, 32);// 字体
	public static final Font SCORE_FONT = new Font("华文琥珀", Font.BOLD, 24);// 字体

}
