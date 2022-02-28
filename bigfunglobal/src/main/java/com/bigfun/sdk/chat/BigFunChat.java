//package com.bigfun.sdk.chat;
//
//import android.app.Activity;
//import android.content.Context;
//import android.util.ArrayMap;
//
//import androidx.annotation.Keep;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import zendesk.chat.Chat;
//import zendesk.chat.ChatConfiguration;
//import zendesk.chat.ChatEngine;
//import zendesk.chat.ProfileProvider;
//import zendesk.chat.Providers;
//import zendesk.chat.VisitorInfo;
//import zendesk.messaging.MessagingActivity;
//
//@Keep
//public class BigFunChat {
//    private static final String ACCOUNT_KEY = "oYHRItbGeLakwycsAUxG8R0ZmooqeuIa";
//    private static Context mContext;
//    private List<String> mTags = new ArrayList<>();
//
//    private BigFunChat() {
//
//    }
//
//    /**
//     * 初始化
//     *
//     * @param context
//     */
//    @Keep
//    public static void init(Context context) {
//        mContext = context;
//        Chat.INSTANCE.init(context, ACCOUNT_KEY);
//    }
//
//    @Keep
//    private static class InstanceHolder {
//        private static BigFunChat INSTANCE = new BigFunChat();
//    }
//
//    @Keep
//    public static BigFunChat getInstance() {
//        return InstanceHolder.INSTANCE;
//    }
//
//    @Keep
//    public void chat(Activity activity, ArrayMap<String, String> params) {
//        if (!checkParams(params)) {
//            throw new IllegalArgumentException("BigFunChat--gameUserId and appId is request");
//        }
//        ProfileProvider profileProvider = null;
//        Providers providers = Chat.INSTANCE.providers();
//        if (providers != null) {
//            profileProvider = providers.profileProvider();
//        }
//        if (profileProvider != null) {
//            //添加Tags
//            mTags.add(getParams("appId", params));
//            mTags.add(getParams("appName", params));
//            profileProvider.addVisitorTags(mTags, null);
//            //添加用户信息
//            VisitorInfo visitorInfo = VisitorInfo.builder()
//                    .withName(getParams("name", params) + "--" + getParams("gameUserId", params))
//                    .withEmail(getParams("email", params))
//                    .withPhoneNumber(getParams("phone", params)).build();
//            profileProvider.setVisitorInfo(visitorInfo, null);
//        }
//        ChatConfiguration chatConfiguration = ChatConfiguration.builder()
//                .withPreChatFormEnabled(false)
//                .build();
//        MessagingActivity.builder().withEngines(ChatEngine.engine())
//                .show(activity, chatConfiguration);
//    }
//
//    private String getParams(String key, ArrayMap<String, String> params) {
//        String result = "";
//        if (params.containsKey(key)) {
//            result = params.get(key);
//        }
//        return result;
//    }
//
//    private boolean checkParams(ArrayMap<String, String> params) {
//        return params.containsKey("gameUserId") && params.containsKey("appId");
//    }
//}
