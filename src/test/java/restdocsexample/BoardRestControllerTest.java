package restdocsexample;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BoardRestController.class)
public class BoardRestControllerTest extends RestControllerTest {

    @MockitoBean
    private BoardService boardService;

    @Test
    void findBoards() throws Exception {
        // given
        BoardsResponse response = BoardsResponse.from(List.of(new Board(1L, "공지사항")));
        BDDMockito.given(boardService.findAll()).willReturn(response);

        // when
        mockMvc.perform(get("/boards"))
                .andExpect(status().isOk())
                .andDo(document("board-find-all",
                        responseFields(
                                fieldWithPath("items").type(JsonFieldType.ARRAY)
                                        .description("게시판 목록"),
                                fieldWithPath("items[].id").type(JsonFieldType.NUMBER)
                                        .description("게시판 아이디"),
                                fieldWithPath("items[].title").type(JsonFieldType.STRING)
                                        .description("게시판 이름")
                        )));
    }

    @Test
    void createBoard() throws Exception {
        // given
        BoardResponse response = new BoardResponse(1L, "공지사항");
        BDDMockito.given(boardService.create(BDDMockito.any())).willReturn(response);

        // when
        ResultActions 공지사항 = mockMvc.perform(post("/boards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new CreateBoardRequest("공지사항"))))
                .andExpect(status().isOk())
                .andDo(document("board-create",
                        requestFields(
                          fieldWithPath("title").type(JsonFieldType.STRING)
                                  .description("게시판 이름")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER)
                                        .description("게시판 아이디"),
                                fieldWithPath("title").type(JsonFieldType.STRING)
                                        .description("게시판 이름")
                        )));
    }
}
