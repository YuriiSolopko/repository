package com.js.election.dao;

import com.js.election.domain.Region;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by julia on 15.04.2015.
 */

@Repository
public class RegionDaoImpl implements RegionDao {


    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Long create(Region region) {
        sessionFactory.getCurrentSession().save(region);
        return region.getId();
    }

    @Override
    public Region read(Long id) {
        return (Region) sessionFactory.getCurrentSession().get(Region.class, id);
    }

    @Override
    public void update(Region region) {
        sessionFactory.getCurrentSession().update(region);
    }

    @Override
    public void delete(Region region) {
        sessionFactory.getCurrentSession().delete(region);
    }

    @Override
    public List<Region> findAll() {
        return sessionFactory.getCurrentSession().createCriteria(Region.class).list();
    }

    @Override
    public Region findByName(String regionName) {
        return (Region) sessionFactory.getCurrentSession().createCriteria(Region.class)
                .add(Restrictions.eq("name", regionName))
                .uniqueResult();
    }


    @Override
    public Boolean isExist(String regionName) {
        Region region=findByName(regionName);

        if (region != null) {
            return true;
        } else {
            return false;
        }
    }


}
