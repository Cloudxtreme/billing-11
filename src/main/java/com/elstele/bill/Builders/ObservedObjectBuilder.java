package com.elstele.bill.Builders;

import com.elstele.bill.domain.LocalUser;
import com.elstele.bill.domain.ObservedObject;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Enums.ObjectOperationType;
import com.elstele.bill.utils.Enums.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

public class ObservedObjectBuilder {
    private ObservedObject observedObject;
    private ObjectMapper mapper;
    final static Logger LOGGER = LogManager.getLogger(ObservedObjectBuilder.class);

    public ObservedObjectBuilder build() {
        mapper = new ObjectMapper();
        observedObject = new ObservedObject();
        return this;
    }

    public ObservedObjectBuilder withId(int val) {
        observedObject.setId(val);
        return this;
    }

    public ObservedObjectBuilder withStatus(Status val) {
        observedObject.setStatus(val);
        return this;
    }

    public ObservedObjectBuilder withObjId(int val) {
        observedObject.setObjId(val);
        return this;
    }

    public ObservedObjectBuilder withChangedObject(Object val) {
        try {
            String objectInString = mapper.writeValueAsString(val);
            observedObject.setChangedObject(objectInString);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return this;
    }

    public ObservedObjectBuilder withChangesDate(Date val) {
        observedObject.setChangesDate(val);
        return this;
    }

    public ObservedObjectBuilder withOperationType(ObjectOperationType val) {
        observedObject.setChangesType(val);
        return this;
    }

    public ObservedObjectBuilder withChangerName(HttpSession session) {
        LocalUser user = (LocalUser) session.getAttribute(Constants.LOCAL_USER);
        observedObject.setChanger(user.getUsername());
        return this;
    }

    public ObservedObject getRes() {
        if (observedObject == null) {
            build();
        }
        return observedObject;
    }
}
