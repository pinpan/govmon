package cz.gov.monitor.mfcr.model;

import javax.persistence.*;

@Entity
@Table(name="organization_type")
public enum OrganizationType {
    Obec(4, "Obec", "LOCAL"), Urad(1, "Urad","LOCAL");

    OrganizationType(int id, String label, String area) {
        this.id = id;
        this.label = label;
        this.area = area;
    }

    @javax.persistence.Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;

    @Column(name="type")
    String label;

    @Column(name="area")
    String area;
}
