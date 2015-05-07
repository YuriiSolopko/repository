package com.js.election.controller;

import com.js.election.domain.Region;
import com.js.election.service.CandidateService;
import com.js.election.service.RegionService;
import com.js.election.service.VoterService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Jura on 17.04.2015.
 */
@Controller
public class RegistrationController {

    public static final Logger log = Logger.getLogger(RegistrationController.class);

    @Autowired
    private VoterService voterService;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private RegionService regionService;

    @RequestMapping(value = "/registration.html", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String registration() {
        log.info("/registration.html controller");
        return "registration";
    }

    @RequestMapping(value = "/getRegions", method = RequestMethod.GET)
    public @ResponseBody List<Region> getRegions() {
        log.info("/getRegions controller");
        List<Region> list = regionService.getAll();
//        log.info(list.toString());

        return list;
    }

    @RequestMapping(value = "/registerVoter", method = RequestMethod.POST)
    public @ResponseBody String registerVoter(@RequestParam String vFirstName, @RequestParam String vLastName,
                                @RequestParam String vPatronymic, @RequestParam String vPlace,
                                @RequestParam String vPassportId, @RequestParam String vRegion) {
        log.info("/registerVoter controller");
        log.info(vRegion);
        if (voterService.register(vPassportId, vFirstName, vLastName, vPatronymic, vRegion, vPlace)) {
            return "Registration successful";
        }
        return "Registration error";
    }

    @RequestMapping(value = "/registerCandidate", method = RequestMethod.POST)
    public @ResponseBody String registerCandidate(@RequestParam String cFirstName, @RequestParam String cLastName,
                                @RequestParam String cPatronymic, @RequestParam String cRegion) {
        log.info("/registerCandidate controller");
        log.info(cRegion);
        if (candidateService.register(cFirstName, cLastName, cPatronymic, cRegion)) {
            return "Registration successful";
        }
        return "Registration error";
    }

    @RequestMapping(value = "/registerRegion", method = RequestMethod.POST)
    public @ResponseBody String registerRegion(@RequestParam String regionName) {
        log.info("/registerRegion controller");
        if (regionService.register(regionName)) {
            return "Registration successful";
        }
        return "Registration error";
    }

}
