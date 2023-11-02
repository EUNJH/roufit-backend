package com.roufit.backend.domain.exercise.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roufit.backend.domain.exercise.application.ExerciseFindService;
import com.roufit.backend.domain.exercise.application.ExerciseService;
import com.roufit.backend.domain.exercise.domain.exercise.Equipment;
import com.roufit.backend.domain.exercise.domain.exercise.ExerciseType;
import com.roufit.backend.domain.exercise.dto.request.CategoryRequest;
import com.roufit.backend.domain.exercise.dto.request.ExerciseRequest;
import com.roufit.backend.domain.exercise.dto.response.ExerciseResponse;
import com.roufit.backend.global.config.SecurityConfiguration;
import com.roufit.backend.global.security.jwt.JwtAuthenticationFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ExerciseController.class,
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = {JwtAuthenticationFilter.class, SecurityConfiguration.class}
                )})
@MockBean(JpaMetamodelMappingContext.class)
class ExerciseControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ExerciseService exerciseService;
    @MockBean
    private ExerciseFindService exerciseFindService;

    @WithMockUser
    @Test
    public void create() throws Exception {
        //given
        ExerciseRequest request = ExerciseRequest.builder()
                .name("push up")
                .description("push up")
                .equipment(Equipment.BAND)
                .type(ExerciseType.COUNT)
                .category(1L)
                .build();

        //when & then
        mockMvc.perform(post("/api/v1/exercise")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @WithMockUser
    @Test
    public void getByCategory() throws Exception {
        //given
        ExerciseResponse response1 = ExerciseResponse.builder()
                .name("push up")
                .description("push up")
                .equipment("NONE")
                .type("COUNT")
                .build();
        ExerciseResponse response2 = ExerciseResponse.builder()
                .name("pull up")
                .description("pull up")
                .equipment("BAND")
                .type("COUNT")
                .build();
        given(exerciseFindService.findByCategory(any()))
                .willReturn(List.of(response1, response2));
        //when & then
        MvcResult result = mockMvc.perform(get("/api/v1/exercise/category/3")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<Map<String, String>> responseMap =
                new ObjectMapper().readValue(result.getResponse().getContentAsString(), List.class);

        assertThat(responseMap.get(0).get("name")).isEqualTo("push up");
        assertThat(responseMap.get(0).get("description")).isEqualTo("push up");
        assertThat(responseMap.get(0).get("equipment")).isEqualTo("NONE");
        assertThat(responseMap.get(0).get("type")).isEqualTo("COUNT");

        assertThat(responseMap.get(1).get("name")).isEqualTo("pull up");
        assertThat(responseMap.get(1).get("description")).isEqualTo("pull up");
        assertThat(responseMap.get(1).get("equipment")).isEqualTo("BAND");
        assertThat(responseMap.get(1).get("type")).isEqualTo("COUNT");
    }
}