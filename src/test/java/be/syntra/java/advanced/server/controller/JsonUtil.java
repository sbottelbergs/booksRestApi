package be.syntra.java.advanced.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MvcResult;

@Component
public class JsonUtil {

    private ObjectMapper mapper;

    @Autowired
    public JsonUtil(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    <T> T fromJsonResult(MvcResult result, Class<T> clazz) throws Exception {
        return this.mapper.readValue(result.getResponse().getContentAsString(), clazz);
    }

    private byte[] toJson(Object object) throws Exception {
        return this.mapper.writeValueAsString(object).getBytes();
    }
}
