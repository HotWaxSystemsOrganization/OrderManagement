package com.mayank.order.services;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.ServiceUtil;

import java.util.Map;

public class UpdateOrder {

    public static Map<String, Object> updateOrder(DispatchContext dctx, Map<String, ? extends Object> context) throws GenericEntityException {
        Delegator delegator = dctx.getDelegator();
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String orderId = (String) context.get("orderId");
        String address1 = (String) context.get("address1");
        String address2 = (String) context.get("address2");
        String city = (String) context.get("city");
        String stateProvinceGeoID = (String) context.get("stateProvinceGeoID");
        String postalCode = (String) context.get("postalCode");
        String countryGeoID = (String) context.get("countryGeoID");

        if (orderId == null) {
            return ServiceUtil.returnError("Missing orderId please enter orderId.");
        }

        GenericValue postal = EntityQuery.use(delegator).from("PostalAddress").where(
                "address1", address1,
                "address2", address2,
                "city", city,
                "stateProvinceGeoID", stateProvinceGeoID,
                "countryGeoID", countryGeoID,
                "postalCode", postalCode
        ).queryOne();

        String contactMechId = postal.getString("contactMechId");

        if (contactMechId == null) {
            contactMechId = delegator.getNextSeqId("ContactMech");
            GenericValue contactMech = delegator.makeValue("ContactMech", UtilMisc.toMap(
                    "contactMechId", contactMechId,
                    "contactMechTypeId", "POSTAL_ADDRESS"
            ));
            try {
                delegator.create(contactMech);
            } catch (GenericEntityException e) {
                return ServiceUtil.returnError("Error creating contact mech: " + e.getMessage());
            }
            GenericValue postaladd = delegator.makeValue("PostalAddress", UtilMisc.toMap(
                    "address1", address1,
                    "address2", address2,
                    "city", city,
                    "stateProvinceGeoID", stateProvinceGeoID,
                    "countryGeoID", countryGeoID,
                    "postalCode", postalCode
            ));
            try {
                delegator.create(postaladd);
            } catch (GenericEntityException e) {
                return ServiceUtil.returnError("Error creating postal address: " + e.getMessage());
            }
        }
        GenericValue ordercontact = EntityQuery.use(delegator).from("OrderContactMech").where("orderId", orderId).queryOne();
        ordercontact.set("contactMechId", contactMechId);
        ordercontact.store();
        result.put("orderId", orderId);
        return result;
    }
}
