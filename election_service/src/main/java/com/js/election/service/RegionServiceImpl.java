package com.js.election.service;

import com.js.election.dao.RegionDao;
import com.js.election.domain.Candidate;
import com.js.election.domain.Region;
import com.js.election.domain.Voter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

/**
 * Created by Евгений_2 on 17.04.2015.
 */
@Service
@Transactional
public class RegionServiceImpl implements RegionService {

    private RegionDao regionDao;

    public RegionServiceImpl() {}
    @Autowired
    public RegionServiceImpl(RegionDao regionDao) {
        this.regionDao = regionDao;
    }

    @Override
    public boolean register(String regionName) {
        if(isExist(regionName)){
            return false;
        }else {
            Region region = new Region(regionName, new HashSet<Voter>(), new HashSet<Candidate>());
            Long id = regionDao.create(region);
            return id != null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExist(String regionName) {
        return regionDao.isExist(regionName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Region> getAll() {
        return regionDao.findAll();
    }
}
