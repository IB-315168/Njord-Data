package com.sep3yg9.njorddata.models;

import com.sep3yg9.njorddata.grpc.protobuf.project.SpecificTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class SpecificTimeConverter
{
  public SpecificTimeConverter()
  {
  }

  public static LocalDateTime convertToLocalDateTime(SpecificTime specificTime)
  {
    return LocalDateTime.of(
        LocalDate.of(specificTime.getYear(), specificTime.getMonth(),
            specificTime.getDay()),
        LocalTime.of(specificTime.getHour(), specificTime.getMinute()));
  }

  public static SpecificTime convertToSpecificTime(LocalDateTime dateTime)
  {
    return SpecificTime.newBuilder().setDay(dateTime.getDayOfMonth())
        .setMonth(dateTime.getMonthValue()).setYear(dateTime.getYear())
        .setHour(dateTime.getHour()).setMinute(dateTime.getMinute()).build();
  }
}
