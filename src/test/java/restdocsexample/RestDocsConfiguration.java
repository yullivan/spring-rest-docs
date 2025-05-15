package restdocsexample;

import org.springframework.boot.test.autoconfigure.restdocs.RestDocsMockMvcConfigurationCustomizer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

/**
 * Spring REST Docs 설정을 커스터마이징하는 클래스.
 * REST Docs에서 생성되는 JSON 요청/응답이 예쁘게 출력되도록 설정합니다.
 */
@TestConfiguration
public class RestDocsConfiguration {

    /**
     * REST Docs 설정을 커스터마이징합니다.
     * 요청과 응답에 대해 prettyPrint를 적용합니다.
     */
    @Bean
    public RestDocsMockMvcConfigurationCustomizer restDocsMockMvcConfigurationCustomizer() {
        return configurer -> configurer.operationPreprocessors()
                .withRequestDefaults(prettyPrint())
                .withResponseDefaults(prettyPrint());
    }

    /**
     * UTF-8 인코딩 필터를 제공합니다.
     */
    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }
}