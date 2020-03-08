package cz.gov.monitor.mfcr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonIgnore
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

    /**
     *  county
     */
    @Column(name="county")
    private String county;       //:"Brno - m─¢sto",

    /**
     * address
     */
    @Column(name="address")
    private String address;      //:"Dominik├ínsk├⌐ n├ím. 196/1, 601 67 Brno-st┼Öed",

    /**
     * sector
     */
    @Column(name="sector")
    private String sector;       //:"13130 - M├¡stn├¡ vl├ídn├¡ instituce",

    /**
     * creationDate
     */
    @Column(name="creation_date")
    private String creationDate; //:"1992-07-01",

    /**
     * Organization type
     */
    @Enumerated
    @Column(name="org_type")
    private OrganizationType type;

    @OneToMany(mappedBy="organization",
            targetEntity=FinancialReport.class,
            fetch=FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private List<FinancialReport> financialReports;

    public void setFinancialReports(List<FinancialReport> reports) {
        financialReports = reports;
        _financialReports = reports;
    }

    @Transient
    private List<FinancialReport> _financialReports;

    List<FinancialReport> get_FinancialReports() {
        return _financialReports;
    }
}
