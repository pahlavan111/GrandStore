package org.bpf.grandstore.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddItemToCartRequest {

    @NotNull
    Long productId;
}
