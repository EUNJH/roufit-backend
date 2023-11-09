package com.roufit.backend.domain.workout.domain.template;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SetTemplateTest {

    @Test
    public void increaseIntensity_시간() throws Exception {
        //given
        InitTemplate template = new InitTemplate();
        SetTemplate setTemplate = template.setTemplates.get(0);
        //when
        setTemplate.increaseIntensity();
        //then
        assertThat(setTemplate.getGoalTime()).isEqualTo(null);
        assertThat(setTemplate.getGoalRepetition()).isEqualTo(13);
    }

    @Test
    public void increaseIntensity_갯수() throws Exception {
        //given
        InitTemplate template = new InitTemplate();
        SetTemplate setTemplate = template.setTemplates.get(1);
        //when
        setTemplate.increaseIntensity();
        //then
        assertThat(setTemplate.getGoalTime()).isEqualTo(65);
        assertThat(setTemplate.getGoalRepetition()).isEqualTo(null);
    }
}