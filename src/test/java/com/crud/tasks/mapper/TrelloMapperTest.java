package com.crud.tasks.mapper;

import com.crud.tasks.domain.Dto.TrelloBoardDto;
import com.crud.tasks.domain.Dto.TrelloCardDto;
import com.crud.tasks.domain.Dto.TrelloListDto;
import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.domain.TrelloList;
import com.crud.tasks.trello.facade.TrelloFacade;
import nonapi.io.github.classgraph.utils.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TrelloMapperTest {

    @Autowired
    TrelloFacade trelloFacade;

    @Autowired
    TrelloMapper trelloMapper;


    @Test
    void testMapToBoards() {
        //Given
        TrelloListDto trelloListDto = new TrelloListDto("1", "Test name", true);
        List<TrelloListDto> trelloListDtos = new ArrayList<>();
        trelloListDtos.add(trelloListDto);

        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("1", "Test Name",  trelloListDtos);
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(trelloBoardDto);

        //When
        List<TrelloBoard> value = trelloMapper.mapToBoards(trelloBoardDtos);

        //Then
        Assertions.assertEquals("1", value.get(0).getId());
        Assertions.assertEquals("Test Name", value.get(0).getName());

        Assertions.assertEquals("1", value.get(0).getLists().get(0).getId());
        Assertions.assertEquals("Test name", value.get(0).getLists().get(0).getName());
        Assertions.assertTrue(value.get(0).getLists().get(0).isClosed());

    }

    @Test
    void testMapToBoardsDto() {
        //Given
        TrelloList trelloList = new TrelloList("1", "Test name", true);
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(trelloList);

        TrelloBoard trelloBoard = new TrelloBoard("1", "Test Name",  trelloLists);
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(trelloBoard);

        //When
        List<TrelloBoardDto> value = trelloMapper.mapToBoardsDto(trelloBoards);

        //Then
        Assertions.assertEquals("1", value.get(0).getId());
        Assertions.assertEquals("Test Name", value.get(0).getName());

        Assertions.assertEquals("1", value.get(0).getLists().get(0).getId());
        Assertions.assertEquals("Test name", value.get(0).getLists().get(0).getName());
        Assertions.assertTrue(value.get(0).getLists().get(0).isClosed());
    }

    @Test
    void testMapToList() {
        TrelloListDto trelloListDto = new TrelloListDto("1", "Test name", true);
        List<TrelloListDto> trelloListDtos = new ArrayList<>();
        trelloListDtos.add(trelloListDto);

        //When
        List<TrelloList> value = trelloMapper.mapToList(trelloListDtos);

        //Then
        Assertions.assertEquals("1", value.get(0).getId());
        Assertions.assertEquals("Test name", value.get(0).getName());
    }

    @Test
    void testMapToListDto() {
        //Given
        TrelloList trelloList = new TrelloList("1", "Test name", true);
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(trelloList);

        //When
        List<TrelloListDto> value = trelloMapper.mapToListDto(trelloLists);

        //Then
        Assertions.assertEquals("1", value.get(0).getId());
        Assertions.assertEquals("Test name", value.get(0).getName());
    }

    @Test
    void testMapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Test name", "Test description", "Test pos", "1");

        //When
        TrelloCardDto value = trelloCardDto;

        //Then
        Assertions.assertEquals("Test name", value.getName());
        Assertions.assertEquals("Test description", value.getDescription());
        Assertions.assertEquals("Test pos", value.getPos());
        Assertions.assertEquals("1", value.getListId());

    }

    @Test
    void testMapToCardDto() {
        TrelloCard trelloCard = new TrelloCard("Test name", "Test description", "Test pos", "1");

        //When
        TrelloCard value = trelloCard;

        //Then
        Assertions.assertEquals("Test name", value.getName());
        Assertions.assertEquals("Test description", value.getDescription());
        Assertions.assertEquals("Test pos", value.getPos());
        Assertions.assertEquals("1", value.getListId());
    }
}
