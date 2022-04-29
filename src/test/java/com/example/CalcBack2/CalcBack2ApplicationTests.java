package com.example.CalcBack2;

import com.example.CalcBack2.DTO.ResultDTO;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.BodyInserters;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient(timeout = "PT1M")//30 seconds
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor(onConstructor_ = @Autowired)

class CalcBack2ApplicationTests {

	private String serverURL;

	@LocalServerPort
	private int port;

	private final WebTestClient webTestClient;

	@Mock
	private HttpServletRequest request;

	@BeforeAll
	public void setUp(){
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		serverURL = String.format("%s:%s", "localhost", port);

	}
	
	@Test
	void save() {
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setInput("58*63");


		ResultDTO save = this.webTestClient
				.post()
				.uri(serverURL + "/api/service/result/")
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.body(BodyInserters.fromValue(resultDTO))
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(ResultDTO.class)
				.returnResult()
				.getResponseBody();


		assertNotNull(save);
		assertEquals("3654.0", save.getResult());

	}

	@Test
	void save2() {
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setInput("(2+2)*2");


		ResultDTO save2 = this.webTestClient
				.post()
				.uri(serverURL + "/api/service/result/")
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.body(BodyInserters.fromValue(resultDTO))
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(ResultDTO.class)
				.returnResult()
				.getResponseBody();


		assertNotNull(save2);
		assertEquals("8.0", save2.getResult());
	}

	@Test
	void save3 () {
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setInput("7*0");


		ResultDTO save3 = this.webTestClient
				.post()
				.uri(serverURL + "/api/service/result/")
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.body(BodyInserters.fromValue(resultDTO))
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(ResultDTO.class)
				.returnResult()
				.getResponseBody();


		assertNotNull(save3);
		assertEquals("0.0", save3.getResult());
	}

	@Test
	void save4() {
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setInput("75.64+53.12");


		ResultDTO save4 = this.webTestClient
				.post()
				.uri(serverURL + "/api/service/result/")
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.body(BodyInserters.fromValue(resultDTO))
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(ResultDTO.class)
				.returnResult()
				.getResponseBody();


		assertNotNull(save4);
		assertEquals("128.76", save4.getResult());

	}

	@Test
	void save5() {
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setInput("(76*21)/(26+34)");


		ResultDTO save5 = this.webTestClient
				.post()
				.uri(serverURL + "/api/service/result/")
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.body(BodyInserters.fromValue(resultDTO))
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(ResultDTO.class)
				.returnResult()
				.getResponseBody();


		assertNotNull(save5);
		assertEquals("26.6", save5.getResult());

	}


	@ParameterizedTest
	@ValueSource (strings = {""," ", "+++", "---"})
	@NullSource

	@Test
	void Test(String input) {
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setInput(input);

		assertThrows(AssertionError.class, () -> {
			ResultDTO Test = this.webTestClient
					.post()
					.uri(serverURL + "/api/service/result/")
					.contentType(APPLICATION_JSON)
					.accept(APPLICATION_JSON)
					.body(BodyInserters.fromValue(resultDTO))
					.exchange()
					.expectStatus().is2xxSuccessful()
					.expectBody(ResultDTO.class)
					.returnResult()
					.getResponseBody();

		});
	}

	@Test
	void getById() {
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setInput("(2+2)*2");

		ResultDTO save = this.webTestClient
				.post()
				.uri(serverURL + "/api/service/result/")
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.body(BodyInserters.fromValue(resultDTO))
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(ResultDTO.class)
				.returnResult()
				.getResponseBody();

		assertNotNull(save);


	}
}
