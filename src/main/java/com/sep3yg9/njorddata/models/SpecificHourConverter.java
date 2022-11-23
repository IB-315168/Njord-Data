package com.sep3yg9.njorddata.models;

import com.sep3yg9.njorddata.grpc.protobuf.task.SpecificHour;

import java.time.LocalTime;

public class SpecificHourConverter {
    public SpecificHourConverter()
    {
    }

    public static LocalTime convertToLocalDateTime(SpecificHour specific)
    {
        return LocalTime.of(specific.getHour(),specific.getMinute());
    }

    public static SpecificHour convertToSpecificHour(LocalTime time)
    {
        return SpecificHour.newBuilder().setHour(time.getHour()).setMinute(time.getMinute()).build();
    }
}
