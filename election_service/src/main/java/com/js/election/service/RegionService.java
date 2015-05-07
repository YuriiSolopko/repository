package com.js.election.service;

import com.js.election.domain.Region;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Евгений_2 on 17.04.2015.
 */
@Transactional
public interface RegionService {

    public boolean register(String name);

    @Transactional(readOnly = true)
    public boolean isExist(String name);

    @Transactional(readOnly = true)
    public List<Region> getAll();

}
