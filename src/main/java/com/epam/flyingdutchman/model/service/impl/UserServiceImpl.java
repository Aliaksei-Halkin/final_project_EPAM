package com.epam.flyingdutchman.model.service.impl;

import com.epam.flyingdutchman.entity.User;

public class UserServiceImpl {


    public   boolean isUsernameNotFree(String username) {

        return true;
    }
    public boolean registerNewUser(User user){
        return true;
    }

    public boolean isPhoneNotFree(String userName) {return  true;
    }

    public boolean isEmailNotFree(String userName) {return true;
    }
}
