package com.sep3yg9.njorddata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication public class NjordDataApplication
{
  private static final Logger log = LoggerFactory.getLogger(NjordDataApplication.class);
  public static void main(String[] args)
  {
    SpringApplication.run(NjordDataApplication.class, args);
  }
}
