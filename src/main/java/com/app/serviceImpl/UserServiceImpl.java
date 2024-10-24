package com.app.serviceImpl;

import com.app.POJO.User;
import com.app.contens.AppConstants;
import com.app.dao.UserDao;
import com.app.service.UserService;
import com.app.utils.AppUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service //Business Logic
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside signup {}", requestMap);
        try {
            if (validateSignUpMap(requestMap)) {
                User user = userDao.findByEmailId(requestMap.get("email"));
                if (Objects.isNull(user)) {
                    userDao.save(getUserFromMap(requestMap));
                    return AppUtils.getResponseEntity("Successfully registered.", HttpStatus.OK);
                } else {
                    return AppUtils.getResponseEntity("Email already exists.", HttpStatus.BAD_REQUEST);
                }
            } else {
                return AppUtils.getResponseEntity(AppConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return AppUtils.getResponseEntity(AppConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    private boolean validateSignUpMap(Map<String , String> requestMap){
       if( requestMap.containsKey("name") && requestMap.containsKey("phoneNumber") && requestMap.containsKey("email")
                && requestMap.containsKey("password") ) {
           return true;
       }
       return false;
    }

    private User getUserFromMap(Map<String, String> reqMap){
        User user = new User();
        user.setName(reqMap.get("name"));
        user.setPhoneNumber(reqMap.get("phoneNumber"));
        user.setSurname(reqMap.get("surname"));
        user.setEmail(reqMap.get("email"));
        user.setPassword(reqMap.get("password"));
        user.setStatus("false");
        user.setStatus(reqMap.get("role"));
        return user;

    }
}
