/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package cz.gov.monitor.mfcr.controller;

import cz.gov.monitor.mfcr.config.GovMonitorServerConfig;
import cz.gov.monitor.mfcr.model.*;
import cz.gov.monitor.mfcr.service.MfcrMonitorDBService;
import cz.gov.monitor.mfcr.service.MfcrMonitorRESTService;
import cz.gov.monitor.mfcr.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/v1")
@Api(
        value = "/api/v1/",
        consumes = "application/json",
        tags = {"Monitoring API for MFCR Provided Financial Reports of CZ Government Organizations"}
    )
public class MfcrMonitorController {

    private final AtomicLong counter = new AtomicLong();


    @Autowired
    private GovMonitorServerConfig govMonitorServerConfig;

    @Autowired
    private MfcrMonitorDBService mfcrMonitorDBService;

    @Autowired
    private MfcrMonitorRESTService mfcrMonitorRESTService;

    @RequestMapping(method = RequestMethod.GET, path = "/monitor/financial_report", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Returns a list of financial reports for given organization and fiscal period.")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Financial reports list of an organization identified by company ID for a given fiscal period.")
    })
    public GetFinancialReportResponse getFinancialReport(@RequestParam(value = "ico", defaultValue = "00123456") String ico
                                                       , @RequestParam(value = "obdobi", defaultValue = "1909") String period
                                                       , @RequestBody(required = false) StatementFilter statementFilter
                                                    ) {
        // 1. Fetch the organization
        Organization organization = getOrganization(ico);

        // 2. Fetch organization reports for given period
        FinancialReport report = null;
        Optional<FinancialReport> optionalReportFromDB = mfcrMonitorDBService.findReportByQuery(ico, period);
        if ((optionalReportFromDB != null)  && optionalReportFromDB.isPresent()) {
            report = optionalReportFromDB.get();
        } else {
            report = mfcrMonitorRESTService.fetchReport(ico, period);
            report.setOrganization(organization);

            mfcrMonitorDBService.saveReport(report);
        }

        // Apply statements filter
        if ((statementFilter != null) && (statementFilter.getExpensesFilter() != null) && !statementFilter.getExpensesFilter().isEmpty()) {

            List<String> filterValues = preprocess(statementFilter.getExpensesFilter());
            if ((filterValues != null) && !filterValues.isEmpty()) {

                List<ExpenseStatement> theExpenses = new ArrayList<ExpenseStatement>();
                for (ExpenseStatement statement : report.getExpenses()) {
                    for (String filterValue : filterValues) { // For each filter value test every statement record

                        if (filterValue.equals(statement.getCode())) {
                            theExpenses.add(statement);
                        }
                    }
                }
                report.setExpenses(theExpenses);
            }

            if ((statementFilter != null) && (statementFilter.getRevenuesFilter() != null) && !statementFilter.getRevenuesFilter().isEmpty()) {
                filterValues = preprocess(statementFilter.getRevenuesFilter());

                List<RevenueStatement> theRevenues = new ArrayList<RevenueStatement>();
                for (RevenueStatement statement : report.getRevenues()) {
                    for (String filterValue : filterValues) { // For each filter value test every statement record
                        if (filterValue.equals(statement.getCode())) {
                            theRevenues.add(statement);
                        }
                    }
                }
                report.setRevenues(theRevenues);
            }
        }

        return new GetFinancialReportResponse(report);
    }

    private List<String> preprocess(String statementsFilter) {
        List<String> values = new ArrayList<String>();

        String[] filterValues = statementsFilter.split(";");
        for (String value : filterValues) {
            String[] parts = value.split("-");
            if (parts.length > 1) {
                String[] segmentsStart = parts[0].split("\\.");
                Integer segmentStart = Integer.valueOf(segmentsStart[2]);

                String[] segmentsEnd = parts[1].split("\\.");
                Integer segmentEnd = Integer.valueOf(segmentsEnd[2]);

                StringBuilder aBuilder = new StringBuilder();
                aBuilder.append(segmentsStart[0]).append(".");
                values.add(aBuilder.toString());
                values.add(aBuilder.append(segmentsStart[1]).append(".").toString());

                for (int n=segmentStart; n<=segmentEnd; n++) {
                    String valueN = new StringBuilder()
                                    .append(segmentsStart[0]).append(".")
                                    .append(segmentsStart[1]).append(".")
                                    .append(n).append(".")
                                    .toString();
                    values.add(valueN);
                }
            } else {
                values.add(parts[0]);
            }
        }

        return values;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/monitor/organization", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Returns an Organization data view, optionally with all financial reports.")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "An organization data and its financial reports.")
    })
    public GetOrganizationResponse getOrganization( @RequestParam(value = "ico", defaultValue = "00123456") String ico
                                                  , @RequestParam(value = "fetch_reports", defaultValue = "false") Boolean showReports
                                                  ) {
        Organization organization = getOrganization(ico);

        if (Boolean.TRUE.equals(showReports)) {
            List<FinancialReport> reports = mfcrMonitorDBService.fetchAllOrganizationReports(organization);
            if ((reports == null)  || reports.isEmpty()){
                Map<Integer, FinancialReport> reportsMap = mfcrMonitorRESTService.fetchOrganizationReports(organization.getIco());
            }
            organization.setFinancialReports(reports);
        }

        return new GetOrganizationResponse(organization);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/monitor/fiscal_periods", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Returns list of fiscal periods for which master source has data regardless of selected organization.")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "A list of fiscal periods information.")
    })
    public List<FiscalPeriod> getFiscalPeriods() {
        Iterable<FiscalPeriod> fiscalPeriods =  mfcrMonitorDBService.fetchAlFiscalPeriods();
        //If not found in the DB, Get from Rest and save to DB
        if ((fiscalPeriods != null)  && fiscalPeriods.iterator().hasNext()) {
            List<FiscalPeriod> result = new ArrayList<>();
            fiscalPeriods.forEach( p -> {
                result.add(p);
            });
            return result;
        }

        List fiscalPeriodsList = mfcrMonitorRESTService.getFiscalPeriods();
        mfcrMonitorDBService.saFiscalPEriods(fiscalPeriodsList);
        return fiscalPeriodsList;
    }

    private Organization getOrganization(@RequestParam(value = "ico", defaultValue = "00123456") String ico) {
        Organization organization = mfcrMonitorDBService.fetchOrganizationByICO(ico);
        if (organization == null) {
            // Feth from REST source
            organization = mfcrMonitorRESTService.fetchOrganizationByICO(ico);
            // Save to DB
            mfcrMonitorDBService.saveOrganization(organization);
        }

        return organization;
    }

    /*public List<FinancialReport> getOrganizationReports(@RequestParam(value = "ico", defaultValue = "00123456") String ico) {
        Organization organization = mfcrMonitorDBService.fetchOrganizationByICO(ico);
        if (organization == null) {
            // Feth from REST source
            organization = mfcrMonitorRESTService.fetchOrganizationByICO(ico);
            // Save to DB
            mfcrMonitorDBService.saveOrganization(organization);
        }

        return organization;
    }*/
}
