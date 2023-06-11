package com.yetistudios.rsww.common.dto;

import com.yetistudios.rsww.common.util.DatetimeUtil;
import lombok.Builder;
import lombok.Data;

import java.text.SimpleDateFormat;

@Data
@Builder
public class DepartureOrArrivalDocument {
    public String airportCode;
    public String date;
    public String time;

    public Long getEpochSeconds() {
        return DatetimeUtil.dateAndTimeToUnix(date, time);
    }
}
