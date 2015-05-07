package com.js.election.service;

import com.js.election.dao.CandidateDao;
import com.js.election.dao.RegionDao;
import com.js.election.dao.VoterDao;
import com.js.election.domain.Candidate;
import com.js.election.domain.Region;
import com.js.election.domain.Voter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Евгений_2 on 15.04.2015.
 */
@Service
@Transactional
public class CandidateServiceImpl implements CandidateService {

    private CandidateDao candidateDao;
    private VoterDao voterDao;
    private RegionDao regionDao;

    public CandidateServiceImpl() {}
    @Autowired
    public CandidateServiceImpl(VoterDao voterDao, CandidateDao candidateDao, RegionDao regionDao) {
        this.voterDao = voterDao;
        this.candidateDao = candidateDao;
        this.regionDao = regionDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Candidate> getByRegion(String passId) {
        Voter voter = voterDao.getByPassId(passId);
        List<Candidate> candidates = candidateDao.findByRegion(voter.getRegion());
        return candidates;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExist(String firstName, String lastName) { //String passId
        return candidateDao.isExist(firstName, lastName);
    }

    @Override
    public boolean register(String firstName, String lastName, String patronymic, String regionName) {
        if(isExist(firstName, lastName)){ // passId
          return false;
        }
        Region region = regionDao.findByName(regionName);
        if(region == null){
            return false;
        }else {
            Candidate candidate = new Candidate(firstName, lastName, patronymic, 0, region);
            Long id = candidateDao.create(candidate);
            return id != null;
        }
    }
}
