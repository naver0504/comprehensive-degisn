package com.example.comprehensivedegisn.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.time.LocalDate;

/***
 *
 *
 * XmlProperty(localName = "등기일자") String registrationDate) {}
 * //
 * //    @JacksonXmlProperty(localName = "거래금액")
 * //    private String dealAmount;
 * //
 * //    @JacksonXmlProperty(localName = "건축년도")
 * //    private String buildYear;
 * //
 * //    @JacksonXmlProperty(localName = "년")
 * //    private String dealYear;
 * //
 * //    @JacksonXmlProperty(localName = "도로명")
 * //    private String roadName;
 * //
 * //    @JacksonXmlProperty(localName = "도로명건물본번호코드")
 * //    private String roadNameBonbun;
 * //
 * //    @JacksonXmlProperty(localName = "도로명건물부번호코드")
 * //    private String roadNameBubun;
 * //
 * //    @JacksonXmlProperty(localName = "도로명시군구코드")
 * //    private String roadNameSigunguCode;
 * //
 * //    @JacksonXmlProperty(localName = "도로명일련번호코드")
 * //    private String roadNameSeq;
 * //
 * //    @JacksonXmlProperty(localName = "도로명지상지하코드")
 * //    private String roadNameBasementCode;
 * //
 * //    @JacksonXmlProperty(localName = "도로명코드")
 * //    private String roadNameCode;
 * //
 * //    @JacksonXmlProperty(localName = "법정동")
 * //    private String dong;
 * //
 * //    @JacksonXmlProperty(localName = "법정동본번코드")
 * //    private String bonbun;
 * //
 * //    @JacksonXmlProperty(localName = "법정동부번코드")
 * //    private String bubun;
 * //
 * //    @JacksonXmlProperty(localName = "법정동시군구코드")
 * //    private String sigunguCode;
 * //
 * //    @JacksonXmlProperty(localName = "법정동읍면동코드")
 * //    private String eupmyeondongCode;
 * //
 * //    @JacksonXmlProperty(localName = "법정동지번코드")
 * //    private String landCode;
 * //
 * //    @JacksonXmlProperty(localName = "아파트")
 * //    private String apartmentName;
 * //
 * //    @JacksonXmlProperty(localName = "월")
 * //    private String dealMonth;
 * //
 * //    @JacksonXmlProperty(localName = "일")
 * //    private String dealDay;
 * //
 * //    @JacksonXmlProperty(localName = "일련번호")
 * //    private String serialNumber;
 * //
 * //    @JacksonXmlProperty(localName = "전용면적")
 * //    private String areaForExclusiveUse;
 * //
 * //    @JacksonXmlProperty(localName = "지번")
 * //    private String jibun;
 * //
 * //    @JacksonXmlProperty(localName = "지역코드")
 * //    private String regionalCode;
 * //
 * //    @JacksonXmlProperty(localName = "층")
 * //    private String floor;
 * //
 * //    @JacksonXmlProperty(localName = "중개사소재지")
 * //    private String rdealerLawDnm;
 * //
 * //    @JacksonXmlProperty(localName = "등기일자")
 * //    private String registrationDate;

 */
@JacksonXmlRootElement(localName = "item")
@JsonIgnoreProperties(ignoreUnknown = true)
public record ApartmentDetail(@JacksonXmlProperty(localName = "거래금액") String dealAmount,
                              @JacksonXmlProperty(localName = "건축년도") int buildYear,
                              @JacksonXmlProperty(localName = "년") int dealYear,
                              @JacksonXmlProperty(localName = "도로명") String roadName,
                              @JacksonXmlProperty(localName = "도로명건물본번호코드") int roadNameBonbun,
                              @JacksonXmlProperty(localName = "도로명건물부번호코드") int roadNameBubun,
                              @JacksonXmlProperty(localName = "도로명시군구코드") int roadNameSigunguCode,
                              @JacksonXmlProperty(localName = "도로명일련번호코드") int roadNameSeq,
                              @JacksonXmlProperty(localName = "도로명지상지하코드") int roadNameBasementCode,
                              @JacksonXmlProperty(localName = "도로명코드") int roadNameCode,
                              @JacksonXmlProperty(localName = "법정동") String dong,
                              @JacksonXmlProperty(localName = "법정동본번코드") int bonbun,
                              @JacksonXmlProperty(localName = "법정동부번코드") int bubun,
                              @JacksonXmlProperty(localName = "법정동시군구코드") int sigunguCode,
                              @JacksonXmlProperty(localName = "법정동읍면동코드") int eupmyeondongCode,
                              @JacksonXmlProperty(localName = "법정동지번코드") int landCode,
                              @JacksonXmlProperty(localName = "아파트") String apartmentName,
                              @JacksonXmlProperty(localName = "월") int dealMonth,
                              @JacksonXmlProperty(localName = "일") int dealDay,
                              @JacksonXmlProperty(localName = "일련번호") int serialNumber,
                              @JacksonXmlProperty(localName = "전용면적") double areaForExclusiveUse,
                              @JacksonXmlProperty(localName = "지번") int jibun,
                              @JacksonXmlProperty(localName = "지역코드") int regionalCode,
                              @JacksonXmlProperty(localName = "층") int floor,
                              @JacksonXmlProperty(localName = "중개사소재지") String rdealerLawDnm,
                              @JacksonXmlProperty(localName = "등기일자") String registrationDate) {

    public LocalDate getRegistrationDate() {
        String[] yearMonthDay = registrationDate.split("\\.");
        return LocalDate.of(getYearByRegistrationDate(Integer.parseInt(yearMonthDay[0])), Integer.parseInt(yearMonthDay[1]), Integer.parseInt(yearMonthDay[2]));
    }

    public int getYearByRegistrationDate(int year) {
        int currentYear = LocalDate.now().getYear() - 2000;
        if (year <= currentYear) {
            return year + 2000;
        } else {
            return year + 1900;
        }
    }
}