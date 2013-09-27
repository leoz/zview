package com.leoz.bz.zgallery;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import com.leoz.bz.zbase.ZFile;

public class CtlGalleryFragment extends Fragment {

	private static final String TAG = "FileInfo"; /// TODO: FIX ME

	private CtlGalleryAdapter adapter;
	private Gallery view;
	private ZFile dir;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
        view = new Gallery(getActivity());
        
        dir = null;
        setFragmentData(null);

        view.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();

        		int w = -1;
        		int h = -1;

        		if (v != null) {
         				w = v.getWidth();
        				h = v.getHeight();
        		}
        		
        		Log.v(TAG, "######### imageSelected:" + w + ":" + h); /// FIX ME							

               
            }
        });
        
		return view;
	}
	
	public void imageSelected (ZFile f) {
		/// TODO: FIX ME!!!
		dir = f.getParentFile();
		setFragmentData(dir);
		Toast.makeText(getActivity(), "Image selected: " + f.getName() + "\n" + "Parent: " + dir.getName(), Toast.LENGTH_SHORT).show();							
	}
	
	private void setFragmentData (ZFile f) {
		
    	if (view != null) {
	        if (adapter == null) {
		    	adapter = new CtlGalleryAdapter(getActivity(), f);
		    	view.setAdapter(adapter);
	        }
	        else {
	        	view.setAdapter(null);
	        	adapter.clearData();
	        	adapter.setData(f);
	        	view.setAdapter(adapter);
	        }
	    }
	}
	
    public void updateVisibleItem(String file, Bitmap b) {
        Gallery lv = view;
        int childCount = lv.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View v = lv.getChildAt(i);
            ImageView iv = (ImageView) v.findViewById(1003);
            String name = (String) iv.getTag();
            if (name.equals(file)) {
            	iv.setImageBitmap(b);
            }
        }
    }
}
