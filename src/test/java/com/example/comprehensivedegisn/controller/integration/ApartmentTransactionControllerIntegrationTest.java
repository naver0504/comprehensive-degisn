package com.example.comprehensivedegisn.controller.integration;

import com.example.comprehensivedegisn.adapter.domain.*;
import com.example.comprehensivedegisn.adapter.order.CustomPageImpl;
import com.example.comprehensivedegisn.adapter.order.OrderType;
import com.example.comprehensivedegisn.adapter.repository.apart.ApartmentTransactionRepository;
import com.example.comprehensivedegisn.adapter.repository.dong.DongRepository;
import com.example.comprehensivedegisn.adapter.repository.predict_cost.PredictCostRepository;
import com.example.comprehensivedegisn.config.error.ControllerAdvice;
import com.example.comprehensivedegisn.config.error.CustomHttpDetail;
import com.example.comprehensivedegisn.config.error.CustomHttpExceptionResponse;
import com.example.comprehensivedegisn.controller.ApartmentTransactionController;
import com.example.comprehensivedegisn.controller.integration.config.IntegrationTestForController;
import com.example.comprehensivedegisn.dto.SearchResponseRecord;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.comprehensivedegisn.adapter.repository.apart.QuerydslApartmentTransactionRepositoryTest.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTestForController
public class ApartmentTransactionControllerIntegrationTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    private ApartmentTransactionController apartmentTransactionController;

    @Autowired
    private ApartmentTransactionRepository apartmentTransactionRepository;
    @Autowired
    private DongRepository dongRepository;
    @Autowired
    private PredictCostRepository predictCostRepository;

    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(apartmentTransactionController)
                .setControllerAdvice(new ControllerAdvice())
                .build();
    }

    @Test
    void searchApartmentTransactions_With_Not_Valid_Input() throws Exception {
        // given
        String url = "/apartment-transactions";
        long count = 100L;
        Gu gu = Gu.마포구;
        String dong = null;
        String apartmentName = "testApartmentName";
        CustomHttpExceptionResponse expectedError = new CustomHttpExceptionResponse(CustomHttpDetail.BAD_REQUEST.getStatusCode(), "검색 조건이 올바르지 않습니다.");

        // when
        ResultActions resultActions = mockMvc.perform(get(url)
                .param("count", String.valueOf(count))
                .param("gu", gu.name())
                .param("dong", dong)
                .param("apartmentName", apartmentName)
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
        String result = resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        CustomHttpExceptionResponse content = objectMapper.readValue(result, CustomHttpExceptionResponse.class);
        Assertions.assertThat(content).isEqualTo(expectedError);
    }

    @Test
    void searchApartmentTransactions_With_Valid_Input() throws Exception {
        // given
        String url = "/apartment-transactions";
        Gu gu = Gu.송파구;
        String dong = TEST_DONG;
        String aptName = TEST_APT_NAME;
        Double area = TEST_AREA;
        LocalDate startDate = TEST_START_DATE;
        int dealAmount = 1000;

        OrderType orderType = OrderType.AREA_FOR_EXCLUSIVE_USE;
        List<SearchResponseRecord> expected = setEntities(dealAmount, aptName, startDate, area, gu, dong);
        // then
        ResultActions resultActions = mockMvc.perform(get(url)
                .param("dong", dong)
                .param("gu", gu.name())
                .param("apartmentName", aptName)
                .param("area", area.toString())
                .param("startDate", startDate.toString())
                .param("orderType", orderType.name())
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk());
        String result = resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        CustomPageImpl<SearchResponseRecord>  contents = objectMapper.readValue(result, new TypeReference<>() {});
        Assertions.assertThat(contents.getContent()).isEqualTo(expected);
    }

    @Test
    void findApartmentNames_With_Valid_Input() throws Exception {
        // given
        String url = "/apartment-transactions/apartment-name";
        Gu gu = Gu.서초구;
        String dong = TEST_DONG;
        String aptName = TEST_APT_NAME;

        DongEntity dontEntity = dongRepository.save(DongEntity.builder().gu(gu).dongName(dong).build());
        int repeat = 3;
        for (int i = 0; i < repeat; i++) {
            apartmentTransactionRepository.save(ApartmentTransaction.builder()
                    .apartmentName(aptName + i)
                    .dongEntity(dontEntity)
                    .build());
        }

        // when
        ResultActions resultActions = mockMvc.perform(get(url)
                .param("gu", gu.name())
                .param("dong", dong)
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk());
        String result = resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<SearchResponseRecord> contents = objectMapper.readValue(result, new TypeReference<>() {});
        Assertions.assertThat(contents).hasSize(repeat);
        Assertions.assertThat(contents)
                .extracting(SearchResponseRecord::apartmentName)
                .allMatch(name -> name.startsWith(aptName));
    }

    @Test
    void findApartmentNames_With_Not_Valid_Input() throws Exception {
        // given
        String url = "/apartment-transactions/apartment-name";
        Gu gu = Gu.서초구;
        String dong = null;
        CustomHttpExceptionResponse expectedError = new CustomHttpExceptionResponse(CustomHttpDetail.BAD_REQUEST.getStatusCode(), "검색 조건이 올바르지 않습니다.");

        // when
        ResultActions resultActions = mockMvc.perform(get(url)
                .param("gu", gu.name())
                .param("dong", dong)
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
        String result = resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        CustomHttpExceptionResponse content = objectMapper.readValue(result, CustomHttpExceptionResponse.class);
        Assertions.assertThat(content).isEqualTo(expectedError);
    }

    @Test
    void findAreaForExclusive_With_Valid_Input() throws Exception {
        // given
        String url = "/apartment-transactions/area";
        Gu gu = Gu.서초구;
        String dong = TEST_DONG;
        String aptName = TEST_APT_NAME;
        double area = TEST_AREA;

        DongEntity dongEntity = dongRepository.save(DongEntity.builder().gu(gu).dongName(dong).build());
        int repeat = 3;
        List<Double> expected = new ArrayList<>(repeat);
        for (int i = 0; i < repeat; i++) {
            double areaForExclusiveUse = area * i;
            expected.add(areaForExclusiveUse);
            apartmentTransactionRepository.save(ApartmentTransaction.builder()
                    .apartmentName(aptName)
                    .dongEntity(dongEntity)
                    .areaForExclusiveUse(areaForExclusiveUse)
                    .build());
        }

        // when
        ResultActions resultActions = mockMvc.perform(get(url)
                .param("gu", gu.name())
                .param("dong", dong)
                .param("apartmentName", aptName)
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk());
        String result = resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<SearchResponseRecord> contents = objectMapper.readValue(result, new TypeReference<>() {});
        Assertions.assertThat(contents).hasSize(repeat);
        Assertions.assertThat(contents)
                .extracting(SearchResponseRecord::areaForExclusiveUse)
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void findAreaForExclusive_With_Not_Valid_Input() throws Exception {
        // given
        String url = "/apartment-transactions/area";
        Gu gu = Gu.서초구;
        String dong = null;
        String aptName = TEST_APT_NAME;
        CustomHttpExceptionResponse expectedError = new CustomHttpExceptionResponse(CustomHttpDetail.BAD_REQUEST.getStatusCode(), "검색 조건이 올바르지 않습니다.");

        // when
        ResultActions resultActions = mockMvc.perform(get(url)
                .param("gu", gu.name())
                .param("dong", dong)
                .param("apartmentName", aptName)
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
        String result = resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        CustomHttpExceptionResponse content = objectMapper.readValue(result, CustomHttpExceptionResponse.class);
        Assertions.assertThat(content).isEqualTo(expectedError);
    }

    private List<SearchResponseRecord> setEntities(int dealAmount, String aptName, LocalDate startDate, Double area, Gu gu, String dong) {

        DongEntity dongEntity = dongRepository.save(DongEntity.builder().gu(gu).dongName(dong).build());

        ApartmentTransaction firstAT = apartmentTransactionRepository.save(ApartmentTransaction.builder()
                .apartmentName(aptName)
                .areaForExclusiveUse(area)
                .dongEntity(dongEntity)
                .dealDate(startDate)
                .dealAmount(dealAmount)
                .build());

        ApartmentTransaction secondAT = apartmentTransactionRepository.save(ApartmentTransaction.builder()
                .apartmentName(aptName)
                .areaForExclusiveUse(area * 2)
                .dongEntity(dongEntity)
                .dealDate(startDate.plusMonths(1))
                .dealAmount(dealAmount * 2)
                .build());

        PredictCost firstPC = predictCostRepository.save(PredictCost.builder()
                .apartmentTransaction(firstAT)
                .predictedCost(1000L)
                .isReliable(false)
                .predictStatus(PredictStatus.RECENT)
                .build());

        PredictCost secondPC = predictCostRepository.save(PredictCost.builder()
                .apartmentTransaction(secondAT)
                .predictedCost(1000L)
                .isReliable(true)
                .predictStatus(PredictStatus.RECENT)
                .build());

        return List.of(
                new SearchResponseRecord(firstAT.getId(), aptName, gu, dong, firstAT.getAreaForExclusiveUse(), firstAT.getDealDate(), firstAT.getDealAmount(), firstPC.getPredictedCost(), firstPC.isReliable()),
                new SearchResponseRecord(secondAT.getId(), aptName, gu, dong, secondAT.getAreaForExclusiveUse(), secondAT.getDealDate(), secondAT.getDealAmount(), secondPC.getPredictedCost(), secondPC.isReliable()));
    }
}
