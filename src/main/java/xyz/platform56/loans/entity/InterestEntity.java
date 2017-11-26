package xyz.platform56.loans.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "interest")
@lombok.Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InterestEntity extends DomainObject {

    @Column(name = "loanRef", nullable = false, length = 100)
    private String loanRef;
}
