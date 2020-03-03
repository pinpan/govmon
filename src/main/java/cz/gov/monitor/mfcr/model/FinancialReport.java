package cz.gov.monitor.mfcr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="financial_report")
@JsonPropertyOrder({"organization", "period", "expenses", "revenues"})
public class FinancialReport {

    @ManyToOne(optional=false)
    @JoinColumn(name="organization_id",referencedColumnName="id")
    private Organization organization;

    @Column(name="period")
    private String period;

    @javax.persistence.Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @OneToMany(mappedBy="report",
               targetEntity= BalanceStatement.class,
               fetch=FetchType.EAGER)
    private List<BalanceStatement> expenses;

    @OneToMany(mappedBy="report",
               targetEntity= BalanceStatement.class,
               fetch=FetchType.EAGER)
    private List<BalanceStatement> revenues;
}
