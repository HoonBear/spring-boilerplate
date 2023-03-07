package com.example.boilerplate.controller;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.boilerplate.mocking.AuthMocking;
import com.example.boilerplate.BeforeEachTest;
import com.example.boilerplate.EnumStringCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@WithMockUser
@AutoConfigureWireMock(port = 11000)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
class MemberControllerTest extends BeforeEachTest {
    private MockMvc mockMvc;

    @Autowired ObjectMapper objectMapper;
    @Autowired
    AuthMocking authMocking;
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
    void 단일회원정보() throws Exception {
        LinkedMultiValueMap<String, String> requestHeaders = new LinkedMultiValueMap<>();
        requestHeaders.add("Authorization", "Bearer " + "access-token");
        HttpHeaders httpHeaders = new HttpHeaders(requestHeaders);

        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/member")
                                                             .contextPath("/api")
                                                             .headers(httpHeaders)
                                                             .accept(MEDIA_TYPE_JSON_UTF8)
                                                             .contentType(MEDIA_TYPE_JSON_UTF8))
                    .andExpect(status().isOk())
                    .andDo(document(
                            "memberInfo",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            requestHeaders(
                                headerWithName("Authorization").description("Bearer + ' ' + access-token")
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
                                PayloadDocumentation.fieldWithPath("data.email")
                                                    .description("이메일(아이디)")
                                                    .type(JsonFieldType.STRING),
                                PayloadDocumentation.fieldWithPath("data.hp")
                                                    .description("전화번호(숫자만 11자리)")
                                                    .type(JsonFieldType.STRING),
                                PayloadDocumentation.fieldWithPath("data.name")
                                                    .description("이름")
                                                    .type(JsonFieldType.STRING),
                                PayloadDocumentation.fieldWithPath("data.withdraw")
                                                    .description("탈퇴여부")
                                                    .type(JsonFieldType.BOOLEAN),
                                PayloadDocumentation.fieldWithPath("data.status")
                                                    .description(EnumStringCreator.memberStatus())
                                                    .type(JsonFieldType.STRING)
                            )
                        )
                    );
    }
}
