package com.driver.services;


import com.driver.EntryDto.ProductionHouseEntryDto;
import com.driver.EntryDto.WebSeriesEntryDto;
import com.driver.model.ProductionHouse;
import com.driver.model.WebSeries;
import com.driver.repository.ProductionHouseRepository;
import com.driver.repository.WebSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductionHouseService {

    @Autowired
    ProductionHouseRepository productionHouseRepository;

    public Integer addProductionHouseToDb(ProductionHouseEntryDto productionHouseEntryDto){

        ProductionHouse productionHouse = new ProductionHouse();
        productionHouse.setName(productionHouseEntryDto.getName());
        productionHouse.setRatings(0.0);

        ProductionHouse newProductionHouse = productionHouseRepository.save(productionHouse);
        return newProductionHouse.getId();
    }
}
