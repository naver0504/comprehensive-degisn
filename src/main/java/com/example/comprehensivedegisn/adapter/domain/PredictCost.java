package com.example.comprehensivedegisn.adapter.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity(name = "predict_cost")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class PredictCost {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private long predictedCost;

    private boolean isReliable;

    @Enumerated(EnumType.STRING)
    private PredictStatus predictStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_transaction_id")
    private ApartmentTransaction apartmentTransaction;
}