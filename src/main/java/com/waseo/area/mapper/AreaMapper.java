package com.waseo.area.mapper;

import com.waseo.area.bean.Area;

import java.util.List;

/**
 * 区域类mapper接口
 */
public interface AreaMapper {

    public Area get(String areaId);

    public List<Area> list();

    public Area create(Area area);

    public Area update(Area area);

    public void delete(String areaId);

}
