# ClimbingAnts
![](https://img.shields.io/badge/jdk-1.8.0-FFB6C1.svg?&logo=github)

## Overview

有一根300厘米的细木杆，在第30厘米、80厘米、110厘米、160厘米、250厘米这五个位置上各有一只蚂蚁。

木杆很细，不能同时通过两只蚂蚁。开始时，蚂蚁的头朝左还是朝右是任意的，它们只会朝前走或调头，但不会后退。当任意两只蚂蚁碰头时，两只蚂蚁会同时调头朝相反方向走。

假设蚂蚁们每秒钟可以走5厘米的距离。请编写一个程序，计算各种可能情形下所有蚂蚁都离开木杆的最小时间和最大时间。

## How to play
* 运行源码中的 ```App:main``` 方法


## Notes

* 文本编码格式为UTF-8，若注释出现乱码请修改编译器的文本编码格式


* sun包在不同操作系统和不同版本的JDK中可能发生变化，无法确保工作在所有JAVA平台上，请使用jdk1.8运行本代码。

## Package Contents

* app：控制包，负责游戏的初始化与启动。


* entity：存访实体类Ant和Pole。


* util：常量与工具类。

