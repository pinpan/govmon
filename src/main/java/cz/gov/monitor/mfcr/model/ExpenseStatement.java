package cz.gov.monitor.mfcr.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "Expense")
@Table(name = "balance_statement")
//@DiscriminatorValue(value = "1")
public class ExpenseStatement extends BalanceStatement {

}
