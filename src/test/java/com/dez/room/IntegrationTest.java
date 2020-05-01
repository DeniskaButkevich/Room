package com.dez.room;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = {"/country-list-before.sql", "/room-list-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/country-list-after.sql", "/room-list-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@TestPropertySource("/application-test.properties")
public class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void roomPageTest() throws Exception {
        this.mockMvc.perform(get("/room/1")
                .with(request -> { request.setRemoteAddr("37.215.3.8"); return request; }))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(xpath("//*[@id=\"body_room\"]/div[1]/h2").string("You in the room number 1 with name Belarus"));
    }

    @Test
    public void indexPageTest() throws Exception {
        this.mockMvc.perform(get("/")
                .with(request -> { request.setRemoteAddr("127.0.0.1"); return request; }))
                .andDo(print())
                .andExpect(xpath("//html/body/div[1]/h2").string("Your Country is Anonymous"));
    }

    @Test
    public void countryВefinitionTest() throws Exception {
        this.mockMvc.perform(get("/")
                .with(request -> { request.setRemoteAddr("37.215.3.8"); return request; }))
                .andDo(print())
                .andExpect(xpath("//html/body/div[1]/h2").string("Your Country is Belarus"));
    }

    @Test
    public void getCountryTest() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(xpath("//*[@id='exampleFormControlSelect1']/option").nodeCount(4))
                .andExpect(xpath("//*[@id='exampleFormControlSelect1']/option[@value='222']").exists())
                .andExpect(xpath("//*[@id='exampleFormControlSelect1']/option[@value='36']").exists())
                .andExpect(xpath("//*[@id='exampleFormControlSelect1']/option[@value='111']").exists())
                .andExpect(xpath("//*[@id='exampleFormControlSelect1']/option[@value='56']").exists());
    }

    @Test
    public void addRoomTest() throws Exception {
        MockHttpServletRequestBuilder multipart = multipart("/")
                .param("name", "TestBelarusRoom")
                .param("id", "36");

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(xpath("//html/body/div[1]/table/tbody/tr").nodeCount(5))
                .andExpect(xpath("//*[@id='th-10']").exists())
                .andExpect(xpath("//*[@id='th-10']/td[1]").string("TestBelarusRoom"));
    }
}
