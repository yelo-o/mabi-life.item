package life.mabi.demo.exceptions.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import life.mabi.demo.exceptions.ErrorObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ObjectMapper mapper = new ObjectMapper();
        //Create Response Content
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode((HttpServletResponse.SC_UNAUTHORIZED));
        errorObject.setMessage("요청된 리소스에 대한 유효한 인증 자격증명이 실패하였습니다!");

        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(errorObject);
        response.getWriter().write(json);

    }
}
