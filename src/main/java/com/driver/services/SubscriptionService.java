package com.driver.services;


import com.driver.EntryDto.SubscriptionEntryDto;
import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;
import com.driver.repository.SubscriptionRepository;
import com.driver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    UserRepository userRepository;

    public Integer buySubscription(SubscriptionEntryDto subscriptionEntryDto){

        //Save The subscription Object into the Db and return the total Amount that user has to pay
        //We need to buy subscription and save its relevant subscription to the db and return the finalAmount
        User user = userRepository.findById(subscriptionEntryDto.getUserId()).get();

        Subscription subscription = new Subscription();
        subscription.setSubscriptionType(subscriptionEntryDto.getSubscriptionType());
        subscription.setNoOfScreensSubscribed(subscriptionEntryDto.getNoOfScreensRequired());

        SubscriptionType subType = subscription.getSubscriptionType();
        int amount = 0;

        if(subType == SubscriptionType.BASIC){
            amount = 500 + 200 * subscription.getNoOfScreensSubscribed();
        }
        else if(subType == SubscriptionType.PRO){
            amount = 800 + 250 * subscription.getNoOfScreensSubscribed();
        }
        else{
            amount = 1000 + 350 * subscription.getNoOfScreensSubscribed();
        }
        subscription.setTotalAmountPaid(amount);
        subscription.setUser(user);
        user.setSubscription(subscription);

        userRepository.save(user);
        return amount;
    }

    public Integer upgradeSubscription(Integer userId)throws Exception{

        //If you are already at an ElITE subscription : then throw Exception ("Already the best Subscription")
        //In all other cases just try to upgrade the subscription and tell the difference of price that user has to pay
        //update the subscription in the repository
        User user = userRepository.findById(userId).get();

        Subscription subscription = user.getSubscription();
        SubscriptionType subType = subscription.getSubscriptionType();
        int totAmount = 0;
        if(subType == SubscriptionType.ELITE){
            throw new Exception("Already the best Subscription");
        }
        else if(subType == SubscriptionType.BASIC){
            totAmount = 800 + 250 * subscription.getNoOfScreensSubscribed() - subscription.getTotalAmountPaid();
            subscription.setSubscriptionType(SubscriptionType.PRO);
        }
        else if(subType == SubscriptionType.PRO){
            totAmount = 1000 + 350 * subscription.getNoOfScreensSubscribed()-subscription.getTotalAmountPaid();
            subscription.setSubscriptionType(SubscriptionType.ELITE);
        }
        subscriptionRepository.save(subscription);
        return totAmount;
    }
    public Integer calculateTotalRevenueOfHotstar(){

        //We need to find out total Revenue of hotstar : from all the subscriptions combined
        //Hint is to use findAll function from the SubscriptionDb
        int totRevenue = 0;
        List<Subscription> subscriptionList = subscriptionRepository.findAll();

        for(Subscription s : subscriptionList){
            totRevenue += s.getTotalAmountPaid();
        }
        return totRevenue;
    }

}
