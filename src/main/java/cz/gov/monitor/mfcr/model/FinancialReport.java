package cz.gov.monitor.mfcr.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="financial_report")
public class FinancialReport {

    @javax.persistence.Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false)
    @JoinColumn(name="organization_id",referencedColumnName="id")
    private Organization organization;

    @Column(name="period")
    private Integer period;

    @OneToMany(mappedBy="report",
               targetEntity=Expense.class,
               fetch=FetchType.EAGER)
    private List<Expense> expenses;
}
