package com.example.boilerplate.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.boilerplate.BeforeEachTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.boilerplate.domain.auth.dto.AuthDTO.CommonLoginRequest;
import com.example.boilerplate.domain.auth.dto.AuthDTO.CommonSignupRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureWireMock(port = 11000)
class AuthControllerTest extends BeforeEachTest {
    private MockMvc mockMvc;

    @Autowired ObjectMapper objectMapper;
    MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                                      .addFilters(new CharacterEncodingFilter("UTF-8", true))
                                      .apply(documentationConfiguration(restDocumentation)
                                          .uris()
                                          .withScheme("http")
                                          .withHost("127.0.0.1")
                                          .withPort(8081))
                                      .build();
    }

    @Test
    @Transactional
    void 일반_회원가입() throws Exception {
        String content = objectMapper.writeValueAsString(
            CommonSignupRequest.builder()
                               .email("이메일(아이디)")
                               .password("비밀번호")
                               .hp("01012345678")
                               .name("이름")
                               .build()
        );

        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v1/auth/signup")
                                                             .contextPath("/api")
                                                             .content(content)
                                                             .accept(MEDIA_TYPE_JSON_UTF8)
                                                             .contentType(MEDIA_TYPE_JSON_UTF8))
                    .andExpect(status().isOk())
                    .andDo(document(
                            "authCommonSignup",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            PayloadDocumentation.requestFields(
                                PayloadDocumentation.fieldWithPath("email")
                                                    .description("이메일(아이디)")
                                                    .type(JsonFieldType.STRING),
                                PayloadDocumentation.fieldWithPath("password")
                                                    .description("비밀번호")
                                                    .type(JsonFieldType.STRING),
                                PayloadDocumentation.fieldWithPath("hp")
                                                    .description("전화번호(숫자만 11자리)")
                                                    .type(JsonFieldType.STRING),
                                PayloadDocumentation.fieldWithPath("name")
                                                    .description("이름")
                                                    .type(JsonFieldType.STRING)
                            ),
                            PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("code")
                                                    .description("응답코드")
                                                    .type(JsonFieldType.STRING),
                                PayloadDocumentation.fieldWithPath("message")
                                                    .description("응답메시지")
                                                    .type(JsonFieldType.STRING)
                            )
                        )
                    );
    }

    @Test
    @Transactional
    void 일반_로그인() throws Exception {
        String content = objectMapper.writeValueAsString(
            CommonLoginRequest.builder()
                              .email("test@google.com")
                              .password("비밀번호")
                              .build()
        );

        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v1/auth/common")
                                                             .contextPath("/api")
                                                             .content(content)
                                                             .accept(MEDIA_TYPE_JSON_UTF8)
                                                             .contentType(MEDIA_TYPE_JSON_UTF8))
                    .andExpect(status().isOk())
                    .andDo(document(
                            "authCommonLogin",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            PayloadDocumentation.requestFields(
                                PayloadDocumentation.fieldWithPath("email")
                                                    .description("이메일(아이디)")
                                                    .type(JsonFieldType.STRING),
                                PayloadDocumentation.fieldWithPath("password")
                                                    .description("비밀번호")
                                                    .type(JsonFieldType.STRING)
                            ),
                            PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("code")
                                                    .description("응답코드")
                                                    .type(JsonFieldType.STRING),
                                PayloadDocumentation.fieldWithPath("message")
                                                    .description("응답메시지")
                                                    .type(JsonFieldType.STRING),
                                PayloadDocumentation.fieldWithPath("data")
                                                    .description("응답데이터")
                                                    .type(JsonFieldType.OBJECT),
                                PayloadDocumentation.fieldWithPath("data.accessToken")
                                                    .description("엑세스 토큰")
                                                    .type(JsonFieldType.STRING),
                                PayloadDocumentation.fieldWithPath("data.refreshToken")
                                                    .description("리프레시 토큰")
                                                    .type(JsonFieldType.STRING),
                                PayloadDocumentation.fieldWithPath("data.accessExpiredTime")
                                                    .description("엑세스 토큰 만료시간")
                                                    .type(JsonFieldType.STRING),
                                PayloadDocumentation.fieldWithPath("data.refreshExpiredTime")
                                                    .description("리프레시 토큰 만료시간")
                                                    .type(JsonFieldType.STRING)
                            )
                        )
                    );
    }
}
