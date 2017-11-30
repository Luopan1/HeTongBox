package com.test720.hetong.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test720.hetong.R;
import com.test720.hetong.network.ApiService;
import com.test720.hetong.network.HttpsRequest;
import com.test720.hetong.utils.ActivityUtil;
import com.test720.hetong.utils.ClickEventUtils;
import com.test720.hetong.utils.KeyBoardUtils;
import com.test720.hetong.utils.SPUtils;
import com.test720.hetong.utils.SizeUtils;
import com.test720.hetong.utils.StatusBar;
import com.test720.hetong.utils.ToastUtil;

import java.lang.reflect.Method;

import butterknife.ButterKnife;
import rx.Subscription;

public abstract class BaseToolbarActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView tvBaseTitle;
    private int menuResId;
    private String menuStr;
    protected Context mContext;
    protected Activity mActivity;
    public ApiService apiService;
    /**
     * 左边点击事件
     */
    View.OnClickListener onClickListenerTopLeft;

    /**
     * 右边点击事件
     */
    View.OnClickListener onClickListenerTopRight;

    /**
     * 网络请求时，发生退出，用于取消网络请求
     */
    public Subscription mSubscription;

    /**
     * 记录是点击时屏幕的Y轴起点
     */
    private float startY;
    /**
     * 页面直接引用的LOG标识
     */
    protected String TAG = this.getClass().getSimpleName();

    /**
     * 是否全屏
     */
    protected boolean isFullScreen = false;

    private LinearLayout mDecorView = null;//根布局

    private FrameLayout mContentView = null;//activity内容布局

    /**
     * 是否添加ToolBar
     */
    protected boolean isShowToolBar = true;
    public SPUtils spUtils;
    public SizeUtils sizeUtils;
    /**
     * 状态栏颜色
     */
    protected int stateColor = R.color.colorPrimary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mActivity = this;

        initBase();
        if (mDecorView == null) {
            initDecorView();//初始化跟布局（添加toolbar，添加mContentview给子布局留空间）
        }
        //如果已经创建就先把内容清空，再添加
        if (mContentView != null) {
            mContentView.removeAllViews();//mContentview清空里面的view
        }
        setContentView(getContentView());    //添加布局
        ButterKnife.bind(this);
        spUtils = new SPUtils(mContext);
        sizeUtils = new SizeUtils(mActivity);
        ActivityUtil.addActivity(mActivity);
        apiService = HttpsRequest.provideClientApi();
        initView();
        init(savedInstanceState);
        initData();
        setListener();
    }

    /**
     * 根布局添加title布局
     */
    private void initDecorView() {
        //根容器
        mDecorView = new LinearLayout(this);
        mDecorView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mDecorView.setOrientation(LinearLayout.VERTICAL);
        if (isShowToolBar) {
            //Title
            addToolBar();

        }
        //内容
        mContentView = new FrameLayout(this);
        mContentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mDecorView.addView(mContentView);
    }

    //获取屏幕原始尺寸高度，包括虚拟功能键高度
    public static int getDpi(Context context){
        int dpi = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics",DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            dpi=displayMetrics.heightPixels;
        }catch(Exception e){
            e.printStackTrace();
        }
        return dpi;
    }

    /**
     * 获取 虚拟按键的高度
     * @param context
     * @return
     */
    public static  int getBottomStatusHeight(Context context){
        int totalHeight = getDpi(context);

        int contentHeight = getScreenHeight(context);

        return totalHeight  - contentHeight;
    }


    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context)
    {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }




    public boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
        }
        return hasNavigationBar;
    }



    /**
     * 重写布局设置，加入title布局
     *
     * @param layoutResID
     */
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        getLayoutInflater().inflate(layoutResID, mContentView);
        if (isShowToolBar) {
            //Title
            mDecorView.setFitsSystemWindows(true);//保证沉浸式窗体，状态栏和标题栏不重叠
        }
        super.setContentView(mDecorView);
    }


    /**
     * 实例化Toolbar，设置基本参数
     */
    protected void addToolBar() {
        View view = getLayoutInflater().inflate(R.layout.activity_base_toolbar, mDecorView);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        tvBaseTitle = (TextView) view.findViewById(R.id.tv_base_title);
//        //初始化设置 Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    /**
     * @param title 标题
     */
    public void initToobar(String title){
        if(!TextUtils.isEmpty(title))
        {
            tvBaseTitle.setText(title);
        }
        setTopLeftButton(R.mipmap.youbiaobai);
    }


    /**
     * @param title 标题
     * @param icon 左边图标
     */
    public void initToobar(String title,int icon){
        if(!TextUtils.isEmpty(title))
        {
            tvBaseTitle.setText(title);
        }
        if(icon == 0)setTopLeftButton(R.mipmap.youbiaobai);
        else setTopLeftButton(icon);
    }

    /**
     * @param title  标题
     * @param menuResId 右边图标
     * @param icon 左边图标
     */
    public void initToobar(String title,int menuResId,int icon){
        if(!TextUtils.isEmpty(title))
        {
            tvBaseTitle.setText(title);
        }
        this.menuResId = menuResId;
        if(icon == 0)setTopLeftButton(R.mipmap.youbiaobai);
        else setTopLeftButton(icon);
    }


    /**
     * @param title  标题
     * @param menuStr 右边标题
     * @param icon 左边图标
     */
    public void initToobar(String title,String menuStr,int icon){
        if(!TextUtils.isEmpty(title))
        {
            tvBaseTitle.setText(title);
        }
        this.menuStr = menuStr;
        if(icon == 0)setTopLeftButton(R.mipmap.youbiaobai);
        else setTopLeftButton(icon);
    }


    /**
     * 初始化UI所需参数
     */
    protected void initView(){};


    /**
     * 设置点击事件
     */
    public void setListener(){};

    /**
     * 设置布局
     * @return 布局文件ID
     */
    protected abstract int getContentView();


    /**
     * 初始化  基本上不使用到地图该方法不用被重写
     * @param savedInstanceState
     */
    public void init(Bundle savedInstanceState){};


    /**
     * @param iconResId  左边按钮图片
     */
    protected void setTopLeftButton(int iconResId){
        toolbar.setNavigationIcon(iconResId);
        onClickListenerTopLeft = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LeftOnClick();
            }
        };
    }

    protected void setTopLeftButton(){
        toolbar.setNavigationIcon(null);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menuResId != 0 || !TextUtils.isEmpty(menuStr)){
            getMenuInflater().inflate(R.menu.menu_activity_base_top_bar, menu);
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menuResId != 0) {
            menu.findItem(R.id.menu_1).setIcon(menuResId);
        }
        if (!TextUtils.isEmpty(menuStr)){
            menu.findItem(R.id.menu_1).setTitle(menuStr);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            LeftOnClick();
        }
        else if (item.getItemId() == R.id.menu_1){
            RightOnClick();
        }

        return true; // true 告诉系统我们自己处理了点击事件
    }


    /**
     * 左边按钮的点击事件
     */
    public void LeftOnClick(){
        Log.e("--------","点击左边");
        finish();

    }


    /**
     * 右边按钮的点击事件
     */
    public void RightOnClick(){
        Log.e("--------","点击右边按钮");
    }


    /**
     * 初始化数据--构造数据--网络数据等
     */
    protected abstract void initData();

    /**
     * 封装跳转方式
     *
     * @param clazz  跳转到指定页面
     * @param finish 是否关闭当前页面
     */
    public void jumpToActivity(Class<? extends Activity> clazz, boolean finish) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        if (finish) {
            finish();
        }
    }


    /**
     * 封装跳转方式
     *
     * @param intent 传递参数
     * @param finish 是否关闭当前页面
     */
    public void jumpToActivity(Intent intent, boolean finish) {

        startActivity(intent);
        if (finish) {
            finish();
        }
    }


    /**
     * 封装跳转方式
     *
     * @param clazz  跳转到指定页面
     * @param bundle 传递参数
     * @param finish 是否关闭当前页面
     */
    public void jumpToActivity(Class<? extends Activity> clazz, Bundle bundle, boolean finish) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) intent.putExtras(bundle);
        startActivity(intent);
        if (finish) {
            finish();
        }
    }


    /**
     * 启动Activity，
     *
     * @param clazz  到指定页面
     * @param bundle 传递参数
     * @param flags  Intent.setFlag参数，设置为小于0，则不设置
     * @param finish
     */
    public void jumpToActivity(Class<? extends Activity> clazz, Bundle bundle, int flags, boolean finish) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null)
            intent.putExtras(bundle);
        if (flags > 0) {
            intent.setFlags(flags);
        }

        startActivity(intent);
        if (finish) {
            finish();
        }
    }

    /**
     * Activity销毁调用生命周期
     */
    @Override
    protected void onDestroy() {
        ActivityUtil.finishActivity(mActivity);
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();//回退时，网络未取消，取消当前网络请求
        }
        super.onDestroy();
    }

    /**
     * 初始化基类参数--复写该方法处理是否可预先
     * 设置是否全屏
     */
    protected void initBase() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        //是否全屏
        if (isFullScreen) {
            isShowToolBar = false;

            //全屏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            StatusBar.setBackgroundResource(this,stateColor);
        }

    }

    /**
     * 针对Android系统页面存在EditTextView是，判断是点击非EditText是否消失软键盘
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean isTrue = super.dispatchTouchEvent(ev);//让系统的东西先执行,如果有

        //事件组合一直持续，为保证判定一次，故在MotionEvent.ACTION_UP下执行键盘事件
        View view = this.getCurrentFocus();
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            startY = ev.getY();
        }
        if (view != null && ev.getAction() == MotionEvent.ACTION_UP) {//屏幕上有光标，并且点击区域为EditText
            if (ClickEventUtils.isClickEditText(view, ev) || Math.abs(ev.getY() - startY) > 10) {//点击的是EditText或者手指滑动一段距离都不隐藏
                // LogUtils.i(TAG, "屏幕上有光标-点击的是输入框--不隐藏");
            } else {
                //LogUtils.i(TAG, "屏幕上有光标-点击的不是输入框--隐藏输入框");
                KeyBoardUtils.hideSoftInput(this);
            }
        }
        return isTrue;
    }

    public void ShowToast(String msg)
    {
        ToastUtil.show(this,msg);
    }

}
