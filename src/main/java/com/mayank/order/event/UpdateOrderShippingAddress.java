//package com.mayank.order.event;
//
//import org.apache.ofbiz.entity.Delegator;
//import org.apache.ofbiz.entity.condition.EntityCondition;
//import org.apache.ofbiz.service.DispatchContext;
//import org.apache.ofbiz.service.LocalDispatcher;
//import org.apache.ofbiz.service.GenericServiceException;
//import org.apache.ofbiz.service.ServiceUtil;
//import org.apache.ofbiz.entity.GenericEntityException;
//import org.apache.ofbiz.entity.GenericValue;
//import org.apache.ofbiz.webapp.event.EventUtil;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Map;
//import java.util.HashMap;
//
//
//public class UpdateOrderShippingAddress {
//
//    public static String updateOrderShippingAddressEvent(HttpServletRequest request, HttpServletResponse response) {
//        Delegator delegator = (Delegator) request.getAttribute("delegator");
//        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
//        GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
//
//        try {
//            // Collect request parameters
//            String newShippingAddress = request.getParameter("shippingAddress"); // JSON map expected
//            EntityCondition entityCondition = EntityCondition.makeCondition();
//
//            // Invoke the updateOrderShippingAddress service
//            Map<String, Object> result = dispatcher.runSync("updateOrderShippingAddress", serviceContext);
//            if (ServiceUtil.isError(result)) {
//                request.setAttribute("errorMessage", ServiceUtil.getErrorMessage(result));
//                return "error";
//            }
//
//            return "success";
//
//        } catch (GenericServiceException e) {
//            request.setAttribute("errorMessage", "Error updating shipping address: " + e.getMessage());
//            return "error";
//        }
//    }
//}
