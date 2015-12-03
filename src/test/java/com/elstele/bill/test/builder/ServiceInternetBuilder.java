package com.elstele.bill.test.builder;

import com.elstele.bill.domain.*;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Enums.Status;
import org.apache.commons.lang.RandomStringUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

public class ServiceInternetBuilder {
    private ServiceInternet service;
    Random random = new Random();

    public ServiceInternetBuilder build() {
        service = new ServiceInternet();
        service.setStatus(Status.ACTIVE);
        return this;
    }

    public ServiceInternet getRes() {
        if (service == null){
            build();
        }
        return service;
    }

    public ServiceInternetBuilder randomService(){
        Constants.Period periodRandom = Constants.Period.values()[(int) (Math.random() * Constants.Period.values().length)];
        service.setPeriod(periodRandom);
        service.setDateStart(getTimestamp());
        service.setUsername(RandomStringUtils.randomAlphanumeric(4));
        service.setPassword(RandomStringUtils.randomAlphanumeric(4));

        AccountBuilder ab = new AccountBuilder();
        Account account = ab.build().withAccName(RandomStringUtils.randomAlphanumeric(8)).withAccType(Constants.AccountType.PRIVATE).withBalance(random.nextFloat()).withRandomPhyAddress().getRes();
        service.setAccount(account);

        ServiceTypeBuilder stb = new ServiceTypeBuilder();
        ServiceType serviceType = stb.build().withName(RandomStringUtils.randomAlphanumeric(8)).withServiceType(Constants.SERVICE_PHONE).withPrice(random.nextFloat()).withRandomAttribute().getRes();
        service.setServiceType(serviceType);
        return this;
    }

    public ServiceInternetBuilder withAccount(Account account){
        service.setAccount(account);
        return this;
    }
    public ServiceInternetBuilder withServiceType(ServiceType serviceType){
        service.setServiceType(serviceType);
        return this;
    }
    private Timestamp getTimestamp(){
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp;
    }
}
