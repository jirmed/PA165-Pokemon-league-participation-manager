package cz.muni.fi.pa165.pokemon.league.participation.manager.facade;

import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.PokemonSpeciesBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.ChangePreevolutionDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.ChangeTypingDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonSpeciesCreateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonSpeciesDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.builders.PokemonSpeciesDTOBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.PokemonSpecies;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.EvolutionChainTooLongException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.NoSuchEntityException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.BeanMappingService;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.PokemonSpeciesService;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.config.ServiceConfiguration;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Rule;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import org.springframework.test.context.ContextConfiguration;

/**
 * Tests for pokemon species facade.
 *
 * @author Tamás Rózsa 445653
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class PokemonSpeciesFacadeTest {

    @Mock
    private PokemonSpeciesService pokemonSpeciesService;

    @Mock
    private BeanMappingService beanMappingService;

    @InjectMocks
    private final PokemonSpeciesFacadeImpl pokemonSpeciesFacade = new PokemonSpeciesFacadeImpl();

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private static PokemonSpeciesDTO pikachuSpecies;
    private static PokemonSpeciesDTO raichuSpecies;

    private static PokemonSpecies pikachuEntity;
    private static PokemonSpecies raichuEntity;
    private static PokemonSpecies rockEntity;

    public PokemonSpeciesFacadeTest() {
    }

    @BeforeClass
    public static void setUpClass() {

        pikachuSpecies = new PokemonSpeciesDTOBuilder()
                .id(1L)
                .speciesName("Electric")
                .primaryType(PokemonType.ELECTRIC)
                .build();

        raichuSpecies = new PokemonSpeciesDTOBuilder()
                .id(2L)
                .speciesName("Electric")
                .primaryType(PokemonType.ELECTRIC)
                .evolvesFrom(pikachuSpecies)
                .build();

        pikachuEntity = new PokemonSpeciesBuilder()
                .id(1L)
                .speciesName("Electric")
                .primaryType(PokemonType.ELECTRIC)
                .build();

        raichuEntity = new PokemonSpeciesBuilder()
                .id(2L)
                .speciesName("Electric")
                .primaryType(PokemonType.ELECTRIC)
                .evolvesFrom(pikachuEntity)
                .build();

        rockEntity = new PokemonSpeciesBuilder()
                .speciesName("Rock")
                .primaryType(PokemonType.ROCK)
                .secondaryType(PokemonType.GROUND)
                .build();
    }

    @Before
    public void setUp() throws EvolutionChainTooLongException {
        when(beanMappingService.mapTo(pikachuEntity, PokemonSpeciesDTO.class)).thenReturn(pikachuSpecies);
    }

    @Test
    public void testFindByID() {
        when(pokemonSpeciesService.findPokemonSpeciesById(pikachuSpecies.getId())).thenReturn(pikachuEntity);
        PokemonSpeciesDTO found = pokemonSpeciesFacade.findPokemonSpeciesById(pikachuSpecies.getId());
        assertThat(found.getId()).isEqualTo(pikachuSpecies.getId());
    }

    @Test
    public void testCreatePokemonSpecies() throws Exception {
        PokemonSpeciesCreateDTO rockSpecies;
        rockSpecies = new PokemonSpeciesCreateDTO();
        rockSpecies.setSpeciesName("Rock");
        rockSpecies.setPrimaryType(PokemonType.ROCK);
        rockSpecies.setSecondaryType(PokemonType.GROUND);
        when(beanMappingService.mapTo(rockSpecies, PokemonSpecies.class)).thenReturn(rockEntity);
        assertThat(pokemonSpeciesFacade.createPokemonSpecies(rockSpecies))
                .isEqualTo(rockEntity.getId());
        verify(pokemonSpeciesService, atLeastOnce()).createPokemonSpecies(rockEntity);
    }

    @Test
    public void testCreatePokemonSpeciesNoSuchEntityException() throws Exception {
        PokemonSpeciesCreateDTO rockSpecies;
        rockSpecies = new PokemonSpeciesCreateDTO();
        rockSpecies.setSpeciesName("Rock");
        rockSpecies.setPrimaryType(PokemonType.ROCK);
        rockSpecies.setSecondaryType(PokemonType.GROUND);
        rockSpecies.setEvolvesFromId(Long.MAX_VALUE);
        
        assertThatExceptionOfType(NoSuchEntityException.class)
                .isThrownBy(() -> pokemonSpeciesFacade.createPokemonSpecies(rockSpecies));
    }

    @Test
    public void testGetAllPokemonSpecies() {
        List<PokemonSpecies> pokemonEntities = Arrays.asList(new PokemonSpecies[]{pikachuEntity, raichuEntity});
        List<PokemonSpeciesDTO> pokemonDTOs = Arrays.asList(new PokemonSpeciesDTO[]{pikachuSpecies, raichuSpecies});
        when(pokemonSpeciesService.getAllPokemonSpecies()).thenReturn(pokemonEntities);
        when(beanMappingService.mapTo(pokemonEntities, PokemonSpeciesDTO.class)).thenReturn(pokemonDTOs);
        assertThat(pokemonSpeciesFacade.getAllPokemonSpecies())
                .usingFieldByFieldElementComparator().containsOnly(pikachuSpecies, raichuSpecies);
    }

    @Test
    public void testGetAllEvolutionsOfPokemonSpecies() {
        List<PokemonSpecies> pikachuEntityList = Arrays.asList(new PokemonSpecies[]{pikachuEntity});
        List<PokemonSpeciesDTO> pikachuSpeciesList = Arrays.asList(new PokemonSpeciesDTO[]{pikachuSpecies});
        when(pokemonSpeciesService.findPokemonSpeciesById(2L)).thenReturn(raichuEntity);
        when(pokemonSpeciesService.getAllEvolutionsOfPokemonSpecies(raichuEntity)).thenReturn(pikachuEntityList);
        when(beanMappingService.mapTo(pikachuEntityList, PokemonSpeciesDTO.class)).thenReturn(pikachuSpeciesList);
    }

    @Test
    public void testChangeTyping() throws NoSuchEntityException {
        ChangeTypingDTO pokemon = new ChangeTypingDTO();
        pokemon.setId(15L);
        pokemon.setPrimaryType(PokemonType.FIRE);
        when(pokemonSpeciesService.findPokemonSpeciesById(15L)).thenReturn(rockEntity);

        doAnswer(invocation -> {
            rockEntity.setPrimaryType(PokemonType.FIRE);
            return null;
        }).when(pokemonSpeciesService).changeTyping(rockEntity, PokemonType.FIRE, null);

        pokemonSpeciesFacade.changeTyping(pokemon);
        assertThat(rockEntity.getPrimaryType()).isEqualTo(PokemonType.FIRE);
    }

    @Test
    public void testChangeTypingNoSuchEntityException() {
        ChangeTypingDTO pokemon = new ChangeTypingDTO();
        pokemon.setId(15L);
        pokemon.setPrimaryType(PokemonType.FIRE);

        assertThatExceptionOfType(NoSuchEntityException.class).isThrownBy(() -> pokemonSpeciesFacade.changeTyping(pokemon));
    }

    @Test
    public void testChangePreevolution() throws Exception {
        ChangePreevolutionDTO pokemon = new ChangePreevolutionDTO();
        pokemon.setId(rockEntity.getId());
        pokemon.setEvolvesFrom(pikachuEntity.getId());
        when(pokemonSpeciesService.findPokemonSpeciesById(rockEntity.getId()))
                .thenReturn(rockEntity);
        when(pokemonSpeciesService.findPokemonSpeciesById(pikachuEntity.getId()))
                .thenReturn(pikachuEntity);

        doAnswer(invocation -> {
            rockEntity.setEvolvesFrom(pikachuEntity);
            return null;
        }).when(pokemonSpeciesService).changePreevolution(rockEntity, pikachuEntity);

        pokemonSpeciesFacade.changePreevolution(pokemon);
        assertThat(rockEntity.getEvolvesFrom()).isEqualTo(pikachuEntity);
    }

    @Test
    public void testChangePreevolutionToNull() throws Exception {
        ChangePreevolutionDTO pokemon = new ChangePreevolutionDTO();
        pokemon.setId(rockEntity.getId());
        pokemon.setEvolvesFrom(null);
        when(pokemonSpeciesService.findPokemonSpeciesById(rockEntity.getId()))
                .thenReturn(rockEntity);

        doAnswer(invocation -> {
            rockEntity.setEvolvesFrom(null);
            return null;
        }).when(pokemonSpeciesService).changePreevolution(rockEntity, null);

        pokemonSpeciesFacade.changePreevolution(pokemon);
        assertThat(rockEntity.getEvolvesFrom()).isNull();
    }

    @Test
    public void testChangePreevolutionToNonExistentId() throws Exception {
        ChangePreevolutionDTO pokemon = new ChangePreevolutionDTO();
        pokemon.setId(rockEntity.getId());
        pokemon.setEvolvesFrom(15L);

        when(pokemonSpeciesService.findPokemonSpeciesById(rockEntity.getId()))
                .thenReturn(rockEntity);

        assertThatExceptionOfType(NoSuchEntityException.class)
                .isThrownBy(() -> pokemonSpeciesFacade.changePreevolution(pokemon));
    }

    @Test
    public void testChangePreevolutionOfNonExistentId() throws Exception {
        ChangePreevolutionDTO pokemon = new ChangePreevolutionDTO();
        pokemon.setId(15L);
        pokemon.setEvolvesFrom(pikachuEntity.getId());

        assertThatExceptionOfType(NoSuchEntityException.class)
                .isThrownBy(() -> pokemonSpeciesFacade.changePreevolution(pokemon));
    }

    @Test
    public void testRemovePokemonSpecies() throws Exception {
        when(pokemonSpeciesService.findPokemonSpeciesById(3L)).thenReturn(rockEntity);
        pokemonSpeciesFacade.removePokemonSpecies(3L);
        verify(pokemonSpeciesService, atLeastOnce()).remove(rockEntity);
    }

    @Test
    public void testRemovePokemonSpeciesNoSuchEntity() throws Exception {
        assertThatExceptionOfType(NoSuchEntityException.class)
                .isThrownBy(() -> pokemonSpeciesFacade.removePokemonSpecies(15L));
    }
}
