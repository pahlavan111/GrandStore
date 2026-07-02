package org.bpf.grandstore.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class  UpdateCartRequest {
    @NotNull(message = "Quantity must be provided")
    @Min(value = 1, message = "Quantity must be larger than zero")
    @Max(value = 1000, message = "Quantity must be smaller than 1000")
    private Integer quantity;
}
