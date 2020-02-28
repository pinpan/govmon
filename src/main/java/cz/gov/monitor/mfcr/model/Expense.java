package cz.gov.monitor.mfcr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="expense")
public class Expense {
    /*
    "expenses":[
    {
        "name":"N├üKLADY CELKEM",
        "code":"A.",
        "mainActivity":8.6031396783E9,
        "economicActivity":1.00475413709E9,
        "mainActivityPrev":1.232743269034E10,
        "economicActivityPrev":1.78342113717E9,
        "synAccount":"-",
        "lineNumber":0
    }
   */

    /**
     * DB Id
     */
    @javax.persistence.Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name; //":"N├üKLADY CELKEM",

    @Column(name="code")
    private String code; //":"A.",

    @Column(name="main_activity")
    private String mainActivity; //":8.6031396783E9,

    @Column(name="economic_activity")
    private String economicActivity; //":1.00475413709E9,

    @Column(name="main_activity_prev")
    private String mainActivityPrev; //":1.232743269034E10,

    @Column(name="economic_activity_prev")
    private String economicActivityPrev; //":1.78342113717E9,

    @Column(name="syn_account")
    private String synAccount; //":"-",

    @Column(name="line_number")
    private String lineNumber; //":0

    @ManyToOne(optional=false)
    @JoinColumn(name="report_id",referencedColumnName="id")
    private FinancialReport report;
}
