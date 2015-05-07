package com.js.election.dao;

import com.js.election.domain.Voter;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by White Queen on 14.04.2015.
 */
@Repository
public class VoterDaoImpl implements VoterDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean voterAuthentication(String passId) {
        Voter voter = (Voter) sessionFactory.getCurrentSession()
                .createCriteria(Voter.class)
                .add(Restrictions.eq("passportId", passId))
                .uniqueResult();
        if (voter == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean voterAuthentication(String passId,String firstName,String lastName, String patronymic) {
        Voter voter = (Voter) sessionFactory.getCurrentSession()
                .createCriteria(Voter.class)
                .add(Restrictions.eq("passportId", passId))
                .add(Restrictions.eq("firstName", firstName))
                .add(Restrictions.eq("lastName", lastName))
                .add(Restrictions.eq("patronymic", patronymic))
                .uniqueResult();
        if (voter == null) {
            return false;
        } else {
            return true;
        }
    }


    @Override
    public Long create(Voter voter) {
        sessionFactory.getCurrentSession().save(voter);
        return voter.getId();
    }

    @Override
    public Voter read(Long id) {
        return (Voter) sessionFactory.getCurrentSession().get(Voter.class, id);
    }

    @Override
    public void update(Voter voter) {
        sessionFactory.getCurrentSession().update(voter);
    }

    @Override
    public void delete(Voter voter) {
        sessionFactory.getCurrentSession().delete(voter);
    }

    @Override
    public List<Voter> findAll() {
        return sessionFactory.getCurrentSession().createCriteria(Voter.class).list();
    }

    @Override
    public Voter getByPassId(String passId) {
        return (Voter) sessionFactory.getCurrentSession().createCriteria(Voter.class)
                .add(Restrictions.eq("passportId", passId))
                .uniqueResult();
    }

    @Override
    public Boolean isExist(String passId) {
        Voter voter=getByPassId(passId);

        if (voter != null) {
            return true;
        } else {
            return false;
        }
    }


}
