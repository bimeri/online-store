package com.solution.onlinestore.services;

import com.solution.onlinestore.dto.EmailDto;
import com.solution.onlinestore.dto.PurchaseDto;
import com.solution.onlinestore.entity.Laptop;
import com.solution.onlinestore.entity.Ram;
import com.solution.onlinestore.repository.LaptopRepository;
import com.solution.onlinestore.repository.RamRepository;
import com.solution.onlinestore.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;


/**
 * @author Bimeri Noel
 * @date 5/22/2023
 */

@Service
public class ServiceImplementation<T> implements Services{

    private final LaptopRepository laptopRepository;
    private final RamRepository ramRepository;
    private final RestTemplate restTemplate;
    private final Environment environment;
    private final RabbitService rabbitService;

    @Autowired
    public ServiceImplementation(LaptopRepository laptopRepository, RestTemplate restTemplate, Environment environment, RamRepository ramRepository, RabbitService rabbitService) {
        this.laptopRepository = laptopRepository;
        this.restTemplate = restTemplate;
        this.environment = environment;
        this.ramRepository = ramRepository;
        this.rabbitService = rabbitService;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> purchase(PurchaseDto purchaseDto) {
        if (purchaseDto == null || purchaseDto.getQuantity() < 1) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        Optional<Laptop> laptops = laptopRepository.findByModelName(purchaseDto.getModelName());
        if (!laptops.isPresent()) {
            map.put("data", new ArrayList<>());
            map.put("erc", 0);
            map.put("response", Constants.ERROR_RESPONSE);
            return map;
        }
        Laptop laptop = laptops.get();
        if (laptop.getAvailableQty() < 1) {
            map.put("data", new ArrayList<>());
            map.put("erc", 1);
            map.put("response", Constants.RESPONSE_FINISHED_MESSAGE);
            return map;
        }
        if (laptop.getAvailableQty() < purchaseDto.getQuantity()) {
            map.put("data", new ArrayList<>());
            map.put("erc", 1);
            map.put("response", Constants.LIMITED_AMOUNT + laptop.getAvailableQty() + " left over");
            return map;
        }

        // case 1, update quantity to new quantity else zero, in other not to have negative value
        int quantity = laptop.getAvailableQty() - purchaseDto.getQuantity();
        int newQuantity = quantity < 1 ? 0 : quantity;
        laptop.setAvailableQty(newQuantity);
        Laptop ll = laptopRepository.save(laptop);

        System.out.printf("laptop updated: "+ ll.getAvailableQty());
        // case 2  do A REST call to an external Invoicing service
        String url = "http://localhost:8080/invoice-service/sendInvoice";
        doRestTemplateHttpPostRequest((T) purchaseDto, url);

        // case 3  do an api call to send email to email service
        List<Ram> ramsModel = ramRepository.findAllByLaptop_IdOrderByIdAsc(laptop.getId());
        EmailDto emailDto = new EmailDto();
        emailDto.setModelName(laptop.getModelName());
        emailDto.setMessage("quantity: " + purchaseDto.getQuantity());
        emailDto.setRams(ramsModel);

        url = "http://localhost:8080/email-service/sendMail";
        doRestTemplateHttpPostRequest((T) emailDto, url);

        String profile = Arrays.toString(environment.getActiveProfiles());
        profile = profile.toLowerCase();
        if (profile.contains("mq_notification")) {
            rabbitService.sendNotificationToRabbit(emailDto);
        }

        map.put("data", laptop);
        map.put("erc", 1);
        map.put("response", Constants.SUCCESS_PURCHASE);
        return map;
    }


    public void doRestTemplateHttpPostRequest(T t, String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<T> entity = new HttpEntity<>(t, headers);
        ResponseEntity<Object> stringResponseEntity = restTemplate.postForEntity(url, entity, Object.class);

        stringResponseEntity.getBody();

    }

}
