package com.example.swipelistview;

import com.example.swipelistview.SwipeLayout.Status;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * 项目名称：SwipeListViewSelf<br>
 * 类名称：FrontLayout<br>
 * 类描述：左滑删除的内容布局<br>
 * 创建人：刘栋财<br>
 * 创建时间：2016年4月5日下午1:47:17<br>
 * 修改人： <br>
 * 修改时间： <br>
 * 修改备注：
 * @version V1.0
 */
public class FrontLayout extends RelativeLayout {

	private SwipeLayoutInterface mISwipeLayout;

	public FrontLayout(Context context) {
		super(context);
	}

	public FrontLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FrontLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setSwipeLayout(SwipeLayoutInterface mSwipeLayout){
		this.mISwipeLayout = mSwipeLayout;
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if(mISwipeLayout.getCurrentStatus() == Status.Close){
			return super.onInterceptTouchEvent(ev);
		}else {
			return true;
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(mISwipeLayout.getCurrentStatus() == Status.Close){
			return super.onTouchEvent(event);
		}else {
			if(event.getActionMasked() == MotionEvent.ACTION_UP){
				mISwipeLayout.close();
			}
			return true;
		}
	}

}
