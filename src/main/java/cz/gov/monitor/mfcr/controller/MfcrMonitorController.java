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
import cz.gov.monitor.mfcr.service.MonitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private MonitorService monServ;

    @RequestMapping(method = RequestMethod.GET, path = "/monitor/financialreport")
    @ApiOperation(
            value = "Returns a list of financial reports for given organization and fiscal period.")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Financial reports list of a organization for a given fiscal period.")
    })
    public FinancialReport getFinancialReport(@RequestParam(value = "ico", defaultValue = "00123456") String ico
                                                   ,@RequestParam(value = "obdobi", defaultValue = "1909") String period
                                                    ) {
        return monServ.fetchReport(ico, period);
    }
}
