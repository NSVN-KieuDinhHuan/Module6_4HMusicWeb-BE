package com.codegym.g2m6appmusicbe.controller;

import com.codegym.g2m6appmusicbe.model.dto.SearchItem;
import com.codegym.g2m6appmusicbe.service.search.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class SearchController {
    @Autowired
    private SearchItemService searchItemService;
    @GetMapping("/search")
    public ResponseEntity<SearchItem> search(@RequestParam(name = "q") String q){
        SearchItem searchItem = searchItemService.getSearchItem(q);
        return new ResponseEntity<>(searchItem, HttpStatus.OK);
    }
}
