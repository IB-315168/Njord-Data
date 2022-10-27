package com.sep3yg9.njorddata;

import com.sep3yg9.njorddata.models.User;
import com.sep3yg9.njorddata.repos.UserRepository;
import com.sep3yg9.njorddata.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication public class NjordDataApplication
{
  private static final Logger log = LoggerFactory.getLogger(NjordDataApplication.class);
  public static void main(String[] args)
  {
    SpringApplication.run(NjordDataApplication.class, args);
  }
}
