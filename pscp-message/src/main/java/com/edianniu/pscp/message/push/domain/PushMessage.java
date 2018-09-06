/**
 *
 */
package com.edianniu.pscp.message.push.domain;

import java.io.Serializable;

/**
 * @author cyl
 */
public class PushMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long uid;
    private String title;
    private String content;
    private Integer type;
    private String category;
    private String pushTime;

    private String logoUrl;
    private String logo = "logo.png";
    private String url;
    private boolean isRing = true;//收到通知是否响铃：true响铃，false不响铃。默认响铃。
    private boolean isVibrate = true;//收到通知是否振动：true振动，false不振动。默认振动。
    private boolean isClearable = true;//通知是否可清除： true可清除，false不可清除。默认可清除。
    private int duration;//收到消息的展示时间
    private int showType;//0默认(通知栏)，1:弹窗
    private String ext;//扩展信息

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPushTime() {
        return pushTime;
    }

    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isRing() {
        return isRing;
    }

    public void setRing(boolean isRing) {
        this.isRing = isRing;
    }

    public boolean isVibrate() {
        return isVibrate;
    }

    public void setVibrate(boolean isVibrate) {
        this.isVibrate = isVibrate;
    }

    public boolean isClearable() {
        return isClearable;
    }

    public void setClearable(boolean isClearable) {
        this.isClearable = isClearable;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

}
