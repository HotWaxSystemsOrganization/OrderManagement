package com.mayank.order.services;

import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.*;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.ServiceUtil;
import org.apache.ofbiz.entity.Delegator;

import java.sql.Timestamp;
import java.util.*;

public class FindOrderService {

    public static Map<String, Object> findOrderService(DispatchContext dctx, Map<String, Object> context) {
        Delegator delegator = dctx.getDelegator();
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String orderId = (String) context.get("orderId");
        String customerName = (String) context.get("customerName");
        Timestamp orderDateFrom = (Timestamp) context.get("orderDateFrom");
        Timestamp orderDateTo = (Timestamp) context.get("orderDateTo");
        String statusId = (String) context.get("statusId");
        String paymentStatusId = (String) context.get("paymentStatusId");
        String address1 = (String) context.get("address1");
        String address2 = (String) context.get("address2");
        String city = (String) context.get("city");
        String stateProvinceGeoID = (String) context.get("stateProvinceGeoID");
        String countryGeoID = (String) context.get("countryGeoID");
        String postalCode = (String) context.get("postalCode");
        List<EntityCondition> conditions = new ArrayList<>();
        if (UtilValidate.isNotEmpty(orderId)) {
            conditions.add(EntityCondition.makeCondition("orderId", EntityOperator.LIKE, "%" + orderId + "%"));
        }
        if (UtilValidate.isNotEmpty(customerName)) {
            conditions.add(EntityCondition.makeCondition("firstName", EntityOperator.LIKE, "%" + customerName + "%"));
            conditions.add(EntityCondition.makeCondition("lastName", EntityOperator.LIKE, "%" + customerName + "%"));
        }
        if (orderDateFrom != null) {
            conditions.add(EntityCondition.makeCondition("orderDate", EntityOperator.GREATER_THAN_EQUAL_TO, orderDateFrom));
        }
        if (orderDateTo != null) {
            conditions.add(EntityCondition.makeCondition("orderDate", EntityOperator.LESS_THAN_EQUAL_TO, orderDateTo));
        }
        if (UtilValidate.isNotEmpty(statusId)) {
            conditions.add(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, statusId));
        }
        if (UtilValidate.isNotEmpty(paymentStatusId)) {
            conditions.add(EntityCondition.makeCondition("paymentStatusId", EntityOperator.EQUALS, paymentStatusId));
        }
        if (UtilValidate.isNotEmpty(address1)) {
            conditions.add(EntityCondition.makeCondition("address1", EntityOperator.LIKE, "%" + address1 + "%"));
        }
        if (UtilValidate.isNotEmpty(address2)) {
            conditions.add(EntityCondition.makeCondition("address2", EntityOperator.LIKE, "%" + address2 + "%"));
        }
        if (UtilValidate.isNotEmpty(city)) {
            conditions.add(EntityCondition.makeCondition("city", EntityOperator.LIKE, "%" + city + "%"));
        }
        if (UtilValidate.isNotEmpty(stateProvinceGeoID)) {
            conditions.add(EntityCondition.makeCondition("stateProvinceGeoID", EntityOperator.LIKE, "%" + stateProvinceGeoID + "%"));
        }
        if (UtilValidate.isNotEmpty(countryGeoID)) {
            conditions.add(EntityCondition.makeCondition("countryGeoID", EntityOperator.LIKE, "%" + countryGeoID + "%"));
        }
        if (UtilValidate.isNotEmpty(postalCode)) {
            conditions.add(EntityCondition.makeCondition("postalCode", EntityOperator.LIKE, "%" + postalCode + "%"));
        }
        EntityCondition finalCondition = EntityCondition.makeCondition(conditions, EntityOperator.AND);
        try {
            List<GenericValue> orders = delegator.findList("FindOrderView", finalCondition, null, null, null, false);
            result.put("orders", orders);
            result.put("totalRecords", orders.size());
            context.putAll(result);
        } catch (GenericEntityException e) {
            return ServiceUtil.returnError("Error retrieving orders: " + e.getMessage());
        }
        return result;
    }
}
