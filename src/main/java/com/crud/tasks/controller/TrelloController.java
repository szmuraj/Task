package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/trello")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TrelloController {

    private final TrelloClient trelloClient;

    @GetMapping("boards")
    public ResponseEntity<List<TrelloBoardDto>> getTrelloBoards() {
        return ResponseEntity.ok(trelloClient.getTrelloBoards());
    }

    @GetMapping("boards/{name}" )
    public void getTrelloBoardsByName(@PathVariable String name) {
        List<TrelloBoardDto> trelloBoards = trelloClient.getTrelloBoards();

        trelloBoards.forEach(trelloBoardDto -> {
            if(trelloBoardDto.getName().contains(name)) {
                System.out.println(trelloBoardDto.getId() + " " + trelloBoardDto.getName());
            } else {
                System.out.println("There is any board named " + name);
            }
        });
    }

    @PostMapping("cards")
    public ResponseEntity<CreatedTrelloCard> createTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {
        return ResponseEntity.ok(trelloClient.createNewCard(trelloCardDto));
    }
}