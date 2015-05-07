package com.js.election.controller;

import com.js.election.domain.Candidate;
import com.js.election.domain.Voter;
import com.js.election.service.CandidateService;
import com.js.election.service.VoterService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by White Queen on 14.04.2015.
 */
@Controller
@SessionAttributes("voter")
public class VoteController {

    public static final Logger log = Logger.getLogger(VoteController.class);

    @Autowired
    private VoterService voterService;

    @Autowired
    private CandidateService candidateService;

    public VoteController(){}

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String index() {
        log.info("/index controller");
        return "index";
    }

    //0 - авторизация не пройдена, 1 - можно голосовать, 2 - уже проголосовано, 3 - выборы уже закончились (не начались)
    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public @ResponseBody String auth(@RequestParam String firstName, @RequestParam String lastName,
                                     @RequestParam String patronymic, @RequestParam String place,
                                     @RequestParam String passportId, Model model) {
        log.info("/auth controller");
        if ( voterService.isCurrentDate() ) {
            if (voterService.voterAuthentication(passportId, firstName, lastName, patronymic)) {
                Voter voter = voterService.getByPass(passportId);
                model.addAttribute("voter", voter);
                if (voterService.isVoted(voter) ) {
                    return "2";
                } else {
                    return "1";
                }
            } else {
                return "0";
            }
        } else {
            return "3";
        }
    }

    @RequestMapping(value = "/getCandidates", method = RequestMethod.GET)
    public @ResponseBody List<Candidate> getCandidates(@ModelAttribute Voter voter) {
        log.info("/getCandidates controller");
        if (voter != null && voter.getPassportId() != null) {
            List<Candidate> list = candidateService.getByRegion(voter.getPassportId());
            return list;
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/vote", method = RequestMethod.POST)
    public @ResponseBody String vote(@ModelAttribute Voter voter, @RequestParam Long candidateId, Model model) {
        log.info("/vote controller");
        if (voter != null && voter.getPassportId() != null) {
            model.addAttribute("voter", new Voter());
            if (voterService.vote(voter.getId(), candidateId)) {
                return "true";
            }
            return "false";
        }
        return "false";
    }
}
