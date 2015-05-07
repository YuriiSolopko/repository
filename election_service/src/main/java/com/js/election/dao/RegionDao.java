package com.js.election.dao;

import com.js.election.domain.Region;

import java.util.List;

/**
 * Created by julia on 15.04.2015.
 */
public interface RegionDao {
    public Long create(Region region);
    public Region read(Long id);
    public void update(Region region);
    public void delete(Region region);
    public List<Region> findAll();

    public Boolean isExist(String regionName);
    public Region findByName(String regionName);

}
