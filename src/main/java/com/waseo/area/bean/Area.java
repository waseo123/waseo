package com.waseo.area.bean;

import java.io.Serializable;

/**
 * 区域表
 */
@SuppressWarnings("serial")
public class Area implements Serializable {

    private static final long serialVersionUID = -526324944915280489L;

    private String areaId;
    private String displayName;
    private String parentAreaId;

    @Override
    public String toString() {
        return "area{" +
                "areaId='" + areaId + '\'' +
                ", displayName='" + displayName + '\'' +
                ", parentAreaId='" + parentAreaId + '\'' +
                '}';
    }

    public Area() {
    }

    public Area(String areaId, String displayName, String parentAreaId) {
        this.areaId = areaId;
        this.displayName = displayName;
        this.parentAreaId = parentAreaId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getParentAreaId() {
        return parentAreaId;
    }

    public void setParentAreaId(String parentAreaId) {
        this.parentAreaId = parentAreaId;
    }
}
