package com.bigfun.sdk;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

public class Share {
    private static final String TAG = "Share";

    /**
     * Current activity
     */
    private Context activity;

    /**
     * Share title
     */
    private String title;

    /**
     * Share file Uri
     */

    private Uri shareFileUri;

    /**
     * Share content text
     */
    private String contentText;

    /**
     * Share to special component PackageName
     */
    private String componentPackageName;

    /**
     * Share to special component ClassName
     */
    private String componentClassName;

    /**
     * Share complete onActivityResult requestCode
     */
    private int requestCode;


    /**
     * Forced Use System Chooser
     */
    private boolean forcedUseSystemChooser;

    private Share(@NonNull Builder builder) {
        this.activity = builder.context;
        this.title = builder.title;
        this.shareFileUri = builder.shareFileUri;
        this.contentText = builder.textContent;
        this.componentPackageName = builder.componentPackageName;
        this.componentClassName = builder.componentClassName;
        this.requestCode = builder.requestCode;
        this.forcedUseSystemChooser = builder.forcedUseSystemChooser;
    }

    /**
     * shareBySystem
     */
    public void shareBySystem() {
        if (checkShareParam()) {
            Intent shareIntent = createShareIntent();

            if (shareIntent == null) {
                Log.e(TAG, "shareBySystem cancel.");
                return;
            }

            if (title == null) {
                title = "";
            }

            if (forcedUseSystemChooser) {
                shareIntent = Intent.createChooser(shareIntent, title);
            }

            if (shareIntent.resolveActivity(activity.getPackageManager()) != null) {
                try {
                    activity.startActivity(shareIntent);
                } catch (Exception e) {
                    Log.e(TAG, Log.getStackTraceString(e));
                }
            }
        }
    }

    private Intent createShareIntent() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.addCategory("android.intent.category.DEFAULT");

        if (!TextUtils.isEmpty(this.componentPackageName) && !TextUtils.isEmpty(componentClassName)) {
            ComponentName comp = new ComponentName(componentPackageName, componentClassName);
            shareIntent.setComponent(comp);
        }

        if (shareFileUri == null) {
            shareIntent.putExtra(Intent.EXTRA_TEXT, contentText);
            shareIntent.setType("text/plain");
        } else {
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addCategory("android.intent.category.DEFAULT");
            shareIntent.setType("*/*");
            shareIntent.putExtra(Intent.EXTRA_STREAM, shareFileUri);
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            Log.d(TAG, "Share uri: " + shareFileUri.toString());

            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                List<ResolveInfo> resInfoList = activity.getPackageManager().queryIntentActivities(shareIntent, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolveInfo : resInfoList) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    activity.grantUriPermission(packageName, shareFileUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
            }
        }

        return shareIntent;
    }


    private boolean checkShareParam() {
        if (this.activity == null) {
            Log.e(TAG, "activity is null.");
            return false;
        }

        return true;
    }

    public static class Builder {
        private Context context;
        private String title;
        private String componentPackageName;
        private String componentClassName;
        private Uri shareFileUri;
        private String textContent;
        private int requestCode = -1;
        private boolean forcedUseSystemChooser = true;

        public Builder(Context context) {
            this.context = context;
        }


        /**
         * 设置标题
         *
         * @param title title
         * @return Builder
         */
        public Builder setTitle(@NonNull String title) {
            this.title = title;
            return this;
        }

        /**
         * 设置共享文件路径
         *
         * @param shareFileUri shareFileUri
         * @return Builder
         */
        public Builder setShareFileUri(Uri shareFileUri) {
            this.shareFileUri = shareFileUri;
            return this;
        }

        /**
         * 设置文本内容
         *
         * @param textContent textContent
         * @return Builder
         */
        public Builder setTextContent(String textContent) {
            this.textContent = textContent;
            return this;
        }

        /**
         * 将共享设置为组件
         *
         * @param componentPackageName componentPackageName
         * @param componentClassName   componentPackageName
         * @return Builder
         */
        public Builder setShareToComponent(String componentPackageName, String componentClassName) {
            this.componentPackageName = componentPackageName;
            this.componentClassName = componentClassName;
            return this;
        }

        /**
         * 设置为ActivityResult requestCode，默认值为-1
         *
         * @param requestCode requestCode
         * @return Builder
         */
        public Builder setOnActivityResult(int requestCode) {
            this.requestCode = requestCode;
            return this;
        }

        /**
         * 强制使用系统选择器来共享
         *
         * @param enable default is true
         * @return Builder
         */
        public Builder forcedUseSystemChooser(boolean enable) {
            this.forcedUseSystemChooser = enable;
            return this;
        }

        /**
         * build
         *
         * @return Share
         */
        public Share build() {
            return new Share(this);
        }

    }
}
