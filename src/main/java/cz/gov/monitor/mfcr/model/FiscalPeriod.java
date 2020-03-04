package cz.gov.monitor.mfcr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FiscalPeriod {

    /*
      "year":2029,
      "loadID":2912,
      "month":"prosinec",
      "isQuarter":true,
      "isYear":true,
      "balanceSheetProfitLoss":false,
      "cashFlowEquityCapital":false,
      "finM":false,
      "finU":false,
      "statementsAfterCorrections":false,
      "clearanceOfAccounts":false,
      "finSPO":false,
      "monitoring":false,
      "budgetApproved":false,
      "budgetPreparation":false,
      "ucjed":false,
      "transactionData":false,
      "label":"12. 2029"
     */

    private Integer year;                       // 2029,
    private Integer  loadID;                    // 2912,
    private String  month;                      // "prosinec",
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
