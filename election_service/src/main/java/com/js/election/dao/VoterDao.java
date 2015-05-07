package com.js.election.dao;

import com.js.election.domain.Voter;

import java.util.List;

/**
 * Created by White Queen on 14.04.2015.
 */
public interface VoterDao {
    public boolean voterAuthentication(String passId);
    public boolean voterAuthentication(String passId, String firstName, String lastName, String patronymic);

    public Long create(Voter voter);
    public Voter read(Long id);
    public void update(Voter voter);
    public void delete(Voter voter);
    public List<Voter> findAll();

    public Voter getByPassId(String passId);
    public Boolean isExist(String passId);
}
