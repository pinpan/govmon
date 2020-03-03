package cz.gov.monitor.mfcr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
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
     * Id according to monitor.mfcr.cz
     */
    @Column(name="mfcr_id")
    private String mfcrId;

    /**
     *  IČ(O) - is a string because can have leading 0-es
     */
    @Column(name="ico")
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
     * Organization type
     */
    @Enumerated
    @Column(name="org_type")
    private OrganizationType type;

    @OneToMany(mappedBy="organization",
            targetEntity=FinancialReport.class,
            fetch=FetchType.EAGER)
    private List<FinancialReport> financialReports;

}
