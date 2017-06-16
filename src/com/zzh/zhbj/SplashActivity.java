package com.zzh.zhbj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.zzh.zhbj.global.GlobalContacts;
import com.zzh.zhbj.utils.SharePreUtils;

public class SplashActivity extends Activity {
	
	private RelativeLayout rlRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        rlRoot = (RelativeLayout) findViewById(R.id.rl_bg_splash);
        
        //开始动画
        startAnimation();
    }
    /**
     * 开始动画方法
     */
    private void startAnimation(){
    	//旋转动画
    	RotateAnimation rotate  = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    	rotate.setDuration(1000);//设置动画时间
    	rotate.setFillAfter(true);//保持动画效果
    	
    	//缩放动画
    	ScaleAnimation scale = new ScaleAnimation(0, 1f, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    	scale.setDuration(1000);//设置动画时间
    	scale.setFillAfter(true);//保持动画效果
    	
    	//透明动画
    	AlphaAnimation alpha = new AlphaAnimation(0, 1);
    	alpha.setDuration(1000);//设置动画时间
    	alpha.setFillAfter(true);//保持动画效果
    	
    	//动画集合器
    	AnimationSet set = new AnimationSet(false);
    	set.addAnimation(rotate);
    	set.addAnimation(scale);
    	set.addAnimation(alpha);
    	
    	//开始动画
    	rlRoot.setAnimation(set);
    	
    	//监听动画结束后，跳转到引导界面
    	set.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				//动画开始
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				//动画重复
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				//读取保存的界面
				boolean fisrtGuide=SharePreUtils.getSPBoolean(SplashActivity.this, GlobalContacts.FIRST_GUIDE, false);
				if (!fisrtGuide) {
					//动画结束,如果是第一个次，跳转到引导界面
					Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
					startActivity(intent);
				}else{
					//如果不是，直接进入主界面
					startActivity(new Intent(SplashActivity.this,MainActivity.class));
				}
				finish();
				
			}
		});
    }

}
