package com.js.election.dao;

import com.js.election.domain.Candidate;
import com.js.election.domain.Region;

import java.util.List;

/**
 * Created by julia on 15.04.2015.
 */
public interface CandidateDao {
    public Long create(Candidate candidate);
    public Candidate read(Long id);
    public void update(Candidate candidate);
    public void delete(Candidate candidate);
    public List<Candidate> findAll();

    public List<Candidate> findByRegion(Region region);
    public Candidate getByName(String firstName, String lastName);
    public Boolean isExist(String firstName, String lastName);
}
