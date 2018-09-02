package com.example.localuser.retrofittest.DragSortListView;

import android.app.Activity;
import android.app.ListFragment;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.localuser.retrofittest.R;

import java.util.List;

/**
 * Created by zhouyd on 2016/5/13.
 */
public class DSLVFragment extends ListFragment {
    protected int layoutResId = -1;
    protected int dragLayoutId;
    private LayoutInflater mInflater;
    private DragSortListView mDslv;
    private DragSortController mController;
    private BaseAdapter adapter;
    private DragSortListView.DropListener onDrop;
    private boolean isLongPress;
    private SetConfig config;
    private int dividerHeight = 0;

    private int getLayoutResId() {
        //return layoutResId;
        if(layoutResId == -1) {
            return R.layout.dslv_fragment_main;
        }else{
            return layoutResId;
        }
    }

    //set resouceId of this fragment's layout
    public  void setLayoutResId(int resId){
        this.layoutResId = resId;
    }

    private int getDragLayoutId() {
        return dragLayoutId;
    }

    public void setDragLayoutId(int id)
    {
        this.dragLayoutId = id;
    }

    public void setDropListener(DragSortListView.DropListener onDrop)
    {
        this.onDrop = onDrop;
    }
    /**
     * Called when the activity is first created.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mInflater = inflater;
        mDslv = (DragSortListView) inflater.inflate(getLayoutResId(), container, false);

        mController = buildController(mDslv);
        mDslv.setFloatViewManager(mController);
        mDslv.setOnTouchListener(mController);
        mDslv.setDragEnabled(true);
        mDslv.setDividerHeight(config.getDividerHeight());
        return mDslv;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mDslv = (DragSortListView) getListView();
        mDslv.setDropListener(onDrop);
        setListAdapter(adapter);
        ((DragSortListView)getListView()).setmContext(((CommonAdapter)adapter).getmContext());
        setListViewStartAndEndDragIndex();
    }

//    private DragSortListView.DropListener onDrop =
//            new DragSortListView.DropListener() {
//                @Override
//                public void drop(int from, int to) {
//                    if (from != to) {
//                        I item = (T)adapter.getItem(from);
//                        adapter.remove(item);
//                        adapter.insert(item, to);
//                    }
//                }
//            };

    public  void setMyListAdapter(BaseAdapter adapter)
    {
        this.adapter = adapter;
    }

    private DragSortController buildController(DragSortListView dslv) {
        MyDSController m = new MyDSController(mDslv);
        return m;
    }

    private class MyDSController extends DragSortController {

        DragSortListView mDslv;
        private Bitmap mFloatBitmap;
        private ImageView mImageView;
        private int mFloatBGColor = Color.BLACK;

        public MyDSController(DragSortListView dslv) {
            super(dslv);
            setDragHandleId(getDragLayoutId());
            mDslv = dslv;
            if(isLongPress) {
                setDragInitMode(ON_LONG_PRESS);
            }
        }

        @Override
        public View onCreateFloatView(int position) {
            if(position >= ((CommonAdapter)adapter).getStartDragIndex() && position <= ((CommonAdapter)adapter).getEndDragIndex()) {
                //View v = adapter.getView(position, null, mDslv);
                View v = mDslv.getChildAt(position+mDslv.getHeaderViewsCount() - mDslv.getFirstVisiblePosition());
                //View v = mDslv.getChildAt(position);
                //v.getBackground().setLevel(10000);
                //Toast.makeText(getActivity(), "startDrag", Toast.LENGTH_LONG).show();
                if (v == null) {
                    return null;
                }
                v.setPressed(false);

                // Create a copy of the drawing cache so that it does not get
                // recycled by the framework when the list tries to clean up memory
                //v.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                v.setDrawingCacheEnabled(true);
                mFloatBitmap = Bitmap.createBitmap(v.getDrawingCache());
                mFloatBitmap = scaleBitmap(mFloatBitmap,1.05f);
                v.setDrawingCacheEnabled(false);

                if (mImageView == null) {
                    mImageView = new ImageView(mDslv.getContext());
                }
                mImageView.setBackgroundColor(mFloatBGColor);
                mImageView.setPadding(0, 0, 0, 0);
                mImageView.setImageBitmap(mFloatBitmap);
                mImageView.setScaleType(ImageView.ScaleType.CENTER);
                GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,new int[]{Color
                        .parseColor("#00000000"),Color.parseColor("#ffffffff"),Color.parseColor("#00000000")});
                drawable.setGradientCenter(0.5f,0.5f);
                drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                drawable.setSize(v.getWidth(), (int) (v.getHeight() * 1.05f+ 10));
                mImageView.setBackgroundDrawable(drawable);
                mImageView.setLayoutParams(new ViewGroup.LayoutParams(v.getWidth(), (int) (v.getHeight() * 1.05f+ 10)));

                return mImageView;
            }else{
                return null;
            }
        }

        @Override
        public void onDestroyFloatView(View floatView) {
            //do nothing; block super from crashing
            //Toast.makeText(getActivity(), "endDrag", Toast.LENGTH_LONG).show();
        }

        @Override
        public int startDragPosition(MotionEvent ev) {
            int res = super.dragHandleHitPosition(ev);
            int width = mDslv.getWidth();

            if (ev.getX() < (width*(99.0)/100)) {
                return res;
            } else {
                return DragSortController.MISS;
            }
        }

        /**
         * 缩放Bitmap
         * @param bitmap 原图
         * @param ratio 比例
         *
         * @return 处理后的图片
         */
        private Bitmap scaleBitmap(Bitmap bitmap,float ratio) {
            Matrix matrix = new Matrix();
            matrix.postScale(ratio,ratio); //长和宽放大缩小的比例
            Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
            return resizeBmp;
        }

    }

    public List getDataList()
    {
        return ((CommonAdapter)adapter).getDataList();
    }


    public void setListViewStartAndEndDragIndex()
    {
        mDslv.setStartDragIndex(((CommonAdapter)adapter).getStartDragIndex());
        mDslv.setEndDragIndex(((CommonAdapter)adapter).getEndDragIndex());
    }

    public void setIsLongPress(boolean b)
    {
        this.isLongPress = b;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof SetConfig)
        {
            config = (SetConfig) activity;
        }
    }

    public interface SetConfig
    {
        int getDividerHeight();
    }
}
