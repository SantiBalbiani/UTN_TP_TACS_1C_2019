package findYourPlace.service.impl;

import findYourPlace.entity.Place;
import findYourPlace.entity.PlaceList;
import findYourPlace.entity.User;
import findYourPlace.entity.exception.ElementAlreadyExistsException;
import findYourPlace.entity.exception.ElementDoesNotExistException;
import findYourPlace.mongoDB.UserDao;
import findYourPlace.service.FourSquareService;
import findYourPlace.service.PlaceService;
import findYourPlace.service.UserService;
import findYourPlace.service.impl.exception.CouldNotRetrieveElementException;
import findYourPlace.service.impl.exception.CouldNotSaveElementException;
import findYourPlace.utils.Encrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PlaceService placeService;

    @Autowired
    private FourSquareService fourSquareService;


    @Override
    public User createUser(User user) throws CouldNotSaveElementException {
        try {
            user.setPassword(Encrypt.encrypt(user.getPassword()));
            return userDao.save(user);
        } catch (Exception ex){
            throw new CouldNotSaveElementException(ex.getMessage());
        }
    }


    @Override
    public User getUserByUsername(String username) throws CouldNotRetrieveElementException {
        User user = userDao.findByUsername(username);
        if (user == null)
            throw new CouldNotRetrieveElementException("");
        return user;
    }


    @Override
    public User getUser(String userId) throws CouldNotRetrieveElementException {
        try {
            User user = userDao.findById(userId).get();
            for(PlaceList placeList:user.getPlaceLists()){
                for(Place place:placeList.getPlaces()){
                    place.setListName(placeList.getName());
                    place.setUserId(userId);
                    try {
                        Place placeFS = fourSquareService.getPlaceById(place.getFortsquareId());
                        place.setAddress(placeFS.getAddress());
                        place.setCc(placeFS.getCc());
                        place.setCity(placeFS.getCity());
                        place.setCountry(placeFS.getCountry());
                        place.setLatitude(placeFS.getLatitude());
                        place.setLongitude(placeFS.getLongitude());
                        place.setPostalCode(placeFS.getPostalCode());
                        place.setState(placeFS.getState());
                        place.setName(placeFS.getName());
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            return user;
        } catch (Exception ex){
            throw new CouldNotRetrieveElementException(ex.getMessage());
        }
    }

    @Override
    public void deleteUser(String userName) throws CouldNotRetrieveElementException {
        try{
            userDao.deleteUserByUsername(userName);
        } catch (Exception ex){
            throw new CouldNotRetrieveElementException(ex.getMessage());
        }
    }

    @Override
    public List<PlaceList> getUserPlaces(String username) throws CouldNotRetrieveElementException {
        return getUserByUsername(username).getPlaceLists();
    }

    @Override
    public User createUserPlaces(String username, PlaceList placeList) throws CouldNotSaveElementException {
        try {
            User user = getUserByUsername(username);
            user.createPlaceList(placeList);
            userDao.save(user);
            return user;
        } catch (Exception ex){
            throw new CouldNotSaveElementException(ex.getMessage());
        }
    }

    @Override
    public  PlaceList getUserPlacesByName(String username, String placeListName) throws  CouldNotRetrieveElementException {
        try{
            User user = getUserByUsername(username);
            return user.findPlaceListByName(placeListName);
        } catch (Exception ex){
            throw new CouldNotRetrieveElementException(ex.getMessage());
        }
    }

    @Override
    public User deleteUserPlaces(String username, String placeListName) throws CouldNotRetrieveElementException {
        try{
            User user = getUserByUsername(username);
            user.removePlaceList(placeListName);
            userDao.save(user);
            return user;
        } catch (Exception ex){
            throw new CouldNotRetrieveElementException(ex.getMessage());
        }
    }

    @Override
    public PlaceList modifyUserPlaces(String username, String placeListCurrentName, String placeListNewName) throws CouldNotRetrieveElementException {
        try {
            User user = getUserByUsername(username);
            updatePlaces(user.getId(), placeListCurrentName, placeListNewName, user);
            user.modifyPlaceList(placeListCurrentName, placeListNewName);
            userDao.save(user);
            return user.findPlaceListByName(placeListNewName);
        } catch (Exception ex){
            throw new CouldNotRetrieveElementException(ex.getMessage());
        }
    }

    private void updatePlaces(String userId, String placeListCurrentName, String placeListNewName, User user) {
        PlaceList placeList = user.findPlaceListByName(placeListCurrentName);
        for(Place place : placeList.getPlaces()){
            place.setListName(placeListCurrentName);
            place.setUserId(userId);
            placeService.updatePlaceListName(place,placeListNewName);
        }
    }

    @Override
    public PlaceList addPlaceToPlaceList(String username, String placeListName, Place place) throws CouldNotRetrieveElementException{
        try {
            User user = getUserByUsername(username);

            //save place as subordinated object in user/placeList
            user.addPlaceToPlaceList(placeListName, place);
            userDao.save(user);

            //save place as independent object
            place.setUserId(user.getId());
            place.setListName(placeListName);
            placeService.save(place);

            return user.findPlaceListByName(placeListName);
        } catch (ElementDoesNotExistException ex) {
            //List does not exist
            throw new CouldNotRetrieveElementException(ex.getMessage());
        } catch (ElementAlreadyExistsException ex){
            //Element already belongs to list
            throw new CouldNotSaveElementException(ex.getMessage());
        }
    }

    @Override
    public PlaceList addPlaceToPlaceList(String userId, String placeListName, String placeId) throws CouldNotRetrieveElementException{
        try {
            User user = getUser(userId);
            Place place = new Place(placeId);

            //save place as subordinated object in user/placeList
            user.addPlaceToPlaceList(placeListName, place);
            userDao.save(user);

            //save place as independent object
            place.setUserId(user.getId());
            place.setListName(placeListName);
            placeService.save(place);

            return user.findPlaceListByName(placeListName);
        } catch (ElementDoesNotExistException ex) {
            //List does not exist
            throw new CouldNotRetrieveElementException(ex.getMessage());
        } catch (ElementAlreadyExistsException ex){
            //Element already belongs to list
            throw new CouldNotSaveElementException(ex.getMessage());
        }
    }

    @Override
    public Place getPlaceFromPlaceList(String username, String placeListName, String placeId) throws CouldNotRetrieveElementException {
        try {
            User user = getUserByUsername(username);
            return user.getPlaceFromPlaceList(placeListName, placeId);
        } catch (Exception ex){
            throw new CouldNotRetrieveElementException(ex.getMessage());
        }
    }

    @Override
    public PlaceList deletePlaceFromPlaceList(String username, String placeListName, String placeId) throws CouldNotRetrieveElementException {
        try {
            User user = getUserByUsername(username);
            user.deletePlaceFromPlaceList(placeListName, placeId);
            userDao.save(user);
            placeService.deleteByComposedIndex(placeId,user.getId(),placeListName);
            return user.findPlaceListByName(placeListName);
        } catch (Exception ex){
            throw new CouldNotRetrieveElementException(ex.getMessage());
        }
    }

    @Override
    public Place markPlaceAsVisited(String username, String placeListName, String placeId)
            throws CouldNotRetrieveElementException {
        try {
            User user = getUserByUsername(username);
            Place place = user.getPlaceFromPlaceList(placeListName, placeId);
            place.setVisited(true);
            userDao.save(user);
            updatePlace(user.getId(), placeListName, place);
            return place;
        } catch (Exception ex){
            throw new CouldNotRetrieveElementException(ex.getMessage());
        }
    }

    private void updatePlace(String userId, String placeListName, Place place) {
        Place placeInPlaceCollection = new Place(place.getFortsquareId());
        placeInPlaceCollection.setUserId(userId);
        placeInPlaceCollection.setListName(placeListName);
        placeInPlaceCollection.setVisited(place.getVisited());
        placeService.updatePlace(placeInPlaceCollection);
    }

    @Override
    public List<User> getUsers() throws CouldNotRetrieveElementException {
        try {
            List<User> users = userDao.findAll();
            return users;
        } catch (Exception e){
            throw new CouldNotRetrieveElementException(e.getMessage());
        }
    }

}
