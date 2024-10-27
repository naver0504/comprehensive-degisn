package com.example.comprehensivedegisn.batch.kakao_map.dto;

import com.example.comprehensivedegisn.domain.AddressUtils;
import com.example.comprehensivedegisn.domain.Gu;

public record TransactionWithGu(long id, Gu gu, String dong, String jibun) {

    public String getJibunAddress() {
        return AddressUtils.getJibunAddress(gu, dong, jibun);
    }
}
