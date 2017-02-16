package com.qq.googleplay.common.utils;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
/**
 * ============================================================
 * Copyright：Google有限公司版权所有 (c) 2017
 * Author：   陈冠杰
 * Email：    815712739@qq.com
 * GitHub：   https://github.com/JackChen1999
 * 博客：     http://blog.csdn.net/axi295309066
 * 微博：     AndroidDeveloper
 * <p>
 * Project_Name：GooglePlay
 * Package_Name：com.qq.googleplay
 * Version：1.0
 * time：2016/2/16 13:33
 * des ：${TODO}
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 **/
/*
 * The solution is taken from here: http://stackoverflow.com/questions/10042695/how-to-get-camera-result-as-a-uri-in-data-folder
 */

public class InternalStorageContentProvider extends ContentProvider {
    public static final Uri CONTENT_URI = Uri.parse("content://eu.janmuller.android.simplecropimage.example/");
	private static final HashMap<String, String> MIME_TYPES = new HashMap<String, String>();
	
	static {
		MIME_TYPES.put(".jpg", "image/jpeg");
		MIME_TYPES.put(".jpeg", "image/jpeg");
	}

	@Override
	public boolean onCreate() {
		try {
			File mFile = new File(getContext().getFilesDir(), CropImageActivity.TEMP_PHOTO_FILE_NAME);
			if(!mFile.exists()) {
				mFile.createNewFile();
				getContext().getContentResolver().notifyChange(CONTENT_URI, null);
			}
			return (true);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public String getType(Uri uri) {
		String path = uri.toString();
		for (String extension : MIME_TYPES.keySet()) {
			if (path.endsWith(extension)) {
				return (MIME_TYPES.get(extension));
			}
		}
		return (null);
	}
	
	@Override
	public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
		File f = new File(getContext().getFilesDir(), CropImageActivity.TEMP_PHOTO_FILE_NAME);
		if (f.exists()) {
			return (ParcelFileDescriptor.open(f, ParcelFileDescriptor.MODE_READ_WRITE));
		}
		throw new FileNotFoundException(uri.getPath());
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		return 0;
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		return null;
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		return null;
	}
	
	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		return 0;
	}
}
