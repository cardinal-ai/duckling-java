package com.terrylmay.duckling.regex.parser.impl;

import com.terrylmay.duckling.context.Context;
import com.terrylmay.duckling.context.DigitalTimeContext;
import com.terrylmay.duckling.entity.BaseEntity;
import com.terrylmay.duckling.entity.DigitalTime;
import com.terrylmay.duckling.regex.parser.RegexParser;

import java.util.Calendar;

public abstract class TimeRegexParser implements RegexParser {
    /**
     * 如果用户选项是倾向于未来时间，检查checkTimeIndex所指的时间是否是过去的时间，如果是的话，将大一级的时间设为当前时间的+1。
     * <p>
     * 如在晚上说“早上8点看书”，则识别为明天早上;
     * 12月31日说“3号买菜”，则识别为明年1月的3号。
     *
     * @param digitalTime        当前处理的时间对象
     * @param digitalTimeContext 处理过的时间对象
     */
    public abstract void preferFuture(DigitalTime digitalTime, DigitalTimeContext digitalTimeContext);

    protected void setDigitalTime(DigitalTime digitalTime, Calendar calendar) {
        digitalTime.setYear(calendar.get(Calendar.YEAR));
        digitalTime.setMonth(calendar.get(Calendar.MONTH) + 1);
        digitalTime.setDay(calendar.get(Calendar.DATE));
    }

    protected Calendar getCalendarFromDigitalTime(DigitalTime digitalTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        if (digitalTime.getYear() != -1) {
            calendar.set(Calendar.YEAR, digitalTime.getYear());
        }

        if (digitalTime.getMonth() != -1) {
            calendar.set(Calendar.MONTH, digitalTime.getMonth() - 1);
        }

        if (digitalTime.getDay() != -1) {
            calendar.set(Calendar.DATE, digitalTime.getDay());
        }

        if (digitalTime.getHour() != -1) {
            calendar.set(Calendar.HOUR_OF_DAY, digitalTime.getHour());
        }

        if (digitalTime.getMinute() != -1) {
            calendar.set(Calendar.MINUTE, digitalTime.getMinute());
        }
        return calendar;
    }

	public BaseEntity parse(String token, BaseEntity baseEntity, Context context) {
		return null;
	}
}
