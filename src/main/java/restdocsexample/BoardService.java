package restdocsexample;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    public BoardsResponse findAll() {
        return BoardsResponse.from(List.of(new Board(1L, "공지사항"), new Board(2L, "자유게시판")));
    }

    public BoardResponse create(CreateBoardRequest request) {
        return new BoardResponse(1L, request.title());
    }
}
