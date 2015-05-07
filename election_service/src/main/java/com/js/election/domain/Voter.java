package com.js.election.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * Created by White Queen on 14.04.2015.
 */
@Entity
@Table(name = "VOTERS")
public class Voter {

    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "VOTERS_SEQ",
            allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @Column(name = "VOTER_ID")
    private Long id;

    @Column(name = "PASSPORT_ID")
    private String passportId;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "PATRONYMIC")
    private String patronymic;

    @ManyToOne
    @JsonBackReference
    private Region region;

    @Column(name = "PLACE")
    private String place;

    @Column(name = "VOTE")
    private Boolean vote;


    public Voter() {
    }

    public Voter(String passportId, String firstName, String lastName, String patronymic, Region region, String place) {
        this.passportId = passportId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.region = region;
        this.place = place;
    }

    public Voter(String passportId, String firstName, String lastName, String patronymic, Region region, String place, Boolean vote) {
        this.passportId = passportId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.region = region;
        this.place = place;
        this.vote = vote;
    }

    public Long getId() {
        return id;
    }

   // public void setId(Long id) {
   //     this.id = id;
   // }

    public String getPassportId() {
        return passportId;
    }

    public void setPassportId(String passportId) {
        this.passportId = passportId;
    }

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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Boolean getVote() {
        return vote;
    }

    public void setVote(Boolean vote) {
        this.vote = vote;
    }

    @Override
    public String toString() {
        return "Voter{" +
                "id=" + id +
                ", passportId='" + passportId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", region=" + region +
                ", place='" + place + '\'' +
                ", vote=" + getVote() +
                '}';
    }
}
