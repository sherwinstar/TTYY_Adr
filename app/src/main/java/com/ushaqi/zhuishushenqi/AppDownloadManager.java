package com.ushaqi.zhuishushenqi;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Andy.zhang
 * @date 2019/5/13
 */
public class AppDownloadManager {

    private static volatile AppDownloadManager sInstance;
    private List<String> mDownloadAds = null;
    private List<String> mDownloadGames = null;
    private List<Long> mDownloadIds = null;
    private List<String> mDlBooks;
    /**
     * 书籍是否免费表
     */
    private Map<String, Boolean> mBookAllowFreeMap;
    /**
     * 书籍cbid表
     */
    private Map<String, String>  mCBidMap;

    public static AppDownloadManager getInstance() {
        if (sInstance == null) {
            synchronized (AppDownloadManager.class) {
                if (sInstance == null) {
                    sInstance = new AppDownloadManager();
                }
            }
        }

        return sInstance;
    }


    public List<String> getDlBooks() {
        if (mDlBooks == null) {
            mDlBooks = new ArrayList<>();
        }
        return mDlBooks;
    }

    public List<String> getDownloadAds() {
        if (mDownloadAds == null) {
            mDownloadAds = new ArrayList<>();
        }
        return mDownloadAds;
    }

    public List<String> getDownloadGames() {
        if (mDownloadGames == null) {
            mDownloadGames = new ArrayList<>();
        }
        return mDownloadGames;
    }

    public List<Long> getDownloadIds() {
        if (mDownloadIds == null) {
            mDownloadIds = new ArrayList<>();
        }
        return mDownloadIds;
    }

    public void clearBookAllowFree(String bookId) {
        if (mBookAllowFreeMap != null && !TextUtils.isEmpty(bookId) &&
                mBookAllowFreeMap.containsKey(bookId)) {
            mBookAllowFreeMap.remove(bookId);
        }
    }

    public void putBookAllowFree(String bookId, boolean allowFree) {
        if (mBookAllowFreeMap == null) {
            mBookAllowFreeMap = new HashMap<>(4,1);
        }

        if (!TextUtils.isEmpty(bookId)) {
            mBookAllowFreeMap.put(bookId, allowFree);
        }
    }

    public boolean isBookAllowFree(String bookId) {
        boolean allowFree = false;
        if (mBookAllowFreeMap != null && !TextUtils.isEmpty(bookId)
                && mBookAllowFreeMap.containsKey(bookId)) {
            Boolean ret = mBookAllowFreeMap.get(bookId);
            allowFree = (ret != null) && ret.booleanValue();
        }

        return allowFree;
    }

    /**
     * @param bookId
     * @return
     */
    public String getCBid(String bookId) {
        return (mCBidMap != null) ? mCBidMap.get(bookId) : null;
    }

    /**
     * @param bookId
     */
    public void clearCBid(String bookId) {
        if (mCBidMap != null && !TextUtils.isEmpty(bookId) && mCBidMap.containsKey(bookId)) {
            mCBidMap.remove(bookId);
        }
    }

    /**
     * @param bookId
     * @param cbid
     */
    public void putCBid(String bookId, String cbid) {
        if (TextUtils.isEmpty(bookId) || TextUtils.isEmpty(cbid)) {
            return;
        }

        if (mCBidMap == null) {
            mCBidMap = new HashMap<>(4, 1);
        }
        mCBidMap.put(bookId, cbid);
    }
}
