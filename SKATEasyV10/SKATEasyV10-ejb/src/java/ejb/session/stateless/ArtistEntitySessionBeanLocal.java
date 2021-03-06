/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ArtistEntity;
import javax.ejb.Local;
import util.exception.ArtistNotFoundException;
import util.exception.ArtistUsernameExistException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author harmo
 */
@Local
public interface ArtistEntitySessionBeanLocal {
    
    public ArtistEntity createNewArtist(ArtistEntity newArtistEntity) throws ArtistUsernameExistException, UnknownPersistenceException, InputDataValidationException;
    
    public ArtistEntity retrieveArtistByUsername(String username) throws ArtistNotFoundException;

    public ArtistEntity retrieveArtistById(Long artistId) throws ArtistNotFoundException;

    public void updateArtist(ArtistEntity artist);
}
