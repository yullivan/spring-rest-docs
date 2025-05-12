package restdocsexample;

import java.util.List;

public record BoardsResponse(
        List<BoardResponse> items
) {

    public static BoardsResponse from(List<Board> boards) {
        return new BoardsResponse(
                boards.stream()
                        .map(board -> new BoardResponse(board.getId(), board.getTitle()))
                        .toList()
        );
    }
}
