package restdocsexample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardRestController {

    private final BoardService boardService;

    public BoardRestController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/boards")
    public BoardsResponse findAll() {
        return boardService.findAll();
    }

    @PostMapping("/boards")
    public BoardResponse create(@RequestBody CreateBoardRequest request) {
        return boardService.create(request);
    }
}
