package cz.gov.monitor.mfcr.model;

import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "Revenue")
//@Table(name = "balance_statement")
@DiscriminatorValue("2")
public class RevenueStatement extends BalanceStatement {

    @Column(name="dummy_string")
    private String dummyString = "dummy";
}
