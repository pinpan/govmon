package cz.gov.monitor.mfcr.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import cz.gov.monitor.mfcr.utils.OrganizationTypeDeserializer;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="organization_type")
//@AllArgsConstructor
//@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
//@JsonDeserialize(using = OrganizationTypeDeserializer.class)
public enum OrganizationType {
    DISTRICT(4, "Obec", "LOCAL"), MUNICIPLITY(1, "Urad","LOCAL"), UNKNOWN(-1, "Unknown", "LOCAL");

    OrganizationType(int id, String label, String area) {
        this.id = id;
        this.label = label;
        this.area = area;
    }


    @JsonCreator
    public static OrganizationType fromValue(@JsonProperty("id") Integer id) {
        if (id == null) {
            return null;
        }

        Integer theId = Integer.valueOf(id);
        switch (theId) {
            case 1: return MUNICIPLITY;
            case 4: return DISTRICT;
            default: return UNKNOWN;
        }
    }

   /* @JsonCreator
    public static OrganizationType fromJson(@JsonProperty("type") String type) {
        if (type == null) {
            return null;
        }
        switch (type) {
            case "1": return MUNICIPLITY;
            case "4": return DISTRICT;
            default: return UNKNOWN;
        }

        //valueOf()
    }*/


    @javax.persistence.Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;

    @Column(name="type")
    String label;

    @Column(name="area")
    String area;
/*
    public void setId(Integer id) {
        this.id = id;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setLabel(String label) {
        this.label = label;
    }*/
}
