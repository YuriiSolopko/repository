package com.js.election.service;

import com.js.election.domain.Voter;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by White Queen on 14.04.2015.
 */
@Transactional
public interface VoterService {

    @Transactional(readOnly = true)
    public boolean voterAuthentication(String passId);

    @Transactional(readOnly = true)
    public boolean voterAuthentication(String passId, String firstName, String lastName, String patronymic);

    @Transactional(readOnly = true)
    public Voter getByPass(String passId);

    public boolean isCurrentDate();

    public boolean vote(Long voterId, Long candidateId);

    @Transactional(readOnly = true)
    public boolean isVoted(Voter voter);

    @Transactional(readOnly = true)
    public boolean isExist(String passportId);

    public boolean register(String passportId,
                            String firstName,
                            String lastName,
                            String patronymic,
                            String regionName,
                            String placeName);
}
