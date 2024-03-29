package com.driver.services;


import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;
import com.driver.model.WebSeries;
import com.driver.repository.UserRepository;
import com.driver.repository.WebSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    WebSeriesRepository webSeriesRepository;


    public Integer addUser(User user){

        //Just simply add the user to the Db and return the userId returned by the repository
        return userRepository.save(user).getId();
//        return user.getId();
    }

    public Integer getAvailableCountOfWebSeriesViewable(Integer userId){

        //Return the count of all webSeries that a user can watch based on his ageLimit and subscriptionType
        //Hint: Take out all the Webseries from the WebRepository
        User user = userRepository.findById(userId).get();
        Subscription subscription = user.getSubscription();
        SubscriptionType subType = subscription.getSubscriptionType();

        int count = 0;
        List<WebSeries> webSeriesList = webSeriesRepository.findAll();
        if(subType == SubscriptionType.BASIC){
            for(WebSeries web :webSeriesList){
                if(subType == SubscriptionType.BASIC){
                    count++;
                }
            }
        }else if(subType == SubscriptionType.PRO){
            for(WebSeries seb : webSeriesList){
                if(subType == SubscriptionType.PRO){
                    count++;
                }
            }
        }else {
            for(WebSeries web : webSeriesList){
                count++;
            }
        }
        return count;
    }
}
