package cz.gov.monitor.mfcr.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "Revenue")
@Table(name = "balance_statement")
//@DiscriminatorValue("2")
public class RevenueStatement extends BalanceStatement {
    
}
