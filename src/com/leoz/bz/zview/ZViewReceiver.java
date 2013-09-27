package com.leoz.bz.zview;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.leoz.bz.zbase.ZBaseData;
import com.leoz.bz.zclient.ZCltReceiver;
import com.leoz.bz.zfile.ImgCache;
import com.leoz.bz.zfile.ImgKey;
//import com.leoz.blinkzee.gallery.CtlGalleryFragment;
import com.leoz.bz.zgallery.CtlGalleryFragment;
import com.leoz.bz.zgrid.CtlGridFragment;
//import com.leoz.blinkzee.list.CtlListFragment;

public class ZViewReceiver extends ZCltReceiver {

	private static final String TAG = "[z::view] ZViewReceiver"; /// TODO: FIX ME
	
//	private static CtlListFragment mList = null;
	private static CtlGridFragment mGrid = null;
	private static CtlGalleryFragment mGallery = null;
	
	public ZViewReceiver() {
	}
	
//	public static void setList(final CtlListFragment f) {
//		mList = f;		
//	}

	public static void setGrid(final CtlGridFragment f) {
		mGrid = f;		
	}

	public static void setGallery(final CtlGalleryFragment f) {
		mGallery = f;		
	}

	@Override
	public IntentFilter getFilter() {
    	IntentFilter filter = new IntentFilter();
    	filter.addAction(ZBaseData.ACTION);
		return filter;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		
        if(intent.getAction().equals(ZBaseData.ACTION)) {
 			receiveData(intent);
        }
	}
	
    private void receiveData(Intent intent) {
    	
    	Log.v(TAG, "receiveData" );
    	
    	String file = intent.getStringExtra("file");
    	int size = intent.getIntExtra("size", 0);
    	
    	ImgKey key = new ImgKey(file, size);
    	
    	Log.d(TAG, key.toString());
    	
    	byte[] bitmapdata = intent.getByteArrayExtra("image");
    	if (bitmapdata != null) {
        	Bitmap b = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
        	if (b != null) {
    	        ImgCache.INSTANCE.putThumbnail(key, b);
//            	if (mList != null && key.mSize == 48) {
//            		mList.updateVisibleItem(file, b);
//            	}
            	if (mGrid != null && key.mSize == 96) {
            		mGrid.updateVisibleItem(file, b);
            	}            	
            	if (mGallery != null && key.mSize > 96) {
            		mGallery.updateVisibleItem(file, b);
            	}            	
        	}
    	}
    }
}
