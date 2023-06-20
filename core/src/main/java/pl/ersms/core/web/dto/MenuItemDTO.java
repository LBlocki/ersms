package pl.ersms.core.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.NonNull;

import java.math.BigDecimal;

public record MenuItemDTO(
        @NonNull
        Long id,
        @NonNull
        Long restaurantId,
        @NonNull
        String restaurantName,
        @NonNull
        String name,
        @NonNull
        String description,
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        @NonNull
        BigDecimal price,
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        @NonNull
        StateDTO state
) {

    public enum StateDTO {
        AVAILABLE,
        PENDING,
        RESERVED,
        FINISHED
    }
}
