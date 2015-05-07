package com.js.election.domain;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;


/**
 * Created by julia on 15.04.2015.
 */

@Entity
@Table(name = "REGION")
public class Region {
    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "REGION_SEQ",
            allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @Column(name = "REGION_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "region")
    @Fetch(FetchMode.SELECT)
    @JsonManagedReference
    private Set<Voter> voterSet;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "region")
    @Fetch(FetchMode.SELECT)
    @JsonManagedReference
    private Set<Candidate> candidateSet;

    public Region() {
    }

    public Region(String name) {
        this.name = name;
   }

    public Region(String name, Set<Voter> voterSet, Set<Candidate> candidateSet) {
        this.name = name;
        this.voterSet = voterSet;
        this.candidateSet = candidateSet;
    }

    public Long getId() {
        return id;
    }

   // public void setId(Long id) {
   //     this.id = id;
   // }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Voter> getVoterSet() {
        return voterSet;
    }

    public void setVoterSet(Set<Voter> voterSet) {
        this.voterSet = voterSet;
    }

    public Set<Candidate> getCandidateSet() {
        return candidateSet;
    }

    public void setCandidateSet(Set<Candidate> candidateSet) {
        this.candidateSet = candidateSet;
    }

    public boolean addVoter(Voter voter){
        return voterSet.add(voter);
    }

    public boolean addCandidate(Candidate candidate){
        return candidateSet.add(candidate);
    }

    @Override
    public String toString() {
        return "Region{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
