package com.raspberry.ota.model;

import java.util.Date;

public class ipaDetail {
    private Integer id;

    private String packageName;

    private String packageType;

    private String packageIcon;

    private String packageVersion;

    private String displayName;

    private String displayTime;

    private String displayLinkurl;

    private String displayRemark;

    private Date createTime;

    public ipaDetail(Integer id, String packageName, String packageType, String packageIcon, String packageVersion, String displayName, String displayTime, String displayLinkurl, String displayRemark, Date createTime) {
        this.id = id;
        this.packageName = packageName;
        this.packageType = packageType;
        this.packageIcon = packageIcon;
        this.packageVersion = packageVersion;
        this.displayName = displayName;
        this.displayTime = displayTime;
        this.displayLinkurl = displayLinkurl;
        this.displayRemark = displayRemark;
        this.createTime = createTime;
    }

    public ipaDetail() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName == null ? null : packageName.trim();
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType == null ? null : packageType.trim();
    }

    public String getPackageIcon() {
        return packageIcon;
    }

    public void setPackageIcon(String packageIcon) {
        this.packageIcon = packageIcon == null ? null : packageIcon.trim();
    }

    public String getPackageVersion() {
        return packageVersion;
    }

    public void setPackageVersion(String packageVersion) {
        this.packageVersion = packageVersion == null ? null : packageVersion.trim();
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName == null ? null : displayName.trim();
    }

    public String getDisplayTime() {
        return displayTime;
    }

    public void setDisplayTime(String displayTime) {
        this.displayTime = displayTime == null ? null : displayTime.trim();
    }

    public String getDisplayLinkurl() {
        return displayLinkurl;
    }

    public void setDisplayLinkurl(String displayLinkurl) {
        this.displayLinkurl = displayLinkurl == null ? null : displayLinkurl.trim();
    }

    public String getDisplayRemark() {
        return displayRemark;
    }

    public void setDisplayRemark(String displayRemark) {
        this.displayRemark = displayRemark == null ? null : displayRemark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}