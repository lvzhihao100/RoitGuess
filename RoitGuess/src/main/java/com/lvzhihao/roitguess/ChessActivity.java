package com.lvzhihao.roitguess;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Message;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.lvzhihao.roitguess.bean.ChessArray;
import com.lvzhihao.roitguess.bean.FromToMessage;
import com.lvzhihao.roitguess.manager.ThreadManager;
import com.lvzhihao.roitguess.receiver.JMessageReceiver;
import com.lvzhihao.roitguess.util.HttpUtils;
import com.lvzhihao.roitguess.util.MyCommonCallBack;
import com.lvzhihao.roitguess.view.DrawableTextView;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ChessActivity extends Activity {


    private boolean online;
    private static final int SELECTED = 1;
    private static final int UNSELECTED = 0;
    private static final int SPACE = 54;
    private static final int CHESSBOARD = 50;
    private static final int BGWIDTH = 414;
    private static final int BGHEIGHT = 628;
    private static final int STARTY = 20;
    private static final int STARTX = -34;
    private static final int OUTSIDE = 3;
    private static final int BLANK = 2;
    private static final int ME = 0;
    private static final int EMY = 1;
    private static final int ROAD = 4;
    private static final int BOARDHEIGHT = 11;
    private static final int BOARDWIDTH = 9;
    private static final int PREPARE = 0;
    private static final int STARTED = 1;
    private static final int WATING = 2;
    public RelativeLayout root;
    private int mPaperNum = 5;
    private int mRockNum = 5;
    int a;
    private int mScissorNum = 5;
    private int mPaperNumEmy = 5;
    private int mRockNumEmy = 5;
    private int mScissorNumEmy = 5;

    int gameState;
    private int meColumn;
    private int meRow;
    private int emyColumn;
    private int emyRow;
    private int myComp;
    private int emyComp;
    private int result;
    int lastRow;
    int lastColumn;
    private int round;
    private PopupWindow mPopupWindow;
    public int layheight;
    public int laywidth;
    private View load_popunwindwow;
    private TextView scissornum;
    private TextView papernum;
    private TextView rocknum;
    private ImageView scissorImg;
    private ImageView paperImg;
    private ImageView rockImg;
    private ChessArray[][] chessArrays = new ChessArray[BOARDHEIGHT][BOARDWIDTH];

    boolean isLock = false;
    public List<ChessArray> emyChesses;
    private List<ChessArray> aimdChesses;
    public String emyName;
    private JMessageReceiver jMessageReceiver;


    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    content = msg.getData().getString("content");
                    emyName = content.substring(1);
                    System.out.println("emyname=" + emyName);
                    if (content.charAt(0) == '0') {
                        myComp = ME;
                        emyComp = EMY;
                    } else if (content.charAt(0) == '1') {
                        myComp = EMY;
                        emyComp = ME;
                    }
                    initData();
                    break;
                case 1:
                    int upRow = msg.getData().getInt("upRow");
                    int upColumn = msg.getData().getInt("upColumn");
                    int downColumn = msg.getData().getInt("downColumn");
                    int downRow = msg.getData().getInt("downRow");
                    result = msg.getData().getInt("result");
                    boolean isMove=false;
                    if (round == ME) {
                        removeRoad();
                        showFightResult(result, downRow, downColumn, upRow, upColumn);
                        if (result == 1) {
                            isMove=true;
                            emyChesses.remove(chessArrays[upRow][upColumn]);
                        }
                    } else if (round == EMY) {
                        if (result == 1) {
                            result = 2;
                        } else if (result == 2) {
                            result = 1;
                        }
                        showFightResult(result, upRow, upColumn, downRow, downColumn);
                        if (result == 1) {
                            isMove=true;
                            emyChesses.remove(chessArrays[upRow][upColumn]);
                        }
                    }
                    if (!isMove) {
                        handler.sendEmptyMessageDelayed(7, 1000);
                    }
                    break;
                case 2:
                    isEmyCompOver = true;
                    if (isMeCompOver) {
                        if (online) {

                            handler.sendEmptyMessage(3);
                        } else {
                            handler.sendEmptyMessage(3);
                        }

                    } else {

                    }
                    break;
                case 3:
                    if (online) {
                        for (ChessArray chess : meChesses) {
                            chess.setDarkOrsun(myComp);
                            chess.setComp(ME);
                            chess.initCharacterAndSkill();
                        }
                        for (ChessArray chess : emyChesses) {
                            chess.setDarkOrsun(emyComp);
                            chess.setComp(EMY);
                        }
                    } else {
                        myComp = ME;
                        emyComp = EMY;
                        for (ChessArray chess : meChesses) {
                            chess.setDarkOrsun(myComp);
                            chess.setComp(ME);
                            chess.initCharacterAndSkill();
                        }
                        for (ChessArray chess : emyChesses) {
                            chess.setDarkOrsun(emyComp);
                            chess.setComp(EMY);
                            chess.setWeapon(getweapon(EMY) + 1);
                            chess.initCharacterAndSkill();
                        }

                    }
                    round = myComp;
                    if (round == ME) {
                        isLock = false;
                    }
                    gameStart();
                    break;
                case 4:
                    if (online) {
                        content = msg.getData().getString("content");
                        fromToMessage = gson.fromJson(content, FromToMessage.class);
                        System.out.println(fromToMessage.toString());
                        int fromRow = 10 - fromToMessage.getFromRow();
                        int fromColumn = fromToMessage.getFromColumn();
                        int toColumn = fromToMessage.getToColumn();
                        int toRow = 10 - fromToMessage.getToRow();
                        if (chessArrays[toRow][toColumn].getComp() == ME) {
                            chessArrays[fromRow][fromColumn].setWeapon(fromToMessage.getWeapon());
                            chessArrays[fromRow][fromColumn].setSkill(fromToMessage.getSkill());
                            chessArrays[fromRow][fromColumn].setCharacter(fromToMessage.getCharacter());
                            popFight.doFight(chessArrays[toRow][toColumn], chessArrays[fromRow][fromColumn]);
                            params = new RequestParams(HttpUtils.DOMAIN + HttpUtils.CHESS_PATH);
                            fromToMessage = new FromToMessage(chessArrays[toRow][toColumn].getSkill(), chessArrays[toRow][toColumn].getCharacter(),
                                    chessArrays[toRow][toColumn].getWeapon());
                            String content = gson.toJson(fromToMessage);
                            params.addParameter("content", content);
                            params.addParameter("emy", emyName);
                            params.addParameter("what", 4);
                            x.http().get(params, new MyCommonCallBack() {
                                @Override
                                public void onSuccess(String result) {

                                }
                            });
                        } else if (chessArrays[toRow][toColumn].getComp() == BLANK) {
                            changeToBlank(chessArrays[toRow][toColumn]);
                            chessMove(toRow, toColumn, fromRow, fromColumn);
                        }
                    } else {
                        int fromRow = msg.getData().getInt("fromRow");
                        int fromColumn = msg.getData().getInt("fromColumn");
                        int toColumn = msg.getData().getInt("toColumn");
                        int toRow = msg.getData().getInt("toRow");
                        if (chessArrays[toRow][toColumn].getComp() == ME) {
                            popFight.doFight(chessArrays[toRow][toColumn], chessArrays[fromRow][fromColumn]);
                        } else if (chessArrays[toRow][toColumn].getComp() == BLANK) {
                            changeToBlank(chessArrays[toRow][toColumn]);
                            chessMove(toRow, toColumn, fromRow, fromColumn);
                        }
                    }
                    break;
                case 5:
                    Bundle bundle = msg.getData();
                    String skill = null;
                    String character = null;
                    int weapon = 0;
                    if (online) {
                        fromToMessage = gson.fromJson(bundle.getString("content"), FromToMessage.class);
                        skill = fromToMessage.getSkill();
                        character = fromToMessage.getCharacter();
                        weapon = fromToMessage.getWeapon();
                    } else {
                        skill = bundle.getString("skill");
                        character = bundle.getString("character");
                        weapon = bundle.getInt("weapon");
                    }
                    chessArrays[emyRow][emyColumn].setWeapon(weapon);
                    chessArrays[emyRow][emyColumn].setSkill(skill);
                    chessArrays[emyRow][emyColumn].setCharacter(character);
                    popFight.doFight(chessArrays[meRow][meColumn], chessArrays[emyRow][emyColumn]);
                    break;
                case 6:
                    int fromRow = msg.getData().getInt("fromRow");
                    int fromColumn = msg.getData().getInt("fromColumn");
                    int toColumn = msg.getData().getInt("toColumn");
                    int toRow = msg.getData().getInt("toRow");
                    temp = chessArrays[toRow][toColumn];
                    chessArrays[toRow][toColumn] = chessArrays[fromRow][fromColumn];
                    chessArrays[fromRow][fromColumn] = temp;
                    chessArrays[fromRow][fromColumn].setComp(BLANK);
                    root.removeView(chessArrays[fromRow][fromColumn].getImageView());
                    chessArrays[fromRow][fromColumn].setImageView(null);
                    setImagePosition(toRow, toColumn, chessArrays[toRow][toColumn].getImageView());
                    handler.sendEmptyMessage(7);
                    break;
                case 7:
                    if (round == ME) {
                        round = EMY;
                        if (online) {
                            flag = 2;
                        } else {
                            waitEmyMove();
                        }
                        lastRow = -1;
                        lastColumn = -1;
                    } else {
                        round = ME;
                        isLock = false;
                    }
                    if (emyChesses.size()==0){
                        Intent intent=new Intent();
                        intent.putExtra("isVectory",true);
                        ChessActivity.this.setResult(2, intent);
                        ChessActivity.this.finish();
                    }else if (meChesses.size()==0){
                        Intent intent=new Intent();
                        intent.putExtra("isVectory",false);
                        ChessActivity.this.setResult(2,intent);
                        ChessActivity.this.finish();
                    }
                    break;

            }
        }
    };
    private Animation animationBig;
    private Animation animationSmall;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private Message message;
    private RequestParams params;
    private String content;
    private Gson gson;
    private FromToMessage fromToMessage;


    private void showFightResult(int result, int fromRow, int fromColumn, int toRow, int toColumn) {
        switch (result) {
            case 0:
                break;
            case 1:
                changeToBlank(chessArrays[toRow][toColumn]);
                chessMove(toRow, toColumn, fromRow, fromColumn);
                break;
            case 2:
                changeToBlank(chessArrays[fromRow][fromColumn]);
                break;
        }
    }

    private boolean isEmyCompOver = false;
    private boolean isMeCompOver = false;

    private RelativeLayout fight_popupwindow;
    private ImageView emyCharacter;
    private ImageView meCharacter;
    private ImageView emySkill;
    private ImageView meSkill;
    private Rect rect;
    private PopFight popFight;
    private Button autoPlaceBT;
    private Button startGameBT;
    private Random random;

    public List<ChessArray> meChesses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess);
        online = getIntent().getExtras().getBoolean("online");
        System.out.println(online);
        gson = new Gson();
        jMessageReceiver = new JMessageReceiver(handler);
        IntentFilter filter = new IntentFilter();
        filter.addAction("cn.jpush.android.intent.REGISTRATION");
        filter.addAction("cn.jpush.android.intent.UNREGISTRATION");
        filter.addAction("cn.jpush.android.intent.MESSAGE_RECEIVED");
        filter.addAction("cn.jpush.android.intent.NOTIFICATION_RECEIVED");
        filter.addAction("cn.jpush.android.intent.NOTIFICATION_OPENED");
        filter.addAction("cn.jpush.android.intent.ACTION_RICHPUSH_CALLBACK");
        filter.addAction("cn.jpush.android.intent.CONNECTION");
        filter.addCategory("com.lvzhihao.roitguess");
        registerReceiver(jMessageReceiver, filter);
        init();
        System.out.println("chessactivity create");
        if (online) {
            params = new RequestParams(HttpUtils.DOMAIN + HttpUtils.CHESS_PATH);
            params.addParameter("what", 0);
            params.addParameter("comeFrom", BaseApplication.user.getName());
            x.http().get(params, new MyCommonCallBack() {
                @Override
                public void onSuccess(String result) {

                }
            });
        } else {
            root.post(new Runnable() {
                @Override
                public void run() {
                    initData();
                }
            });
        }
    }

    private void initData() {
        laywidth = root.getMeasuredWidth();
        layheight = root.getMeasuredHeight();
        popFight = new PopFight(ChessActivity.this);
        popFight.init();
        initBoard();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(jMessageReceiver);
    }

    private void init() {
        ChessArray.init();
        if (online) {
            isEmyCompOver = false;
            isMeCompOver = false;
            isLock = true;
        }else {
            isEmyCompOver = false;
            isMeCompOver = false;
            isLock = false;
        }
        random = new Random();
        gameState = PREPARE;
        animationBig = AnimationUtils.loadAnimation(this, R.anim.bigger);
        animationBig.setDuration(1000);
        animationSmall = AnimationUtils.loadAnimation(this, R.anim.smaller);
        animationSmall.setDuration(1000);
        root = (RelativeLayout) findViewById(R.id.root);
        autoPlaceBT = (Button) findViewById(R.id.autoplace);
        startGameBT = (Button) findViewById(R.id.startgame);
        emyChesses = new ArrayList<ChessArray>();
        meChesses = new ArrayList<ChessArray>();
        aimdChesses = new ArrayList<ChessArray>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("onstart");

    }

    int test = 0;

    /**
     * 初始化棋盘
     */
    private void initBoard() {
        rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        for (int i = 0; i < BOARDHEIGHT; i++) {
            for (int j = 0; j < BOARDWIDTH; j++) {
                if (i == 0 || i == BOARDHEIGHT - 1 || j == 0 || j == BOARDWIDTH - 1 || i + j <= 3 || i + j >= 15 || i - j <= -5 || i - j >= 7) {
                    chessArrays[i][j] = new ChessArray((ImageView) null);
                    chessArrays[i][j].setComp(OUTSIDE);
                } else if (i <= 3) {
                    ImageView imageView = getImage(i, j, R.mipmap.chessbacklun);
                    root.addView(imageView);
                    chessArrays[i][j] = new ChessArray(imageView);
                    emyChesses.add(chessArrays[i][j]);
                } else if (i >= 7) {
                    ImageView imageView = getImage(i, j, R.mipmap.yellow);
                    root.addView(imageView);
                    chessArrays[i][j] = new ChessArray(imageView);
                    meChesses.add(chessArrays[i][j]);
                } else {
                    chessArrays[i][j] = new ChessArray((ImageView) null);
                    chessArrays[i][j].setComp(BLANK);
                }

            }
        }
    }

    private ImageView getImage(int row, int column, int resourceId) {
        ImageView imageView = new ImageView(ChessActivity.this);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(resourceId);
        setImagePosition(row, column, imageView);
        return imageView;
    }


    public void start(View v) {
        System.out.println("start");
        if (mRockNum + mPaperNum + mScissorNum == 0) {
            isLock = true;
            isMeCompOver = true;
            if (online) {

                ThreadManager.getInstance().createLongPool().excute(new Runnable() {
                    @Override
                    public void run() {
                        params = new RequestParams(HttpUtils.DOMAIN + HttpUtils.CHESS_PATH);
                        params.addParameter("what", 1);
                        params.addParameter("emy", emyName);
                        params.addParameter("content", "");
                        x.http().get(params, new MyCommonCallBack() {
                            @Override
                            public void onSuccess(String result) {
                                if (!result.equals("ok")) {
                                    Toast.makeText(ChessActivity.this, "连接中断", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });
                if (isEmyCompOver) {
                    handler.sendEmptyMessage(3);
                }
            } else {
                handler.sendEmptyMessage(2);
            }
            autoPlaceBT.setVisibility(View.GONE);
            startGameBT.setVisibility(View.GONE);
            System.out.println("real start");
        }
    }


    private void gameStart() {
        gameState = STARTED;
        final ImageView ban = new ImageView(this);
        ban.setImageResource(R.mipmap.ban);
        final ImageView mingren = new ImageView(this);
        mingren.setImageResource(R.mipmap.head);
        final Animation animationfirstUp = AnimationUtils.loadAnimation(this, R.anim.hero_into_animation_first_up);
        Animation animationfirstDown = AnimationUtils.loadAnimation(this, R.anim.hero_into_animation_first_down);
        final Animation animationsecondUp = AnimationUtils.loadAnimation(this, R.anim.hero_into_animation_up);
        final Animation animationsecondDown = AnimationUtils.loadAnimation(this, R.anim.hero_into_animation_down);
        animationfirstUp.setDuration(1000);
        animationfirstUp.setAnimationListener(new MyAnimationListener() {
            @Override
            void onEnd(Animation animation) {
                animationsecondUp.setAnimationListener(new MyAnimationListener() {
                    @Override
                    void onEnd(Animation animation) {
                        isLock = false;
                    }
                });
                if (myComp == ME) {
                    ban.startAnimation(animationsecondUp);
                } else {
                    mingren.startAnimation(animationsecondUp);
                }
            }
        });
        animationfirstDown.setDuration(1000);
        animationfirstDown.setAnimationListener(new MyAnimationListener() {
            @Override
            void onEnd(Animation animation) {
                animationsecondDown.setAnimationListener(new MyAnimationListener() {
                    @Override
                    void onEnd(Animation animation) {
                        isLock = false;
                    }
                });
                if (myComp == ME) {
                    mingren.startAnimation(animationsecondDown);
                } else {
                    ban.startAnimation(animationsecondDown);
                }
            }
        });
        animationsecondUp.setDuration(2000);
        animationsecondUp.setFillAfter(true);
        animationsecondDown.setDuration(2000);
        animationsecondDown.setFillAfter(true);
        if (myComp==ME){
        ban.startAnimation(animationfirstUp);
        mingren.startAnimation(animationfirstDown);
        }else {
            ban.startAnimation(animationfirstDown);
            mingren.startAnimation(animationfirstUp);
        }
        isLock = true;
        root.addView(ban);
        root.addView(mingren);
    }

    public void back(View v) {
        finish();
    }

    private int getweapon(int comp) {
        int flag = -1;
        if (comp == EMY) {
            if (mScissorNumEmy <= 0 && mRockNumEmy <= 0 && mPaperNumEmy <= 0) {
                return -1;
            }
            while (true) {
                int which = random.nextInt(3);
                // System.out.println(which);
                switch (which) {
                    case 0:
                        if (mScissorNumEmy > 0) {
                            mScissorNumEmy--;
                            flag = 0;
                        }
                        break;
                    case 1:
                        if (mRockNumEmy > 0) {
                            mRockNumEmy--;
                            flag = 1;
                        }
                        break;
                    case 2:
                        if (mPaperNumEmy > 0) {
                            mPaperNumEmy--;
                            flag = 2;
                        }
                        break;
                }
                if (flag != -1) {
                    break;
                }
            }
        } else {
            if (mScissorNum <= 0 && mRockNum <= 0 && mPaperNum <= 0) {
                return -1;
            }
            while (true) {
                int which = random.nextInt(3);
                switch (which) {
                    case 0:
                        if (mScissorNum > 0) {
                            mScissorNum--;
                            flag = 0;
                        }
                        break;
                    case 1:
                        if (mRockNum > 0) {
                            mRockNum--;
                            flag = 1;
                        }
                        break;
                    case 2:
                        if (mPaperNum > 0) {
                            mPaperNum--;
                            flag = 2;
                        }
                        break;
                }
                if (flag != -1) {
                    break;
                }
            }

        }
        return flag;
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && !isLock) {
            int column = getColumn(event.getX());
            int row = getRow(event.getY());
            if (column > 0 && column < 8 && row > 0 && row < 10) {
                if (chessArrays[row][column].getComp() == ME &&row>=7&& gameState == PREPARE) {
                    chessArray = chessArrays[row][column];
                    popwindowShow();
                } else if (gameState == STARTED && round == ME) {
                    if (chessArrays[row][column].getComp() == ME) {
                        removeRoad();
                        chessArray = chessArrays[row][column];
                        lastRow = row;
                        lastColumn = column;
                        blanks.clear();
                        findWay(row, column);
                        for (ChessArray chessArray : blanks) {
                            showRoad(chessArray);
                        }
                        flag = 1;
                    } else if (chessArrays[row][column].getComp() == ROAD) {
                        removeRoad();
                        isLock=true;
                        if (online) {
                            params = new RequestParams(HttpUtils.DOMAIN + HttpUtils.CHESS_PATH);
                            fromToMessage = new FromToMessage(lastRow, lastColumn, row, column);
                            gson = new Gson();
                            content = gson.toJson(fromToMessage);
                            params.addParameter("content", content);
                            params.addParameter("what", 2);
                            params.addParameter("emy", emyName);
                            x.http().get(params, new MyCommonCallBack() {
                                @Override
                                public void onSuccess(String result) {

                                }
                            });
                        }
                        chessMove(row, column, lastRow, lastColumn);

                    } else if (chessArrays[row][column].getComp() == EMY && flag == 1) {
                        if (Math.abs(row - lastRow) <= 1 && Math.abs(column - lastColumn) <= 1) {
                            removeRoad();
                            isLock=true;
                            meRow = lastRow;
                            meColumn = lastColumn;
                            emyRow = row;
                            emyColumn = column;
                            if (online) {
                                params = new RequestParams(HttpUtils.DOMAIN + HttpUtils.CHESS_PATH);
                                fromToMessage = new FromToMessage(lastRow, lastColumn, row, column,
                                        chessArrays[lastRow][lastColumn].getSkill(), chessArrays[lastRow][lastColumn].getCharacter(),
                                        chessArrays[lastRow][lastColumn].getWeapon());
                                gson = new Gson();
                                content = gson.toJson(fromToMessage);
                                params.addParameter("content", content);
                                params.addParameter("what", 3);
                                params.addParameter("emy", emyName);
                                x.http().get(params, new MyCommonCallBack() {
                                    @Override
                                    public void onSuccess(String result) {

                                    }
                                });
                            } else {
                                Message message = new Message();
                                Bundle bundle = new Bundle();
                                bundle.putString("character", chessArrays[emyRow][emyColumn].getCharacter());
                                bundle.putString("skill", chessArrays[emyRow][emyColumn].getSkill());
                                bundle.putInt("weapon", chessArrays[emyRow][emyColumn].getWeapon());
                                message.setData(bundle);
                                message.what = 5;
                                handler.sendMessage(message);
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    int flag;

    private List<Point> blankPoints = new ArrayList<Point>();

    private void findWay(int row, int column) {
        for (int i = row - 1; i <= row + 1; i++) {
            if (i > 0 && i < 10) {
                for (int j = column - 1; j <= column + 1; j++) {
                    if (j > 0 && j < 8) {
                        System.out.println("i=" + i + "j=" + j + "" + chessArrays[i][j].getComp());
                        if (chessArrays[i][j].getComp() == BLANK) {
                            ImageView image = getImage(i, j, R.mipmap.yellow);
                            chessArrays[i][j].setImageView(image);
                            blankPoints.add(new Point(i, j));
                            blanks.add(chessArrays[i][j]);
                        }
                        if (chessArrays[row][column].getComp() == EMY && chessArrays[i][j].getComp() == ME) {
                            aimdChesses.add(chessArrays[i][j]);
                        }
                    }
                }
            }
        }
    }

    private void showRoad(ChessArray chessArray) {
        root.addView(chessArray.getImageView());
        chessArray.setComp(ROAD);
    }

    private void waitEmyMove() {
        ChessArray movedChess = getMovedChess();
        ChessArray toMoveChess = getToMoveChess();
        blanks.clear();
        aimdChesses.clear();
        blankPoints.clear();
        int lastRow = getImageRow(movedChess.getImageView());
        int lastColumn = getImageColumn(movedChess.getImageView());
        int row = toPoint.x;
        int column = toPoint.y;
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putInt("fromRow", lastRow);
        bundle.putInt("fromColumn", lastColumn);
        bundle.putInt("toColumn", column);
        bundle.putInt("toRow", row);
        message.setData(bundle);
        message.what = 4;
        handler.sendMessage(message);
    }

    public int getImageColumn(ImageView imageView) {
        return getColumn(imageView.getX() + 20);
    }

    public int getImageRow(ImageView imageView) {
        return getRow(imageView.getY() + 20);
    }

    int toRow;
    int toColumn;
    Point toPoint;

    private ChessArray getToMoveChess() {
        ChessArray chessArray = null;
        if (aimdChesses.size() > 0) {
            chessArray = aimdChesses.get(random.nextInt(aimdChesses.size()));
            toPoint = new Point(getImageRow(chessArray.getImageView()), getImageColumn(chessArray.getImageView()));
        } else if (blanks.size() > 0) {
            toPoint = blankPoints.get(random.nextInt(blanks.size()));
            chessArray = chessArrays[toPoint.x][toPoint.y];
            root.addView(chessArray.getImageView());
        }
        return chessArray;
    }

    private ChessArray getMovedChess() {
        while (true) {
            int i = random.nextInt(emyChesses.size());
            int row = getImageRow(emyChesses.get(i).getImageView());
            int column = getImageColumn(emyChesses.get(i).getImageView());
            aimdChesses.clear();
            blanks.clear();
            blankPoints.clear();
            findWay(row, column);
            if (aimdChesses.size() > 0 || blanks.size() > 0) {
                return emyChesses.get(i);
            }
        }

    }

    private void changeToBlank(ChessArray chessArray) {
        root.removeView(chessArray.getImageView());
        chessArray.setComp(BLANK);
        chessArray.setImageView(null);
    }

    private void chessMove(int toRow, int toColumn, int fromRow, int fromColumn) {
        if (chessArrays[toRow][toColumn].getComp() == BLANK) {
            chessArrays[toRow][toColumn].setImageView(getImage(toRow, toColumn, R.mipmap.yellow));
            root.addView(chessArrays[toRow][toColumn].getImageView());
        }
        chessArrays[toRow][toColumn].getImageView().startAnimation(animationSmall);
        chessArrays[fromRow][fromColumn].getImageView().startAnimation(animationBig);
        isLock = true;
        message = new Message();
        Bundle bundle = new Bundle();
        bundle.putInt("fromRow", fromRow);
        bundle.putInt("fromColumn", fromColumn);
        bundle.putInt("toColumn", toColumn);
        bundle.putInt("toRow", toRow);
        message.setData(bundle);
        message.what = 6;
        animationSmall.setAnimationListener(new MyAnimationListener() {
            @Override
            void onEnd(Animation animation) {
                handler.sendMessage(message);

            }
        });

    }

    private int getRow(float y) {
        System.out.println(rect.top);
        return (int) ((y - STARTY * layheight / (float) BGHEIGHT - rect.top) / (SPACE * layheight / (float) BGHEIGHT));
    }

    private int getColumn(float x) {
        return (int) ((x - STARTX * laywidth / (float) BGWIDTH) / (SPACE * laywidth / (float) BGWIDTH));
    }

    public void autoPlace(View v) {
        if (mPopupWindow == null) {
            initPopupWindow();
        }
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();

        }
        clearChessNum();
        clearChess();
    }

    private void clearChessNum() {

        mRockNum = 0;
        mScissorNum = 0;
        mPaperNum = 0;
        scissornum.setText(mScissorNum + "个");
        papernum.setText(mPaperNum + "个");
        rocknum.setText(mRockNum + "个");
    }


    private void clearChess() {
        initChessNum();
        ChessArray.init();
        for (ChessArray chess : meChesses) {
            chess.setWeapon(getweapon(ME) + 1);
            switch (chess.getWeapon()) {
                case 1:
                    chess.getImageView().setImageResource(R.mipmap.scissors);
                    break;
                case 2:
                    chess.getImageView().setImageResource(R.mipmap.rock);
                    break;
                case 3:
                    chess.getImageView().setImageResource(R.mipmap.paper);
                    break;
                case 0:
                    chess.getImageView().setImageResource(R.mipmap.paper);
                    break;
            }
            chess.setState(SELECTED);
        }
    }

    private void initChessNum() {
        mRockNum = 5;
        mPaperNum = 5;
        mScissorNum = 5;
    }

    private void removeRoad() {
        for (ChessArray chess : blanks) {
            changeToBlank(chess);

        }
    }

    private void setImagePosition(int row, int column, ImageView imageView) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(laywidth * CHESSBOARD / BGWIDTH, layheight * CHESSBOARD / BGHEIGHT);
        layoutParams.setMargins(laywidth * (STARTX + column * SPACE) / BGWIDTH, layheight * (STARTY + row * SPACE) / BGHEIGHT, 0, 0);
        imageView.setLayoutParams(layoutParams);
    }

    ChessArray temp;

    private void recycle(ImageView imageView) {
        ((BitmapDrawable) (imageView.getDrawable())).getBitmap().recycle();
    }

    List<ChessArray> blanks = new ArrayList<ChessArray>();

    private void popwindowShow() {
        if (null == mPopupWindow) {
            initChessNum();
            initPopupWindow();

        } else if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();

        }
        if (chessArray.getState() == SELECTED) {
            chessArray.getImageView().setImageResource(R.mipmap.yellow);
           // chessArray.removeCharacterAndSkill();
            if (chessArray.getWeapon() == ChessArray.PAPER) {
                mPaperNum++;
                papernum.setText(mPaperNum + "个");
            }
            if (chessArray.getWeapon() == ChessArray.ROCK) {
                mRockNum++;
                rocknum.setText(mRockNum + "个");
            }
            if (chessArray.getWeapon() == ChessArray.SCISSORS) {
                mScissorNum++;
                scissornum.setText(mScissorNum + "个");
            }
            chessArray.setWeapon(ChessArray.NOWEAPON);
            chessArray.setState(UNSELECTED);

        }
        mPopupWindow.showAtLocation(root, Gravity.NO_GRAVITY, chessArray.getImageView().getLeft(), chessArray.getImageView().getTop() - 2 * layheight * SPACE / BGHEIGHT);
    }

    ChessArray chessArray;

    private void initPopupWindow() {
        LayoutInflater mLayoutInflater = (LayoutInflater) ChessActivity.this
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        load_popunwindwow = mLayoutInflater.inflate(
                R.layout.popupwindow_putchess, null);
        scissornum = (DrawableTextView) (load_popunwindwow.findViewById(R.id.scisssornum));
        scissornum.setText(mScissorNum + "个");
        papernum = (DrawableTextView) (load_popunwindwow.findViewById(R.id.papernum));
        papernum.setText(mPaperNum + "个");
        rocknum = (DrawableTextView) (load_popunwindwow.findViewById(R.id.rocknum));
        rocknum.setText(mRockNum + "个");
        scissornum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mScissorNum > 0) {
                    mPopupWindow.dismiss();
                    mScissorNum--;

                    chessArray.getImageView().setImageResource(R.mipmap.scissors);
                  //  chessArray.initCharacterAndSkill();
                    chessArray.setWeapon(ChessArray.SCISSORS);
                    chessArray.setState(SELECTED);
                    scissornum.setText(mScissorNum + "个");
                }
            }
        });
        papernum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPaperNum > 0) {
                    mPopupWindow.dismiss();
                    mPaperNum--;
                    chessArray.getImageView().setImageResource(R.mipmap.paper);
                   // chessArray.initCharacterAndSkill();
                    chessArray.setWeapon(ChessArray.PAPER);
                    chessArray.setState(SELECTED);
                    papernum.setText(mPaperNum + "个");

                }
            }
        });
        rocknum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRockNum > 0) {
                    mPopupWindow.dismiss();
                    mRockNum--;
                    chessArray.getImageView().setImageResource(R.mipmap.rock);
                    chessArray.setWeapon(ChessArray.ROCK);
                  //  chessArray.initCharacterAndSkill();
                    chessArray.setState(SELECTED);
                    rocknum.setText(mRockNum + "个");

                }
            }
        });
        mPopupWindow = new PopupWindow(load_popunwindwow, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
