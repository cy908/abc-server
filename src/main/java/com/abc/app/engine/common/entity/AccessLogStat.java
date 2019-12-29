package com.abc.app.engine.common.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 访问日志统计
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccessLogStat implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String[] TYPES = { //
            "byYear", // 0
            "byMonth", // 1
            "byDay", // 2
            "byHour", // 3
            "byMinute" // 4
    };

    private Date accessTime;
    private Long accessPV;
    private Long accessIP;

    // 参数
    private String statType;
    private Date startTime;
    private Date endTime;

    public String getStatType() {
        if (statType == null) {
            return TYPES[0];
        }
        if (Arrays.stream(TYPES).noneMatch(item -> item.equals(statType))) {
            return TYPES[0];
        }
        return statType;
    }

    public Date getStartTime() {
        if (startTime != null) {
            return startTime;
        }
        if (TYPES[4].equals(getStatType())) {
            return DateUtils.addMinutes(DateUtils.truncate(new Date(), Calendar.MINUTE), -59);
        } else if (TYPES[3].equals(getStatType())) {
            return DateUtils.addHours(DateUtils.truncate(new Date(), Calendar.HOUR_OF_DAY), -23);
        } else if (TYPES[2].equals(getStatType())) {
            return DateUtils.addDays(DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH), -29);
        }
        return null;
    }

    public Date getEndTime() {
        if (endTime != null) {
            return endTime;
        }
        if (TYPES[4].equals(getStatType())) {
            return DateUtils.addMinutes(DateUtils.truncate(new Date(), Calendar.MINUTE), 1);
        } else if (TYPES[3].equals(getStatType())) {
            return DateUtils.addHours(DateUtils.truncate(new Date(), Calendar.HOUR_OF_DAY), 1);
        } else if (TYPES[2].equals(getStatType())) {
            return DateUtils.addDays(DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH), 1);
        }
        return null;
    }

}