package xyz.platform56.loans.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "loans")
@lombok.Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoanEntity extends DomainObject {

    @Column(name = "loanRef", nullable = false, length = 100)
    private String loanRef;

    @Column(name = "status", nullable = false, length = 100)
    private String status;

    public enum Paths {
        loanRef
    }

}
