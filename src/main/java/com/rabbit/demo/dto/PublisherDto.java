package com.rabbit.demo.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public record PublisherDto(
        String name,
        String id
) {
}
