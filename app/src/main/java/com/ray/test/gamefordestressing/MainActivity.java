package com.ray.test.gamefordestressing;

import android.app.Activity;
import android.app.Service;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

public class MainActivity  extends Activity {
    private TableLayout tablay;
    private RelativeLayout last;
    private int row=5, column=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tablay = (TableLayout) findViewById(R.id.lay_tab);
        for(int i=0;i<row;i++){
            newObj(R.mipmap.ic_launcher, row, column);
        }

        tablay.setStretchAllColumns(true);
    }

    private void newObj(int iImg, int row, int column){
        RelativeLayout[] lay = new RelativeLayout[column];
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        for(int i=0; i<lay.length;i++){
            RelativeLayout rlayObj = new RelativeLayout(MainActivity.this);
            LinearLayout layObj = new LinearLayout(MainActivity.this);

            TableRow.LayoutParams lp =
                    new TableRow.LayoutParams(
                            0, metrics.heightPixels/row, 1f);
            lp.setMargins(3,3,3,3);
            rlayObj.setLayoutParams(lp);
            rlayObj.setGravity(Gravity.CENTER);

            RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            rlp.setMargins(3,50,3,50);
            layObj.setLayoutParams(rlp);

            layObj.setOrientation(LinearLayout.VERTICAL);
            layObj.setGravity(Gravity.CENTER);

            ImageView imgObj = new ImageView(MainActivity.this);
            imgObj.setImageResource(iImg);
            layObj.addView(imgObj);

            rlayObj.setBackgroundColor(Color.WHITE);
            rlayObj.addView(layObj);
            rlayObj.setTag(rlayObj);
            rlayObj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RelativeLayout now = (RelativeLayout) v.getTag();

                    if(last!=now){
                        Vibrator myVibrator = (Vibrator) getApplication().getSystemService(Service.VIBRATOR_SERVICE);
                        myVibrator.vibrate(300);
                    }

                    if(last!=null){
                        last.setBackgroundColor(Color.WHITE);
                        now.setBackgroundResource(R.color.colorBg);
                        last=now;
                    }else{
                        now.setBackgroundResource(R.color.colorBg);
                        last=now;
                    }

                }
            });
            lay[i]=rlayObj;
        }

        TableLayout.LayoutParams tab_lp = new TableLayout.LayoutParams(
                        android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                        android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        TableRow tablerow = new TableRow(MainActivity.this);
        tablerow.setBackgroundResource(R.color.colorBg);
        tablerow.setLayoutParams(tab_lp);

        for(int i=0;i<lay.length;i++){tablerow.addView(lay[i]);}
        tablay.addView(tablerow, new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }
}
