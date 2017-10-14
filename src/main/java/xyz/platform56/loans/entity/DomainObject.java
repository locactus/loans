package xyz.platform56.loans.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Data
public class DomainObject implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;

    @Version
    @Column(name = "version")
    private Long entityVersion;

    @Column(name = "created_on", nullable = false)
    private Date createdOn = new Date();

    @Column(name = "updated_on",
            nullable = true)
    private Date updatedOn = new Date();

    @PreUpdate
    public void onPreUpdate() {
        updatedOn = new Date();
    }

    @PrePersist
    void preInsert() {
        if (this.createdOn == null) {
            this.createdOn = new Date();
        }
        if (this.updatedOn == null) {
            this.updatedOn = new Date();
        }

    }

    public enum DomainPaths {
        createdOn, updatedOn
    }
}
