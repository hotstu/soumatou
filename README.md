RecyclerBanner
===================
[![author](https://img.shields.io/badge/author-hglf-blue.svg)](https://github.com/hotstu)
[![Download](https://api.bintray.com/packages/hglf/maven/RecyclerBanner/images/download.svg) ](https://bintray.com/hglf/maven/RecyclerBanner/_latestVersion)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

(yet Another) Banner widget based on RecyclerView


轮播组件（基于RecyclerView）

已经有很多实现，为什么又造轮子？

* 其他开源实现大多存在以下现象：
   1. 为了迎合受众, 代码庞大臃肿
   2. 代码质量不高，存在明显bug
   3. 没有采用`ItemDecoration`,而是采用组合view的方式实现indicator,后者使用不方便，实现不优雅
   
* 实现自己的，便于维护

实现思路：

* 为什么不使用viewPager:
viewPager代码日益陈旧，可能出现各种兼容性问题，以及和其他组件协作的问题
而recyclerView属于jetpack包，代码随时更新，并且架构设计优秀，支持各种扩展，实现复杂的特效

* 实现想viewpager的翻页效果：
直接使用PagerSnapHelper

* 自动翻页的实现：
也是基于ItemDecoration实现的BannerLoop, 通过postDelay的方式实现

* 滑动过程中暂停自动翻页的实现:
在itemDecoration中添加addOnItemTouchListener监听Recycle滑动事件

* 模块拆分
  最小化拆分，组件之间不依赖
  1. banner
  2. adapter(可选, 如果你使用`MOTypedRecyclerAdapter`，你需要这个包，如果你使用其他的，你需要实现`IBannerAdapter`接口) 
  3. indicator(可选, 使用`ItemDecoration`实现的简单indicator， 其他样式开发中...)
  
  
* 使用

```groovy
implementation 'github.hotstu.recyclerbanner:banner:1.0.0'
implementation 'github.hotstu.recyclerbanner:adapter:1.0.0'//optional
implementation 'github.hotstu.recyclerbanner:indicator:1.0.0'//optional

```

```java
RecyclerView list = findViewById(R.id.list);
LinearLayoutManager layout = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
list.setLayoutManager(layout);
SomatoSnapHelper snapHelper = new SomatoSnapHelper();
snapHelper.attachToRecyclerView(list);
MyBannerAdapter adapter = new MyBannerAdapter(4);
adapter.addItems(new BanerList<>(Arrays.asList("1", "2", "3", "4")));
list.setAdapter(adapter);
list.scrollToPosition(0 + 4 * 1000);
list.addItemDecoration(new CirclePagerIndicator(this));
BannerLoop play = new BannerLoop(1000);
play.attachToRecyclerView(list);
play.startPlay();
        
```



 