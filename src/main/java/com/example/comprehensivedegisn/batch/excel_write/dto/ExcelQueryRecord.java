package com.example.comprehensivedegisn.batch.excel_write.dto;

import com.example.comprehensivedegisn.adapter.domain.AddressUtils;
import com.example.comprehensivedegisn.adapter.domain.ApartmentTransaction;
import com.example.comprehensivedegisn.adapter.domain.DongEntity;
import com.example.comprehensivedegisn.adapter.domain.Interest;

public record ExcelQueryRecord(
        // QuerydslNoOffsetIdPagingItemReader 작동하기 위해서는 ID 필드가 필요함
        long id,
        ApartmentTransaction apartmentTransaction,
        DongEntity dongEntity,
        Interest interest
) {

    public ExcelOutputRecord toExcelOutputRecord() {
        return new ExcelOutputRecord(
                apartmentTransaction.getDealDate(),
                interest.getInterestRate(),
                dongEntity.getGu(),
                dongEntity.getDongName(),
                apartmentTransaction.getAreaForExclusiveUse(),
                apartmentTransaction.getFloor(),
                apartmentTransaction.getBuildYear(),
                apartmentTransaction.getGeography() == null ? null : apartmentTransaction.getGeography().getX(),
                apartmentTransaction.getGeography() == null ? null : apartmentTransaction.getGeography().getY(),
                apartmentTransaction.getDealAmount(),
                AddressUtils.getJibunAddress(dongEntity.getGu(), dongEntity.getDongName(), apartmentTransaction.getJibun())
                        .orElse(null)
        );
    }
}
