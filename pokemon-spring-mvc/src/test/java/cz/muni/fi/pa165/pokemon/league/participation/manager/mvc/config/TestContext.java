package cz.muni.fi.pa165.pokemon.league.participation.manager.mvc.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.BadgeFacade;
import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.GymFacade;
import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.PokemonFacade;
import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.PokemonSpeciesFacade;
import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.TrainerFacade;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.config.ServiceConfiguration;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Jiří Medveď 38451
 */
@EnableWebMvc
@Configuration
//@Import(ServiceConfiguration.class)
@ComponentScan(basePackages = {"cz.muni.fi.pa165.pokemon.league.participation.manager.mvc.controllers"})
public class TestContext implements WebMvcConfigurer {

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    @Primary
    public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH));

        objectMapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);

        jsonConverter.setObjectMapper(objectMapper);
        return jsonConverter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(customJackson2HttpMessageConverter());
    }

    @Bean
    public PokemonFacade pokemonFacade() {
        return Mockito.mock(PokemonFacade.class);
    }

    @Bean
    public GymFacade gymFacade() {
        return Mockito.mock(GymFacade.class);
    }

    @Bean
    public TrainerFacade trainerFacade() {
        return Mockito.mock(TrainerFacade.class);
    }

    @Bean
    public BadgeFacade badgeFacade() {
        return Mockito.mock(BadgeFacade.class);
    }

    @Bean
    public PokemonSpeciesFacade pokemonSpeciesFacade() {
        return Mockito.mock(PokemonSpeciesFacade.class);
    }
}
