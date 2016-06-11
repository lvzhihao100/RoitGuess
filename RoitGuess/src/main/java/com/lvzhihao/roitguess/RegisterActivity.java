package com.lvzhihao.roitguess;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lvzhihao.roitguess.DataBase.Dao;
import com.lvzhihao.roitguess.DataBase.UserDaoImpl;
import com.lvzhihao.roitguess.bean.User;
import com.lvzhihao.roitguess.manager.ThreadManager;
import com.lvzhihao.roitguess.util.HttpUtils;
import com.lvzhihao.roitguess.util.JPushUtils;

import org.xutils.common.Callback;
import org.xutils.x;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.http.request.HttpRequest;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class RegisterActivity extends AppCompatActivity {
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 6003:
                    Toast.makeText(RegisterActivity.this, "昵称不合法", Toast.LENGTH_LONG).show();
                    popupWindowclose();
                    break;
                case 0:
                    Dao dao=new UserDaoImpl(BaseApplication.mhelper);
                    dao.save(user);
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("username", nickname.getText().toString());
                    intent.putExtras(bundle);
                    setResult(20, intent);
                    popupWindowclose();
                    RegisterActivity.this.finish();
                    break;
                case 1:
                    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_LONG).show();
                    popupWindowclose();
                    break;
                case 2:
                    Toast.makeText(RegisterActivity.this, "该昵称已注册", Toast.LENGTH_LONG).show();
                    popupWindowclose();
                    break;
                default:popupWindowclose();
                    break;
            }
        }
    }

        ;
    private User user;

    private void popupWindowclose() {
        if (null!=mPopupWindow&&mPopupWindow.isShowing()){
            mPopupWindow.dismiss();
            mPopupWindow=null;
        }
    }

    private TextView nickname;
        private EditText password;
        private Button registerBT;
        private PopupWindow mPopupWindow;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            registerBT = (Button) findViewById(R.id.email_register_in_button);
            nickname = (TextView) findViewById(R.id.nickname);
            password = (EditText) findViewById(R.id.password);
            registerBT.setOnClickListener(new MyClickListener());
            nickname.addTextChangedListener(new MyTextWatcher());

        }

        class MyClickListener implements View.OnClickListener {

            @Override
            public void onClick(View v) {

                if (checkNicknameAndPW()) {
                    popwindowShow();
                    ThreadManager.getInstance().createLongPool().excute(new Runnable() {//获取线程池中线程，执行任务
                        @Override
                        public void run() {
                            RequestParams params = new RequestParams(HttpUtils.DOMAIN+HttpUtils.REGISTER_PATH);
                            params.addParameter("nickname", nickname.getText().toString());
                            params.addParameter("password", password.getText().toString());
                            user = new User();
                            user.setName(nickname.getText().toString());
                            params.addParameter("json",new Gson().toJson(user));
                            x.http().get(params, new Callback.CommonCallback<String>() {
                                @Override
                                public void onCancelled(CancelledException arg0) {
                                }
                                @Override
                                public void onError(Throwable arg0, boolean arg1) {
                                }

                                @Override
                                public void onFinished() {
                                }

                                @Override
                                public void onSuccess(String arg0) {
                                    switch (arg0) {
                                        case "ok":
                                            System.out.println("ok");
                                            JPushInterface.setAlias(BaseApplication.getApplication(), nickname.getText().toString(), new TagAliasCallback() {
                                                @Override
                                                public void gotResult(int i, String s, Set<String> set) {//获取极光平台返回码，返回码为0，注册成功
                                                    Message message = new Message();
                                                    message.what = i;
                                                    System.out.println(message.what);
                                                    handler.sendMessage(message);
                                                }
                                            });
                                            break;
                                        case "faild":
                                            System.out.println("faild");
                                            Message message = new Message();
                                            message.what = 1;
                                            System.out.println(message.what);
                                            handler.sendMessage(message);
                                            break;
                                        case "registered":
                                            Message message1 = new Message();
                                            message1.what = 2;
                                            System.out.println(message1.what);
                                            handler.sendMessage(message1);
                                            break;
                                    }
                                }
                            });


                        }
                    });
                }
            }
        }

        private void popwindowShow() {
            if (null == mPopupWindow || !mPopupWindow.isShowing()) {
                LayoutInflater mLayoutInflater = (LayoutInflater) RegisterActivity.this
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                View load_popunwindwow = mLayoutInflater.inflate(
                        R.layout.popupwindow_register, null);
                mPopupWindow = new PopupWindow(load_popunwindwow, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                mPopupWindow.showAtLocation(findViewById(R.id.email_register_form_all), Gravity.CENTER, 0, 0);
            }
        }

        private boolean checkNicknameAndPW() {
            int lenth = password.getText().toString().length();
            if (lenth < 6 || lenth > 16) {
                Toast.makeText(getApplication(), "密码格式错误", Toast.LENGTH_LONG).show();
                return false;
            }
            return true;
        }

        class MyTextWatcher implements TextWatcher {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                if (input != "" && input != null && input.length() != 0) {
                    registerBT.setBackgroundColor(Color.GREEN);
                    registerBT.setClickable(true);
                    registerBT.setTextColor(Color.WHITE);
                } else {
                    registerBT.setBackgroundColor(Color.GRAY);
                    registerBT.setClickable(false);
                    registerBT.setTextColor(Color.BLACK);
                }
            }
        }

    }
