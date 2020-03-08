package cz.gov.monitor.mfcr.dao;

import cz.gov.monitor.mfcr.model.FiscalPeriod;
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
public interface FiscalPeriodDao extends CrudRepository<FiscalPeriod, Long> {

    @Query("select f from FinancialReport f where f.organization.ico = :ico and f.period=:period")
    public FiscalPeriod findReportByQuery(@Param("ico") String ico, @Param("period") String period);

    @Query("select f from FiscalPeriod f ")
    public List<FiscalPeriod> getAllfiscalPEriods();

    @Query("select f from FinancialReport f where f.organization = :org and f.period = :period")
    public List<FiscalPeriod> getOrganizationReportsForFiscalPeriod(@Param("org") Organization org,
                                                                    @Param("period") Integer period);
}
