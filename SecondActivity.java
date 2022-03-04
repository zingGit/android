package com.example.zing_android;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    private final static String TAG = "console";

    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;
    private Context selfContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        selfContext = SecondActivity.this;

        initBtnData();
    }

    public void initBtnData() {
        /**
         * 这种方式 获取到button 动态设置其属性 string
         */
        Button btn_one = findViewById(R.id.btn_one);
        Button btn_two = findViewById(R.id.btn_two);
        Button btn_three = findViewById(R.id.btn_three);
        Button btn_four = findViewById(R.id.btn_four);

        /**
         * R.string 是values文件夹下strings.xml
         */
        btn_one.setText(R.string.btn_one);
        btn_two.setText(R.string.btn_two);
        btn_three.setText(R.string.btn_three);
        btn_four.setText(R.string.btn_four);

    }

    public void onButtonEvent( View view) {

        switch (view.getId()) {
            case R.id.btn_one:
                /**
                 * 1: 构造一个dialog对象
                 * 2：生成一个dialog实例 .create()
                 * 3:执行 show()
                 */
                builder = new AlertDialog.Builder(selfContext);
                alert = builder.setIcon(R.drawable.pause).setTitle("系统提示")
                        .setMessage("这是一个普通的系统弹出框")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(selfContext, " 您点击了取消", Toast.LENGTH_LONG).show();
                            }
                        })

                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(selfContext, " 您点击了确定", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNeutralButton(" 中立", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(selfContext, " 您点击了中立", Toast.LENGTH_LONG).show();
                            }
                        })
                        .create();
                alert.show();
                break;

            case R.id.btn_two:
                final String[] list = new String[]{"item0", "item1", "item2"};
                builder = new AlertDialog.Builder(selfContext);
                alert = builder.setIcon(R.drawable.pause)
                        .setTitle("选择项")
                        .setItems(list, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(selfContext, "您选择了" + list[i], Toast.LENGTH_LONG).show();
                            }
                        }).create();

                alert.show();
                break;

            case R.id.btn_three:
                final String[] fruits = new String[]{"苹果", "香蕉", "西瓜"};
                builder = new AlertDialog.Builder(selfContext);
                alert = builder.setIcon(R.drawable.pause)
                        .setTitle("选择你喜欢的水果")
                        .setSingleChoiceItems(fruits, 1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(selfContext, " 您选择了" + fruits[i], Toast.LENGTH_LONG).show();
                            }
                        }).create();
                alert.show();
                break;
            case R.id.btn_four:
                final String[] fruits1 = new String[]{"苹果", "香蕉", "西瓜"};
                final boolean[] checkItems = new boolean[]{false, false,false};
                builder = new AlertDialog.Builder(selfContext);
                alert = builder.setIcon(R.drawable.pause)
                        .setTitle("多选列表")
                        .setMultiChoiceItems(fruits1, checkItems, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                                checkItems[i] = b;

                            }
                        }).setPositiveButton(" 确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                StringBuilder sBuilder = new StringBuilder();

                                for (int item = 0; item < checkItems.length; item++) {
                                    if(checkItems[item]) {
                                        sBuilder.append(fruits1[item] + " ");
                                    }
                                }

                                Toast.makeText(selfContext, " 你选择了：" + sBuilder.toString(), Toast.LENGTH_LONG).show();
                            }
                        })
                        .create();

                alert.show();
                break;

            case R.id.btn_five:

                /**
                 * 显示跳转
                 */
//                Intent intent = new Intent(selfContext, ThreeActivity.class);
//                startActivity(intent);

                /**
                 * 隐式跳转 指定action 需要在androidMannifest文件中声明 action & category
                 */
                Intent intent = new Intent();
                intent.setAction("test_self_action").addCategory("test_self_category");
//                intent.putExtra("k", "three activity"); //向activtity传递参数
                //传递多个参数
                Bundle bundle = new Bundle();
                bundle.putInt("num", 999);
                bundle.putString("str", "呵呵");
                bundle.putString("str2", "呵呵2");
                intent.putExtras(bundle);
                startActivity(intent);
                break;


            case R.id.btn_toFiveActivity:

                Intent intentFive = new Intent(selfContext, FiveActivtity.class);
                startActivity(intentFive);
                break;
        }


    }
}