package restdocsexample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@ActiveProfiles("test")
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
public abstract class RestControllerTest {

    @Autowired
    protected MockMvcTester mvc;

    @Autowired
    protected ObjectMapper objectMapper;

    protected String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(
                    String.format("%s 객체를 JSON으로 직렬화하는데 실패했습니다. 테스트 데이터의 유효성을 확인하세요.",
                            object.getClass().getSimpleName()), e);
        }
    }
}