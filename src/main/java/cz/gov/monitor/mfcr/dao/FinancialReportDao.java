package cz.gov.monitor.mfcr.dao;

import cz.gov.monitor.mfcr.model.FinancialReport;
import cz.gov.monitor.mfcr.model.Organization;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Attila Cseh on 25/01/2020.
 */
@Service
public interface FinancialReportDao extends CrudRepository<FinancialReport, Long> {

    @Query("select f from FinancialReport f where f.organization.ico = :ico and f.period=:period")
    public FinancialReport findReportByQuery(@Param("ico")String ico, @Param("period")String period);

    @Query("Select o from Organization o where ico = :ico")
    public Organization findOrganizationByICO(@Param("ico") String ico);

    @Query("select f from FinancialReport f where f.organization = :org")
    public List<FinancialReport> getAllOrganizationReports(@Param("org") Organization org);

    @Query("select f from FinancialReport f where f.organization = :org and f.period = :period")
    public List<FinancialReport> getOrganizationReportsForFiscalPeriod(@Param("org") Organization org,
                                                                       @Param("period") Integer period);
}
