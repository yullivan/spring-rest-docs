package restdocsexample;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

@WebMvcTest(BoardRestController.class)
public class BoardRestControllerTest extends RestControllerTest {

    @MockitoBean
    private BoardService boardService;

    @Test
    void findBoards() {
        BoardsResponse response = BoardsResponse.from(List.of(new Board(1L, "공지사항")));
        given(boardService.findAll()).willReturn(response);

        assertThat(this.mvc.get()
                .uri("/boards")
                .accept(MediaType.APPLICATION_JSON))
                .hasStatusOk()
                .apply(document("board-find-all",
                        responseFields(
                                fieldWithPath("items").type(JsonFieldType.ARRAY)
                                        .description("게시판 목록"),
                                fieldWithPath("items[].id").type(JsonFieldType.NUMBER)
                                        .description("게시판 아이디"),
                                fieldWithPath("items[].title").type(JsonFieldType.STRING)
                                        .description("게시판 이름")
                        )
                ));
    }

    @Test
    void createBoard() {
        BoardResponse response = new BoardResponse(1L, "공지사항");
        given(boardService.create(any())).willReturn(response);

        assertThat(this.mvc.post()
                .uri("/boards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new CreateBoardRequest("공지사항"))))
                .hasStatusOk()
                .apply(document("board-create",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING)
                                        .description("게시판 이름")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER)
                                        .description("게시판 아이디"),
                                fieldWithPath("title").type(JsonFieldType.STRING)
                                        .description("게시판 이름")
                        )
                ));
    }
}
