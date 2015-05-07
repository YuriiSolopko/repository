package com.js.election.service;

import com.js.election.dao.CandidateDao;
import com.js.election.dao.RegionDao;
import com.js.election.dao.VoterDao;
import com.js.election.domain.Candidate;
import com.js.election.domain.Region;
import com.js.election.domain.Voter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by White Queen on 14.04.2015.
 */
@Service
@Transactional
public class VoterServiceImpl implements VoterService {


    private VoterDao voterDao;
    private CandidateDao candidateDao;
    private RegionDao regionDao;
    @Value("#{${electionDate}}")
    private String electionDateString;
    private Calendar electionDate;

    public VoterServiceImpl(){}
    @Autowired
    public VoterServiceImpl(VoterDao voterDao, CandidateDao candidateDao, RegionDao regionDao){
        this.voterDao = voterDao;
        this.candidateDao = candidateDao;
        this.regionDao = regionDao;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean voterAuthentication(String passId) {
        return voterDao.voterAuthentication(passId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean voterAuthentication(String passId, String firstName, String lastName, String patronymic) {
        return voterDao.voterAuthentication(passId, firstName, lastName, patronymic);
    }

    @Override
    public Voter getByPass(String passId) {
        return voterDao.getByPassId(passId);
    }

    @Override
    public boolean isCurrentDate() {
        if(electionDate == null) {
            String[] parts = electionDateString.split("\\.");
            int year = Integer.parseInt(parts[2]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[0]);
            electionDate = new GregorianCalendar(year, month - 1, day, 23, 59, 59);
        }
        return electionDate.compareTo(Calendar.getInstance()) > 0;
    }

    @Override
    public boolean vote(Long voterId, Long candidateId) {
        Voter voter = voterDao.read(voterId);
        if(voter == null ||voter.getVote() || !isCurrentDate()) {
            return false;
        }else {
            Candidate candidate = candidateDao.read(candidateId);
            if(candidate.getVotes() == null){
                candidate.setVotes(1);
            }else {
                candidate.setVotes(candidate.getVotes() + 1);
            }
            voter.setVote(true);
            voterDao.update(voter);
            candidateDao.update(candidate);
            return true;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isVoted(Voter voter) {
        if(voter == null){
            return false;
        }
        return (voter.getVote() == null) ? false : voter.getVote();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExist(String passportId) {
        return voterDao.isExist(passportId);
    }

    @Override
    public boolean register(String passportId,
                            String firstName,
                            String lastName,
                            String patronymic,
                            String regionName,
                            String placeName) {
        if(isExist(passportId)){
            return false;
        }
        Region region = regionDao.findByName(regionName);
        if (region == null){
            return false;
        }
        else {
            Voter voter = new Voter(passportId, firstName , lastName, patronymic, region, placeName, false);
            Long id = voterDao.create(voter);
            return id != null;
        }
    }

    public Calendar getElectionDate() {
        return electionDate;
    }
}
