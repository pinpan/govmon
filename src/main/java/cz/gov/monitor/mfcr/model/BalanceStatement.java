package cz.gov.monitor.mfcr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="balance_statement")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
//        discriminatorType = DiscriminatorType.INTEGER,
        name = "statement_type"
//        , columnDefinition = "TINYINT(1)"
)
public class BalanceStatement {
    /**
     * DB Id
     */
    @javax.persistence.Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
/*

    @Column(name="statement_type_id", updatable = false, insertable = false)
    private Integer statementTypeId;
*/

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
    @ToString.Exclude
    private FinancialReport report;
}
