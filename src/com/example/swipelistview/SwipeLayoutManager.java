package com.example.swipelistview;

/**
 * 项目名称：SwipeListViewSelf<br>
 * 类名称：SwipeLayoutManager<br>
 * 类描述：使用单例模式去管理已经打开的SwipeLayout<br>
 * 创建人：刘栋财<br>
 * 创建时间：2016年4月5日下午1:50:43<br>
 * 修改人： <br>
 * 修改时间： <br>
 * 修改备注：
 * @version V1.0
 */
public class SwipeLayoutManager {
	private SwipeLayoutManager(){}
	private static SwipeLayoutManager mInstance = new SwipeLayoutManager();
	public static SwipeLayoutManager getInstance(){
		return mInstance;
	}
	private SwipeLayout currentLayout;//记录当前已经打开的SwipeLayout
	public void setSwipeLayout(SwipeLayout currentLayout){
		this.currentLayout = currentLayout;
	}

	/**
	 * 创建人：刘栋财<br>
	 * 创建时间：2016年4月5日 下午1:50:58<br>
	 * 方法描述：判断当前是否能够滑动,如果有打开的，那么就不能滑动
	 * 		        而且需要判断，当前已经打开的和正在触摸是否是同一个，如果是同一个，则可以滑动，反之就不能<br>
	 * @param swipeLayout
	 * @return
	 */
	public boolean isCanSwipe(SwipeLayout swipeLayout){
		if(currentLayout==null){
			return true;
		}else {
			return currentLayout==swipeLayout;
		}
	}

	/**
	 * 创建人：刘栋财<br>
	 * 创建时间：2016年4月5日 下午1:51:14<br>
	 * 方法描述：关闭已经打开的SwipeLayout<br>
	 */
	public void closeCurrentLayout(){
		if(currentLayout!=null){
			currentLayout.close();
		}
	}

	/**
	 * 创建人：刘栋财<br>
	 * 创建时间：2016年4月5日 下午1:51:24<br>
	 * 方法描述：清除当前记录的SwipeLayout<br>
	 */
	public void clearCurrentLayout(){
		currentLayout = null;
	}
}
