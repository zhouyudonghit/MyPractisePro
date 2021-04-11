//package com.example.localuser.retrofittest.AddressPickerTest;
//
//import android.app.Activity;
//import android.content.Context;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.design.widget.TabLayout;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.PopupWindow;
//
//import com.example.localuser.retrofittest.R;
//
//import java.util.ArrayList;
//
//public class AddressPickerTest3Activity extends AppCompatActivity {
//    private PopupWindow popupWindow;
//    private Context mContext;
//    private Activity mActivity;
//    private ImageView mIv_close;
//    private TabLayout mTabLayout;
//    private RecyclerView mRecyclerView;
//    private NewLoadingView mLoadingView;
//    //地址数据集合 数据源
//    private List<AraeData> mAraeDatas;
//    //地址数据集合  结果集
//    private List<AraeDataResult> mResultList = new ArrayList<>();
//    //标示  用来判断是否为最后一级数据
//    private boolean isLast = false;
//    //标示  用来区分是列表item点击 还是tab点击
//    private boolean itemClick = false;
//    //当前选中的tab
//    private int mTabCurrent = 0;
//    //地址选择完成的监听
//    private OnSelectOkListener mlistener;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.layout_address_picker_test1_main);
//    }
//
//    //初始化
//    private void init() {
//        View pop_view = LayoutInflater.from(mContext).inflate(R.layout.layout_address_picker, null);
//        popupWindow = new PopupWindow(pop_view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        // 点击其他区域关闭
//        popupWindow.setFocusable(true);
//        popupWindow.setOutsideTouchable(true);
//        popupWindow.setAnimationStyle(android.R.style.Animation_Toast);
//        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                Window window = mActivity.getWindow();
//                WindowManager.LayoutParams params = window.getAttributes();
//                params.alpha = 1.0F;
//                window.setAttributes(params);
//                if (isLast) {
//                    mlistener.Select(mResultList);
//                }
//            }
//        });
//        mTabLayout = pop_view.findViewById(R.id.tablayout);
//        mRecyclerView = pop_view.findViewById(R.id.recyclerview);
//        mTabLayout.addOnTabSelectedListener(this);
//    }
//
//    //recycleivew的item点击监听，也就是用户选择地址了
//    RvAdapter.OnSelectorListener mListener = new RvAdapter.OnSelectorListener() {
//        @Override
//        public void setSelect(String name, String id, int sPosition) {
//            int tabCount = mTabLayout.getTabCount();
//            //重新点击新地址后  循环删除 旧tab
//            if (mTabCurrent < tabCount - 1) {
//                for (int i = 0; i < tabCount - 1 - mTabCurrent; i++) {
//                    mTabLayout.removeTabAt(tabCount - 1 - i);
//                    mResultList.remove(tabCount - 1 - i);
//                }
//            }
//            mTabLayout.getTabAt(mTabCurrent).setText(name);
//            mResultList.add(new AraeDataResult(id, name, sPosition));
//
//            itemClick = true;
//            getdata(id, "0", 0, false);
//        }
//    };
//
//    //tablayout中某个tab选中的监听
//    @Override
//    public void onTabSelected(TabLayout.Tab tab) {
//        mTabCurrent = tab.getPosition();
//
//        if (itemClick) {
//
//        } else {
//            String nowID = "0";
//            int sPosition = 0;
//            int tabCount = mTabLayout.getTabCount();
//            //如果是最后一个tab  则不需要默认选中 并且移动recycle位置
//            if (mTabCurrent >= tabCount - 1) {
//                nowID = "0";
//                sPosition = 0;
//            } else {
//                nowID = mResultList.get(mTabCurrent + 1).getAreaId();
//                sPosition = mResultList.get(mTabCurrent + 1).getPosition();
//            }
//            getdata(mResultList.get(mTabCurrent).getAreaId(), nowID, sPosition, true);
//        }
//
//    }
//
//    //添加Tab
//    private void addTab() {
//        mTabLayout.addTab(mTabLayout.newTab().setText("请选择"), true);
//    }
//
//    /**
//     * 网络获取数据
//     *
//     * @param pId       每级地址ID
//     * @param nowId     当前选中的地址id   在recycleview的选中效果中使用
//     * @param sposition 当前选中的item的position 用来把选中的item移动到第一位置
//     * @param onlyShow  用来判断点击的为item 还是tab   如果是item 则要添加tab
//     */
//    private void getdata(final String pId, final String nowId, final int sposition, final boolean onlyShow) {
//        isLast = false;
////        HttpManager manager = new HttpManager.Builder();
////        manager.setListener(new HttpManagerListen() {
////            @Override
////            public void onSucceed(String request) {
////                mAraeDatas = GsonManager.getInstance().getGson().fromJson(request, new TypeToken<List<AraeData>>() {
////                }.getType());
////                if (mAraeDatas == null || mAraeDatas.size() == 0) {
////                    //没有数据了  改变标示
////                    isLast = true;
////                    itemClick = false;
////                }
////                //无数据标示成立，说明是最后一级
////                if (isLast) {
////                    popupWindow.dismiss();
////                    return;
////                }
////                //如果是点击item，则添加tab
////                if (!onlyShow) {
////                    addTab();
////                }
////
////                RvAdapter adapter = new RvAdapter(mContext, nowId, mAraeDatas, mListener);
////                mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
////                mRecyclerView.setAdapter(adapter);
////
////                //显示item为视图第一个
////                if (onlyShow) {
////                    mRecyclerView.scrollToPosition(sposition);
////                }
////                itemClick = false;
////            }
//        }
//}
