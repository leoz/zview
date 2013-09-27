package com.leoz.bz.zgallery;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.leoz.bz.zbase.ZFile;
import com.leoz.bz.zfile.CtlBaseAdapter;
import com.leoz.bz.zfile.FileInfo;
import com.leoz.bz.zfile.FileType;
import com.leoz.bz.zview.R;

public class CtlGalleryAdapter extends CtlBaseAdapter {

	int mGalleryItemBackground;
    private Context mContext;

	public CtlGalleryAdapter(Context context, ZFile f) {
		super(context, f);
        mContext = context;
        TypedArray attr = mContext.obtainStyledAttributes(R.styleable.CtlGallery);
        mGalleryItemBackground = attr.getResourceId(
                R.styleable.CtlGallery_android_galleryItemBackground, 0);
        attr.recycle();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setId(1003);
        imageView.setTag(mFiles[position].getAbsolutePath());
        setViewBitmap(position, imageView);
//        imageView.setLayoutParams(new Gallery.LayoutParams(150, 100));
//        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setBackgroundResource(mGalleryItemBackground);

        return imageView;
    }

    @Override
	public void setData(ZFile f) {
        mFiles = FileInfo.INSTANCE.getDirByType(f, FileType.FTYPE_IMAGE);    		
	}
}
