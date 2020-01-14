package com.raspberry.ota.model;

import java.util.Date;

public class ipaType {
    private Integer id;

    private String packageName;

    private String packageIcon;

    private String displayName;

    private String bundleId;

    private String linkPath;

    private Date createTime;

    public ipaType(Integer id, String packageName, String packageIcon, String displayName, String bundleId, String linkPath, Date createTime) {
        this.id = id;
        this.packageName = packageName;
        this.packageIcon = packageIcon;
        this.displayName = displayName;
        this.bundleId = bundleId;
        this.linkPath = linkPath;
        this.createTime = createTime;
    }

    public ipaType() {
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

    public String getPackageIcon() {
        return packageIcon;
    }

    public void setPackageIcon(String packageIcon) {
        this.packageIcon = packageIcon == null ? null : packageIcon.trim();
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName == null ? null : displayName.trim();
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId == null ? null : bundleId.trim();
    }

    public String getLinkPath() {
        return linkPath;
    }

    public void setLinkPath(String linkPath) {
        this.linkPath = linkPath == null ? null : linkPath.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}