package com.example.ttt.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.example.ttt.R;
import com.example.ttt.adapter.VPAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 2015/10/7.
 */
public class GuideActivity  extends Activity implements ViewPager.OnPageChangeListener{

    private ViewPager viewPager;
    private VPAdapter vpAdapter;
    private List<View> viewList;
    private ImageView[] dots;
    private int[] ids={R.id.iv1,R.id.iv2,R.id.iv3};
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.guide);
        initView();
        initDots();
    }
    private void initView(){
        LayoutInflater layoutInflater=LayoutInflater.from(this);

        viewList=new ArrayList<View>();
        viewList.add(layoutInflater.inflate(R.layout.one,null));
        viewList.add(layoutInflater.inflate(R.layout.two,null));
        viewList.add(layoutInflater.inflate(R.layout.three,null));
        login= (Button) viewList.get(2).findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GuideActivity.this,ChooseActivity.class);
                startActivity(intent);
                finish();
            }
        });

        vpAdapter=new VPAdapter(viewList,this);
        viewPager= (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(vpAdapter);
        viewPager.setOnPageChangeListener(this);

    }
    private void initDots(){
        dots=new ImageView[viewList.size()];
        for (int i = 0; i < viewList.size(); i++) {
            dots[i]=(ImageView)findViewById(ids[i]);

        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {//当页面被滑动时调用

    }

    @Override
    public void onPageSelected(int i) {//当前新的页面被选中时调用
        for (int j = 0; j < ids.length; j++) {
            if(i==j){
                dots[j].setImageResource(R.drawable.login_point_selected);
            }else{
                dots[j].setImageResource(R.drawable.login_point);
            }

        }

    }

    @Override
    public void onPageScrollStateChanged(int i) {//滑动状态改变时调用

    }
}
