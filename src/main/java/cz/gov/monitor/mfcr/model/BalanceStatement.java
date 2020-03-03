package cz.gov.monitor.mfcr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="balance_statement  ")
public class BalanceStatement {
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
    .....
  }

  "revenues":[
    {
      "name":"V├¥NOSY CELKEM",
      "code":"B.",
      "mainActivity":1.160883985309E10,
      "economicActivity":1.75061262657E9,
      "mainActivityPrev":1.475137581354E10,
      "economicActivityPrev":2.54592486814E9,
      "synAccount":"-",
      "lineNumber":0
    }
    .....
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
    @JsonIgnore
    private FinancialReport report;
}
