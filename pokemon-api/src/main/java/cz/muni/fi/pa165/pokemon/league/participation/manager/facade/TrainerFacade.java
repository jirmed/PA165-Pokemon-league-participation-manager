package cz.muni.fi.pa165.pokemon.league.participation.manager.facade;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerAuthenticateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerChangePasswordDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerCreateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerRenameDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonDTO;
import java.util.List;

/**
 * Facade interface for object Trainer.
 *
 * @author Jiří Medveď 38451
 */
public interface TrainerFacade {

    /**
     * Create a new trainer
     *
     * @param trainer Trainer to be created
     * @return new trainer id
     */
    public Long createTrainer(TrainerCreateDTO trainer);

    /**
     * Change trainer name and surname
     *
     * @param trainerRename trainer to be updated
     */
    public void renameTrainer(TrainerRenameDTO trainerRename);

    /**
     * Add a new Pokemon to a Trainer
     *
     * @param trainerId Trainer Id (owner)
     * @param pokemonId Added Pokemon Id
     */
    public void addPokemon(Long trainerId, Long pokemonId);

    /**
     * Remove a Pokemon from a Trainer
     *
     * @param trainerId Trainer Id (owner)
     * @param pokemonId Removed Pokemon Id
     */
    public void removePokemon(Long trainerId, Long pokemonId);

    /**
     * Get list of all trainers
     *
     * @return list of all trainers
     */
    public List<TrainerDTO> getAllTrainers();

    /**
     * Find a Trainer by Id
     *
     * @param trainerId Id of a Trainer to be found
     * @return Trainer found Trainer, null if not found
     */
    public TrainerDTO getTrainerWithId(Long trainerId);

    /**
     * Autenticate trainer with password
     *
     * @param trainer Trainer to be autenticated
     * @return true only if password matches stored hash
     */
    public Boolean authenticate(TrainerAuthenticateDTO trainer);

    /**
     * Change Trainer password
     *
     * @param trainerChangePassword trainer DTO with old and new password
     */
    public void changePassword(TrainerChangePasswordDTO trainerChangePassword);

    /**
     * Is Gym Leader?
     *
     * @param trainerId Trained id
     * @return true only if the Trainer is a Gym Leader
     */
    public Boolean isGymLeader(Long trainerId);

    /**
     * Get all own pokemons
     *
     * @param trainerId Trainer id
     * @return List of pokemon owned
     */
    public List<PokemonDTO> getOwnPokemons(Long trainerId);

}
