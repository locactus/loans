package xyz.platform56.loans.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "principal")
@lombok.Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PrincipalEntity extends DomainObject {

    @Column(name = "street", length = 100)
    private String street;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "state", length = 100)
    private String state;

    @Column(name = "postcode", length = 100)
    private String postcode;

    @Column(name = "region", length = 100)
    private String region;

    @Column(name = "country", length = 100)
    private String country;


    @OneToOne(fetch = FetchType.LAZY, mappedBy = "primaryAddress")
    private LoanEntity customer;
}
