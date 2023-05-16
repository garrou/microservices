package com.spring.statistiques.clients;


import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("badges")
public interface BadgesClient {
    //todo
}
