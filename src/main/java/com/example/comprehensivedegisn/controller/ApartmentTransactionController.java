package com.example.comprehensivedegisn.controller;

import com.example.comprehensivedegisn.adapter.order.CustomPageable;
import com.example.comprehensivedegisn.dto.*;
import com.example.comprehensivedegisn.service.ApartmentTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApartmentTransactionController {


    /**
     * WebDataBinder 통해 직접 필드에 접근할 수 있도록 설정
     * Setter 통해 바인딩하지 않고 필드에 직접 접근하여 바인딩
     *
     */
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.initDirectFieldAccess();
    }

    private final ApartmentTransactionService apartmentTransactionService;

    @GetMapping("/apartment-transactions")
    public ResponseEntity<Page<SearchResponseRecord>> searchApartmentTransactions(@RequestParam(required = false) Long cachedCount,
                                                                                  @ModelAttribute SearchCondition searchCondition,
                                                                                  @ModelAttribute CustomPageable customPageable) {

        return ResponseEntity.ok(apartmentTransactionService.searchApartmentTransactions(cachedCount, searchCondition, customPageable));
    }

    @GetMapping("/apartment-transactions/apartment-name")
    public ResponseEntity<List<SearchApartNameResponse>> findApartmentNames(SearchApartNameRequest request) {
        return ResponseEntity.ok(apartmentTransactionService.findApartmentNames(request));
    }

    @GetMapping("/apartment-transactions/area")
    public ResponseEntity<List<SearchAreaResponse>> findAreaForExclusiveUses(SearchAreaRequest request) {
        return ResponseEntity.ok(apartmentTransactionService.findAreaForExclusive(request));
    }



}
