package com.example.garbagemonitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import co.ceryle.segmentedbutton.SegmentedButtonGroup;

public class MainActivity extends AppCompatActivity {

    private FragmentPager fragmentPager;
    private ViewPager pager;
    private SegmentedButtonGroup pagerMenu;

    private Button getButton;
    private EditText ideddit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager=findViewById(R.id.pager);
        pagerMenu=findViewById(R.id.pager_menu);
        ideddit=findViewById(R.id.idedit);
        getButton=findViewById(R.id.getbutton);



        fragmentPager=new FragmentPager(getSupportFragmentManager(), MainActivity.this);
        pager.setAdapter(fragmentPager);

        pagerMenu.setOnPositionChangedListener(new SegmentedButtonGroup.OnPositionChangedListener() {
            @Override
            public void onPositionChanged(int position) {
                pager.setCurrentItem(position);
            }
        });


        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                pagerMenu.setPosition(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ideddit.getText().toString().length()>0)
                fragmentPager.sendId(ideddit.getText().toString());
            }
        });
    }
}
