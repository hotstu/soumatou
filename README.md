soumatou
===================

## 作为负责任的开源库，已经重新上传至Maven central，再也不用担心用了野库Jcenter关门不能用了

[![author](https://img.shields.io/badge/author-hglf-blue.svg)](https://github.com/hotstu)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

(yet Another) Banner widget based on RecyclerView


轮播组件（基于RecyclerView）

* 其他开源实现大多存在以下现象：
   1. 为了迎合受众, 代码庞大臃肿
   2. 代码质量不高，存在明显bug
   3. 没有采用`ItemDecoration`,而是采用组合view的方式实现indicator,后者使用不方便，实现不优雅
   
* 实现自己的，便于维护

实现思路：

* 为什么不使用viewPager:
viewPager代码日益陈旧，可能出现各种兼容性问题，以及和其他组件协作的问题， 参考viewpager2
而recyclerView属于jetpack包，代码随时更新，并且架构设计优秀，支持各种扩展，实现复杂的特效

* 实现想viewpager的翻页效果：
直接使用PagerSnapHelper

* 自动翻页的实现：
也是基于ItemDecoration实现的BannerLoop, 通过postDelay的方式实现

* 滑动过程中暂停自动翻页的实现:
在itemDecoration中添加addOnItemTouchListener监听Recycle滑动事件

* 
  1. banner
  2. adapter 
  3. indicator(可选, 使用`ItemDecoration`实现的简单indicator， 支持圆形和方形， 其他样式开发中...)
  
  
* 使用




 