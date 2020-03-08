package cz.gov.monitor.mfcr.model;

import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "Expense")
//@Table(name = "balance_statement")
//@DiscriminatorOptions(force = true)
@DiscriminatorValue(value = "1")
public class ExpenseStatement extends BalanceStatement {

    @Column(name="dummy_integer")
    private Integer dummyInteger = new Integer(0);
}
