package com.visu.sb.ams.controller;

import com.visu.sb.ams.service.AccountService;
import com.visu.sb.ams.util.OperationResult;
import com.visu.sb.ams.util.Status;
import com.visu.sb.ams.util.TestConstant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.BDDMockito.given;

import static org.mockito.Matchers.anyObject;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountServiceRestController.class)
public class AccountServiceRestControllerTest {

    private static final Status STATUS = Status.SUCCESS;
    private static final String DESC = TestConstant.OPERATION_PROCESSED_SUCCESSFULLY;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AccountService accountService;

    @Test
    public void testPut() throws Exception {
        OperationResult operationResult = new OperationResult(STATUS, DESC);
        given(accountService.put(anyObject(), anyObject())).willReturn(operationResult);

        MvcResult result = mvc.perform(patch("/service/account/put")
                .param("id", "1")
                .param("amount", "100")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String expected = "{\"status\":\"" + STATUS + "\",\"description\":\"" + DESC + "\"}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testGet() throws Exception {
        OperationResult operationResult = new OperationResult(STATUS, DESC);
        given(accountService.get(anyObject(), anyObject())).willReturn(operationResult);

        MvcResult result = mvc.perform(patch("/service/account/get")
                .param("id", "1")
                .param("amount", "100")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String expected = "{\"status\":\"" + STATUS + "\",\"description\":\"" + DESC + "\"}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testTransfer() throws Exception {
        OperationResult operationResult = new OperationResult(STATUS, DESC);
        given(accountService.transfer(anyObject(), anyObject(), anyObject())).willReturn(operationResult);

        MvcResult result = mvc.perform(patch("/service/account/transfer")
                .param("senderId", "1")
                .param("receiverId", "2")
                .param("amount", "100")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String expected = "{\"status\":\"" + STATUS + "\",\"description\":\"" + DESC + "\"}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }
}