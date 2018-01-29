package com.smetnertest.model;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * An entity class that represents a user.
 */

@Data
@Entity
@Table(name = "users")
public class User extends AbstractModel {
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "middle_name", nullable = true)
    private String middleName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id")
    private Contact contact;

}
