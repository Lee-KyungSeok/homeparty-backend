package com.homeparty.api;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
//@ActiveProfiles("test")
//public class AppControllerTest {
//
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private WebTestClient webTestClient;
//
//    @BeforeEach
//    public void setUp(RestDocumentationContextProvider restDocumentation) {
//        this.webTestClient = WebTestClient
//                .bindToServer().baseUrl("http://localhost:" + port)
//                .filter(documentationConfiguration(restDocumentation))
//                .build();
//    }
//
//    @DisplayName("테스트 잘 되는지 확인")
//    @Test
//    public void Test_Identity() {
//        String provider = "kakao";
//        UUID identityId = UUID.fromString("611da390-40a5-4ce4-afdb-f29b0073877a");
//        String url = "/api/identities/{identityId}?provider=" + provider;
//        EntityExchangeResult<Identity> result = this.webTestClient.get().uri(url, identityId).accept(MediaType.APPLICATION_JSON)
//                .exchange().expectStatus().isOk().expectBody(Identity.class)
//                .consumeWith(document("identities",
//                        preprocessResponse(prettyPrint()),
//                        pathParameters(
//                                parameterWithName("identityId")
//                                        .attributes(constraints("샘플이야"))
//                                        .description("인증 id")
//                        ),
//                        queryParameters(
//                                parameterWithName("age")
//                                        .optional()
//                                        .description("나이"),
//                                parameterWithName("provider")
//                                        .attributes(constraints("소셜인증은 필수야"))
//                                        .description("소셜인증 제공자")
//                        ),
//                        responseFields(
//                                fieldWithPath("sequence").type(JsonFieldType.STRING).optional().description("인증 순서"),
//                                fieldWithPath("id").type(JsonFieldType.STRING).description("인증 Id"),
//                                fieldWithPath("provider").type(JsonFieldType.STRING).description("소셜인증 제공자")
//                        )
//                )).returnResult();
//
//        Identity body = result.getResponseBody();
//        assertThat(body.getId()).isEqualTo(identityId);
//        assertThat(body.getProvider()).isEqualTo(provider);
//    }
//
//    public Attributes.Attribute constraints(final String value){
//        return new Attributes.Attribute("constraints", value);
//    }
//
//    public Attributes.Attribute field(
//            final String key,
//            final String value){
//        return new Attributes.Attribute(key,value);
//    }
//}
