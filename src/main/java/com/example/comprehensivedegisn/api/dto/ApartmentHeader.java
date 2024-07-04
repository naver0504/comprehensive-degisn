package com.example.comprehensivedegisn.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "header")
@JsonIgnoreProperties(ignoreUnknown = true)
public record ApartmentHeader(@JacksonXmlProperty(localName = "resultCode") int resultCode,
                               @JacksonXmlProperty(localName = "resultMsg") String resultMessage) {}
