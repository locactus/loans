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
public class CustomerEntity extends DomainObject {

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;


    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "primary_phone", length = 100)
    private String primaryPhone;

    @Column(name = "email",  length = 100)
    private String email;

    @JoinColumn(name = "primary_address_id",
            referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    private AddressEntity primaryAddress;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "identification_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "identification_id_fk")
    )
    private List<IdentificationEntity> identifications;



    public enum Paths {
        name
    }

    public boolean equals(Object o) {
        return ((this == o) || ((o != null) &&
                (getClass() == o.getClass()) &&
                firstName.equals(((CustomerEntity) o).firstName)) &&
                lastName.equals(((CustomerEntity) o).lastName)

        );
    }

}
