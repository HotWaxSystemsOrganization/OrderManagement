package com.mayank.order.services;

import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.ServiceUtil;
import org.apache.ofbiz.base.util.UtilMisc;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreateOrder {
    public static Map<String, Object> createOrder(DispatchContext dctx, Map<String, Object> context) {
        Delegator delegator = dctx.getDelegator();
        String customerId = (String) context.get("customerId");
        String productId = (String) context.get("productId");
        BigDecimal quantity = (BigDecimal) context.get("quantity");
        String paymentMethodId = (String) context.get("paymentMethodId");
        String paymentMethodTypeId = (String) context.get("paymentMethodTypeId");
        String address1 = (String) context.get("address1");
        String address2 = (String) context.get("address2");
        String city = (String) context.get("city");
        String stateProvinceGeoID = (String) context.get("stateProvinceGeoID");
        String countryGeoID = (String) context.get("countryGeoID");
        String postalCode = (String) context.get("postalCode");
        Timestamp now = UtilDateTime.nowTimestamp();
        Map<String, Object> result = ServiceUtil.returnSuccess();
        GenericValue party = null;
        try {
            party = delegator.findOne("Party", UtilMisc.toMap("partyId", customerId), false);
        } catch (GenericEntityException e) {
            return ServiceUtil.returnError("customer not exist : " + e.getMessage());
        }
        if (party == null) {
            return ServiceUtil.returnError("Customer not found.");
        }
        GenericValue product = null;
        try {
            product = delegator.findOne("Product", UtilMisc.toMap("productId", productId), false);
        } catch (GenericEntityException e) {
            return ServiceUtil.returnError("Order Item product not exist : " + e.getMessage());
        }
        if (product == null) {
            return ServiceUtil.returnError("Customer not found.");
        }
        GenericValue payment = null;
        try {
            payment = delegator.findOne("PaymentMethod", UtilMisc.toMap("paymentMethodId", paymentMethodId), false);
        } catch (GenericEntityException e) {
            return ServiceUtil.returnError("Error finding customer: " + e.getMessage());
        }
        if (payment == null) {
            return ServiceUtil.returnError("Payment Method not found.");
        }

        String orderId = delegator.getNextSeqId("OrderHeader");
        GenericValue orderHeader = delegator.makeValue("OrderHeader", UtilMisc.toMap(
                "orderId", orderId,
                "statusId", "ORDER_CREATED",
                "orderDate",now,
                "entryDate",now
        ));
        try {
            delegator.create(orderHeader);
        } catch (GenericEntityException e) {
            return ServiceUtil.returnError("Error creating order header: " + e.getMessage());
        }

        String orderItemSeqId=delegator.getNextSeqId("OrderItem");
        GenericValue orderItem = delegator.makeValue("OrderItem", UtilMisc.toMap(
                "orderId", orderId,
                "orderItemSeqId", orderItemSeqId,
                "productId", product,
                "quantity", quantity
        ));
        try {
            delegator.create(orderItem);
        } catch (GenericEntityException e) {
            return ServiceUtil.returnError("Error creating order item: " + e.getMessage());
        }
        String contactMechId = delegator.getNextSeqId("ContactMech");
        GenericValue contactMech = delegator.makeValue("ContactMech", UtilMisc.toMap(
                "contactMechId", contactMechId,
                "contactMechTypeId", "POSTAL_ADDRESS"
        ));
        try{
            delegator.create(contactMech);
        } catch (GenericEntityException e) {
            return ServiceUtil.returnError("Error creating contact mech: " + e.getMessage());
        }
        GenericValue postal = delegator.makeValue("PostalAddress",UtilMisc.toMap(
                "contactMechId",contactMechId,
                "address1",address1,
                "address2", address2,
                "city",city,
                "stateProvinceGeoID",stateProvinceGeoID,
                "countryGeoID",countryGeoID,
                "postalCode",postalCode
        ));
        try {
            delegator.create(postal);
        } catch (GenericEntityException e) {
            return ServiceUtil.returnError("Error creating postal address: " + e.getMessage());
        }
        GenericValue ordercontact = delegator.makeValue("OrderContactMech",UtilMisc.toMap(
                "orderId",orderId,
                "contactMechId", contactMechId,
                "contactMechPurposeTypeId","SHIPPING_LOCATION"
        ));
        try{
            delegator.create(ordercontact);
        } catch (GenericEntityException e) {
            return ServiceUtil.returnError("Error creating order contact mech: " + e.getMessage());
        }
        GenericValue payments = delegator.makeValue("OrderPaymentPreference", UtilMisc.toMap(
                "orderPaymentPreferenceId",delegator.getNextSeqId("OrderPaymentPreference"),
                "orderId", orderId,
                "orderItemSeqId", orderItemSeqId,
                "paymentMethodTypeId", paymentMethodTypeId,
                "paymentMethodId", paymentMethodId,
                "statusId","PAYMENT_NOT_RECEIVED"
        ));
        try {
            delegator.create(payments);
        } catch (GenericEntityException e) {
            return ServiceUtil.returnError("-----------------------------------Error creating payment: " + e.getMessage());
        }
        result.put("orderId", orderId);
        context.putAll(result);
        return result;
    }
}
