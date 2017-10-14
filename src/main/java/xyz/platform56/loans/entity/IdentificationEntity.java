package xyz.platform56.loans.entity;

        import lombok.AllArgsConstructor;
        import lombok.Data;
        import lombok.NoArgsConstructor;

        import javax.persistence.*;
        import java.util.List;

@Entity
@Table(name = "identifications")
@lombok.Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class IdentificationEntity extends DomainObject {

    @Column(name = "type", nullable = false, length = 100)
    private String type;

    @Column(name = "id_number", nullable = false, length = 100)
    private String idNumber;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_images_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "id_images_id_fk")
    )
    private List<IdImagesEntity> images;

    @JoinColumn(name = "customer_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "identification_customer_id_pk")
    )
    @ManyToOne
    private CustomerEntity customer;
}
