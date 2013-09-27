package com.leoz.bz.zview;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.leoz.bz.zbase.ZFile;
import com.leoz.bz.zclient.ZCltConnector;
import com.leoz.bz.zclient.ZCltService;
import com.leoz.bz.zfile.FileOpen;
import com.leoz.bz.zgallery.CtlGalleryFragment;
import com.leoz.bz.zgrid.CtlGridFragment;

public class ZViewActivity extends Activity
implements FileOpen.OnDirSelectedListener,
           FileOpen.OnImageSelectedListener {
	
	private static final String TAG = "[z::view] ZViewActivity"; /// TODO: FIX ME
	
	private static CtlGridFragment mGrid = null;
	private static CtlGalleryFragment mGallery = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        ZCltService.INSTANCE.init(this);
        ZCltService.INSTANCE.addReceiver(new ZViewReceiver());
        
        ZCltConnector.INSTANCE.start();

        mGrid = (CtlGridFragment) getFragmentManager().findFragmentById(R.id.ctlgrid_fragment);
	    
//	    ZViewReceiver.setList(mList);
	    ZViewReceiver.setGrid(mGrid);
	    ZViewReceiver.setGallery(mGallery);
	    
		setContentView(R.layout.main);
		
		Uri uri = this.getIntent().getData();
		
		if (uri != null) {
			ZFile f = new ZFile(uri.getPath());
			ZFile d = f.getParentFile();
			onDirSelected(d, 0);
			onImageSelected(f);
			
			Log.v(TAG, "onCreate: dir is " + d.getAbsolutePath() + " file is " + f.getAbsolutePath()); /// FIX ME
		}
		else {
			Log.v(TAG, "onCreate: uri is null!!!"); /// FIX ME
			
		}
		
		Log.v(TAG, "onCreate"); /// FIX ME
    }

	@Override
	protected void onDestroy() {
		
    	Log.v(TAG, "onDestroy"); /// TODO: FIX ME
    	
    	ZCltConnector.INSTANCE.stop();
		
		super.onDestroy();
	}

	public void onDirSelected(ZFile f, int context) {	 
        mGrid = (CtlGridFragment) getFragmentManager().findFragmentById(R.id.ctlgrid_fragment);
        ZViewReceiver.setGrid(mGrid);
//        mList = (CtlListFragment) getFragmentManager().findFragmentById(R.id.list_view);
//        ZViewReceiver.setList(mList);
	    if (mGrid == null || !mGrid.isInLayout()) {
///	        Intent showContent = new Intent(getApplicationContext(),
///	                CtlGridActivity.class);
///	        showContent.setData(Uri.parse(ctlUri));
///	        startActivity(showContent);
	    } else {
			setTitle(f.getAbsolutePath());
//			mList.setFragmentData(f);
			mGrid.setFragmentData(f);
	    }
	}
	
	public void onImageSelected(ZFile f) {
        mGallery = (CtlGalleryFragment) getFragmentManager().findFragmentById(R.id.gallery_fragment);
        ZViewReceiver.setGallery(mGallery);
	    if (mGallery == null || !mGallery.isInLayout()) {
///	        Intent showContent = new Intent(getApplicationContext(),
///	                CtlGalleryActivity.class);
///	        showContent.setData(Uri.parse(ctlUri));
///	        startActivity(showContent);
	    } else {
	    	mGallery.imageSelected(f);
	    }		
	}
}