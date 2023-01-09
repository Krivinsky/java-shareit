package ru.practicum.shareit.request.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * TODO Sprint add-item-requests.
 */

//@Builder
@Data
public class ItemRequestDto {


    private String description;
}
