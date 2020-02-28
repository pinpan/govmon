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
public interface MfcrMonitorDao extends CrudRepository<FinancialReport, Long> {

    @Query("Select o from Organization o where id = :dbId")
    public FinancialReport findOrganizationById(@Param("dbId") String dbId);

    @Query("Select o from Organization o where id = :query")
    public FinancialReport findOrganizationByQuery(@Param("query") String query);

    @Query("select f from FinancialReport f where f.organization = :org")
    public List<FinancialReport> getAllOrganizationReports(@Param("org") Organization org);

    @Query("select n from FinancialReport n where n.organization = :org and n.period = :period")
    public List<FinancialReport> getOrganizationReportsForFiscalPeriod(@Param("org") Organization org,
                                                                       @Param("period") Integer period);
}
