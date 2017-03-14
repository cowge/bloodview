# bloodview
血条进度动画
![](https://github.com/cowge/bloodview/blob/master/bloodview.png)<br/>
![](https://github.com/cowge/bloodview/blob/master/bloodview.mp4)<br/>

用法说明：<br/>
<com.cowge.anim.bloodview.BloodView
        android:id="@+id/bloodview_pking"
        android:layout_width="270dp"
        android:layout_height="27dp"
        app:rxy="23.5dp" />

 final BloodView mBloodView = (BloodView) findViewById(R.id.bloodview_pking);
 mBloodView.startAnim(0.97f, 3000);