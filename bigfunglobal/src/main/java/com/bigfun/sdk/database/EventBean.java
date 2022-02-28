package com.bigfun.sdk.database;

import androidx.annotation.Keep;

@Keep
public class EventBean {
    private String actionType;
    private String actionContent;

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getActionContent() {
        return actionContent;
    }

    public void setActionContent(String actionContent) {
        this.actionContent = actionContent;
    }
}
