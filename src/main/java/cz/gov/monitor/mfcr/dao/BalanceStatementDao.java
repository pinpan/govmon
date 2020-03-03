package cz.gov.monitor.mfcr.dao;

import cz.gov.monitor.mfcr.model.BalanceStatement;
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
public interface BalanceStatementDao extends CrudRepository<BalanceStatement, Long> {

    @Query("select s from BalanceStatement s where s.report = :report")
    public List<FinancialReport> getAllOrganizationReports(@Param("report") FinancialReport report);
}
