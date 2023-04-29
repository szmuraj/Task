package com.crud.tasks.domain.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrelloCardDto {

    private String name;
    private String description;
    private String pos;
    private String listId;
}