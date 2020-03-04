package cz.gov.monitor.mfcr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import cz.gov.monitor.mfcr.utils.OrganizationTypeDeserializer;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="organization")
public class Organization {

    /**
     * Internal DB ID
     */
    @javax.persistence.Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    /**
     *  IČ(O) - is a string because can have leading 0-es
     */
    @Column(name="ico")
    @JsonProperty("ic")
    private String ico;

    /**
     *
     */
    @Column(name="name")
    private String name;

    /**
     *
     */
    @Column(name="short_name")
    private String shortName;

    /**
     * nuts
     */
    @Column(name="nuts")
    private String nuts;

    private String county;       //:"Brno - m─¢sto",
    private String address;      //:"Dominik├ínsk├⌐ n├ím. 196/1, 601 67 Brno-st┼Öed",
    private String sector;       //:"13130 - M├¡stn├¡ vl├ídn├¡ instituce",

    private String creationDate; //:"1992-07-01",

    /**
     * Organization type
     */
    @Enumerated
    @Column(name="org_type")
    private OrganizationType type;

    @OneToMany(mappedBy="organization",
            targetEntity=FinancialReport.class,
            fetch=FetchType.EAGER)
    @JsonIgnore
    private List<FinancialReport> financialReports;

}
