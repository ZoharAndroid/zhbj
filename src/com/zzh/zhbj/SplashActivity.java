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
        
        //��ʼ����
        startAnimation();
    }
    /**
     * ��ʼ��������
     */
    private void startAnimation(){
    	//��ת����
    	RotateAnimation rotate  = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    	rotate.setDuration(1000);//���ö���ʱ��
    	rotate.setFillAfter(true);//���ֶ���Ч��
    	
    	//���Ŷ���
    	ScaleAnimation scale = new ScaleAnimation(0, 1f, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    	scale.setDuration(1000);//���ö���ʱ��
    	scale.setFillAfter(true);//���ֶ���Ч��
    	
    	//͸������
    	AlphaAnimation alpha = new AlphaAnimation(0, 1);
    	alpha.setDuration(1000);//���ö���ʱ��
    	alpha.setFillAfter(true);//���ֶ���Ч��
    	
    	//����������
    	AnimationSet set = new AnimationSet(false);
    	set.addAnimation(rotate);
    	set.addAnimation(scale);
    	set.addAnimation(alpha);
    	
    	//��ʼ����
    	rlRoot.setAnimation(set);
    	
    	//����������������ת����������
    	set.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				//������ʼ
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				//�����ظ�
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				//��ȡ����Ľ���
				boolean fisrtGuide=SharePreUtils.getSPBoolean(SplashActivity.this, GlobalContacts.FIRST_GUIDE, false);
				if (!fisrtGuide) {
					//��������,����ǵ�һ���Σ���ת����������
					Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
					startActivity(intent);
				}else{
					//������ǣ�ֱ�ӽ���������
					startActivity(new Intent(SplashActivity.this,MainActivity.class));
				}
				finish();
				
			}
		});
    }

}
