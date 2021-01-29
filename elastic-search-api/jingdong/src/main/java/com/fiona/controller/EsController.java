package com.fiona.controller;


import com.fiona.service.EsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class EsController {

    @Autowired
    private EsService bulkRequest;


    @GetMapping("/parse/{keyword}")
    public boolean keywordSearch(@PathVariable("keyword") String keyword) throws IOException {

    return bulkRequest.bulkRequest(keyword);

    }

    @GetMapping("/search/{keyword}/{pageNo}/{pageSize}")
    public List<Map<String, Object>>keywordEsSearch(@PathVariable("keyword") String keyword,
                                                    @PathVariable("pageNo") int pageNo,
                                                    @PathVariable("pageSize") int pageSize) throws IOException {

      return  bulkRequest.searchPage(keyword, pageNo, pageSize);

    }

}
