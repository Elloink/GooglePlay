package com.qq.googleplay.cache;

import android.content.Context;
import android.os.Environment;

import com.qq.googleplay.common.io.IOUtils;
import com.qq.googleplay.utils.CommonUtil;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
 * des ：缓存工具类
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 **/

public class DiskLruCacheUtil {

    private static int appVersion = CommonUtil.getVersionCode();

    private static int valueCount = 1;// 同一个key可以对应多少个缓存文件

    private static int maxSize = 10 * 1024 * 1024;// 一个缓存文件最大可以缓存10M

    public static final String CACHE_OBJECT = "object";// 对象缓存目录

    public static void saveObject(Serializable ser, String key) {
        ObjectOutputStream oos = null;
        try {
            DiskLruCache.Editor editor = getDiskLruCacheOutputStream(CommonUtil.getContext(),
                    CACHE_OBJECT, key);
            if (editor != null) {
                oos = new ObjectOutputStream(editor.newOutputStream(0));
                oos.writeObject(ser);
                oos.flush();
                editor.commit();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(oos);
        }
    }

    public static Serializable readObject(String key) {
        ObjectInputStream ois = null;
        try {
            DiskLruCache.Editor editor = getDiskLruCacheOutputStream(CommonUtil.getContext(),
                    CACHE_OBJECT, key);
            ois = new ObjectInputStream(editor.newInputStream(0));
            return (Serializable) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(ois);
        }
        return null;
    }

    /**
     * 获取DiskLruCache的editor
     * 
     * @param context
     * @param key
     * @return
     * @throws IOException
     */
    public static DiskLruCache.Editor getDiskLruCacheOutputStream(
            Context context, String uniqueName, String key) throws IOException {
        DiskLruCache mDiskLruCache = DiskLruCache.open(
                getDiskCacheDir(uniqueName), appVersion, valueCount, maxSize);
        DiskLruCache.Editor editor = mDiskLruCache.edit(hashKeyForDisk(key));
        return editor;
    }

    public static File getDiskCacheDir(String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = CommonUtil.getContext().getExternalCacheDir().getPath();
        } else {
            cachePath = CommonUtil.getContext().getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * 传入缓存的key值，以得到相应的MD5值
     * 
     * @param key
     * @return
     */
    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
