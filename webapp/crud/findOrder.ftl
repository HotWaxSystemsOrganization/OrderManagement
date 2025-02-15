 <form method="post" action="<@ofbizUrl>/findOrder</@ofbizUrl>">
     <table>
         <tr>
             <td><label>Order ID:</label></td>
             <td><input type="text" name="orderId" value="${parameters.orderId!}"/></td>
         </tr>
         <tr>
             <td><label>Customer Name:</label></td>
             <td><input type="text" name="customerName" value="${parameters.customerName!}"/></td>
         </tr>
         <tr>
             <td><label>Order Status:</label></td>
             <td>
                 <select name="orderStatus">
                     <option value="">-- Select Status --</option>
                     <option value="ORDER_APPROVED">Approved</option>
                     <option value="ORDER_COMPLETED">Completed</option>
                     <option value="ORDER_CANCELLED">Cancelled</option>
                 </select>
             </td>
         </tr>
         <tr>
             <td><label>Payment Status:</label></td>
             <td>
                 <select name="paymentStatus">
                     <option value="">-- Select Status --</option>
                     <option value="PAYMENT_RECEIVED">Received</option>
                     <option value="PAYMENT_PENDING">Pending</option>
                 </select>
             </td>
         </tr>
         <tr>
             <td><label>Address1:</label></td>
             <td><input type="text" name="address1" value="${parameters.address1!}"/></td>
         </tr>
         <tr>
             <td><label>Address2:</label></td>
             <td><input type="text" name="address2" value="${parameters.address2!}"/></td>
         </tr>
         <tr>
             <td><label>city:</label></td>
             <td><input type="text" name="city" value="${parameters.city!}"/></td>
         </tr>
         <tr>
             <td><label>Order Date From:</label></td>
             <td><input type="date" name="fromDate" value="${parameters.fromDate!}"/></td>
         </tr>
         <tr>
             <td><label>Order Date To:</label></td>
             <td><input type="date" name="toDate" value="${parameters.toDate!}"/></td>
         </tr>
         <tr>
             <td colspan="2">
                 <input type="submit" value="Search Orders"/>
             </td>
         </tr>
     </table>
 </form>
