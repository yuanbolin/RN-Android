package com.ruowei.baseandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.ruowei.baseandroid.rn.AndroidInfo;
import com.ruowei.baseandroid.rn.LinkUrl;
import com.ruowei.baseandroid.rn.MyReactActivity;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Main2Activity extends AppCompatActivity {


    @BindView(R.id.gotoRnTest)
    Button gotoRnTest;
    @BindView(R.id.gotoRnIndex)
    Button gotoRnIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.gotoRnTest, R.id.gotoRnIndex})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.gotoRnTest:
                // APP中唤起RN页面的Activity，并将路由信息通过常量传递到对应的js中。
                LinkUrl.LINK_URL = "Test";
                HashMap<String,Object> map = new HashMap<>();
                map.put("test1","test111111");
                map.put("test2","test222222");
                map.put("test3",666);
                AndroidInfo.map = map;
                startActivity(new Intent(Main2Activity.this, MyReactActivity.class));
                break;
            case R.id.gotoRnIndex:
                // APP中唤起RN页面的Activity，并将路由信息通过常量传递到对应的js中。
                LinkUrl.LINK_URL = "Index";
                HashMap<String,Object> map2 = new HashMap<>();
                map2.put("Index1","Index111111");
                map2.put("Index2","Index222222");
                map2.put("Index3",777);
                AndroidInfo.map = map2;
                startActivity(new Intent(Main2Activity.this, MyReactActivity.class));
                break;
        }
    }

//    @OnClick(R.id.gotoRN)
//    public void onViewClicked() {
//        // APP中唤起RN页面的Activity，并将路由信息通过linking传递到对应的js中。
//
//        LinkUrl.LINK_URL = "Test";
////        Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("appscheme://apphost/path?params=Test"));
////        startActivity(intent1);
//        startActivity(new Intent(Main2Activity.this, MyReactActivity.class));
//    }
}