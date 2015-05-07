package com.js.election.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * Created by White Queen on 14.04.2015.
 */
@Entity
@Table(name = "CANDIDATES")
public class Candidate {

    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "CANDIDATES_SEQ",
            allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @Column(name = "CANDIDATE_ID")
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "PATRONYMIC")
    private String patronymic;

    @Column(name = "VOTES")
    private Integer votes;

    @ManyToOne
//    @JsonIgnore(true)
    @JsonBackReference
    private Region region;

    public Candidate() {

    }

    public Candidate(String firstName, String lastName, String patronymic, Region region) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.region = region;
    }

    public Candidate(String firstName, String lastName, String patronymic, Integer votes, Region region) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.votes = votes;
        this.region = region;
    }

    public Long getId() {
        return id;
    }

   // public void setId(Long id) {
   //     this.id = id;
   // }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", region=" + region +
                '}';
    }

}
