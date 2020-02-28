package cz.gov.monitor.mfcr.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeInterval {

    private Date dateFrom;
    private Date dateTo;
}
