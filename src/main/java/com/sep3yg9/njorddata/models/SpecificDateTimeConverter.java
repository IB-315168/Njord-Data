package com.sep3yg9.njorddata.models;

import com.sep3yg9.njorddata.grpc.protobuf.specificdatetime.SpecificDateTime;
import com.sep3yg9.njorddata.grpc.protobuf.specificdatetime.SpecificTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class SpecificDateTimeConverter
{
  public SpecificDateTimeConverter()
  {
  }

  public static LocalDateTime convertToLocalDateTime(
      SpecificDateTime specificDateTime)
  {
    return LocalDateTime.of(
        LocalDate.of(specificDateTime.getYear(), specificDateTime.getMonth(),
            specificDateTime.getDay()),
        LocalTime.of(specificDateTime.getHour(), specificDateTime.getMinute()));
  }

  public static SpecificDateTime convertToSpecificDateTime(LocalDateTime dateTime)
  {
    return SpecificDateTime.newBuilder().setDay(dateTime.getDayOfMonth())
        .setMonth(dateTime.getMonthValue()).setYear(dateTime.getYear())
        .setHour(dateTime.getHour()).setMinute(dateTime.getMinute()).build();
  }

  public static LocalTime convertToLocalTime(SpecificTime specificTime) {
    return LocalTime.of(specificTime.getHour(), specificTime.getMinute(),
        specificTime.getSecond());
  }

  public static SpecificTime convertToSpecificTime(LocalTime localTime) {
    return SpecificTime.newBuilder().setHour(localTime.getHour()).setMinute(
        localTime.getMinute()).setSecond(localTime.getSecond()).build();
  }
}
