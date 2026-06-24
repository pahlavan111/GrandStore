package org.bpf.grandstore.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProduceDto {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Byte categoryId;
}
