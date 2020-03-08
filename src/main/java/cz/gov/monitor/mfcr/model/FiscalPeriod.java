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

    @Column(name="fiscal_year")
    private Integer fiscalYear;                // 2029,

    @Column(name="load_id")
    private Integer loadID;                     // 2912,

    @Column(name="fiscal_month")
    private String  fiscalMonth;               // "prosinec",

    @Column(name="is_quarter")
    private boolean isQuarter;                  // true,

    @Column(name="is_year")
    private boolean isYear;                     // true,

    @Column(name="balance_sheet_profit_loss")
    private boolean balanceSheetProfitLoss;     // false,

    @Column(name="cash_flow_equity_capital")
    private boolean cashFlowEquityCapital;      // false,

    @Column(name="fin_m")
    private boolean finM;                       // false,

    @Column(name="fin_u")
    private boolean finU;                       // false,

    @Column(name="statements_after_corrections")
    private boolean statementsAfterCorrections; // false,

    @Column(name="clearance_of_accounts")
    private boolean clearanceOfAccounts;        // false,

    @Column(name="fin_spo")
    private boolean finSPO;                     // false,

    @Column(name="monitoring")
    private boolean monitoring;                 // false,

    @Column(name="budget_approved")
    private boolean budgetApproved;             // false,

    @Column(name="budget_preparation")
    private boolean budgetPreparation;          // false,

    @Column(name="ucjed")
    private boolean ucjed;                      // false,

    @Column(name="transaction_data")
    private boolean transactionData;            // false,

    @Column(name="label")
    private String label;                       // "12. 2029"
}
