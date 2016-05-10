package com.example.swipelistview;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.example.swipelistview.R;
import com.example.swipelistview.SwipeLayout.SwipeListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

/**
 * 项目名称：SwipeListViewSelf<br>
 * 类名称：MainActivity<br>
 * 类描述：主界面<br>
 * 创建人：刘栋财<br>
 * 创建时间：2016年4月5日下午1:47:36<br>
 * 修改人： <br>
 * 修改时间： <br>
 * 修改备注：
 * @version V1.0
 */
public class MainActivity extends Activity {

	private List<String> mDataList=new ArrayList<String>();
	private ListView lv_listview;
	
	private SwipeListAdapter mAdapter;
	HashSet<SwipeLayout> mUnClosedLayouts = new HashSet<SwipeLayout>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initView();
		initData();
		
		mAdapter=new SwipeListAdapter(mDataList);
		lv_listview.setAdapter(mAdapter);
		lv_listview.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
					// 关闭已经打开的
					SwipeLayoutManager.getInstance().closeCurrentLayout();
					SwipeLayoutManager.getInstance().clearCurrentLayout();
					mUnClosedLayouts.clear();
				}
			}
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
			}
		});
	}

	/**
	 * 创建人：刘栋财<br>
	 * 创建时间：2016年4月5日 下午1:48:09<br>
	 * 方法描述：初始化布局<br>
	 */
	private void initView() {
		lv_listview = (ListView) findViewById(R.id.lv_listview);
	}
	
	/**
	 * 创建人：刘栋财<br>
	 * 创建时间：2016年4月5日 下午1:48:21<br>
	 * 方法描述：模拟数据<br>
	 */
	private void initData() {
		for (int i = 0; i < 200; i++) {
			mDataList.add("水淹七军"+i);
		}
	}
	
	/**
	 * 项目名称：SwipeListViewSelf<br>
	 * 类名称：SwipeListAdapter<br>
	 * 类描述：左滑删除listview的Adapter<br>
	 * 创建人：刘栋财<br>
	 * 创建时间：2016年4月5日下午1:48:36<br>
	 * 修改人： <br>
	 * 修改时间： <br>
	 * 修改备注：
	 * @version V1.0
	 */
	public class SwipeListAdapter extends BaseAdapter {
		private List<String> datalist;
		public SwipeListAdapter(List<String> list) {
			super();
			this.datalist=list;
		}
		@Override
		public int getCount() {
			return datalist.size();
		}
		@Override
		public String getItem(int position) {
			return datalist.get(position);
		}
		@Override
		public long getItemId(int position) {
			return position;
		}
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder mHolder;
			if (convertView != null) {
				mHolder = (ViewHolder) convertView.getTag();
			}else {
				convertView = View.inflate(getApplicationContext(), R.layout.item_delete, null);
				mHolder = ViewHolder.fromValues(convertView);
				convertView.setTag(mHolder);
			}
			SwipeLayout view = (SwipeLayout) convertView;
			view.close(false, false);
			view.getFrontView().setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//如果有左滑，关闭；没有则触发点击事件
						Toast.makeText(getApplicationContext(), datalist.get(position)+position, 0).show();
						SwipeLayoutManager.getInstance().closeCurrentLayout();
						SwipeLayoutManager.getInstance().clearCurrentLayout();
						mUnClosedLayouts.clear();
				}
			});
			view.setSwipeListener(mSwipeListener);
			mHolder.mName.setText(datalist.get(position));
			mHolder.mButtonDel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					SwipeLayoutManager.getInstance().closeCurrentLayout();
					SwipeLayoutManager.getInstance().clearCurrentLayout();
					mUnClosedLayouts.clear();
					//删除
					datalist.remove(position);
					refreshList(datalist);
				}
			});
			
			return view;
		}
		SwipeListener mSwipeListener = new SwipeListener() {
			@Override
			public void onOpen(SwipeLayout swipeLayout) {
				mUnClosedLayouts.add(swipeLayout);
			}
			@Override
			public void onClose(SwipeLayout swipeLayout) {
				mUnClosedLayouts.remove(swipeLayout);
			}
			@Override
			public void onStartClose(SwipeLayout swipeLayout) {
			}
			@Override
			public void onStartOpen(SwipeLayout swipeLayout) {
				closeAllLayout();
				mUnClosedLayouts.add(swipeLayout);
			}
		};
		public int getUnClosedCount(){
			return mUnClosedLayouts.size();
		}
		public void closeAllLayout() {
			if(mUnClosedLayouts.size() == 0)
				return;
			for (SwipeLayout l : mUnClosedLayouts) {
				l.close(true, false);
			}
			mUnClosedLayouts.clear();
		}
		//刷新
		public void refreshList(List<String> datas) {
			this.datalist = datas;
			notifyDataSetChanged();
		}
	}
	
	/**
	 * 项目名称：SwipeListViewSelf<br>
	 * 类名称：ViewHolder<br>
	 * 类描述：SwipeListAdapter的ViewHolder<br>
	 * 创建人：刘栋财<br>
	 * 创建时间：2016年4月5日下午1:48:57<br>
	 * 修改人： <br>
	 * 修改时间： <br>
	 * 修改备注：
	 * @version V1.0
	 */
	static class ViewHolder {
		public TextView mButtonDel;
		public TextView mName;
		private ViewHolder(
				TextView mButtonDel, TextView mName) {
			super();
			this.mButtonDel = mButtonDel;
			this.mName = mName;
		}
		public static ViewHolder fromValues(View view) {
			return new ViewHolder(
				(TextView) view.findViewById(R.id.tv_delete),
				(TextView) view.findViewById(R.id.tv_content));
		}
	}
	
}
