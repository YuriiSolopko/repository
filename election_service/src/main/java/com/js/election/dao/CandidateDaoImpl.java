package com.js.election.dao;

import com.js.election.domain.Candidate;
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
public class CandidateDaoImpl implements CandidateDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Long create(Candidate candidate) {
        sessionFactory.getCurrentSession().save(candidate);
        return candidate.getId();
    }

    @Override
    public Candidate read(Long id) {
        return (Candidate) sessionFactory.getCurrentSession().get(Candidate.class, id);
    }

    @Override
    public void update(Candidate candidate) {
        sessionFactory.getCurrentSession().update(candidate);
    }

    @Override
    public void delete(Candidate candidate) {
        sessionFactory.getCurrentSession().delete(candidate);
    }

    @Override
    public List<Candidate> findAll() {
        return sessionFactory.getCurrentSession().createCriteria(Candidate.class).list();
    }

    @Override
    public List<Candidate> findByRegion(Region region) {
        return sessionFactory.getCurrentSession()
                .createCriteria(Candidate.class).add(Restrictions.eq("region", region))
                .list();
    }

    @Override
    public Candidate getByName(String firstName, String lastName) {
        return (Candidate) sessionFactory.getCurrentSession().createCriteria(Candidate.class)
                .add(Restrictions.eq("firstName", firstName))
                .add(Restrictions.eq("lastName", lastName))
                .uniqueResult();
    }

    @Override
    public Boolean isExist(String firstName, String lastName) {
        Candidate candidate=getByName(firstName, lastName);

        if (candidate != null) {
            return true;
        } else {
            return false;
        }
    }


}
