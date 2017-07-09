package com.smetnertest.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * An entity class that represents a user contact.
 */

@Data
@Entity
@Table(name = "contacts")
public class Contact extends AbstractModel {

    @Column(name = "phone_number", nullable = false)
    private String number;
    @Column(name = "type_number")
    private String type;
    private String comment;

}
