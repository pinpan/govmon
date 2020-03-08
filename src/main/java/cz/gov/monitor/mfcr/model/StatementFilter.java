package cz.gov.monitor.mfcr.model;

import lombok.Data;

@Data
public class StatementFilter {
    private String expensesFilter;
    private String revenuesFilter;
}
