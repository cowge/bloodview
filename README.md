# bloodview
## 血条进度动画

![](https://github.com/cowge/bloodview/blob/master/bloodview.png)<br/>

## 实现机制
右边画1个圆角矩形的血条背景
```
左边圆角矩形血条用一个斜线的多边形做xfermode的用DST_OUT处理，根据比例做裁剪

## 用法

BloodView mBloodView = (BloodView) findViewById(R.id.bloodview_pking);
```
mBloodView.startAnim(0.97f, 3000);