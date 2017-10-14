package xyz.platform56.loans.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "id_images")
@lombok.Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class IdImagesEntity extends DomainObject {

    @Column(name = "url" )
    private String url;

    @Column(name = "description" )
    private String description;


    @JoinColumn(name = "identification_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "id_images_identification_id_pk")
    )
    @ManyToOne
    private IdentificationEntity identification;
}
