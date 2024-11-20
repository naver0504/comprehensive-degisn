package com.example.comprehensivedegisn.adapter.impl;

import com.example.comprehensivedegisn.adapter.ApartmentTransactionAdapter;
import com.example.comprehensivedegisn.adapter.domain.ApartmentTransaction;
import com.example.comprehensivedegisn.adapter.order.CustomPageable;
import com.example.comprehensivedegisn.adapter.repository.apart.ApartmentTransactionRepository;
import com.example.comprehensivedegisn.adapter.repository.apart.QuerydslApartmentTransactionRepository;
import com.example.comprehensivedegisn.dto.SearchCondition;
import com.example.comprehensivedegisn.dto.SearchResponseRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ApartmentTransactionAdapterImpl implements ApartmentTransactionAdapter {

    private final ApartmentTransactionRepository apartmentTransactionRepository;
    private final QuerydslApartmentTransactionRepository querydslApartmentTransactionRepository;

    @Override
    public Optional<ApartmentTransaction> findById(Long id) {
        return apartmentTransactionRepository.findById(id);
    }

    @Override
    public Page<SearchResponseRecord> searchApartmentTransactions(Long cachedCount, SearchCondition searchCondition, CustomPageable customPageable) {
        return querydslApartmentTransactionRepository.searchApartmentTransactions(cachedCount, searchCondition, customPageable);
    }


}
