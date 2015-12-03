package com.elstele.bill.test.builder;

import com.elstele.bill.domain.Account;
import com.elstele.bill.domain.Service;
import com.elstele.bill.domain.ServiceType;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Enums.Status;
import org.apache.commons.lang.RandomStringUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

public class ServiceMarkerBuilder{
    private Service service;
    Random random = new Random();

    public ServiceMarkerBuilder build() {
        service = new Service();
        service.setStatus(Status.ACTIVE);
        return this;
    }

    public Service getRes() {
        if (service == null){
            build();
        }
        return service;
    }

    public ServiceMarkerBuilder randomService(){
        Constants.Period periodRandom = Constants.Period.values()[(int) (Math.random() * Constants.Period.values().length)];
        service.setPeriod(periodRandom);
        service.setDateStart(getTimestamp());

        AccountBuilder ab = new AccountBuilder();
        Account account = ab.build().withAccName(RandomStringUtils.randomAlphanumeric(8)).withAccType(Constants.AccountType.PRIVATE).withBalance(random.nextFloat()).withRandomPhyAddress().getRes();
        service.setAccount(account);

        ServiceTypeBuilder stb = new ServiceTypeBuilder();
        ServiceType serviceType = stb.build().withName(RandomStringUtils.randomAlphanumeric(8)).withServiceType(Constants.SERVICE_PHONE).withPrice(random.nextFloat()).withRandomAttribute().getRes();
        service.setServiceType(serviceType);
        return this;
    }

    public ServiceMarkerBuilder withAccount(Account account){
        service.setAccount(account);
        return this;
    }
    public ServiceMarkerBuilder withServiceType(ServiceType serviceType){
        service.setServiceType(serviceType);
        return this;
    }
    private Timestamp getTimestamp(){
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp;
    }
}
