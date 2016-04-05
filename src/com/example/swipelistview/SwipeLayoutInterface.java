package com.example.swipelistview;

import com.example.swipelistview.SwipeLayout.Status;

public interface SwipeLayoutInterface {
	
	Status getCurrentStatus();
	
	void close();
	
	void open();
}
