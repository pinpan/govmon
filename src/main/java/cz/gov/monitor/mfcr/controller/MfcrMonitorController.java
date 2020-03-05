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
import cz.gov.monitor.mfcr.model.FinancialReport;
import cz.gov.monitor.mfcr.model.FiscalPeriod;
import cz.gov.monitor.mfcr.model.Organization;
import cz.gov.monitor.mfcr.service.MfcrMonitorDBService;
import cz.gov.monitor.mfcr.service.MfcrMonitorRESTService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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

    @RequestMapping(method = RequestMethod.GET, path = "/monitor/financial_report")
    @ApiOperation(
            value = "Returns a list of financial reports for given organization and fiscal period.")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Financial reports list of an organization identified by company ID for a given fiscal period.")
    })
    public GetFinancialReportResponse getFinancialReport(@RequestParam(value = "ico", defaultValue = "00123456") String ico
                                             , @RequestParam(value = "obdobi", defaultValue = "1909") String period
                                                    ) {
        // 1. Fetch the organization
        Organization organization = getOrganization(ico);

        // 2. Fetch organization reports for given period
        FinancialReport report = mfcrMonitorRESTService.fetchReport(ico, period);
        report.setOrganization(organization);
        return new GetFinancialReportResponse(report);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/monitor/organization")
    @ApiOperation(
            value = "Returns an Organization data view, optionally with all financial reports.")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "An organization data and its financial reports.")
    })
    public GetOrganizationResponse getOrganization(@RequestParam(value = "ico", defaultValue = "00123456") String ico
                                                  ,@RequestParam(value = "fetch_reports", defaultValue = "false") Boolean showReports
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

    @RequestMapping(method = RequestMethod.GET, path = "/monitor/fiscal_periods")
    @ApiOperation(
            value = "Returns list of fiscal periods for which master source has data regardless of selected organization.")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "A list of fiscal periods information.")
    })
    public List<FiscalPeriod> getFiscalPEriods() {
        // TODO: Get from DB firs
        List<FiscalPeriod> fiscalPEriods = mfcrMonitorRESTService.getFiscalPeriods();

        return fiscalPEriods;
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
