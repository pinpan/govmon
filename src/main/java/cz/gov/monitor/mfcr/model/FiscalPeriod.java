package cz.gov.monitor.mfcr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="fiscal_period")
public class FiscalPeriod {


    /**
     * Internal DB ID
     */
    @javax.persistence.Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private Integer fiscal_year;                // 2029,
    private Integer loadID;                     // 2912,
    private String  fiscal_month;               // "prosinec",
    private boolean isQuarter;                  // true,
    private boolean isYear;                     // true,
    private boolean balanceSheetProfitLoss;     // false,
    private boolean cashFlowEquityCapital;      // false,
    private boolean finM;                       // false,
    private boolean finU;                       // false,
    private boolean statementsAfterCorrections; // false,
    private boolean clearanceOfAccounts;        // false,
    private boolean finSPO;                     // false,
    private boolean monitoring;                 // false,
    private boolean budgetApproved;             // false,
    private boolean budgetPreparation;          // false,
    private boolean ucjed;                      // false,
    private boolean transactionData;            // false,
    private String label;                       // "12. 2029"
}
