package cz.gov.monitor.mfcr.controller;

import cz.gov.monitor.mfcr.model.FinancialReport;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FinancialReportResponse {

    private FinancialReport financialReport;
}
