package com.js.election.service;

import com.js.election.domain.Candidate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Евгений_2 on 15.04.2015.
 */
@Transactional
public interface CandidateService {
    @Transactional(readOnly = true)
    public List<Candidate> getByRegion(String passId);

    @Transactional(readOnly = true)
    public boolean isExist(String firstName, String lastName);

    public boolean register(String firstName,
                            String lastName,
                            String patronymic,
                            String regionName);
}
